(ns sme-ui.app.core
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(def expression (r/atom "This is a fish"))

(defn foo [] 
  [:input 
   {:type "button" 
    :value "[" 
    :on-click #(reset! expression (str @expression "["))}])

(defn bar [] [:p @expression])

(defn render []
  (rdom/render [:div [foo] [bar]] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
