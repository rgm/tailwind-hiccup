(ns tailwind-hiccup.core-test
  (:require
   [clojure.test :as t :refer [deftest is]]
   [tailwind-hiccup.core :as sut]))

(deftest single-no-props
  (is (= {:class ["font-bold"]}
         (sut/tw [:font-bold]))))

(deftest single-with-props
  (let [f (fn [])]
    (is (= {:on-click f :class ["font-bold"]}
           (sut/tw [:font-bold] {:on-click f})))))

(deftest multi
  (is (= {:class ["font-bold" "mt-4" "hover:font-normal"]}
         (sut/tw [:font-bold] [:mt-4 "hover:font-normal"]))))

(deftest multi-with-props
  (let [f (fn [])]
    (is (= {:on-click f :class ["font-bold" "text-center"]}
           (sut/tw [:font-bold] [:text-center] {:on-click f})))))

(deftest multi-with-props-and-prefix
  (let [f (fn [])]
    (is (= {:on-click f :class ["tw-font-bold" "tw-text-center"]}
           (sut/twp "tw-" [:font-bold] [:text-center] {:on-click f})))))

(deftest tw->prefixed-str-test
  (let [f #'sut/tw->prefixed-str] ;; suppress not-public warning
    (is (= "tw-font-bold" (f "tw-" "font-bold")))
    (is (= "hover:tw-font-bold" (f "tw-" "hover:font-bold")))))

(deftest prefix-with-variants
  (is (= {:class ["hover:tw-font-bold" "lg:tw-bg-red-500"]}
         (sut/twp "tw-" ["hover:font-bold" "lg:bg-red-500"]))))
