(ns sme-ui.app.surveyor
  (:require [clojure.string :as string]
            [reagent.core :as r]
            [sme-ui.app.component :as component]))

(def polygon-placeholder [[0 0] [100 0] [100 100] [0 100]])

(def polygon (r/atom polygon-placeholder))

(def inputs (r/atom []))

(defn input-component [i]
  (let [dir (r/atom 0) dis (r/atom 0)]
    [:div
     [:div.well
      [:label {:for (str "dir" i)} (str "Direction " i)]
      [:input {:type "number"
               :id (str "dir" i)
               :on-change #(reset! dir (-> % .-target .-value))}]
      [:label {:for (str "dis" i)} (str "Distance " i)]
      [:input {:type "number"
               :id (str "dis" i)
               :on-change #(reset! dis (-> % .-target .-value))}]]]))

(def point-num (r/atom 1))

(def input-components (r/atom [:div (input-component @point-num)]))

(defn stringify-points [poly]
  (apply str (reduce #(conj %1 (str (string/join "," %2) " ")) [] poly)))

;; TODO :: Properly collect atoms
(defn parse-inputs [input-components]
  (let [[_ [_ & i]] input-components
        pts (filter #(= :input (first %)) i)]
    (loop [acc [] rem pts]
      (let [[dir dis] rem]
        (if (nil? dir) acc
            (recur (conj acc [(component/parse-int dir)
                              (component/parse-int dis)])
                   (next (rest rem))))))))

(defn survey [] [:div
                 [:div
                  [:h1 "Area Surveyor"]
                  [:p "Calculating the area of a pasture, grazing site, 
                       or other parcel of land may be difficult when: "]
                  [:ul
                   [:li "Access to GIS or GPS data is constrained by limited cellular or other 
                         network access"]
                   [:li "Access to more sophisticated surveying equiptment is not available"]]
                  [:p "This tool facilitates the measurement of two-dimensional spaces using, at
                       minumum: a baseplate compass, a measuring device (preferrably a wheel), 
                       and an instrument for recording data if internet access is unavailable."]
                  [:h3 "Limitations"]
                  [:p "Points surveyed must form a simple polygon, that is: " [:em "a flat shape consisting of 
                       straight, non-intersecting line segments or 'sides' that are joined pairwise to form a 
                       single closed path. If the sides intersect then the polygon is not simple."]]
                  [:h3 "Instructions"]
                  [:img {:src "compass.jpg" :style {:box-shadow  "10px 10px 5px lightblue" :margin-bottom "2%"}}]
                  [:p "Beginning at any location on the perimiter of the area you wish to survey, 
                       hold the compass flat and point the Direction of Travel Arrow (1) towards your target.
                       Next, rotate the Compass Housing (2) so that the red end of the magnetic needle (3) 
                       sits inside the Orienteering Arrow (4). Record the degree measured in the center of 
                       the Direction of Travel Arrow. The example image shows a reading of roughly 320\u00B0. 
                       This degree represents your direction."]
                  [:p "Using your measuring device, measure the distance between your current location 
                       and your target in a straight line. This is your distance. Record it next to your direction. 
                       From this point, repeat the process until you have a list of distance and direction 
                       measurements for each point on the perimiter of your site. E.g., the following readings 
                       should survey a square of 2,220 units."]
                  [:ul
                   [:li "Direction: 30, Distance: 37"]
                   [:li "Direction: 300, Distance: 60 "]
                   [:li "Direction: 210, Distance: 37"]
                   [:li "Direction: 120, Distance: 60"]]
                  [:p "The last point surveyed will automatically by connected to the first point."]]
                 [:div.col-sm-3
                  [:h3 {:style {:color "red"}} "In flight"]
                  [:button.btn-primary {:title "Add Point"
                                        :on-click #(swap! input-components conj
                                                          (input-component (swap! point-num inc)))}
                   "Add Point"]
                  [:button.btn-warning {:title "Remove Point"
                                        :on-click #(when (> @point-num 1)
                                                     (do (swap! point-num dec)
                                                         (swap! input-components pop)))}
                   "Remove Point"]
                  [:button.btn-danger {:title "Clear Points"
                                       :on-click #(do
                                                    (reset! point-num 0)
                                                    (reset! input-components [:div]))}
                   "Clear Points"]
                  [:div {:style {:margin-top "8px"}}
                   @input-components]]
                 [:div.col-sm-9
                  [:div]
                  [:h3 {:style {:color "red"}} "In flight"]
                  [:svg {:height "2500" :width "2500"}
                   [:polygon {:points (stringify-points @polygon)
                              :fill "green" :stroke "black"}]]]])
