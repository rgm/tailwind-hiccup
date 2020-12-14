(ns ^:figwheel-hooks tailwind-hiccup-example.core
  (:require
   [rum.core             :as rum]
   [tailwind-hiccup.core :refer [tw]]))

(rum/defc
 TestLayout
 []
 [:div
  [:h1 (tw [:mt-4 :font-bold :text-red-500 :text-3xl])
   "this text should be bold red if everything worked"]
  [:h2.green-example (tw [:my-4])
   "And this should be green. Go check out the style assembled with " [:code "@apply"]
   " in " [:code "styles.src.css"]]
  [:p (tw [:mt-10])
   "Try " [:code "make prd"]
   " and take a look at the dead CSS elimination. All that should be there is
    the normalize styles and a small handful of the tailwindcss styles that
    have actually been used in the advanced compilation build or this host HTML
    page."]])

(defn ^:after-load mount
  []
  (rum/mount (TestLayout "this text should be bold if everything worked")
             (.getElementById js/document "app")))

(defn ^:export init
  []
  ;; init-db here
  (mount))
