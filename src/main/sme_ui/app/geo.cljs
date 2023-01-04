(ns sme-ui.app.geo
  (:require  [sme-ui.app.util :as util]))

(defn to-radians [degrees]
  (* degrees (/ Math/PI 180)))

(defn law-of-cosines [a b y]
  (Math/sqrt (-
              (+ (* a a) (* b b))
              (* 2 a b (Math/cos (to-radians y))))))

(defn get-quadrant [deg]
  (inc (quot (mod deg 360) 90)))

(defn get-coords [mag deg]
  (let [quad (get-quadrant deg)
        angle (mod deg 90)
        x (* mag (Math/sin (to-radians angle)))]
    [x mag]))

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

