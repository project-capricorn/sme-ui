(ns sme-ui.app.geo-test
  (:require
   [cljs.test :refer-macros [deftest is]]
   [sme-ui.app.util :as util]
   [sme-ui.app.geo :as geo]))

(def roughly (partial util/appr 0.001))

(deftest to-rad
  (is (roughly 0 (geo/to-rad 0)))
  (is (roughly 0.523 (geo/to-rad 30)))
  (is (roughly 2.094 (geo/to-rad 120))
      (is (roughly 4.712 (geo/to-rad 270)))))

(deftest to-deg
  (is (roughly 0 (geo/to-deg 0)))
  (is (roughly 30 (geo/to-deg (/ Math/PI 6))))
  (is (roughly 90 (geo/to-deg (/ Math/PI 2))))
  (is (roughly 180 (geo/to-deg Math/PI))))

(deftest to-cart
  (is (roughly 2.50 (first (geo/to-cart 5 60))))
  (is (roughly 4.330 (second (geo/to-cart 5 60))))
  (is (roughly -1.811 (first (geo/to-cart 7 105))))
  (is (roughly 6.761 (second (geo/to-cart 7 105)))))

(deftest get-angle
  (is (roughly 53.130 (geo/to-deg (geo/get-angle [3 4]))))
  (is (roughly 126.87 (geo/to-deg (geo/get-angle [-3 4]))))
  (is (roughly 233.13 (geo/to-deg (geo/get-angle [-3 -4]))))
  (is (roughly 306.869 (geo/to-deg (geo/get-angle [3 -4])))))

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

(deftest get-shift-pt
  (is (= [0 0] (geo/get-shift-pt [[0 0] [0 1] [1 1] [1 0]])))
  (is (= [-1 -1] (geo/get-shift-pt [[1 1] [1 -1] [-1 -1] [-1 1]]))))

(deftest transform
  (is (= [[0 3] [0 0] [3 0] [3 3]] 
         (geo/transform [[-4 -1] [-4 -4] [-1 -4] [-1 -1]] 4 4))))

(deftest get-centroid
  (is (= [0 0] (geo/get-centroid [[3 4] [-3 4] [-3 -4] [3 -4]])))
  (is (= [2.5 -0.25] (geo/get-centroid [[1 4] [9 3] [-3 -4] [3 -4]]))))

(deftest comp-anti-clock
  (not (geo/comp-anti-clock [3 4] [-3 4])))

(deftest sort-anti-clock
  (is (= [[8 5] [4 4] [1 6] [3 1] [7 2]] 
         (geo/sort-anti-clock 
          geo/comp-anti-clock [[7 2] [1 6] [3 1] [8 5] [4 4]]))))

(deftest test-lace
  (is (= 16.5 (geo/lace [[7 2] [3 1] [1 6] [4 4] [8 5]])))
  (is (= 4 (geo/lace [[2 0] [2 2] [0 2] [0 0]]))))

(deftest get-poly-area
  (is (roughly 2220 (geo/get-poly-area [[37 30] [60 300] [37 210] [60 120]]))))

