(ns sme-ui.app.core
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(def a-set (r/atom \S))

(def an-expression (r/atom "relation"))

(defn set-builder [] [:div 
                      [:h3 "{ x \u2208 "  
                       [:span {:style {:border-style "dashed" :border-color "red" :padding-left "4px" :padding-right "4px"}} @a-set] " | " 
                       [:span {:style {:border-style "dashed" :border-color "red" :padding-left "4px" :padding-right "4px"}} @an-expression] " }"]])

(defn keypad []
  [:div
   [:h3 "Common sets"]
   [:button {:title "Negative real numbers" :style {:margin "5px"} :on-click #(reset! a-set "\u211D-")} "\u211D-"]
   [:button {:title "Real numbers" :style {:margin "5px"} :on-click #(reset! a-set "\u211D")} \u211D]
   [:button {:title "Positive real numbers" :style {:margin "5px"} :on-click #(reset! a-set "\u211D+")} "\u211D+"]
   [:button {:title "Negative integers" :style {:margin "5px"} :on-click #(reset! a-set "\u2124-")} "\u2124-"]
   [:button {:title "Integers" :style {:margin "5px"} :on-click #(reset! a-set "\u2124")} \u2124]
   [:button {:title "Positive integers" :style {:margin "5px"} :on-click #(reset! a-set "\u2124+")} "\u2124+"]
   [:button {:title "Natural numbers" :style {:margin "5px"} :on-click #(reset! a-set "\u2115")} "\u2115"]
   [:h3 "Relational operators"]
   [:button {:title "Less than" :style {:margin "5px"}} \<]
   [:button {:title "Less than or equal to" :style {:margin "5px"}} \u2264]
   [:button {:title "Equal to" :style {:margin "5px"}} \=]
   [:button {:title "Not equal to" :style {:margin "5px"}} \u2260]
   [:button {:title "Greater than or equal to" :style {:margin "5px"}} \u2265]
   [:button {:title "Great than" :style {:margin "5px"}} \>]])

(defn render []
  (rdom/render [:div [keypad] [set-builder]] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
