(ns small.node
  (:require [small.core :as core]
            [clojure.string :as s]

            [promesa.core :as p]
            [serialport :refer [SerialPort]]
            [fs]
            [os]
            [child_process]
            [express :default express]
            [rpio :default rpio]))

(.on js/process "uncaughtException" js/console.error)
(.on js/process "unhandledRejection" js/console.error)

(def INPUT (.-INPUT rpio))
(def OUTPUT (.-OUTPUT rpio))
(def HIGH (.-HIGH rpio))
(def LOW (.-LOW rpio))

(defonce rpio-init
  (let [opts {:gpiomem false ; needs root for i2c, pwm, spi
              :mapping "physical"}]
    (.init rpio (clj->js opts))))

(defn attenuate-gpio
  "no args: set to INPUT and read value;
   one arg: set to OUTPUT and write boolean value"
  [nr]
  (let [mode (atom nil)
        ensure-mode
        (fn [m]
          (when-not (= m @mode) ; reopen in correct mode
            (when-not (nil? @mode) (.close rpio nr))
            (.open rpio nr m)
            (reset! mode m)))]
    (fn
      ([]
       (ensure-mode INPUT)
       (.read rpio nr))
      ([hi?]
       (ensure-mode OUTPUT)
       (.write rpio nr (if hi? HIGH LOW))))))

(defn attenuate-serial
  [device-path]
  (fn open [{:keys [baud] :as opts}]
    (SerialPort.
     (clj->js
      (merge
       opts
       {:baudRate (or baud 9600)
        :path device-path})))))


(def commands
  {:clear [0xFE 0x01]
   :off [0x7C 0x80]
   :on [0x7C 0x9D]})

(defn lcd-command! [^js s cmd]
  (.write s (clj->js (get commands cmd cmd))))


(defn lcd-write! [^js s text]
  (lcd-command! s :clear)
  (if (seq text)
    (let [[l1 l2] (s/split text #"\n")]
      (when (seq l1)
        (.write s l1))
      (when (seq l2)
        (.write s #js [0xFE (+ 0x80 64)])
        (.write s l2)))))

(defn rpi-caps []
  (when (try (.readFileSync fs "/proc/device-tree/model" "ascii") true
             (catch :default _ false))
    {:lcd-command! lcd-command!
     :lcd-write! lcd-write!
     :serial0 (attenuate-serial "/dev/serial0")
     :gpio0 (attenuate-gpio 27)
     :gpio5 (attenuate-gpio 29)
     :gpio6 (attenuate-gpio 31)}))





;; http server

(defn serve-http [port handler]
  (let [app (express)]
    (#'small.core/*cleanup* (fn []
                              (prn :calling-express-cleanup)
                              (.close app)))
    (.get app "/"
          (fn [req res]
            (if (fn? handler) ; todo: convert req/res to map
              (handler req res)
              (.send res handler))))
    (.listen app port (fn [x y]
                        (js/console.log "cb" x y)))))

(defn fs-caps []
  {:ls (fn [path] (js->clj (.readdirSync fs (or path "."))))
   :slurp (fn [path]
            (try
              (let [stat (.statSync fs path)]
                (if (.isFile stat)
                  (.readFileSync fs path "utf-8")
                  (js->clj (.readdirSync fs (or path ".")))))
              (catch :default e
                (str e))))

   :spit (fn [path data] (.writeFileSync fs path data))
   :exec (fn [cmd] (.exec child_process cmd))})

(def osx? (= "darwin" (.-platform os)))

(defn ^:export init [ident]
  (prn ::init ident)
  (p/let [caps
          (merge (when-not osx? (rpi-caps))
                 (fs-caps)
                 {:serve-http serve-http})
          publish (core/connect! ident caps)]
    (prn ::connected)))