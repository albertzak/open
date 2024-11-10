(ns prodhack.core
  (:require ["ws" :refer [WebSocketServer WebSocket]]))

(defn log [& xs]
  (apply prn xs)
  (last xs))

(defn on-connection [^js ws]
  (.on ws "error" (partial log :error))
  (.on ws "message"
       (fn [data]
         (log :ws-message data)))
  (.send ws "hello"))

(defonce wss
  (WebSocketServer.
   #js {:port 8080}))

(.on wss "connection" (fn [^js ws] (#'on-connection ws)))



(def ws (WebSocket. "ws://localhost:8080"))
(.on ws "error" log)

