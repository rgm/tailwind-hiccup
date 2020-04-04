(ns tw-cljs.core-test
  (:require
   [clojure.test :as t :refer [deftest is]]
   [tw-cljs.core :as sut]))

(deftest single-no-props
  (is (= {:class ["font-bold"]}
         (sut/tw [:font-bold]))))

(deftest single-with-props
  (let [f (fn [])]
    (is {:on-click f :class ["font-bold"]}
        (sut/tw [:font-bold] {:on-click f}))))

(deftest multi
  (is {:class ["font-bold" "mt-4" "hover:font-normal"]}
      (sut/tw [:font-bold] [:mt-4 "hover:font-normal"])))

(deftest multi-with-props
  (let [f (fn [])]
    (is {:on-click f :class ["font-bold" "text-center"]}
        (sut/tw [:font-bold] [:text-center] {:on-click f}))))
