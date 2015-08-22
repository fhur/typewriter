(ns typewriter.util
  (:require [clojure.string :as s]))

(defn attr-to-getter-name
  [[first & rest]]
  (str "get" (s/upper-case first)
       (s/join rest)))

(defn n-spaces
  [n]
  (loop [result ""]
    (if (= n (count result))
      result
      (recur (str " " result)))))

(defn indent
  [num-spaces string]
  (let [indent-space (n-spaces num-spaces)]
    (->> (s/split-lines string)
         (map #(str indent-space %))
         (s/join "\n"))))


