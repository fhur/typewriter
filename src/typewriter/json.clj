(ns typewriter.json
  (:require [clj-json.core :refer :all]))

(defn keywordize
  "Takes a hashmap as argument and recursively changes all keys into
  keywords"
  [hashmap]
  (loop [result {}
         kv-pairs (seq hashmap)]
    (if (empty? kv-pairs)
      result
      (let [[k v] (first kv-pairs)
            new-key (keyword k)
            new-val (if (map? v) (keywordize v) v)]
        (recur (assoc result new-key new-val)
               (rest kv-pairs))))))

(defn parse
  [json-string]
  (->> (parse-string json-string)
       (keywordize)))
