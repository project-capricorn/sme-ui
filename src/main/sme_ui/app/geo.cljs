(ns sme-ui.app.geo
  (:require  [sme-ui.app.util :as util]))

(defn to-radians
  "Converts from degrees to radians"
  [degrees]
  (* degrees (/ Math/PI 180)))

(defn to-degrees
  "Converts from radians to degrees"
  [radians]
  (* radians (/ 180 Math/PI)))

(defn to-cartesian
  "Converts from polar to cartesian coordinates"
  [r theta]
  (let [x (* r (Math/cos (to-radians theta)))
        y (* r (Math/sin (to-radians theta)))]
    [x y]))

(defn get-angle
  "Returns an angle in radians"
  [point]
  (let [[x y] point
        angle (Math/atan2 y x)]
    (if (<= angle 0)
      (+ (* 2 Math/PI) angle)
      angle)))

(defn get-distance
  "Returns the distance of a point from the origin"
  [point]
  (let [[x y] point]
    (Math/sqrt (+ (* x x) (* y y)))))

(defn transform
  "Shift a vector of points by x-shift, y-shift"
  [points x-shift y-shift]
  (map (fn [point] (let [[x y] point] [(+ x x-shift) (+ y y-shift)])) points))

(defn get-centroid
  "Returns the geometric center of points"
  [points]
  (let [num-points (count points)
        x-mean (/ (apply + (map first points)) num-points)
        y-mean (/ (apply + (map second points)) num-points)]
    [x-mean y-mean]))

(defn compare-clockwise
  ""
  [p1 p2]
  (let [a1 (get-angle p1)
        a2 (get-angle p2)
        d1 (get-distance p1)
        d2 (get-distance p2)]
    (cond
      (> a1 a2) true
      (and (= a1 a2) (< d1 d2)) true
      :else false)))

(defn sort-anti-clock [points]
  (let [[x-mean y-mean] (get-centroid points) 
        trans (transform points (- x-mean) (- y-mean))]
    trans))


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

