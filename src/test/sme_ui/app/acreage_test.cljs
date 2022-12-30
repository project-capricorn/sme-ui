(ns sme-ui.app.acreage-test
  (:require [cljs.test :refer-macros [deftest is]]
            [sme-ui.app.acreage :as a]))


(deftest test-lace                              
  (let [points [[1 6] [3 1] [7 2] [4 4] [8 5]]] 
    (is (= 16.5 (a/lace points)))))       


(deftest test-to-radians                        
  (is (=  2.0943951023931953 (a/to-radians 120))))


(deftest test-law-of-cosines
  (is (=  14.933184523068078 (a/law-of-cosines 11 6 120))))

