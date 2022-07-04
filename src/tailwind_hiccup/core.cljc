(ns tailwind-hiccup.core
  (:require
   [clojure.string :as string]))

(defn- tw->str [x] (if (keyword? x) (name x) (str x)))

(defn- simplify-classes [x] (if (coll? x) (flatten x) x))

(defn tw
  "Merge tailwind class collections into props. Gives the tailwind call sites a
   bit more meaning to spot/get a hold of them later.

   Accepts one or more collections of keywords or strings, plus an optional
   props map (similar to stylefy.core/use-style). Keywords tend to be less
   noisy as ClojureScript syntax. But many of the responsive tailwind styles
   will have to be supplied as strings (eg.  \"hover:text-bold\"), since the
   inboard colon means the reader can't parse it as a keyword.

   Examples:
   (tw [:a :b] {:on-click f}) => {:class [\"a\" \"b\"] :on-click f}
   (tw [:a] [\"b:c\"] [:d])   => {:class [\"a\" \"b:c\" \"d\"]}
   "
  [& xs]
  (let [has-props? (map? (last xs))
        props      (if has-props? (last xs) {})
        classes    (->> (if has-props? (butlast xs) xs)
                        (apply concat)
                        (mapv tw->str))]
    (-> (merge-with merge {:class classes} props)
        (update :class simplify-classes))))

(defn- tw->prefixed-str
  "Convert keyword or string to a properly-prefixed class string. Just tacks the
   prefix on for non-variant classes. For variant classes, eg. hover:font-bold,
   we have to nestle the prefix between the variant and the base class, eg.
   hover:xx-font-bold if prefix is xx-."
  [prefix x]
  (let [s (tw->str x)
        variant? #(string/includes? % ":")]
    (if (variant? s)
      (let [[variant base] (string/split s #":" 2)]
        (str variant ":" prefix base))
      (str prefix s))))

(defn twp
  "Similar to `tw`, but accepts an initial prefix to apply such that tailwind styles
   don't conflict with others.

   Examples:
   (twp \"tw-\" [:a :b] {:on-click f}) => {:class [\"tw-a\" \"tw-b\"] :on-click f}

   See also https://tailwindcss.com/docs/configuration#prefix."
  [prefix & xs]
  (let [has-props? (map? (last xs))
        props      (if has-props? (last xs) {})
        classes    (->> (if has-props? (butlast xs) xs)
                        (apply concat)
                        (mapv (partial tw->prefixed-str prefix)))]
    (-> (merge-with merge {:class classes} props)
        (update :class simplify-classes))))
