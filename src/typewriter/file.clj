(ns typewriter.file
  "Provides function for reading and writing files"
  (:require [clojure.string :refer [split]]))

(defn list-files
  [file]
  (seq (.listFiles file)))

(defn iterate-file-tree
  "Iterates a file tree where the node is path,
  returns a list of all files in the tree."
  [path]
  (let [root (new java.io.File path)]
    (loop [result []
           next-files (list-files root)]
      (if (empty? next-files)
        result
        (let [file (first next-files)]
          (if (.isDirectory file)
            (recur result (concat (list-files file) (rest next-files)))
            (recur (conj result file) (rest next-files))))))))

(defn get-extension
  "Returns a files extension or an empty string if it is a "
  [file]
  (if (.isDirectory file)
    ""
    (let [file-name (.getName file)]
      (if (.contains file-name ".")
        (last (split file-name #"\."))
        ""))))

(defn known-ext?
  [file]
  (= (get-extension file) "json"))

(defn find-typewriter-programs
  [root]
  (->> (iterate-file-tree root)
       (filter known-ext?)))

(defn read-file
  [file]
  (with-open [reader (clojure.java.io/reader file)]
    (loop [line (.readLine reader)
           result []]
      (if (nil? line)
        (clojure.string/join "\n" result)
        (recur (.readLine reader)
               (conj result line))))))


