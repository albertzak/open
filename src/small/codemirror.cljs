(ns small.codemirror
  (:require [small.core :as core]
            [small.editor :as editor]
            [small.ui :as ui]
            [small.util :as util :refer [clog]]
            [portal.web :as portal-web]
            [clojure.string :as s]
            [zprint.core :as zp]
            [shadow.cljs.modern :refer (defclass)]
            [promesa.core :as p]
            [reagent.core :as r]
            [applied-science.js-interop :as j]
            ["react" :refer [StrictMode]]
            ["react-dom/client" :refer [createRoot]]
            ["@codemirror/commands" :refer [history historyKeymap defaultKeymap]]
            ["@codemirror/language" :refer [syntaxTree foldGutter syntaxHighlighting HighlightStyle defaultHighlightStyle]]
            ["@codemirror/state" :refer [Text EditorState StateField StateEffect EditorSelection Extension StateCommand
                                         ChangeSet ChangeDesc TransactionSpec StrictTransactionSpec]]
            ["@codemirror/search" :refer [search searchKeymap highlightSelectionMatches]]
            ["@codemirror/view" :as view :refer [MatchDecorator Decoration DecorationSet EditorView ViewPlugin lineNumbers highlightActiveLine highlightActiveLineGutter dropCursor highlightSpecialChars]]
            ["./rainbowBrackets.js" :default rainbowBrackets]
            ["./codemirror.js" :refer [addWidget widgetPlugin hideIds highlightField QPlugin rewriteQResults jumpToNextLineWithPrefix jumpToPrevLineWithPrefix toggleBooleanUnderCursor modifyNumberUnderCursor toggleClojureFormComment toggleClojureDoubleFormComment toggleTopLevelBang toggleQueryExpression toggleLineComment]]
            ["./colorpicker.js" :refer [colorPicker]]
            #_["@uiw/codemirror-extensions-color/esm/index.js" :refer [color]]
            #_["./colorpicker2.js" :refer [color]]
            [nextjournal.clojure-mode :as cm-clj]
            [nextjournal.clojure-mode.test-utils :as test-utils]
            [nextjournal.clojure-mode.node :as n]
            [nextjournal.clojure-mode.extensions.eval-region :as eval-region]
            [nextjournal.clojure-mode.extensions.formatting :as formatting]
            [clojure.edn :as edn]))

(defonce !view (r/atom nil)) ; CM EditorView


;;
;; state change -> notifies cm to maybe update regions
;; cm scrolls -> get latest values from state
;;


(def example-?-state
  {:aa
   {:editor {:value 3, :vsn 0, :t "2024-01-10T22:33:19.409Z"}
    :web {:value 3, :vsn 0, :t "2024-01-10T22:33:20.410Z"}
    :bye {:value 3, :vsn 0, :t "2024-01-10T22:33:20.411Z"}}})


(defn latest-?-result [qs]
  (update-vals
   qs
   (fn [nodes]
     (last (sort-by :t (vals nodes))))))


(comment
  (latest-?-result example-?-state))



(defn walk-syntax-tree [v f]
  (let [!results (transient [])]
    (doall
     (for [range (.-visibleRanges v)]
       (do
         (.iterate
          (syntaxTree (.-state v))
          #js {:from (.-from range)
               :to (.-to range)
               :enter
               (fn [x]
                 (when-let [parsed (f x)]
                   (conj! !results parsed)))}))))
    (persistent! !results)))


(defn child [^js v ^js c]
  {:type (.-name c)
   :from (.-from c)
   :to (.-to c)

   #_#_#_#_#_#_
   :text (.sliceString (.. v -state -doc) (.-from c) (.-to c))
   :span [ (.-to c)]
   :indent (- (.-from c)
              (.-from (.lineAt (.. v -state -doc) (.-from c))))})

(defn doc-text-of [^js v {:keys [from to]}]
  (.sliceString (.. v -state -doc)from to))

(defn doc-indent-of [^js v {:keys [value id]}]
  (let [{:keys [from]} value
        line (.lineAt (.. v -state -doc) from)
        id-offset (if (s/includes? (.-text line) id) ;; dunno
                    (- (count (str id)))
                    0)]
    (prn (.-text line) id (s/includes? (.-text line) id))
    (- from
       (.-from line)
       id-offset)))

(defn tree-children [^js v ^js cursor]
  (when (.firstChild cursor)
    (butlast ; ignore ")" node
     (loop [children []]
       (if (.nextSibling cursor)
         (recur (conj children (child v cursor)))
         children)))))


(defn matches? [^js v
                [expected-type expected-text]
                {:keys [type] :as expr}]
  (and
   (or (= :* expected-type) (= type expected-type))
   (or (nil? expected-text) (= (doc-text-of v expr) expected-text))))

(defn shaped? [^js v shapes expr]
  (and
   (= (count expr) (count shapes))
   (every? true?
           (map (fn [s e]
                  (matches? v s e))
                shapes expr))))

(defn find-q-spans [^js v]
  (walk-syntax-tree
   v
   (j/fn [^:js {:keys [type from to node]}]
     (when (= (.-name type) "List")
       (let [cursor (.cursor node)
             children (tree-children v cursor)]
         ; value should be called result, it is what gets replaced
         (condp (partial shaped? v) children
           [["Operator" "?"]
            ["Keyword"]
            [:*]
            [:*]]
           {:type :?
            :id (keyword (subs (doc-text-of v (nth children 1)) 1))
            :value (nth children 3)}

           [["Operator" "?"]
            ["Keyword"]
            [:*]
            [:*] ; xform
            [:*]]
           {:type :?
            :id (keyword (subs (doc-text-of v (nth children 1)) 1))
            :xform (edn/read-string (doc-text-of v (nth children 3)))
            :value (nth children 4)}

           [["Operator" "?>"]
            ["Keyword"]
            [:*]
            [:*]]
           {:type :?>
            :id (keyword (subs (doc-text-of v (nth children 1)) 1))
            :value (nth children 3)}

           [["Operator" "?>"]
            ["Keyword"]
            [:*]
            [:*] ; xform
            [:*]]
           {:type :?>
            :id (keyword (subs (doc-text-of v (nth children 1)) 1))
            :value (nth children 4)}


           [["Operator" "?>>"]
            ["Keyword"]
            [:*]
            [:*]]
           {:type :?>>
            :id (keyword (subs (doc-text-of v (nth children 1)) 1))
            :value (nth children 3)}

           [["Operator" "?>>"]
            ["Keyword"]
            [:*]
            [:*] ; xform
            [:*]]
           {:type :?>>
            :id (keyword (subs (doc-text-of v (nth children 1)) 1))
            :value (nth children 4)}

           nil))))))

(defn indent [s in]
  (let [extra-indent (if (= "'" (first s)) " " "")] ; quoted value
    (->> (s/split-lines s)
         (map-indexed
          (fn [i line]
            (if (zero? i) line
                (str (.repeat " " in) extra-indent line))))
         (s/join "\n"))))

(defn q-changes [^js v qresults]
  (doall
   (for [q (find-q-spans v)
         :when (contains? qresults (:id q))]
     (let [ind (doc-indent-of v q)
           result (get qresults (:id q))
           xformed-result (core/transform-query-result result)
           xform-meta (merge (meta xformed-result)
                             (meta (:xform q))) ; reader meta on viz fn overrides meta returned by viz fn



           rendered (cond-> {}
                      (:?/block xform-meta) (merge {:block true})
                      (:?/block xform-meta) (merge {:hiccup xformed-result})

                      :else
                      (merge ; default replace pprint indented
                       {:insert
                        (-> xformed-result
                            (core/quoted-serialize)
                            (indent ind))}))]
       (merge
        q
        {:result result
         :xform-meta xform-meta
         :xformed-result xformed-result
         :from (:from (:value q))
         :to (:to (:value q))
         }
        rendered)))))


(def change-q (.define StateEffect))




(def q-statefield
  (.define
   StateField
   (j/lit
    {:create (fn [] (.-none Decoration))
     :update (fn [value tr]
               (let [value (.map value (.-changes tr))
                     effects (prim-seq (.-effects tr))]
                 (loop [effects effects
                        value value]
                   (let [e (first effects)]
                     (if e
                       (recur (next effects)
                              (if (.is e change-q)
                                (let [v (aget (.-value e) 0)]
                                  (.update
                                   value
                                   #js {:filter (fn [from to]
                                                  (not
                                                   (and (= (.-from v) from)
                                                        (= (.-to v) to))))
                                        :add #js [v]
                                        :sort true}))

                                value))
                       value)))))
     :provide (fn [f] (.from (.-decorations EditorView) f))})))

(def q-deco (.mark Decoration #js {:class "query-result"}))

(def id-deco (.replace Decoration #js {}))


(def id-matcher (MatchDecorator. #js {:regexp (js* "/ABC/g")
                                      :decoration (fn [match] (.replace Decoration))}))






(defn add-?-result-watch []
  (add-watch
   core/state :cm-?
   (fn [_ _ old new] ; this fires often (on any node state change!) be sure to debounce/check if expensive work is necessary
     (let [old (get old '?)
           new (get new '?)
           changed
           (->> new
                (remove (fn [[nk nv]]
                          (let [a (last nv)
                                b (last (get old nk))]
                            (and
                             (or
                              (= (:value a) (:value b))
                              (and (number? (:value a)) (number? (:value b))
                                   (js/isNaN (:value a)) (js/isNaN (:value b))))
                             (= (:xform a) (:xform b)))))) ; todo maybe perf check only value, xform
                (into {}))
           changes (when (seq changed)
                     (q-changes @!view changed))]

       #_(prn :?? new)
       (when (seq changes)
         (try
           (.dispatch @!view
                      (clj->js
                       {:changes (map (fn [{:keys [from to insert block]}]
                                        {:from from
                                         :to to
                                         :insert (if block ":>"
                                                     (or insert "_"))})
                                      changes)
                        :effects


                        (->> [(let [new-blocks
                                    (->> changes
                                         (map (fn [{:keys [from to id type xform xformed-result block] :as x}]

                                                (when block
                                                  #js {:from to
                                                       :to to ; sic
                                                       :x {:a :b}
                                                       :renderTo (fn [el]
                                                                   (clog "EL" el xformed-result)

                                                                   (.render
                                                                    (createRoot el)
                                                                    (r/as-element
                                                                     [:> StrictMode
                                                                      xformed-result])))})

                                                #_(when (or (meta xformed-result)
                                                            (meta xform))
                                                    {:id id :type type :from from :to to
                                                     :xformed-result xformed-result})))
                                         (filter identity))]

                                (when (seq new-blocks)
                                  (.of addWidget
                                       (clog :xxs (into-array new-blocks)))))



                           ; background of "read-only" results
                              (.of change-q
                                   (->> changes
                                        (filter :insert)
                                        (map (fn [{:keys [from to insert block]}]
                                               (.range q-deco from
                                                       (+ from (count (if block ":>" insert))))))
                                        clj->js))]
                             (filter identity)
                             (into-array))}))
           (catch :default e
             nil
             ; not sure why this triggers it: ! (? :bbe646a8 (** 2 #_10) 1024)
             #_(js/console.error "caught view plugin crash" e))))))))




(defn localstorage-key []
  js/window.location.hash)

(defn schedule-update [^js view]
  (let [doc (str (.. view -state -doc))]
    (js/localStorage.setItem (localstorage-key) doc)
    (editor/on-doc-changed doc)))

;; (defclass HandleUpdate
;;   (constructor [this] (super))
;;   Object
;;   (update
;;    [this ^js vu]
;;    (when (or (.-docChanged vu) ; on every keystroke/doc-change
;;              (.-viewportChanged vu)
;;              (not= (syntaxTree (.-startState vu))
;;                    (syntaxTree (.-state vu))))
;;      (if (= (str (.. vu -startState -doc))
;;             (str (.. vu -state -doc)))
;;        (prn :ignoring-view-update)
;;        (schedule-update (.-view vu))))))

(defn insert-random-id [^js vs]
  (let [id (str (util/random-id))
        from (.. vs -state -selection -main -from)
        to (.. vs -state -selection -main -to)
        pos' (+ to (count id))]
    (.dispatch
     vs
     #js {:selection #js {:anchor pos' :head pos'}
          :changes #js {:from from
                        :to to
                        :insert id}})))

(defn left [n] (when n (n/left n)))
(defn right [n] (when n (n/right n)))
(defn up [n] (when n (n/up n)))
(defn down [n] (when n (n/down n)))

(defn matches-structure?
  "Checks node names, empty string is wildcard"
  [structure node]
  {:example-structure ["(" "Operator" "Keyword" "" "" ")"]}
  (and
   node
   (every?
    true?
    (map
     (fn [expected-name node]
       (if (= "" expected-name)
         true
         (= expected-name (n/name node))))
     structure
     (take-while identity (iterate right node))))))

(defn text-of [state node]
  (and
   node
   (str (.slice (.-doc state) (n/start (n/range node)) (n/end (n/range node))))))

(defn cursor-pos [state]
  (.. state -state -selection -main -anchor))

(defn qmacro? [state start-node]
  (when-let [qmark (and start-node (not (n/top? start-node)) (right start-node))]
    (and
     (#{"?" "?>" "?>>"}  (text-of state qmark))
     (or
      (matches-structure? ; expr val
       ["(" "Operator" "Keyword" "" "" ")"] start-node)
      (matches-structure? ; expr xform val
       ["(" "Operator" "Keyword" "" "" "" ")"] start-node)))))


(defn node= [a b]
  (and
   (= (.-from a) (.-from b))
   (= (.-to a) (.-to b))))

(defn threading-parent-operator [state start-node]
  (let [start-node (if (#{"(" ")"} (.-name start-node))
                     (up start-node)
                     start-node)
        parent (and start-node (not (n/top? start-node))
                    (not (n/top? (up start-node)))
                    (-> start-node up down right))]
    (clog "prrrr" (text-of state (-> parent right right)))
    (clog :threading-parent
          (and
           start-node
           parent
           (not (node= (-> parent right) start-node)) ; first threading child should not be wrapped with ?> but is just a normal value
           (= "Operator" (.-name parent))
           (#{"->" "->>"} (text-of state parent)))))
  )

(defn toggle-query-emacro [range-fn ^js vs]
  (let [state (.-state vs)
        range (range-fn state)
        u (n/nearest-touching state (.-from range) -1)]

    (def state vs)
    (def u u); for repl debugging



    (->>
     (if-let [[qmacro queried-expr]
              (cond
                (qmacro? state u) ; whole qmacro selected
                (do
                  (clog "whole")
                  [(n/range (up u))
                   (n/range (right (right (right u))))])

                (qmacro? state (left (left (left u)))) ; just queried symbol
                (do
                  (clog "sym")
                  [(n/range (up (left (left (left u)))))
                   (n/range u)])

                (and
                 (#{"(" ")"} (.-name u))
                 (qmacro? state (down (up (up u))))) ; just queried expr
                (do
                  (clog "exp")
                  [(n/range (up (up u)))
                   (n/range (up u))]))]

       ; unwrap
       {:changes
        [{:from (.-from qmacro)
          :to (.-from queried-expr)
          :insert ""}
         {:from (.-to queried-expr)
          :to (.-to qmacro)
          :insert ""}]}
       ; wrap
       (let [qmark (clog :qm
                         (case (threading-parent-operator state u)
                           "->" "?>"
                           "->>" "?>>"
                           "?"))
             prefix (str "(" qmark " " (util/random-id) " ")]
         {:changes
          [{:from (.-from range)
            :insert prefix}
           {:from (.-to range)
            :insert " _)"}]
          :selection {:anchor (+ (cursor-pos vs) (count prefix))}}))
     clj->js
     (.dispatch vs))))

(comment

  (str (.-doc state))
  (text-of state u)
  u
  state


  )


(defonce example-n (atom 0))
(defonce all-examples (atom nil))

(defn reload-examples! []
  (-> (js/fetch "/examples.cljs")
      (.then (fn [r] (.text r)))
      (.then (fn [t]
               (reset! all-examples
                       (->> (map s/trim (s/split t ";---"))
                            (map (fn [x] (let [x' (s/split x ";%")]
                                           (map
                                            #(if (s/blank? %)
                                               (subs % 2)
                                               %)
                                            x')))))))))

  ; comment this in to make resolve immediately for video/"prod"
  #_(js/Promise.resolve @all-examples))

(defn format-example [x]
  (let [pre "\n\n\n"]
    (cond
      (string? x) [(count pre) (str pre x "\n\n\n")]
      nil [0 ""]
      :else
      (case (count x)
        0 [0 ""]
        1 [(count pre)
           (str pre (first x))]
        2 [(count (str (first x)))
           (str (first x) (second x) "\n\n\n")]
        3 [(count (str (first x) "\n"))
           (str (first x) "\n" (second x) "\n\n\n\n\n\n\n\n" (last x))]))))

(defn start-cursor [[or-at x]]
  [(or (s/index-of x "|") or-at)
   (s/replace-first x "|" "")])

(defn examples [n]
  (reload-examples!)
  (swap! example-n (fn [n'] (->
                             (+ n n')
                             (max 0)
                             (min (count @all-examples)))))
  (start-cursor
   (format-example (nth @all-examples @example-n nil))))


(def shortcuts
  (j/lit
   [{:key "Mod-/"
     :run toggleLineComment
     :preventDefault true}
    {:key "Mod-;"
     :run insert-random-id
     :preventDefault true}
    {:key "Alt-4"
     :run insert-random-id
     :preventDefault true}
    {:key "Mod-b"
     :preventDefault true
     :run toggleBooleanUnderCursor}


    {:key "Mod-ArrowUp"
     :preventDefault true
     :stopPropagation true
     :run (partial modifyNumberUnderCursor inc)
     :shift (partial modifyNumberUnderCursor (partial + 10))}
    {:key "Mod-Alt-ArrowUp"
     :preventDefault true
     :stopPropagation true
     :run (partial modifyNumberUnderCursor (partial + 0.1))}
    {:key "Cmd-ArrowUp"
     :preventDefault true
     :run (constantly true)
     :shift (constantly true)}
    {:key "Mod-Home"
     :preventDefault true
     :run (constantly true)
     :shift (constantly true)}


    {:key "Mod-ArrowDown"
     :preventDefault true
     :stopPropagation true
     :run (partial modifyNumberUnderCursor dec)
     :shift (partial modifyNumberUnderCursor (partial + -10))}
    {:key "Mod-Alt-ArrowDown"
     :preventDefault true
     :stopPropagation true
     :run (partial modifyNumberUnderCursor (partial + -0.1))}

    {:mac "Cmd-ArrowDown"
     :preventDefault true
     :run (constantly true)
     :shift (constantly true)}
    {:mac "Mod-End"
     :preventDefault true
     :run (constantly true)
     :shift (constantly true)}


    {:key "Alt-]"
     :preventDefault true
     :stopPropagation true
     :run jumpToNextLineWithPrefix}
    {:key "Alt-["
     :preventDefault true
     :stopPropagation true
     :run jumpToPrevLineWithPrefix}


    {:key "Mod-." ; >
     :preventDefault true
     :stopPropagation true
     :run (fn [v]
            (let [[cursor text] (examples 1)]
              (.dispatch v (clj->js {:changes {:from 0 :to (.. v -state -doc -length)
                                               :insert text}
                                     :selection {:anchor cursor :head cursor}}))))}

    {:key "Mod-," ; <
     :preventDefault true
     :stopPropagation true
     :run (fn [v]
            (let [[cursor text] (examples -1)]
              (.dispatch v (clj->js {:changes {:from 0 :to (.. v -state -doc -length)
                                               :insert text}
                                     :selection {:anchor cursor :head cursor}}))))}





    {:key "Mod-1"
     :preventDefault true
     :run toggleTopLevelBang}
    {:key "Cmd-1"
     :preventDefault true
     :run (constantly true)}

    {:key "Cmd-k"
     :preventDefault true
     :run #(do
             (js/console.clear)
             (try
               (portal-web/clear) ; throws when portal is closed
               (catch :default _ nil))
             true)}

    {:key "Cmd-p"
     :preventDefault true
     :run #(do (swap! @core/state :debug? not) true)}

    {:key "Mod-3"
     :preventDefault true
     :run toggleClojureFormComment}
    {:key "Mod-4"
     :preventDefault true
     :run toggleClojureDoubleFormComment}

    {:key "Mod-s"
     :preventDefault true
     :run (fn [^js ev] (editor/on-doc-saved (str (.. ev -viewState -state -doc))))}
    {:key "Alt-/"
     :preventDefault true
     :run (fn [^js ev]
            #_(toggleQueryExpression eval-region/node-at-cursor ev)
            (toggle-query-emacro eval-region/cursor-range ev))}]))


(defn editor []
  (r/with-let
    [initial-doc (js/localStorage.getItem (localstorage-key))
     mount!
     (fn [el]
       (when el
         (prn ::mount!)

         (reset!
          !view
          (new EditorView
               (j/obj
                :state
                (.create
                 EditorState
                 #js{:doc initial-doc
                     :extensions
                     (->>
                      [(history)
                       (.theme EditorView (clj->js ui/editor-theme) #js {:dark true})
                       (syntaxHighlighting (HighlightStyle.define (clj->js ui/editor-highlight)))
                       (rainbowBrackets)
                       #_color
                       colorPicker
                       #_(colorPicker (clj->js {:style {:wrapper {:outlineColor :transparent}}}))
                       (view/drawSelection)
                       (dropCursor)
                       (lineNumbers)
                       (search)
                       #_(lineWithPrefixJumping)
                       (highlightActiveLine)
                       (highlightActiveLineGutter)
                       (highlightSelectionMatches)
                       #_(highlightSpecialChars #js {:specialChars (js/RegExp. "^!" "g")})
                       (foldGutter)
                       (.. EditorState -allowMultipleSelections (of true))
                       cm-clj/default-extensions
                       (.of view/keymap
                            (j/lit [~@shortcuts
                                    ~@searchKeymap
                                    ~@historyKeymap
                                    ~@cm-clj/complete-keymap]))
                       (eval-region/extension {:modifier "Alt"})
                       (QPlugin schedule-update)
                       highlightField
                       hideIds
                       q-statefield
                       widgetPlugin]
                      (remove nil?)
                      (clj->js))})
                :parent el)))

         (set! js/window.editorView @!view)
         (.focus @!view)
         (editor/on-doc-saved initial-doc)
         (add-?-result-watch)
         @!view))]
    [:div {:style {:width "100%"
                   :height "100%"}
           :ref mount!}]
    (finally (j/call @!view :destroy))))

(util/clog cm-clj/complete-keymap)

(defn app []
  [:<>
   #_
   [:div {:style {:position :absolute
                  :top 5
                  :right 5}}
    (if (:changed? @core/state) "*")]
   [:div {:style {:display :flex}}
    [:div {:style {:flex-grow 2}}
     [editor]]

    (when (:debug? @core/state)
      [:div {:style {:flex-grow 1
                     #_#_:resize :horizontal
                     :width "40%"
                     :min-width "40%"
                     :overflow :auto
                     :word-break "break-word"}}
       [:pre
        (zp/zprint-str (editor/debug-state) 20)]

       #_[error-boundary :widgets
          [widgets @!node-state]]])]


   (let [css-var (fn [[k v]] (str k ": " v ";\n"))
         root-css (fn [vars]
                    (str ":root {"
                         (s/join "" (map css-var vars))
                         "}"))]
     [:style
      (root-css
       {"--display-line-numbers"
        (if (:line-numbers? @core/state) "visible" "hidden")})])


   (when (:help? @core/state)
     [ui/shortcuts])

   ; first line hidden video frame helper line
   [:div {:style {:position :fixed
                  :border-bottom "1px solid rgba(180, 94, 94, 0.8)"
                  :pointer-events :none
                  :top 0
                  :left 0
                  :right 0
                  :height "25px"}}]])

(defonce react-root (atom nil))

(defn ^:dev/after-load rerender []
  (.render @react-root (r/as-element [:> StrictMode [app]])))


(defn ^:export init []
  (let [ident js/window.location.hash
        root-el (js/document.getElementById "editor-root")
        flag-caps
        (fn [k]
          [k (fn [v] ; gotcha: assoc k would overwrite flag-caps fn in state
               (swap! core/state assoc
                      (keyword
                       (str (name k)
                            "?"))
                      v))])]
    (p/do
      (editor/init
       ident
       (into
        {:line-numbers? true}
        (map flag-caps #{:help :debug :line-numbers})))
      (reset! react-root (createRoot root-el))
      (rerender))))

(comment)
