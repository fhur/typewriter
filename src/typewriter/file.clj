(ns typewriter.file
  "Provides function for reading and writing files"
  (:require [clojure.string :refer [split]])
  (:import java.io.File))

(defn path->file
  "Takes either a file path or File as argument and returns the File"
  [path]
  (if (= File (class path))
    path ; return as it is laready a File
    (File. path)))

(defn list-files
  [file]
  (seq (.listFiles file)))

(defn iterate-file-tree
  "Iterates a file tree where the node is path,
  returns a list of all files in the tree.
  path can be a File or a string"
  [file-or-path]
  (let [root (path->file file-or-path)]
    (loop [result []
           next-files (list-files root)]
      (if (empty? next-files)
        result
        (let [file (first next-files)]
          (if (.isDirectory file)
            (recur result (concat (list-files file) (rest next-files)))
            (recur (conj result file) (rest next-files))))))))

(defn get-extension
  "Returns a files extension or an empty string if it is a directory or has
  no extension"
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
  "Searches in the given tree root for all files with a recognized extension
  See known-ext? for supported types."
  [file-or-path]
  (->> (iterate-file-tree file-or-path)
       (filter known-ext?)))

(defn read-file
  "Takes either a File or file path as argument and returns a string
  with the contents of the file."
  [file]
  (with-open [reader (clojure.java.io/reader file)]
    (loop [line (.readLine reader)
           result []]
      (if (nil? line)
        (clojure.string/join "\n" result)
        (recur (.readLine reader)
               (conj result line))))))


