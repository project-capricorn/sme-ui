(ns sme-ui.app.core
  (:require
   [reagent.core :as r]
   [sme-ui.app.set-builder :as sb]
   [sme-ui.app.home :as h]
   [reagent.dom :as rdom]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]))

(defn header [] [:div.jumbotron [:header [:h1 "Project Capricorn"]]])

(defn navbar [] [:nav {:class "navbar navbar-inverse"}
                 [:div.container-fluid
                  [:div.navbar-header
                   [:a.navbar-brand {:href "#"} "Project Capricorn"]]
                  [:ul {:class "nav navbar-nav"}
                   [:li [:a {:href (rfe/href ::home)} "Home"]]
                   [:li [:a {:href (rfe/href ::set-builder)} "Sets"]]]]])

(defn set-builder [] [:div
                      [:div.col-sm-4 [sb/notes]]
                      [:div.col-sm-3 [sb/keypad]]
                      [:div.col-sm-5 [sb/result]]])

(defonce match (r/atom nil))

(defn current-page []
  [:div
   (when @match
     (let [view (:view (:data @match))]
       [view @match]))])

(def routes
  [["/"
    {:name ::home
     :view h/home}]

   ["/set-builder"
    {:name ::set-builder
     :view set-builder}]])

(defn init! []
  (rfe/start!
   (rf/router routes)
   (fn [m] (reset! match m)) {:use-fragment true})
  (rdom/render [:div
                [header]
                [navbar]
                [current-page]]
               (.getElementById js/document "root")))


(defn ^:export main []
  (init!))

(defn ^:dev/after-load reload! []
  (init!))