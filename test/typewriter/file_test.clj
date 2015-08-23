(ns typewriter.file-test
  (:require [typewriter.file :refer :all]
            [presto.core :refer :all]
            [clojure.test :refer :all]))

(defn files-eq?
  [expected actual]
  (= (set (map #(.getName %) expected))
     (set actual)))

(expected-when "iterate-file-tree test" iterate-file-tree
  when ["./test/res/"] files-eq? ["user.json" "README.md" "bike.json" "car.json"])

(expected-when "find-typewriter-programs test" find-typewriter-programs
  when ["./test/res"] files-eq? #{"user.json" "bike.json" "car.json"})

(expected-when "get-output-location test" get-output-location
  when ["./" "com.fernandohur.cars" "FancyCar"] = "./com/fernandohur/cars/FancyCar.java"
  when ["./foo/bar" "foo" "SomeClass"] = "./foo/bar/foo/SomeClass.java"
  when ["/foo/bar/" "a.b.c" "A"] = "/foo/bar/a/b/c/A.java")




