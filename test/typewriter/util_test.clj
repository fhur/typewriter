(ns typewriter.util-test
  (:require [typewriter.util :refer :all]
            [presto.core :refer :all]
            [clojure.test :refer :all]))


(expected-when "n-spaces test" n-spaces
  when [0] = ""
  when [5] = "     ")

(expected-when "indent test" indent
  when [2 "foo"] = "  foo"
  when [2 "a\nb"] = "  a\n  b"
  when [0 "foobar\nbar"] = "foobar\nbar")


