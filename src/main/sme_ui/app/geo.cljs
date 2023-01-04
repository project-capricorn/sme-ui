(ns sme-ui.app.geo
  (:require  [sme-ui.app.util :as util]))

(defn to-radians [degrees]
  "Convert from degrees to radians"
  (* degrees (/ Math/PI 180)))

(defn to-cartesian [rho theta]
  "Convert from polar to cartesian coordinates"
  (let [x (* rho (Math/cos (to-radians theta)))
        y (* rho (Math/sin (to-radians theta)))]
    [x y]))

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

