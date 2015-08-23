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

