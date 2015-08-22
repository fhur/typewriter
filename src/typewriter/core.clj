(ns typewriter.core
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
  ($>> #(str "import " % ";") imports "\n"))

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

(defn type-writer
  [typedef]
  (str (write-package (:package-name typedef))
       "\n"
       (write-imports (:imports typedef))
       "\n"
       (write-class "public" typedef)))

(def x
  {:class-name "User"
   :package-name "com.fernandohur"
   :imports ["java.lang.String"]
   :doc "this is the class' javadoc"
   :attrs [{:name "firstName"
            :type "String"
            :doc "This is the user's name"}
           {:name "age"
            :type "int"
            :doc  "The users age measured in years"}
           {:name "isHappy"
            :type "boolean"
            :doc  "flag indicating wether this user is happy or not"
            :modifier "protected"}]})


(print (type-writer x))
