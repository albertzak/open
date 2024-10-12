#!/usr/bin/env bb

(ns fun)

(require '[clojure.java.shell :refer [sh]])
(require '[clojure.string :as s]) ; why is this a separate ns 😒

(defn changes? []
  (->> (sh "git" "dif" "--exit-code")
       :exit
       zero?
       not))

(defn commit-count []
  (->> (sh "git" "rev-list" "--count" "HEAD")
       :out
       s/trim
       Integer/parseInt
       inc))

(defn commit-and-push []
  (when (changes?)
    (let [n (commit-count)]
      (sh "git" "add" "-A")
      (sh "git" "commit" "-m" (str n))
      (sh "git" "push")
      (println (str (java.time.Instant/now)) n))))

; for better aesthetics
(defn wait-until-next-full-minute []
  (let [m (* 60 1000)
        now (System/currentTimeMillis)
        next-m (* m (quot (+ now m) m))
        remaining (- next-m now)]
    (Thread/sleep remaining)))

(loop []
  (wait-until-next-full-minute)
  (commit-and-push)
  (recur))
