(ns sme-ui.app.geo-test
  (:require
   [cljs.test :refer-macros [deftest is]]
   [sme-ui.app.util :as util]
   [sme-ui.app.geo :as geo]))

(def roughly (partial util/appr 0.001))

(deftest to-radians
  (is (roughly 0 (geo/to-radians 0)))
  (is (roughly 0.523 (geo/to-radians 30)))
  (is (roughly 2.094 (geo/to-radians 120))
      (is (roughly 4.712 (geo/to-radians 270)))))

(deftest to-degrees
  (is (roughly 0 (geo/to-degrees 0)))
  (is (roughly 30 (geo/to-degrees (/ Math/PI 6))))
  (is (roughly 90 (geo/to-degrees (/ Math/PI 2))))
  (is (roughly 180 (geo/to-degrees Math/PI))))

(deftest to-cartesian
  (is (roughly 2.50 (first (geo/to-cartesian 5 60))))
  (is (roughly 4.330 (second (geo/to-cartesian 5 60))))
  (is (roughly -1.811 (first (geo/to-cartesian 7 105))))
  (is (roughly 6.761 (second (geo/to-cartesian 7 105)))))

(deftest get-angle
  (is (roughly 53.130 (geo/to-degrees (geo/get-angle [3 4]))))
  (is (roughly 126.87 (geo/to-degrees (geo/get-angle [-3 4]))))
  (is (roughly 233.13 (geo/to-degrees (geo/get-angle [-3 -4]))))
  (is (roughly 306.869 (geo/to-degrees (geo/get-angle [3 -4])))))

(deftest get-coords
  (let [[[x1 y1] [x2 y2] [x3 y3]] 
        (geo/get-coords [[2 30] [3 45] [4 120]])]
    (is (roughly 1.732 x1))
    (is (roughly 1.000 y1))
    (is (roughly 3.853 x2))
    (is (roughly 3.121 y2))
    (is (roughly 1.853 x3))
    (is (roughly 6.585 y3))))

(deftest get-distance
  (is (= 5 (geo/get-distance [3 4])))
  (is (= 5 (geo/get-distance [-3 4])))
  (is (= 5 (geo/get-distance [-3 -4])))
  (is (= 5 (geo/get-distance [3 -4]))))

(deftest transform
  (is (= [[0 3] [0 0] [3 0] [3 3]] 
         (geo/transform [[-4 -1] [-4 -4] [-1 -4] [-1 -1]] 4 4))))

(deftest get-centroid
  (is (= [0 0] (geo/get-centroid [[3 4] [-3 4] [-3 -4] [3 -4]])))
  (is (= [2.5 -0.25] (geo/get-centroid [[1 4] [9 3] [-3 -4] [3 -4]]))))

(deftest compare-clockwise
  (not (geo/compare-clockwise [3 4] [-3 4])))

(deftest sort-clockwise
  (is (= [[7 2] [3 1] [1 6] [4 4] [8 5]] 
         (geo/sort-clockwise [[7 2] [1 6] [3 1] [8 5] [4 4]]))))

(deftest test-lace
  (let [points [[7 2] [3 1] [1 6] [4 4] [8 5]]]
    (is (= 16.5 (geo/lace points)))))
