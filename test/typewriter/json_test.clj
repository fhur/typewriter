(ns typewriter.json-test
  (:require [typewriter.json :refer :all]
            [presto.core :refer :all]
            [clojure.test :refer :all]))

(expected-when "keywordize test" keywordize
  when [{"foo" "bar"}] = {:foo "bar"}
  when [{"a" "b" "c" {"d" {"e" {"f" "g"}}}}] =
        {:a "b" :c {:d {:e {:f "g"}}}}

  when [{"a" [{"a" "b"}
              {"c" "d"}]}] =
       {:a [{:a "b"}
            {:c "d"}]}

  when [{"doc" "This is the class doc for a User"
         "imports" ["java.util.Date"]
         "package-name" "com.fernandohur"
         "class-name" "User"
         "attrs" [{"name" "firstName"
                  "type" "String"
                  "doc" "the user's name"}
                 {"name" "birthDay"
                  "type" "Date"
                  "doc" "The user's birth day"}]}] =
        {:doc "This is the class doc for a User"
                 :imports ["java.util.Date"]
                 :package-name "com.fernandohur"
                 :class-name "User"
                 :attrs [{:name "firstName"
                          :type "String"
                          :doc "the user's name"}
                         {:name "birthDay"
                          :type "Date"
                          :doc "The user's birth day"}]})


(expected-when "parse test" parse
  when ["{\"foo\": \"bar\"}"] = {:foo "bar"})
