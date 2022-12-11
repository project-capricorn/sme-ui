(ns sme-ui.app.core
  (:require
   [reagent.core :as r]
   [sme-ui.app.set-builder :as sb]
   [sme-ui.app.home :as h]
   [sme-ui.app.ack :as ack]
   [sme-ui.app.about :as a]
   [sme-ui.app.acreage :as ac]
   [reagent.dom :as rdom]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]))

(defn header [] [:div.jumbotron [:header [:h1 "Project Capricorn"]]])

(defn navbar [] [:nav {:class "navbar navbar-default"}
                 [:div {:class "container-fluid"}
                  [:div {:class "navbar-header"}
                   [:button {:type "button" :class "navbar-toggle", :data-toggle "collapse", :data-target "#myNavbar"}
                    (for [_ (range 3)] [:span {:class "icon-bar"}])]
                   [:a {:class "navbar-brand" :href "#"} "Project Capricorn"]]
                  [:div {:class "collapse navbar-collapse" :id "myNavbar"}
                   [:ul {:class "nav navbar-nav"}
                    [:li [:a {:href "#"} "Home"]]
                    [:li [:a {:href (rfe/href ::about)} "About"]]
                    [:li [:a {:href (rfe/href ::acks)} "Acks"]]
                    [:li {:class "dropdown"}
                     [:a {:class "dropdown-toggle" :data-toggle "dropdown" :href "#"} "Agtech"
                      [:span {:class "caret"}]]
                     [:ul {:class "dropdown-menu"}
                      [:li
                       [:a {:href (rfe/href ::acreage)} "Acreage"]]]]
                    [:li {:class "dropdown"}
                     [:a {:class "dropdown-toggle" :data-toggle "dropdown" :href "#"} "Math"
                      [:span {:class "caret"}]]
                     [:ul {:class "dropdown-menu"}
                      [:li
                       [:a {:href (rfe/href ::set-builder)} "Set Builder"]]]]]]]])

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

(defn footer [] [:footer.footer
                 [:div.container
                  [:p.text-muted "Some stuff"]]])

(def routes
  [["/"
    {:name ::home
     :view h/home}]

   ["/set-builder"
    {:name ::set-builder
     :view set-builder}]

   ["/acreage"
    {:name ::acreage
     :view ac/test-polygon}]

   ["/about"
    {:name ::about
     :view a/about}]

   ["/acks"
    {:name ::acks
     :view ack/acks}]])

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
