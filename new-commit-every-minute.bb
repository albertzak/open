#!/usr/bin/env bb
(require '[clojure.java.shell :refer [sh]])
(require '[clojure.string :as s]) ; why is this a separate ns 😒

(defn changes? []
  (->> (sh "git" "diff" "--exit-code")
       :exit
       zero?
       not))

(defn commit-count []
  (->> (sh "git" "rev-list" "--count" "HEAD")
       :out
       s/trim
       Integer/parseInt
       inc))

(defn commit! []
  (when (changes?)
    (let [n (commit-count)]
      (sh "git" "add" "-A")
      (sh "git" "commit" "-m" (str n))
      (println (str (java.time.Instant/now)) n)
      true)))

; for better aesthetics
(defn wait-until-next-full-minute []
  (let [m (* 60 1000)
        now (System/currentTimeMillis)
        next-m (* m (quot (+ now m) m))
        remaining (- next-m now)]
    (Thread/sleep remaining)))

(loop []
  (wait-until-next-full-minute)
  (when (commit!)
    (sh "git" "push"))
  (recur))
