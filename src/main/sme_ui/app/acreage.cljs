(ns sme-ui.app.acreage
  (:require [clojure.string :as string]
            [reagent.core :as r]))

(def polygon-placeholder [[100 600] [300 100] [700 200] [400 400] [800 500]])

(def polygon (r/atom polygon-placeholder))

(def points-placeholder 4)

(def points (r/atom points-placeholder))

(def inputs-placeholder [:div (for [_ (range points-placeholder)] [:input.text])])

(def inputs (r/atom inputs-placeholder))

(defn stringify-points [poly]
  (apply str (reduce #(conj %1 (str (string/join "," %2) " ")) [] poly)))

(defn input-from [points] (for [_ (range points)] [:input.text]))

(defn test-polygon [] [:div
                       [:div.col-sm-2
                        [:h3 {:style {:color "red"}} "In flight"]
                        [:label {:for "quantity"} "Points in polygon: "]
                        [:input {:type "number"
                                 :min "3"
                                 :max "50"
                                 :on-change #(let [val (-> % .-target .-value)]
                                               (reset! inputs (input-from val)))}] @inputs]
                       [:div.col-sm-10
                        [:svg {:height "2500" :width "2500"}
                         [:polygon {:points (stringify-points @polygon)
                                    :fill "green" :stroke "black"}]]]])

(defn to-radians [degrees]
  (* degrees (/ Math/PI 180)))

(defn law-of-cosines [a b y]
  (Math/sqrt (-
              (+
               (* a a)
               (* b b))
              (* 2 a b (Math/cos (to-radians y))))))

(defn get-quadrant [deg]
  
  (cond
    (and (> deg 0) (< deg 90)) 1
    (and (> deg 90) (< deg 180)) 2
    (and (> deg 180) (< deg 270)) 3
    (and (> deg 270) (< deg 360)) 4
    :else (get-quadrant (mod deg 360))))

(defn lace
  "Determines the area of a simple polygon via the Shoelace Formula. 
Takes a vector of x, y coordinate vectors given in counter clockwise order"
  [points]
  (let [f (first points) l (last points)]
    (loop [acc1 0 acc2 0 rem points]
      (if (nil? (second rem))
        (/ (Math/abs (- (+ acc1 (* (first l) (last f)))
                        (+ acc2 (* (last l) (first f))))) 2)
        (recur (+ acc1 (* (first (first rem))
                          (second (second rem))))
               (+ acc2 (* (first (second rem))
                          (second (first rem))))
               (rest rem))))))

(defn normalize [points] nil)
