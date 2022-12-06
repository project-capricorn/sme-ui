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
                 [:div {:class "container-fluid"}
                  [:div {:class "navbar-header"}
                   [:button {:type "button", :class "navbar-toggle", :data-toggle "collapse", :data-target "#myNavbar"}
                    [:span {:class "icon-bar"}]
                    [:span {:class "icon-bar"}]
                    [:span {:class "icon-bar"}]]
                   [:a {:class "navbar-brand", :href "#"} "Project Capricorn"]]
                  [:div {:class "collapse navbar-collapse", :id "myNavbar"}
                   [:ul {:class "nav navbar-nav"}
                    [:li [:a {:href "#"} "Home"]]
                    [:li [:a {:href "#"} "About"]]
                    [:li [:a {:href "#"} "Acks"]]
                    [:li {:class "dropdown"}
                     [:a {:class "dropdown-toggle", :data-toggle "dropdown", :href "#"} "Math"
                      [:span {:class "caret"}]]
                     [:ul {:class "dropdown-menu"}
                      [:li
                       [:a {:href (rfe/href ::set-builder)} "Set Builder Notation"]]]]]]]])

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
