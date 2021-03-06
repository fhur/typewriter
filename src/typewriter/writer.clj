(ns typewriter.writer
  (:require [clojure.string :as s]
            [typewriter.template :refer :all]
            [typewriter.util :as util]
            [typewriter.equals :refer [write-equals write-hashcode]]))

(defn- or-modifier
  [field modifier]
  (if (nil? field)
    modifier
    field))

(defn- or-private
  [field]
  (or-modifier field "private"))

(defn write-imports
  [imports]
  ($>> #(str "import " % ";") imports "\n" 0))

(defn write-package
  [package-name]
  (str "package " package-name "\n"))

(defn write-doc
  [doc]
  (let [lines (s/split-lines doc)]
    (str "/**\n"
         (s/join "\n" (map #(str " * " %) lines))
        "\n */\n")))

(defn write-field
  [{name :name
    type :type
    doc :doc
    modifier :modifier}]
  (str (write-doc doc)
       (or-private modifier) " " type " " name ";\n\n"))

(defn write-getter
  [{name :name
    type :type
    doc :doc
    modifier :modifier}]
  (str (write-doc (str "A getter for the {@link #" name "} field"))
       "public " type " " (util/attr-to-getter-name name) "() {\n"
       "  return this." name ";\n"
       "}\n"))

(defn write-class
  [modifier {class-name :class-name
             doc :doc
             attrs :attrs}]
  (str (write-doc doc)
       modifier" class "class-name" {\n"
       "\n"
       ($>> write-field attrs "" 4)
       "\n\n"
       ($>> write-getter attrs "\n" 4)
       "\n\n"
       (->> (write-equals class-name attrs)
            (util/indent 4))
       "\n\n"
       (->> (write-hashcode attrs)
            (util/indent 4))
       "\n\n"
       "}\n"))

(defn write-type
  [typedef]
  (str (write-package (:package-name typedef))
       (write-doc "WARNING! this is an autogenerated file")
       "\n"
       (write-imports (:imports typedef))
       "\n"
       (write-class "public" typedef)))


