(ns sme-ui.app.core
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(defn keypad [] 
  [:div 
   [:h3 "Common sets"]
   [:button {:title "Negative real numbers" :style {:margin "5px"}} "\u211D-"]
   [:button {:title "Real numbers" :style {:margin "5px"}} \u211D]
   [:button {:title "Positive real numbers" :style {:margin "5px"}} "\u211D+"]
   [:h3 "Relational operators"]
   [:button {:title "Less than" :style {:margin "5px"}} \<]
   [:button {:title "Less than or equal to" :style {:margin "5px"}} \u2264]
   [:button {:title "Equals" :style {:margin "5px"}} \=]
   [:button {:title "Greater than or equal to" :style {:margin "5px"}} \u2265]
   [:button {:title "Great than" :style {:margin "5px"}} \>]])

(defn render []
  (rdom/render [:div [keypad]] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
