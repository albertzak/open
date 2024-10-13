(ns small.util
  (:require [clojure.string :as s]
            [zprint.core :as zp]
            [promesa.core :as p]
            [applied-science.js-interop :as j]))

(defn log [& xs] (apply prn xs) (last xs))

(defn clog [& xs] (apply js/console.log xs) (last xs))

(defn pprint
  ([x] (pprint x {}))
  ([x opts]
   (zp/zprint-str
    x 66
    (merge-with
     merge
     {:style :backtranslate
      :tagged-literal {:hang? false}
      :reader-cond {:comma? nil}
      :map {:comma? false

            #_#_:force-nl? true}}
     opts))))

(comment
  (pprint {:a 1 :b 2})
  (pprint #js {:a :b :c :d})
  (pprint [1 2 3 4 45 6]))

(defn random-id
  ([] (random-id 8))
  ([length]
   (->> (str (random-uuid))
        (take (dec length))
        (apply str (rand-nth "abcdef")) ; start with letter to be valid keyword))
        keyword)))

(defn random
  ([] (js/Math.random))
  ([n] (rand-int n))
  ([from to] (+ from (rand-int (- to from)))))




(defn now []
  (js/Date.))

(defn parse-ident [ident]
  (let [ident (if (s/starts-with? ident "#") (subs ident 1) ident)
        [broker idents] (s/split ident #"#")
        [proj node] (s/split idents ":")]
    [broker proj node]))

(defn promisify [f]
  (fn [& args]
    (js/Promise.
     (fn [resolve reject]
       (apply f (concat args [(fn [err ok]
                                (if err
                                  (reject err)
                                  (resolve ok)))]))))))

(defn js-import
  [url & things]
  (prn ::js-import url things)
  (p/let [^js p (js/globalThis.jsImport url)] ; window.jsImport needs to wrap import keyword as fn
    (prn :js-import-got p)
    (prn :deff (.-default p) (empty? things))
    (if (empty? things)
      (.-default p)
      (into {} (map (fn [k] [k (j/get p k)]) things)))))

(defn typeof [^js x]
  ((js* "(function(x) { return typeof x})") x))

(defn promise? [^js obj]
  (and obj
       (= "function" (typeof (.-then obj)))))

(defn json [url]
  (-> (js/fetch url)
      (.then #(.json %))
      (.then #(js->clj % :keywordize-keys true))))
