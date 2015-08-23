(ns typewriter.core
  (:require [typewriter.writer :refer [write-type]]
            [typewriter.file :refer [find-typewriter-programs
                                     read-file
                                     get-extension
                                     get-output-location
                                     mkdirs
                                     get-output-dir
                                     write-str]]
            [typewriter.json :as json]))

(defn parse-file
  [file]
  (let [ext (get-extension file)]
    (if (not= "json" ext)
      (throw (new IllegalArgumentException (str "Don't know how to parse " file)))
      (->> (read-file file)
           (json/parse)))))

(defn compile-tree
  [root output]
  (println "Searching for files in" root)
  (for [file (find-typewriter-programs root)]
    (let [parsed (parse-file file)
          compiled (write-type parsed)
          out-dir (get-output-dir output (:package-name parsed))
          out (get-output-location output
                                   (:package-name parsed)
                                   (:class-name parsed))]
      (println "Writing" out)
      (mkdirs out-dir)
      (write-str compiled out))))



