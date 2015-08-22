(ns typewriter.equals
  (:require [clojure.string :as s]
            [typewriter.util :as util]
            [typewriter.template :refer :all]))

(defn- is-primitive
  "Returns non null if the given string is a java primitive,
  or false otherwise"
  [type]
  (#{"short" "byte" "char" "boolean"
    "int" "long" "double" "float"} type))

(defn- write-attr-check
  [{type :type name :name}]
  (if (is-primitive type)
    (str "if ("name" != other."name") return false;\n")
    (str "if ("name" != null ? !"name".equals(other."name") : other."name" != null) return false;\n")))

(defn write-equals
  [class-name attrs]
  (if (empty? attrs)
    ""
    (str "@Override\n"
         "public boolean equals(Object o){\n"
         "    if(this == o) return true;\n"
         "    if(!(o instanceof "class-name")) return false;\n"
         "\n"
         "    "class-name" other = ("class-name") o;\n"
         "\n"
         ($>> write-attr-check attrs "")
         "\n"
         "    return true;\n"
         "}\n")))

(defn write-hashcode
  [attrs]
  (if (empty? attrs)
    ""
    (str "@Override\n"
         "public int hashCode() {\n"
         "    Object[] objs = {"
         (->> (map :name attrs)
              (s/join ", "))
         "};\n"
         "    return java.util.Arrays.hashCode(objs);\n"
         "}\n")))

