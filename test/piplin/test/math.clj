(ns piplin.test.math
  (:use clojure.test)
  (:use [slingshot.slingshot :only [throw+]])
  (:use [piplin types math]))

(deftest j-long-test
  (is (= 3 (+ 1 2)))
  (is (= 6 (* 2 3))))

(deftest uintm-test
  (letfn [(um8 [v] (instance (uintm 8) v))]
    (is (= (+ (um8 200) 1) (um8 201)))
    (is (= (+ (um8 200) 60) (um8 4)))
    (is (= (+ 1 2 3 (um8 4)) (um8 10)))
    (is (= (- 0 (um8 1)) (um8 255)))))

(deftest binop-error-test
  (let [e (error "hi")]
    (is (= (try-errors (+ 0 (throw+ e))) [e]))
    (is (= (try-errors (+ 0 1 2 3 (throw+ e))) [e]))
    (is (= (try-errors (+ 0 1 (throw+ e) 3 (throw+ e))) [e e]))
    (is (= (try-errors (+ (throw+ e) 0)) [e]))
    (is (= (try-errors (+ (throw+ e) (throw+ e))) [e e]))))
