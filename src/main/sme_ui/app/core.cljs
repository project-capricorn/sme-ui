(ns sme-ui.app.core
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(def predicate-placeholder ["P(x)"])

(def set-placeholder "Set")

(def a-set (r/atom set-placeholder))

(def predicate (r/atom predicate-placeholder))

(defn set-builder [] [:div 
                      [:h3 "{ x \u2208 "  
                       [:span.border-red @a-set] " | " 
                       [:span.border-red (apply str @predicate)] " }"]])

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
   [:button {:title "Less than" :style {:margin "5px"} 
             :on-click #(swap! predicate conj \<)} \<]
   [:button {:title "Less than or equal to" :style {:margin "5px"}
             :on-click #(swap! predicate conj \u2264)} \u2264]
   [:button {:title "Equal to" :style {:margin "5px"}} \=]
   [:button {:title "Not equal to" :style {:margin "5px"}} \u2260]
   [:button {:title "Greater than or equal to" :style {:margin "5px"}} \u2265]
   [:button {:title "Great than" :style {:margin "5px"}} \>]
   [:h3 "Predicate Controls"]
   [:button {:title "Reset the predicate" 
             :style {:margin "5px"} 
             :on-click #(
                        (reset! a-set set-placeholder) 
                        (reset! predicate predicate-placeholder))} "Reset"]
   [:button {:title "Delete last charater" :style {:margin "5px"}} "Backspace"]])

(defn render []
  (rdom/render [:div [keypad] [set-builder]] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
