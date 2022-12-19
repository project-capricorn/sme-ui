(ns sme-ui.app.acreage
  (:require [clojure.string :as string]
            [reagent.core :as r]))

(def polygon-placeholder [[0 0] [0 250] [250 250] [250 0]])

(def polygon (r/atom polygon-placeholder))

(def points-placeholder 4)

(def points (r/atom points-placeholder))

(def inputs-placeholder (for [_ (range points-placeholder)] [:input.text]))

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
                                 :on-change #(let [val (-> % .-target .-value)] (reset! inputs (input-from val)))}]
                        @inputs]
                       [:div.col-sm-10
                        [:svg {:height "2500" :width "2500"
                               :style {:padding "2%"}}
                         [:polygon {:points (stringify-points @polygon)
                                    :fill "green" :stroke "black"}]]]])
