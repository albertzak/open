(ns small.web
  (:require [small.core :as core]
            [small.util :as util :refer [pprint js-import]]
            [applied-science.js-interop :as j]
            [clojure.string :as s]
            [promesa.core :as p]
            [reagent.core :as r]
            ["react" :refer [StrictMode]]
            ["react-dom/client" :refer [createRoot]]))

(defonce react-root (atom nil))


(defn welcome [{:keys [node] :as caps}]
  (p/then (js-import "https://esm.sh/canvas-confetti@1.6.0$default") (fn [c] (c)))
  [:div {:style {:display :flex
                 :background-color "black"
                 :color "white"
                 :flex-direction :column
                 :align-items :center
                 :justify-content :center
                 :width "100%"
                 :height "100vh"}}
   [:h1 "hello world"]
   [:small "Copy the code below to get started:"]
   [:pre {:style {:opacity 0.8}}
    "! (node " (str node) "\n   (fn [{:keys [render]}]\n     (render [:h1 \"hello world\"])))"]])


(defn rerender [comp]
  (.render @react-root (r/as-element [:> StrictMode comp])))

(defn ^:export node [ident ^js root-el ^js extra-caps]
  (let [caps (merge (js->clj extra-caps :keywordize-keys true)
                    {:import #'js-import
                     :document js/document
                     :window js/window
                     :render (fn [comp]
                               ((fn rerender-now! []
                                  #_(*cleanup* (fn []
                                                 (prn ::reagent-cleanup!)
                                                 (rerender [app @core/state])))


                                  (prn :rr comp)
                                  (set! (.-rr js/window) rerender-now!)
                                  (rerender (cond (fn? comp) [comp @core/state]
                                                  (nil? comp) [welcome @core/state]
                                                  (vector? comp) comp
                                                  :else (do
                                                          (js/console.warn "not sure how to render" (pprint comp))
                                                          comp))))))})]
    (p/do
      (core/connect! ident caps)
      (reset! react-root (createRoot root-el))
      (rerender [welcome @core/state]))))

(comment)
