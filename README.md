[![Clojars Project](https://img.shields.io/clojars/v/rgm/tailwind-hiccup.svg)](https://clojars.org/rgm/tailwind-hiccup)

# tailwind-hiccup

tailwindcss + hiccup = 👍👍.

## Rationale

By its nature, a functional/atomic/utility class approach to CSS like
[Tailwindcss][tw] turns out to be pretty pleasant to use with Hiccup-based
ClojureScript front-ends like [Reagent][reagent] or [Rum][rum], or server-side
Hiccup templates.

Styling becomes a matter of composing from a standard library of utility
classes. Since they're just data, we can keep them in collections, give them
names, `conj` them together, etc.

Better still on the front-end side: the dead-JavaScript-elimination available
through advanced ClojureScript compilation sets up for impressive [dead-CSS-
elimination][purgecss] performance from PurgeCSS. Any Tailwind class name
strings present in unused components will have been dropped from the JS bundle
along with the component, so the Google Closure compiler is doing most of the
work of figuring out which components could ever be used at runtime.

## Getting started

Add tailwind-hiccup as a dependency, eg. for tools.deps projects

```clojure
;; deps.edn
{:paths [,,,]
 :deps {,,, rgm/tailwind-hiccup {:mvn/version "0.2.0"} ,,,}
```

Setting up the css build can be a little complex. See the [basic usage
example][basic-example].

## API

Inspired by [`stylefy.core/use-style`][stylefy], this library provides a
function `tw` that gives a easy-to-spot way to snap together collections of
utility classes in Hiccup props:

```clojure
(require '[tailwind-hiccup.core :refer [tw]]

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
[basic-example]: https://github.com/rgm/tailwind-hiccup/tree/master/examples/basic
