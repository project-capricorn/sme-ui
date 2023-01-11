(ns sme-ui.app.geo
  (:require  [sme-ui.app.util :as util]))

(defn to-rad
  "Converts from degrees to radians"
  [deg]
  (* deg (/ Math/PI 180)))

(defn to-deg
  "Converts from radians to degrees"
  [rad]
  (* rad (/ 180 Math/PI)))

(defn to-cart
  "Converts from polar to cartesian coordinates"
  ([pt] (to-cart (first pt) (second pt)))
  ([r theta]
   (let [x (* r (Math/cos (to-rad theta)))
         y (* r (Math/sin (to-rad theta)))]
     [x y])))

(defn get-angle
  "Returns an angle in radians"
  [pt]
  (let [[x y] pt
        angle (Math/atan2 y x)]
    (if (<= angle 0)
      (+ (* 2 Math/PI) angle)
      angle)))

(defn get-coords
  "Takes a collection of vectors (magnitude angle pairs). Returns a collection of x, y coords
  representing the succesive sums of those vectors"
  [pts]
  (let [pt (to-cart (first pts))]
    (reduce (fn [acc val] (conj acc (into [] (map + (to-cart val) (last acc)))))
            [pt]
            (rest pts))))

(defn get-distance
  "Returns the distance of a point from the origin"
  [pt]
  (let [[x y] pt]
    (Math/sqrt (+ (* x x) (* y y)))))

(defn get-shift-pt
  "Returns the minimum distance x and y must shift to transform points to the first quadrant"
  [pts]
  (let [x-min (apply min (map first pts))
        y-min (apply min (map second pts))]
    (cond
      (and (neg? x-min) (neg? y-min)) [x-min y-min]
      (neg? x-min) [x-min 0]
      (neg? y-min) [0 y-min]
      :else [0 0])))

(defn transform
  "Shift a vector of points by x-shift, y-shift"
  [pts x-shift y-shift]
  (map (fn [pt] (let [[x y] pt] [(+ x x-shift) (+ y y-shift)])) pts))

(defn get-centroid
  "Returns the geometric center of points"
  [pts]
  (let [num-pts (count pts)
        x-mean (/ (apply + (map first pts)) num-pts)
        y-mean (/ (apply + (map second pts)) num-pts)]
    [x-mean y-mean]))

(defn comp-anti-clock
  "True if point `p1` produces an angle or distance less than point `p2` "
  [p1 p2]
  (let [a1 (get-angle p1)
        a2 (get-angle p2)
        d1 (get-distance p1)
        d2 (get-distance p2)]
    (cond
      (< a1 a2) true
      (and (= a1 a2) (< d1 d2)) true
      :else false)))

(defn sort-anti-clock
  "Sorts points in anti(counter)-clockwise order via comparator `co`"
  [co pts]
  (let [[x-mean y-mean] (get-centroid pts)
        trans (transform pts (- x-mean) (- y-mean))
        sorted (sort co trans)]
    (into [] (transform sorted x-mean y-mean))))

(defn lace
  "Apples the Shoelace formula (Gauss's area formula) to `pts` 
  representing a simple polygon"
  [pts]
  (let [fpt (first pts) lpt (last pts)]
    (loop [acc1 0 acc2 0 rem pts]
      (if (nil? (second rem))
        (/ (Math/abs (- (+ acc1 (* (first lpt) (last fpt)))
                        (+ acc2 (* (last lpt) (first fpt))))) 2)
        (recur (+ acc1 (* (first (first rem))
                          (second (second rem))))
               (+ acc2 (* (first (second rem))
                          (second (first rem))))
               (rest rem))))))

(defn get-poly-area
  "Gets the area of vectors `vcs` forming a simple polygon"
  [vcs]
  (let [coords (get-coords vcs)
        sorted (sort-anti-clock comp-anti-clock coords)]
    (lace sorted)))

