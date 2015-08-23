(ns typewriter.json-test
  (:require [typewriter.json :refer :all]
            [presto.core :refer :all]
            [clojure.test :refer :all]))

(expected-when "keywordize test" keywordize
  when [{"foo" "bar"}] = {:foo "bar"}
  when [{"a" "b" "c" {"d" {"e" {"f" "g"}}}}] =
        {:a "b" :c {:d {:e {:f "g"}}}})

(expected-when "parse test" parse
  when ["{\"foo\": \"bar\"}"] = {:foo "bar"})
