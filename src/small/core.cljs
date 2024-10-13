(ns small.core
  (:refer-clojure :exclude [update-vals])
  (:require
   [small.util :as util :refer [pprint js-import promise? promisify log clog parse-ident now random-id random]]
   #_[small.promesa :as p']
   [reagent.core :as r]
   [clojure.string :as s]
   [clojure.edn :as edn]
   [clojure.walk :as walk]
   [promesa.core :as p]
   [sci.core :as sci]
   [sci.impl.vars :as scivars]
   ["mqtt" :as mqtt]
   [shadow.cljs.modern :refer (js-await)]
   [shadow.esm :refer (dynamic-import)]
   [shadow.esm :as esm]
   [zones.core :as zones :include-macros true]
   [goog.object]))

(defonce state (r/atom nil))

(defonce query-context-sym (gensym (str "qctx" (subs (str (random-id)) 1))))

#_(def ^:dynamic *node-context* {:example :base1})
(def *query-context {:example :base})


(defn stub [id & args]
  (prn :TODO-emitting-eval-request id args))


(defn stateful
  "Eval thunk once, storing return value in node state under key.
   Resolves to original return value without reevaluating expr on subsequent calls."
  [!state id thunk]
  (let [state @!state]
    (if (contains? (::stateful state) id)
      (do
        (get-in state [::stateful id]))
      (let [result (thunk)]
        (swap! !state assoc-in [::stateful id] result)
        result))))


(defn make-ref-cache
  "bidirectional weak reference cache
   weakmap: object -> id
   map: id -> weakref object"
  []
  (let [wm (js/WeakMap.)
        ids (atom {})]
    [wm ids]))

(defonce ref-cache (make-ref-cache))

; problem: white it's being serialized+transfered+inspected+maybe-returned
; we need to hold on the ref.
; NO WE DONT. just use a weakref as the map value, may be nil.
; periodically clean keys of cljs map


(defn insert!
  [[^js/WeakMap wm ids] obj]
  (let [k (random-id)]
    #_(prn ::insert k (count @ids))
    (swap! ids assoc k (js/WeakRef. obj))
    (.set wm obj k)
    k))

(defn upsert!
  "insert obj into cache c, returning a handle
   (returns same handle if same obj already exists)"
  [ref-cache obj]
  (let [[^js/WeakMap wm ids] ref-cache]
    (if (.has wm obj)
      (do
        #_(prn ::cache-hit (count @ids))
        (.get wm obj))
      (insert! ref-cache obj))))

(defn obj-cached? [[^js/WeakMap wm ids] obj]
  (.has wm obj))

(def clj-val?
  (some-fn number? string? keyword? boolean? nil?
           seq? coll? map? vector? list? set? ident?
           tagged-literal? uuid? inst?))

(def unquoted-ok? (some-fn number? string? keyword? boolean? nil?
                           tagged-literal? uuid? inst?))

(defn scivar? [x]
  (satisfies? scivars/IVar x))

(defn queue? [x]
  (= (type x) cljs.core/PersistentQueue))

(defn error? [x]
  (= (type x) js/Error))



(defn tag [v]
  (cond ; todo: handle atom
    (or (var? v)
        (scivar? v)) 'var
    (queue? v) 'queue ; needs to be before clj-val
    (error? v) 'error
    (clj-val? v) nil
    (promise? v) 'promise
    (array? v) nil ; don't try to cache js arrays, they'll roundtrip just fine: #js []

    (fn? v) 'fn
    (object? v) 'object
    ; cave: error #obj :d73238e78 is not ISeqable
    ; because everything's an object (incl. clj-vals) there's probably some type predicate missing above
    (= "object" (util/typeof v)) 'object
    :else nil))


(comment
  (walk-serializable [#queue []] identity)
  (serialize-value (tag #queue []) #queue []))

(declare walk-serializable)

(defmulti serialize-value (fn [tag v] tag))

(defmethod serialize-value 'queue [t v republish-fn]
  #_(clog :serializing-var t v)
  (tagged-literal 'queue (walk-serializable (vec v) republish-fn)))

(defmethod serialize-value 'error [t v republish-fn]
  #_(clog :serializing-var t v)
  (tagged-literal 'error (pr-str v)))

(defmethod serialize-value 'var [t v republish-fn]
  #_(clog :serializing-var t v)
  (tagged-literal 'var (walk-serializable [(upsert! ref-cache v) (symbol v)] republish-fn)))

(defmethod serialize-value 'promise [t v republish-fn]
  (let [[^js/WeakMap wm ids] ref-cache]
    (if-not (obj-cached? ref-cache v)
      (let [id (random-id)
            weakref (js/WeakRef. v)]
        (swap! ids assoc id {:weakref weakref
                             :meta [:pending]})
        (.set wm v id)
        (-> v ; attach handlers only once
            (.then (fn [result] ; save promise result in cache
                     #_(clog :serialized-promise-then id result)
                     (swap! ids assoc-in [id :meta] [:fulfilled result])
                     (when republish-fn
                       #_(clog "republishing result" id result republish-fn)
                       (republish-fn)) ; enclosing value (entire q result)
                     result ; pass on result to any further .then handlers
                     ))
            (.catch (fn [err]
                      #_(clog :serialized-promise-caught id err)
                      (swap! ids assoc-in [id :meta] [:rejected err])
                      (when republish-fn
                        #_(clog "republishing err" id err republish-fn)
                        (republish-fn)) ; enclosing value (entire q result)
                      (throw err) ; rethrow for further .catch handlers
                      )))
        (tagged-literal 'promise [id :pending]))
      (do ; retrieve promise result, if any
        (let [id (.get wm v)
              [status result] (get-in @ids [id :meta])]
          (case status
            :pending (tagged-literal 'promise [id :pending])
            :fulfilled (tagged-literal 'promise [id :fulfilled (walk-serializable result republish-fn)])
            :rejected (tagged-literal 'promise [id :rejected (walk-serializable result republish-fn)])

            :XXX-unknown-state))))))


(defmethod serialize-value nil [t v] v)

(defmethod serialize-value :default [tag-symbol v]
  (tagged-literal tag-symbol (upsert! ref-cache v)))



(defn walk-serializable
  "Replaces unserializable values (fns, js-objects)
   with `#handle :random-key-id` and stores the value in a WeakMap.
   Serializing the same object will resolve to the same key (identity equality)"
  [v republish-fn]
  (walk/postwalk #(serialize-value (tag %) % republish-fn)
                 v))

(defn serialize
  ([v] (serialize v nil))
  ([v republish-fn]
   (util/pprint (walk-serializable v republish-fn))))

(defn quoted-serialize [v]
  (let [v' (walk-serializable v nil)
        printed (util/pprint v')]
    (if (unquoted-ok? v')
      printed
      (str "'" printed))))


(defn deref!
  "Resolved to original value when handle orginated from this node,
   else, when derefing foreign handle returns #remote id"
  [tag id]
  (let [[^js/WeakMap wm ids] ref-cache
        ^js/WeakRef wr (get-in @ids [id :weakref])]
    (if wr
      (.deref wr) ; may return nil when obj was gc'd since serialization
      (tagged-literal tag id))))

(def readers
  {'promise (fn [id] (deref! 'promise id))
   'error (fn [id] (deref! 'error id))
   'var (fn [id] (deref! 'var id))
   'object (fn [id] (deref! 'object id))
   'fn (fn [id] (deref! 'fn id))

   '> (fn [form]
        (prn :xxx (pprint form))
        (if (list? form)
          (first form)
          form))})

(defn deserialize
  [st]
  (edn/read-string
   {:readers readers}
   st))

(defn get-node-context []
  (merge (zones/get node-context*)
         ; default context is not changeable, merge in last
         (select-keys @state #{:proj :node})))

(defn set-node-context [m f]
  (zones/binding [node-context* (merge (get-node-context) m)]
    #_(prn "set node context" (get-node-context))
    (f)))


(defn publish
  ([edn] (publish edn nil))
  ([edn republish-fn]
   (-> edn
       (merge
        {:from (:node @state)
         :node-context (get-node-context)
         :t (now)})
       #_(update :value serialize)
       ((:publish @state) republish-fn))))

#_#_(defn print [& strs]
      (apply js/console.log strs)
      (publish {:type :print
                :strs strs}))

  (defn print-error [& strs]
    (apply js/console.error strs)
    (publish {:type :print-error
              :strs strs}))

(enable-console-print!)

#_#_(alter-var-root *print-fn* (constantly print))
  (alter-var-root *print-err-fn* (constantly print-error))

(sci/alter-var-root sci/print-fn (constantly *print-fn*))
(sci/alter-var-root sci/print-err-fn (constantly *print-err-fn*))

(defn update-vals [m f]
  (into {}
        (map (fn [[k v]]
               [k (f v)]) m)))


(comment
  (every? (set [:a :b :c :d]) #{:a :b}))


(defn publish-query-result [id result meta owo]
  #_(prn :publish-query-result id :nc (get-node-context) :qc owo (every? (set (get-node-context)) owo))
  (when
   (if owo ; publish only when query context matches
     (every? (set (get-node-context)) owo)
     true)

    #_(prn :publish-q-result id owo)

    (publish
     (merge
      {:type :query-result
       :id id
       :vsn (:vsn @state)
       :node-context (get-node-context)
       :query-context owo
       :value result}
      meta)
   ; republish fn when serialized values change (eg. promise resolve)
     #(publish-query-result id result {:republish? true} owo))))


; cmd+f keywords: ?macro qmacro macro? ???
; [editor ux idea: tag fns, find+edit fns by tag search]
(defn ^:sci/macro ? [&form &env id value & xform-and-val] ; xform is ignored on node; only eval'd in the context of the editor
  ;; (prn :?macroexpansion :f &form :e &env :id id :v value :evv evv)
  #_(prn "inside-? 1" (get-node-context))

  (when-not (keyword? id)
    (throw (js/Error. (str "? missing id in form: "
                           (pr-str &form)
                           " - did you type out the ? expression -- use the keyboard shortcut alt+? to wrap an expression with a query"
                           " - if using the query macro inside a threading expression, make sure to match the symbols -> ?> and ->> ?>>"))))

  (case (count xform-and-val)
    2 `(let [result# ~value]
         #_(prn "inside-? 2" (~get-node-context))
         (~publish-query-result ~id result# {:xform '~(first xform-and-val)
                                             :xform-meta '~(meta (first xform-and-val))} ~query-context-sym)
         result#)
    1 `(let [result# ~value]
         (~publish-query-result ~id result# nil ~query-context-sym)
         result#)
    value))

(defn ^:sci/macro ?> [&form &env value id form & xform-and-val]
  (apply list '? id `(-> ~value ~form) xform-and-val))

(defn ^:sci/macro ?>> [&form &env id form & xform-and-val]
  (apply list '? id `(->> ~(last xform-and-val) ~form) (butlast xform-and-val)))



; paused variants
(defn ^:sci/macro ?- [&form &env id value & xform-and-val]
  value)
(defn ^:sci/macro ?>- [&form &env value id form & xform-and-val]
  `(-> ~value ~form))
(defn ^:sci/macro ?>>- [&form &env id form & xform-and-val]
  `(->> ~(last xform-and-val) ~form))




(defn ! [& forms]
  (identity (first forms)))

(defn nuke!! [& nodes]
  (publish {:type :nuke
            :nodes (into #{} nodes)}))

(defn every [ms f & args]
  (fn [caps]
    ((:rerun caps))
    (->
     (p/delay ms)
     (p/then #(apply f caps args)))))

(defn filter-caps [caps]
  ; todo: better way to separate actual caps from node state,
  ; but keep malleability of internals
  ; sci ctx breaks serialization with weird error:
  ;   No protocol method HasName.getName promesa.core
  (dissoc caps :ctx '?))

(defn matches-node? [nids n]
  (cond
    (nil? nids) nil
    (= nids n) true ; keyword, string, number, ...?
    (set? nids) (contains? nids n)
    (map? nids) (get nids n)
    (list? nids) (some #(matches-node? % n) nids)
    :else nil ; don't throw
    ))


(def ^:dynamic *cleanup*
  (fn [f] (throw :ERROR-unbound-cleanup-called)))

(defn superv [state filter-state goal-procs]
  ; todo validate goal-procs data shape

  (let [goal-procs (if (fn? goal-procs)
                     {:p0 goal-procs} ; single root process
                     goal-procs)
        !new-procs (atom nil)] ; hack to pull new-procs out from swap! scope
    (swap! ; save new-fns before swap, otherwise they wouldn't be new anymore
     state
     update
     :procs
     (fn [procs]
       (let [new-procs
             (->> goal-procs ; new procs: not currently running
                  (filter (fn [[id f]]
                            (and id f (fn? f)
                                 (not (contains? procs id)))))
                  (map (fn [[id f]] [id {:f f}])))
             updated-procs
             (->> procs
                  (map
                   (fn [[id p]]
                     (if (contains? goal-procs id)
                       (do
                         #_(prn ::updating-f id)
                         [id (-> p
                                 (dissoc :terminate?) ; this usually won't dissoc anything, except to cancel termination when proc gets updated with same id and new f before termination is complete, then same loop will pick up new fn and just continue
                                 (assoc :f (get goal-procs id)))])
                       (do
                         (prn ::terminating id)
                         [id (assoc p :terminate? true)])))))
             procs' (into {} (concat updated-procs new-procs))]
         (reset! !new-procs new-procs)
         procs')))

    #_(prn ::np @!new-procs)

    (doall ; start new fns (currently-running fns will terminate if no longer needed)
     (map
      (fn [[id {:keys [f]}]]
        (let [proc-state (r/atom nil)]

          #_(add-watch proc-state :supervw (fn [& xs] (prn :VW xs)))


          (swap! state update-in [:procs id] assoc :state proc-state)
          (p/loop [f f]
            #_(prn ::procs @procs)
            (p/let
             [rerun
              (fn rerun
                ([] (rerun true nil))
                ([after-ms] (rerun after-ms nil))
                ([after-ms rerun-arg]
                 #_(prn ::rerun-requested-for id)
                 (swap! state update-in [:procs id] merge {:rerun after-ms
                                                           :rerun-arg rerun-arg})
                 true))

              run-cleanup!
              (fn []
                #_(prn :running-cleanup id (get-in @state [:procs id :cleanup]))
                (doall
                 (for [f (get-in @state [:procs id :cleanup])]
                   (try
                     (prn :running-cleanup id :t (type f) :f (fn? f) :l (list? f) :n (nil? f) f)
                     (f)
                     (prn :ran-cleanup id f)
                     (catch :default e
                       (prn ::cleanup-failed e))))))

              *reg-cleanup ; to register cleanup handlers
              (fn [f]
                ; todo: dedupe by static id + sort?
                (prn :registering-cleanup f)
                (swap! state update-in [:procs id :cleanup] conj f))

              caps
              (-> @state
                  filter-state
                  (merge
                   {:pid id
                    :rerun rerun
                    :state proc-state
                    :import dynamic-import
                    :serialize serialize
                    :deserialize deserialize
                    :fetch js/globalThis.fetch
                    :stateful (partial stateful state) ; to avoid shadowing with nil (it's a global binding for now, but probably should be passed in through caps only)
                    :cleanup *reg-cleanup}))

              result
              (try
                (->
                 (p/promise
                  #_(f caps)

                  (binding [*cleanup* *reg-cleanup]
                    ; can't use set-node-context here because fn must be bound which only works inside sci
                    (zones/binding [node-context* (merge (get-node-context) {:pid id})]
                      (f ;; here the actual user supplied process fn is called
                       caps
                       (get-in @state [:procs id :rerun-arg])))))
                 (p/catch
                  (fn [e]
                    (prn ::pcatch e)
                    (run-cleanup!)
                    (js/console.error e.message)
                    (swap! state update :procs dissoc id) ; todo: terminate or rerun on failure?
                    e)))
                (catch :default e
                  (prn ::catch e)
                  (run-cleanup!)
                  (js/console.error e.message)
                  (swap! state update :procs dissoc id) ; todo: terminate or rerun on failure -- or delegate to some supervisor/strategy given in proc value map
                  e))]

              (let [{:keys [f terminate? rerun] :as p} (get-in @state [:procs id])]
                (cond
                  (and p f rerun (not terminate?))
                  (p/do
                    (if (number? rerun)
                      (p/delay (max 16 rerun))
                      (p/delay 250))
                    #_(prn ::rerunning-process id :p p :rerun rerun :terminate? terminate?)
                    (swap! state update-in [:procs id] assoc :rerun false)
                    (p/recur f))

                  ;; (and p f (not terminate?)) ; todo: not sure what to do in case of run-once but dont terminate (reagent case)
                  ; if we do nothing, then the process looks like it's still running
                  ; if we kill it, we can't run cleanup handlers on replacement, decl proc removal, or node shutdown
                  ; maybe we need to have a special case for run-once? or another state :done?
                  ;; nil

                  :else
                  (p/do
                    #_(prn ::dissocing-process id :p p :rerun rerun :terminate? terminate?)
                    (run-cleanup!)
                    (swap! state update :procs dissoc id)
                    result)))))))
      @!new-procs)))
  goal-procs)

(defn unbind-fns [fns]
  (cond
    (and (fn? fns)
         (:unbound-fn (meta fns)))
    (:unbound-fn (meta fns))

    (map? fns)
    (into {} (map (fn [[k v]] [k (unbind-fns v)]) fns))

    (vector? fns)
    (into [] (map unbind-fns fns))

    :else fns))

(defn node [nids tasks]
  (if (matches-node? nids (:node @state))
    (superv
     state
     #'filter-caps
     (unbind-fns tasks))
    (constantly nil)))

(defn editor
  "shortcut to define generic editor procs without hard-coding a editor node id"
  [tasks]
  (when (:editor? @state)
    (node (:node @state) tasks)))

(defn fwd [publics]
  (reduce
   (fn [ns-map [var-name var]]
     (let [m (meta var)
           no-doc (:no-doc m)
           doc (:doc m)
           arglists (:arglists m)]
       (if no-doc
         ns-map
         (assoc ns-map var-name
                (sci/new-var (symbol var-name) @var
                             (cond-> {:ns (:ns m)
                                      :name (:name m)}
                               (:macro m) (assoc :macro true)
                               doc (assoc :doc doc)
                               arglists (assoc :arglists arglists)))))))
   {}
   publics))

(defn hi [{:keys [node rerun state]}]
  (prn (str "hello world " @state " from node " node))
  (swap! state inc)
  (rerun 3000))


(defn ^:sci/macro
  do!
  [&form &env x]
  (list 'p/do!))



(defn ^:sci/macro with-context [&form &env bindings & forms]
  `(~set-node-context ~bindings (fn []
                                  #_(~check-ctx 2)
                                  ~@forms)))


(defn ^:sci/macro context [&form &env bindings & forms]
  `(let [~query-context-sym ~bindings] ~@forms))


(defn set-zone! [zsym]
  (set! zones/default-zone zsym))

(defn get-zone []
  zones/default-zone)

; adapted from https://github.com/binaryage/cljs-zones/blob/master/src/lib/zones/core.clj
(defn ^:sci/macro bound-fn [&form &env & fntail]
  (let [f# `(fn ~@fntail)
        call-site-zone-sym (gensym "call-site-zone-")
        last-active-zone-sym (gensym "last-active-zone-")
        bound-fn-name-sym (symbol (str "fn-bound-to-" call-site-zone-sym))]
    `(let [~call-site-zone-sym (~get-zone)]
       (with-meta
         (fn ~bound-fn-name-sym [& args#]
           (let [~last-active-zone-sym (~get-zone)]
             (~set-zone! ~call-site-zone-sym)
             (try
               (apply ~f# args#)
               (finally
                 (~set-zone! ~last-active-zone-sym)))))
         {:bound-fn? true
          :unbound-fn ~f#}))))

(defn then [^js o f]
  (.then o (fn [x]
             (f x))))

(defn promise [x]
  (js/Promise. (fn [resolve] (resolve x))))

(defn ^:sci/macro unbound-fn [&form &env & fntail]
  `(clojure.core/fn ~@fntail))

(defn make-ctx []
  (sci/init
   {:classes {:allow :all
              'js js/globalThis}
    :namespaces {'d (fwd (ns-publics 'datascript.core))
                 'edn (fwd (ns-publics 'clojure.edn))
                 's (fwd (ns-publics 'clojure.string))
                 'walk (fwd (ns-publics 'clojure.walk))
                 'math (fwd (ns-publics 'clojure.math))
                 'p (fwd (ns-publics 'promesa.core))
                 'zp (fwd (ns-publics 'zprint.core))

                 #_{'globalThis js/globalThis
                    'Promise js/Promise
                    'setTimeout js/setTimeout
                    'Date js/Date}
                 #_#_'p p'}
    :readers readers
    :bindings
    {'ø nil ; single-character nil (alt-o) for live coding
     'await 'js-await
     'do! do!
     '! #'!
     '? #'?
     '?> #'?>
     '?>> #'?>>

     '?- #'?- ; "paused" passthru variants
     '?>- #'?>-
     '?>>- #'?>>-

     'then then
     'promise promise
     'fn #'bound-fn ; haha!
     #_#_'fn* #'bound-fn ; reader fns #() read as fn*
     'unbound-fn #'unbound-fn
     'update-vals update-vals
     '** (fn [a b] (js/Math.pow a b))
     '-- nil ; todo block separator
     'typeof util/typeof
     'promise? util/promise?
     'atom r/atom
     'every every
     'object? object?
     'clj-val? clj-val?
     'scivar? scivar?
     'with-context #'with-context
     'context #'context
     query-context-sym nil ; to avoid unbound err since resolve is apparently a macro not a fn in cljs/sci?
     'history (fn [xs] (map :value xs))


     ; todo: decide if impure fns should be global?
     'now now
     'random-id random-id
     'random random
     'log log
     'clog util/clog
     'pprint util/pprint
     'node node
     'editor editor
     'hi hi
     'wait (fn [ms] (js/Promise. (fn [resolve]
                                   (js/setTimeout #(resolve true)
                                                  (or ms 500)))))

     ; todo: these bindings should probably only be passed to node fns
     'import dynamic-import
     'serialize serialize
     'deserialize deserialize
     'stateful (partial stateful state)
     'fetch js/globalThis.fetch
     'json util/json

     'nuke!! nuke!! ;; todo: some bindings should be privileged & only available in the editor
     }}))

(defn full-eval [doc]
  (swap! state assoc :ctx (make-ctx)) ; fresh context for full eval
  (try
    (let [result (sci/eval-string* (:ctx @state) doc)
          result (try (serialize result)
                      (catch :default err
                        (js/console.error "serialization failed after full eval" err)
                        (throw err)))]
      (util/clog "=>" result)
      (tap> result)
      {:result result})
    (catch :default ^js e
      (js/console.error "eval-error" e.message)
      {:error e})))

(defn live-eval [docs]
  (when-not (:ctx @state) ; in case full eval is first eval (eg after node restart)
    (swap! state assoc :ctx (make-ctx)))
  (try
    (let
     [result
      (sci/eval-string*
       (:ctx @state)
       ; todo: eval multiple live exprs separately for failure containment
       ; or not for more liveness (changing another block should trigger refresh of possibly related block)
       ; if i mark something as live, it should most definitely be live
       (s/join "\n" docs))]
      {:result result})
    (catch :default ^js e
      (js/console.error "eval-error" e.message)
      {:error e})))

(defn get-state []
  state)

(defn enqueue [q max-n x]
  (let [q (if (empty? q) #queue [] q)]
    (-> (if (<= max-n (count q))
          (nth (iterate pop q)
               (inc (- (count q) max-n)))
          q)
        (conj x))))

(defn on-query-result [{:keys [id value from vsn t xform xform-meta node-context query-context]}]
  (swap! state update-in ['? id]
         enqueue 10
         {:value value
          :node from
          :vsn vsn
          :xform (with-meta xform xform-meta)
          :node-context node-context
          :query-context query-context
          :t t})
  #_(let [xformed-value
          (try (let [xform-ctx
                     {:id id
                      :value value
                      :from from
                      :vsn vsn
                      :t t

                      :get-state get-state}
                     xformed-value
                     (if xform
                       (sci/eval-form
                        (:ctx @state)
                        `(~xform ~value ~xform-ctx))
                       value)
                     xformed-value (try (with-meta ; primitives cant have meta
                                          xformed-value
                                          {:from from
                                           :id id
                                           :t t
                                           :vsn vsn})
                                        (catch :default e xformed-value))]

                 xformed-value)
               (catch :default e value))]
      (swap! state update-in ['? id from] enqueue 5
             {:value value
              :vsn vsn
              :t t}))
  #_(prn :? (get @state '?)))


(defn query-results [id]
  (get-in @state ['? id]))

(defn last-recevied-query-result [qs]
  (->> qs last))


(defn transform-query-result [qs] ; qs map of node-id => #queue [{t value vsn xform}]}
  (let [last (last-recevied-query-result qs)]
    (if (:xform last)
      (let [xform-ctx qs
            ; apparently arity is strictly checked by sci here...
            ; maybe take just one arg?

            eval-form `(~(:xform last) (quote ~xform-ctx) ~(:value last))] ; todo: expose safer (serializable, non-recursively/infitely growing) subset of editor state to viz fn
        (try
          #_(clog :transform-query-result-eval (pr-str eval-form))
          (sci/eval-form
           (:ctx @state)
           eval-form)
          (catch :default e
            (js/console.error "transform-query-result" e)
            e)))
      (:value last))))


(defn nuke [{:keys [nodes]} !state]
  ; just in case when state is too broken
  (prn ::nuke-in-2s)
  (js/setTimeout
   (fn []
     (prn ::nuke-hard-after-timeout)
     (cond
       (exists? js/window.location.reload) (js/window.location.reload)
       (exists? js/process.exit) (js/process.exit 1)))
   4000)

  ; timeout to prevent infinite nuke loop when editor restart and re-emits nuke cmd
  (js/setTimeout
   #(when (:nuke @!state)
      (prn ::nuke)
      ((:nuke @!state)))
   2000))


(defn on-novelty [{:keys [type] :as novelty} !state]
  (try
    (case type
      :nuke (nuke novelty !state)
      :full-eval (full-eval (:source novelty))
      :live-eval (live-eval (:sources novelty))
      :query-result (on-query-result novelty))
    (catch :default e
      (prn :failed-at-novelty novelty)
      (js/console.error "failed to eval novelty" e))))

(defn connect! [ident caps]
  (let [[broker proj node] (log (parse-ident ident))
        ^js client (.connect mqtt broker)
        subscribe (promisify #(.subscribe client proj))
        publish
        (fn [val republish-fn]
          (.publish client proj
                    (serialize val republish-fn)))]

    (set! js/globalThis.state (fn [] (clj->js @state)))
    (set! js/globalThis.refcache
          (fn []
            (js/console.log "RC" (count (second ref-cache)))
            ref-cache))

    (.on client "message"
         (fn [_topic ^js binary]
           (try
             (let [novelty (->> (.toString binary)
                                deserialize)
                   nodes (into #{} (or (:nodes novelty) (:node novelty)))]

               (or
                (empty? nodes)
                (or (contains? nodes (:node @state))))

               (#'on-novelty novelty state))
             (catch :default e
               (js/console.error "failed to read-string novelty"
                                 (.-message e)
                                 (.toString binary))))))
    ; return publish fn after successful connection
    (js/Promise.
     (fn [resolve reject]
       (.subscribe
        client proj
        (fn [err s]
          (if err
            (reject err)
            (do
              (.publish client "presence" node)

              (prn ::caps (keys caps))

              (swap! state merge
                     caps
                     {:proj (keyword proj)
                      :node (keyword node)
                      :broker broker
                      :vsn 0
                      :publish publish})
              (resolve publish)))))))))


(comment
  (set! js/globalThis.state (fn [] (clj->js @state)))
  6654356789098079658435467890876543678N
  (def f1
    (sci/eval-string "(fn [x]
                      (prn :sci #js{} x))"))
  (f1 "aa")
  (f1 (p/delay 300))
  (tap> {:a 2}))
