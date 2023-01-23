(ns sme-ui.app.acreage
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

(defn test-polygon [] [:div
                       [:div
                        [:h1 {:style {:color "red"}} "In flight"]
                        [:h3 "Two Dimensional Area Survey"]]
                       [:div.col-sm-3
                        [:button {:title "Add Point"} "Add Point"]]
                       [:div.col-sm-9
                        [:svg {:height "2500" :width "2500"}
                         [:polygon {:points (stringify-points @polygon)
                                    :fill "green" :stroke "black"}]]]])



