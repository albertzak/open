(ns small.portal
  (:require [portal.shadow.remote :as r]
            [portal.web :as p]))

(defn- submit [value]
  (try
    (p/submit value)
    value
    (catch :default _ value))
  #_(r/submit value))

(defn- error->data [ex]
  (merge
   (when-let [data (.-data ex)]
     {:data data})
   {:runtime :portal
    :cause   (.-message ex)
    :via     [{:type    (symbol (.-name (type ex)))
               :message (.-message ex)}]
    :stack   (.-stack ex)}))

(defn- async-submit [value]
  (cond
    (instance? js/Promise value)
    (-> value
        (.then async-submit)
        (.catch (fn [error]
                  (async-submit error)
                  (throw error))))

    (instance? js/Error value)
    (submit (error->data value))

    :else
    (submit value)))


(defn- error-handler [event]
  (tap> (or (.-error event) (.-reason event))))


(defn init []
  (add-tap async-submit)
  (.addEventListener js/window "error" error-handler)
  (.addEventListener js/window "unhandledrejection" error-handler)
  #_(p/open))
