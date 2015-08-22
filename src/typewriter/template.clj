(ns typewriter.template)

(defmacro $>>
  ([fun varname separator indent]
  `(->> (map ~fun ~varname)
        (clojure.string/join ~separator)
        (typewriter.util/indent ~indent)))
  ([fun varname separator]
  `(->> (map ~fun ~varname)
        (clojure.string/join ~separator)
        (typewriter.util/indent 0))))

(defmacro
  test-macro
  ([a b c d] [a b c d])
  ([a b c] (test-macro a b c :dummy))
  ([a b]   (test-macro a b :dummy2)))


