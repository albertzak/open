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
     (send "hello"))))

(defn start [{:keys [port] :or [port 8080]}]
  (let [wss
        #?(:cljs
           (doto (WebSocketServer. #js {:port port})
             (.on "error" (partial log :error))
             (.on "connection"
                  (fn [^js ws]
                    (#'on-connection
                     {:on-message
                      (fn [f]
                        (.on ws "message"
                             (fn [^js buf]
                               (log :on-ws-message (str buf))
                               (f (str buf)))))
                      :send (fn [s] (.send ws s))}))))
           :clj nil)]
    (swap! state assoc :websocket-server wss)))




(comment
  (def ws (WebSocket. "ws://localhost:8080"))
  (.on ws "error" log)
  (.on ws "open" log)
  (.on ws "message" (partial log :ws-client-rx))
  (.send ws "hi"))
