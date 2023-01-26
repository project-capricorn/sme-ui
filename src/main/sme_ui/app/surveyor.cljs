(ns sme-ui.app.surveyor
  (:require [clojure.string :as string]
            [reagent.core :as r]))

(def polygon-placeholder [[0 0] [100 0] [100 100] [0 100]])

(def polygon (r/atom polygon-placeholder))

(defn input-point [i] [:div [:div.well
                             [:label {:for (str "d" i)} (str "Distance " i)]
                             [:input {:type "number" :id (str "d" i)}]
                             [:label {:for (str "a" i)} (str "Angle " i)]
                             [:input {:type "number" :id (str "a" i)}]]])

(def inputs (r/atom []))

(defn stringify-points [poly]
  (apply str (reduce #(conj %1 (str (string/join "," %2) " ")) [] poly)))

(defn survey [] [:div
                 [:div
                  [:h1 "Area Surveyor"]
                  [:p "Calculating the area of a pasture, grazing site, or other parcel of
land may be difficult when: "]
                  [:ul
                   [:li "Access to GIS or GPS data is constrained by limited cellular or other 
network access"]
                   [:li "Access to more sophisticated surveying equiptment is not available"]]
                  [:p "This tool facilitates the measurement of two-dimensional spaces using, at
minumum: a baseplate compass, a measuring device (preferrably a wheel), and an instrument for recording data if internet access is unavailable."]
                  [:h3 "Limitations"]
                  [:p "Points surveyed must form a simple polygon, that is: " [:em "a flat shape consisting of 
straight, non-intersecting line segments or 'sides' that are joined pairwise to form a single closed path. If the sides intersect then the polygon is not simple."]]
                  [:h3 "Instructions"]
                  [:img {:src "compass.jpg" :style {:box-shadow  "10px 10px 5px lightblue" :margin-bottom "2%"}}]
                  [:p "Beginning at any location on the perimiter of the area you wish to survey, 
hold the compass flat and point the Direction of Travel Arrow towards your target.
Next, rotate the Compass Housing so that the red end of the magnetic needle sits inside the
the Orienteering Arrow, as seen above. Record the degree measured in the center of the Direction of Travel Arrow. 
This degree represents your direction."]
                  [:p "Using your measuring device, measure the distance between your current location 
and your target in a straight line. This is your distance. Record it next to your direction. From this point, 
repeat the process until you have a list of distance and direction measurements for each point on the perimiter 
of your site. The last point surveyed will automatically by connected to the first point."]]
                 [:div.col-sm-3
                  [:h3 {:style {:color "red"}} "In flight"]
                  [:button {:title "Add Point"} "Add Point"]]
                 [:div.col-sm-9
                  [:h3 {:style {:color "red"}} "In flight"]
                  [:svg {:height "2500" :width "2500"}
                   [:polygon {:points (stringify-points @polygon)
                              :fill "green" :stroke "black"}]]]])



