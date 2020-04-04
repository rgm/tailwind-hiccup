# tailwind-cljs

tailwindcss + clojurescript = 👍👍.

## Rationale

By its nature, a functional/atomic/utility class approach to CSS like
[Tailwindcss][tw] turns out to be pretty pleasant to use with Hiccup-based
ClojureScript front-ends like [Reagent][reagent] or [Rum][rum].

Styling becomes a matter of composing from a standard library of utility
classes. Since they're just data, we can keep them in collections, give them
names, `conj` them together, etc.

Better still: the dead-JavaScript-elimination available through advanced
ClojureScript compilation sets up for impressive [dead-CSS-
elimination][purgecss] performance from PurgeCSS, since any Tailwind class name
strings present in unused components will have been dropped from the JS bundle
along with the component.

## API

Inspired by [`stylefy.core/use-style`][stylefy], this library provides a
function `tw` that gives a easy-to-spot way to snap together collections of
utility classes in Hiccup props:

```clojure
(def color-transition [:transition-colors :ease-in-out])
(def short-duration [:duration-300])
(def hover-colors ["hover:text-white" "hover:bg-red-500"])

(defn MyButton
  [button-text]
  [:button.a-non-tw-class
   (tw [:mx-3 :my-4 :font-bold]
       hover-colors
       color-transition short-duration
       {:on-click #(js/alert "surprise!")})
   button-text])
```

[tw]: https://tailwindcss.com
[reagent]: https://github.com/reagent-project/reagent
[rum]: https://github.com/tonsky/rum
[stylefy]: https://github.com/Jarzka/stylefy
[purgecss]: https://tailwindcss.com/docs/controlling-file-size#removing-unused-css
