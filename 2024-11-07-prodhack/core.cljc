(ns prodhack.core
  (:require #?(:cljs ["ws" :refer [WebSocketServer WebSocket]])))

(defn log [& xs]
  (apply prn xs)
  (last xs))

(defonce state (atom {}))

(defn on-connection [{:keys [on-message send] :as _socket}]
  (on-message
   (fn [s]
     (log :got-string s)
     (send "hello back"))))

(defn start [& [{:keys [port]}]]
  (let [port (or port 8080)]
    (swap!
     state update :websocket-server
     (fn [wss]
       (when wss
         #?(:cljs (.close wss)
            :clj nil))
       #?(:cljs
          (doto (WebSocketServer. #js {:port port})
            (.on "error" (partial log :error))
            (.on "connection"
                 (fn [^js ws]
                   (let [on-message-fn (atom identity)]
                     (.on ws "message"
                          (fn [^js buf]
                            (log :on-ws-message (str buf))
                            (@on-message-fn (str buf))))
                     (#'on-connection
                      {:on-message (fn [f] (reset! on-message-fn f))
                       :send (fn [s] (.send ws s))})))))
          :clj nil)))))




(comment
  (start)

  (def ws (WebSocket. "ws://localhost:8080"))
  (.on ws "error" log)
  (.on ws "open" log)
  (.on ws "message" (partial log :ws-client-rx))
  (.send ws "hi")
  )
