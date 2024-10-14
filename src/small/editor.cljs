(ns small.editor
  (:require ["react" :refer [StrictMode]]
            ["react-dom/client" :refer [createRoot]]
            [small.core :as core]
            [small.portal :as portal]
            [small.util :refer [js-import] :as u]
            [applied-science.js-interop :as j]
            [clojure.edn :as edn]
            [clojure.set :refer [difference]]
            [promesa.core :as p]
            [reagent.core :as r]
            [instaparse.core :as insta :refer-macros [defparser]]
            [rewrite-clj.parser :as parser]
            [rewrite-clj.node :as node]
            [clojure.string :as s]))



(defn debug-state []
  (->
   @core/state
   (dissoc :doc :last-saved-doc :publish :ctx :broker :node :proj :changed?)
   #_#_#_#_
   (get-in ['? :?])
   first
   second
   :value



   #_(update :blocks count)))

(defn live-blocks [doc]
  (loop [acc []
         lines (s/split-lines doc)]
    (cond (nil? lines) acc

          (and (string? (first lines))
               (s/starts-with? (first lines) "!"))
          (let [rest-str (s/trim (subs (s/join "\n" lines) 1))
                node (try (->> rest-str
                               (parser/parse-string)
                               (node/string))
                          (catch :default e {:syntax-error e}))]
            (recur (conj acc node)
                   (next lines)))

          :else
          (recur acc (next lines)))))

(defn changed-live-blocks [old new]
  (->>
   (difference (set (:live-blocks new))
               (set (:live-blocks old)))
   (remove :syntax-error)))

(defn on-doc-changed [doc]
  (swap!
   core/state
   (fn [state]
     (let [lbs (live-blocks doc)
           state'
           (-> state
               (assoc :doc doc)
               (assoc :changed? (not= doc (:last-saved-doc state)))
               (update :vsn inc)
               (assoc :live-blocks lbs))
           changed-lbs (changed-live-blocks state state')]

       (when (seq lbs)
         (core/publish {:type :live-eval
                        :sources lbs ; publish all live blocks, not just changed ones
                        :vsn (inc (:vsn state))}))
       state'))))

(defn on-doc-saved [doc]
  (try (parser/parse-string-all doc) ; may throw
       (swap! core/state
              #(-> %
                   (dissoc :syntax-error)
                   (assoc :changed? false)
                   (assoc :last-saved-doc doc)))
       (core/publish
        {:type :full-eval
         :source doc
         :vsn (:vsn @core/state)})
       (catch :default e
         (swap! core/state assoc :syntax-error e)
         (js/console.error "not publishing syntax err doc" e)
         nil)))

(defn theme [m]
  (j/assoc!
   (js/document.getElementById "custom-theme")
   :innerText
   (str
    ":root {"
    (s/join " " (map (fn [[k v]] (str "--" (name k) ": " v ";\n")) m))
    "}")))


(defonce react-root (atom nil))


(defn caption [l1 l2 pos]
  [:div {:style
         (merge
          {:position :absolute
           :background :black
           :padding "1.5rem"
           :font-size "200%"}
          pos)}
   [:b l1]
   [:br]
   [:span {:style {:opacity 0.9}}
    l2]])

(def slides
  {"apx"
   [:div
    [caption "APX" "McDirmid (2015)" {:right 0 :top 0}]
    [:img {:style {:width "100%"
                   :height :auto}
           :src "/vids/apx.gif"}]]
   "ixi"
   [:div.w-100
    [caption "ixi lang" "Magnusson (2010)" {:right 0 :bottom "2rem"}]
    [:video {:style {:width "100%"
                     :height :auto
                     :object-fit :fill}
             :autoplay "autoplay"
             :loop "loop"
             :muted true
             :src "/vids/ixi.mp4"}]]

   "task"
   [:div.w-100
    [caption "TaskTXT" "Jeffery (2021)" {:right 0 :bottom "2rem"}]
    [:video {:style {:width "100%"
                     :height :auto
                     :object-fit :fill}
             :autoplay "autoplay"
             :loop "loop"
             :muted true
             :src "/vids/tasktxt.mov"}]]

   "capn"
   [:div.w-100
    [caption "Cap'n Proto" "Varda+ (2013)" {:right 0 :bottom "2rem"}]
    [:video {:style {:width "100%"
                     :height :auto
                     :object-fit :fill}
             :autoplay "autoplay"
             :loop "loop"
             :muted true
             :src "/vids/capnproto.mov"}]]

   })


(defn slide [k]
  [:div.w-100
   (get slides k (str "no slide: " k))])

(defn global-scope []
  {'clear (fn [] (fn [{:keys [render]}] (render [:div])))
   'slide (fn [k]
            (fn [{:keys [render]}]
              (render (slide k))))})

(defn render-caps []
  {:document js/document
   :slide #'slide})

(defn render [comp]
  (let [comp
        (cond (fn? comp) [comp render-caps]
              (nil? comp) (constantly nil)
              (or (string? comp) (number? comp)) [:h1 comp]
              (vector? comp) comp
              :else [:pre (u/pprint comp)])]
    (js/console.log "rendering" comp)
    (.render @react-root (r/as-element [:> StrictMode comp]))))

(defn ^:export init [ident caps]
  (p/do
    (reset! react-root (createRoot (js/document.getElementById "render-root")))
    (render [:div])
    (core/connect!
     ident ; todo editor caps
     (merge
      caps
      {:import #'js-import
       :editor? true
       :theme #'theme
       :render #'render})
     (global-scope))
    (portal/init)))

(comment
  (subs "\n\na\nbc\n\n" 1)
  (parser/parse-string "\n\n\n1")
  (s/split "-- abc\na\n -- def\na"
           #"^--.*$")
  (re-matches #"^:i[a-zA-z0-9]{3}$" ":i321")
  )
