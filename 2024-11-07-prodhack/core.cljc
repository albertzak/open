(ns prodhack.core
  (:require #?(:cljs ["ws" :refer [WebSocketServer WebSocket]]
               :clj [org.httpkit.server :as server])
            #?(:bb [babashka.http-client.websocket :as ws]
               :clj [hato.websocket :as ws])))

(defn log [& xs]
  (apply prn xs)
  (last xs))

(defn random-id
  ([] (random-id 8))
  ([length]
   (->> (str (random-uuid))
        (take (dec length))
        (apply str (rand-nth "abcdef")) ; start with letter to be valid keyword
        keyword)))

(defonce state (atom {}))

(defn broadcast [s]
  (doseq [send (-> @state :websocket-clients vals)]
    (send s)))

(defn on-connection [{:keys [on-message send on-close] :as socket}]
  (let [id (random-id)]
    (swap! state assoc-in [:websocket-clients id] send)
    (on-close
     (fn [] (swap! state update :websocket-clients dissoc id)))
    (on-message (fn [s] (broadcast s)))))

(defn start [& [{:keys [port]}]]
  (let [port (or port 8080)]
    (swap!
     state update :websocket-server
     (fn [wss]
       (when wss
         #?(:cljs (.close wss)
            :clj (wss :timeout 100)))
       #?(:cljs
          (doto (WebSocketServer. #js {:port port})
            (.on "error" (partial log :error))
            (.on "connection"
                 (fn [^js ws]
                   (let [on-message-fn (atom identity)
                         on-close-fn (atom identity)]
                     (.on ws "message" (fn [^js buf] (@on-message-fn (str buf))))
                     (.on ws "close" (fn [] (@on-close-fn)))
                     (#'on-connection
                      {:on-close (fn [f] (reset! on-close-fn f))
                       :on-message (fn [f] (reset! on-message-fn f))
                       :send (fn [s] (.send ws s))})))))
          :clj (server/run-server
                (fn [ring-req]
                  (if (:websocket? ring-req)
                    (server/as-channel
                     ring-req
                     {:on-open (fn [] (log :s/open))
                      :on-receive (fn [] (log :s/rx))
                      :on-close (fn [] (log :s/close))})
                    {:status 200 :body "connect via websockets"}))
                {:port port}))))))




(comment
  (start)


  @state
  ((:websocket-server @state))

  (def ws (WebSocket. "ws://localhost:8080"))
  (.on ws "error" log)
  (.on ws "open" log)
  (.on ws "message" (partial log :ws-client-rx))
  (.send ws "hiooo")
  (.close ws)

  (log :ok)



  (let [ws
        (ws/websocket
         {:uri "ws://localhost:8080"
          :on-open (fn [ws]
                     (log :c/open))
          :on-error (fn [ws err]
                      (log :c/err err))
          :on-message (fn [ws msg last?]
                        (log :c/rx msg))
          :on-close (fn [ws status reason]
                      (log :c/closed status reason))})]
    (ws/send! ws "hi from clj client")
    (log :ok))
  )
