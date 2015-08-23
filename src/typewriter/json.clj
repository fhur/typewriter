(ns typewriter.json
  (:require [clj-json.core :refer :all]))

(defn keywordize
  "Takes a hashmap as argument and recursively changes all keys into
  keywords"
  [x]
  (cond (and (coll? x) (not (map? x)))
          (map keywordize x)
        (map? x)
          (loop [result {}
                 kv-pairs (seq x)]
            (if (empty? kv-pairs)
              result
              (let [[k v] (first kv-pairs)
                    new-key (keyword k)
                    new-val (keywordize v)]
                (recur (assoc result new-key new-val)
                       (rest kv-pairs)))))
        :else x))

(defn parse
  [json-string]
  (->> (parse-string json-string)
       (keywordize)))
