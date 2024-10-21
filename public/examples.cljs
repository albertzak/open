





(editor (fn [{:keys [line-numbers import render]}]
          (render nil)
          (-> "https://esm.sh/canvas-confetti@1.6.0$default"
            (import)
            (then (fn [c]
                    (? :bc9992a2 (c) #promise [:e71b5034 :fulfilled nil]))))
          (line-numbers false)))

(def rpi :c98d5645c97db5d0be4fc9db)

(defn history [vs]
  (->> vs (map :value)))

(defn graph [vs]
  ^:?/block
  [:div
   [into [:div.d-flex.flex-end.h100]
    (let [vs (->> vs (map (comp parse-int :value)))]
      (->> vs
        (map (fn [v]
               [:div.bg-green
                {:style
                 {:height (* 100 (/ v (apply max vs)))
                  :width 20}}]))))]])



! (node rpi
  (fn [{:keys [usbserial stateful lcd-write!
               gpio0 gpio5 gpio6]}]
    (let [sensor (stateful :dewfqewa #(usbserial {:baud 19200}))]
      (? :ab219224 sensor #fn :f66367b3)
      
      (sensor
        (fn [cm]
          (? :ce8101bf (parse-int cm) 95))))
    (prn :ok)
    (gpio0 false)
    (gpio5 false)
    (gpio6 false)
    ))


;---





;---

'(LIVE 2024

       Run, Build and Grow
       Small Systems
       Without Leaving Your Text Editor|

       Albert Zak
       Karl M. Göschka)


;---




;---
! (editor
  (fn [] ;%



    
    (-> "LIVE"
      pretty|
      say-hi)








))


;

;---


; performance (60+ fps)

(editor
  (fn [{:keys [rerun]}]
    (now)
    (rerun 0)))


;---


; distributed system

|


;---


; distributed system

(def rpi :c98d5645c97db5d0be4fc9db|)



;---


; distributed system

(def rpi :c98d5645c97db5d0be4fc9db)

! (node rpi
    (fn [{:keys [gpio0 gpio5 gpio6]}]
      (gpio0 false)
      (gpio5 false)
      (gpio6 false)|))





;---


! (editor
    (fn [{:keys [theme help line-numbers]}]
      (help false)
      (line-numbers false)
      (theme
        {:keyword "#e494ff"
         :function "#ffe200"
         :string "#a3f49e"})))



;---


  (editor
    (fn [{:keys [import]}]
      (then
        (import "https://esm.sh/canvas-confetti@1.6.0$default")
        (fn [c]
          (? :bc9992a3 (c) _)))))

;---


; open world

! (node rpi
    (fn [{:keys [slurp]}]
      (slurp "/")))





;---



; processes

(defn blink [ms pin]
  (fn [{:keys [rerun] :as caps}]
    (-> (wait 0)
      (then #((caps pin) true))
      (then #(wait ms))
      (then #((caps pin) false))
      (then #(rerun ms)))))

! (node rpi
    {|:red (blink 120 :gpio0)
     :yellow (blink 160 :gpio5)
     :green (blink 180 :gpio6)})


















;


;---

; state

(defn blink [{:keys [rerun gpio0]} state]
  (gpio0 state)
  (rerun 300 (not state)))
  
(node rpi blink)





;---



; stateful subexpr

! (node rpi
    (fn [{:keys [usbserial stateful]}]
      (let [sensor (stateful :dewfqewa 
                     #(usbserial {:baud 19200}))]
        (sensor (fn [cm] |cm)))))



;---


; context 
















































(def funfiles ["⛔️ TOP SECRET ⛔️ Strategic Defense Initiative and Computer Networks: Plans and implementations (Conference Notes) 227 pages, revised Sept, 1985"
               "⛔️ CLASSIFIED ⛔️ Strategic Defense Initiative and Computer Networks: Plans and implementations (Conference Notes) 300 pages, June, 1986"
               "⛔️ TOP SECRET ⛔️ SDINET Connectivity Requirements 65 pages, revised April, 1986"
               "⛔️ CLASSIFIED ⛔️ How to link into the SDINET 25 pages, July 1986"
               "⛔️ TOP SECRET ⛔️ Strategic Defense Network X.25 and X.75 connections to SDINET (includes Japanese, European, and Hawaii nodes) 8 pages, December, 1986"
               "⛔️ CLASSIFIED ⛔️ SDINET management plan for 1986 to 1988 47 pages, November 1985"
               "⛔️ TOP SECRET ⛔️ Milnet SDINET membership list 24 pages, November 1896"
               "⛔️ CLASSIFIED ⛔️ Classified SDINET membership list 9 pages, November, 1986"
               "⛔️ TOP SECRET ⛔️ Classified Developments in SDINET and Sdi Disnet 28 pages, October, 1986"
               ])

(def boringfiles ["NASA - Latest Mars Rover Discoveries"
                  "Hubble Space Telescope: New Image Releases"
                  "ArXiv - Recent Papers in Astronomy and Astrophysics"
                  "Astrophysical Journal - Current Issue"
                  "Sky & Telescope - Observing the June Night Sky"
                  "SpaceWeather.com - Solar Activity Reports"
                  "Starry Night Software - Live Sky Map"
                  "International Astronomical Union - Upcoming Conferences"
                  "Keck Observatory - Recent Findings and Data"
                  "ESO - European Southern Observatory Research Highlights"
                  "AstroBin - Deep Sky Astrophotography Gallery"
                  "Astronomer's Telegram - Real-time Astronomical Transient Reports"
                  "Cosmos - The Science of Everything: Astronomy Articles"
                  ])

(defn header [])
(defn controls [x])
(defn document [x] {:title x})
(defn document-viewer [x])
(defn screen [props]
  (with-context {:user (:user props)}
    [:div
     [header]
     [document-viewer (:id props)]
     [controls props]]))




; context 

(context #{[:user "dave"]}
  (defn document-viewer [doc]
    (:title doc)))


(defn screen [props]
  (with-context {:user (:user props)}
    [:div
     [header]
     [document-viewer (:id props)]
     [controls props]]))|



(editor
  {:sv (fn [{:keys [rerun]}]
         (with-context {:user "sventek"}
           (document-viewer {:title (rand-nth funfiles)})
           (rerun (random 1300 1800))))
   :dave (fn [{:keys [rerun]}]
           (with-context {:user "dave"}
             (document-viewer {:title (rand-nth boringfiles)})
             (rerun (random 1300 1800))))})


(? :da532fbd (rand-nth funfiles)
  "#45.2 Strategic Defense Initiative and Computer Networks: Plans and implementations (Conference Notes) 300 pages, June, 1986")






;---


; inline visualization


(defn blink [ms pin]
  (fn [{:keys [rerun] :as caps}]
    (let [pinw (fn [b]
                 (context #{[:pid :green]}
                   ((caps pin)
                    b)))]
      (-> (wait 0)
        (then #(pinw true))
        (then #(wait ms))
        (then #(pinw false))
        (then #(rerun ms))))))


! (node rpi
    {:red (blink 700 :gpio0)
     :yellow (blink 1000 :gpio5)
     :green (blink 600 :gpio6)})















;




;---


; inline visualization


(defn viz [_ v]
  (if v "🟢" "⚫️"))|

(defn blink [ms pin]
  (fn [{:keys [rerun] :as caps}]
    (let [pinw (fn [b]
                 (context #{[:pid :green]}
                   ((caps pin)
                    (? :d9a1446f b viz _))))]
      (-> (wait 0)
        (then #(pinw true))
        (then #(wait ms))
        (then #(pinw false))
        (then #(rerun ms))))))


! (node rpi
    {:red (blink 700 :gpio0)
     :yellow (blink 1000 :gpio5)
     :green (blink 600 :gpio6)})










;---


; context viz


(defn viz [xs]
  (let [orbs {:red "🔴"
              :yellow "🟡"
              :green "🟢"}]
    (if (-> xs last :value)
      (-> xs last :node-context :pid orbs)
      "⚫️")))


(context #{[:pid :green]}
  (defn blink [ms pin]
    (fn [{:keys [rerun] :as caps}]
      (let [pinw (fn [b]
                   (context #{[:pid :red]}
                     (? :e9bb6a8f b viz "⚫️"))
                   (context #{[:pid :yellow]}
                     (? :d07db348 b viz "🟡"))|
                   ((caps pin)
                    (? :eaaa2848 b viz "🟢")))]
        (-> (wait 0)
          (then #(pinw true))
          (then #(wait ms))
          (then #(pinw false))
          (then #(rerun ms)))))))


! (node rpi
    {:red (blink 700 :gpio0)
     :yellow (blink 1000 :gpio5)
     :green (blink 300 :gpio6)})










;---


; history viz

(defn history [xs]
  (->> xs (map :value)))

(defn led [b]
  (context #{[:pid :red]}
    (? :a477c21d b | _)))

(defn blink [ms pin]
    (fn [{:keys [rerun] :as caps}]
      (let [pinw
            (fn [b]
              ((caps pin)
               (led b)))]
        (-> (wait 0)
          (then #(pinw true))
          (then #(wait ms))
          (then #(pinw false))
          (then #(rerun ms))))))


! (node rpi
    {:red (blink 700 :gpio0)
     :yellow (blink 1000 :gpio5)
     :green (blink 300 :gpio6)})


















;

;---


; html viz

(defn graph [vs]
  ^:?/block
  [:div
   [into [:div.d-flex.flex-end.h100]
    (let [vs (->> vs (map (comp parse-int :value)))]
      (->> vs
        (map (fn [v]
               [:div.bg-green
                {:style
                 {:height (* 100 (/ v (apply max vs)))
                  :width 20}}]))))]])


(node rpi
  (fn [{:keys [usbserial stateful]}]
    (let [sensor (stateful :dewfqewa #(usbserial {:baud 19200}))]
      (? :ab219224 sensor #fn :f66367b3)
      
      (sensor
        (fn [cm]
          (? :ce8101bf cm graph _))))))


















;

;---



'(discussion
   
   > hobbyists, solo devs
   
   > fun, low ceremony

   > lisp? clojure!)



;--- ! (editor (show|))
;%

;%

;--- ! (editor (show "rslnd|.mov"))
;%

;%


;--- ! (editor (show|))
;%

;%

;---


'(related-work

   - Smalltalk
   - Lisp
     - Emacs
       - org-mode)



;--- ! (editor (show|))
;%

;%

;---
! (editor (show "task|.mov"))
;%

;%


;--- ! (editor (show "apx|.mov"))
;%

;%

;---
! (editor (show "capnp|.mov"))
;%

;%



;---
! (editor (show "ixi|.mov"))
;

;%



;---
! (editor (show "gibber|.mov"))
;%

;%


;---
! (editor (show "strudel|.mov"))
;%

;%



;---
! (editor (show "eve|.mov"))
;%

;%


;---
! (editor (show "dark|.mov"))
;%

;%


;---
! (editor (show "preimp|.mov"))
;%

;%



;---
! (editor (show |))
;%

;%






;---


'(nope|

   errors
   codebase
   persistence
   security
   communication)



;--- ! (editor (show|))
;%

;%

;---
! (editor (show "electric|.mov"))
;%

;%



;---
! (editor (show|))
;%

;%


;---


'(near-future|

   editor extensions
   secure enough
   collab via ocaps)


;---

'(open-qs|

   deleting
   async feedback
   dynamic sets
   identity of code)




;---


'(conclusion|

   query macro
   deployment
   context
   caps)

;---


    '(thank-you!)


    albertzak.com
    open practice


"This work has been supported by the Doctoral College Resilient
Embedded Systems, which is run jointly by the TU Wien's Faculty of
Informatics and the UAS Technikum Wien."


;---




'(LIVE 2024

       Run, Build and Grow
       Small Systems
       Without Leaving Your Text Editor|

       Albert Zak
       Karl M. Göschka)


;---

