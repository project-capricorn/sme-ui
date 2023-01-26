(ns sme-ui.app.core
  (:require
   [reagent.core :as r]
   [sme-ui.app.set-builder :as set-builder]
   [sme-ui.app.home :as home]
   [sme-ui.app.ack :as ack]
   [sme-ui.app.about :as about]
   [sme-ui.app.contact :as contact]
   [sme-ui.app.surveyor :as surveyor]
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
                    [:li [:a {:href (rfe/href ::contact)} "Contact"]]
                    [:li {:class "dropdown"}
                     [:a {:class "dropdown-toggle" :data-toggle "dropdown" :href "#"} "Agtech"
                      [:span {:class "caret"}]]
                     [:ul {:class "dropdown-menu"}
                      [:li
                       [:a {:href (rfe/href ::acreage)} "Surveyor"]]]]
                    [:li {:class "dropdown"}
                     [:a {:class "dropdown-toggle" :data-toggle "dropdown" :href "#"} "Math"
                      [:span {:class "caret"}]]
                     [:ul {:class "dropdown-menu"}
                      [:li
                       [:a {:href (rfe/href ::set-builder)} "Set Builder"]]]]]]]])

(defn set-builder [] [:div
                      [:div.col-sm-4 [set-builder/notes]]
                      [:div.col-sm-3 [set-builder/keypad]]
                      [:div.col-sm-5 [set-builder/result]]])

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
     :view home/home}]

   ["/set-builder"
    {:name ::set-builder
     :view set-builder}]

   ["/acreage"
    {:name ::acreage
     :view surveyor/survey}]

   ["/about"
    {:name ::about
     :view about/about}]

   ["/acks"
    {:name ::acks
     :view ack/acks}]

   ["/contact"
    {:name ::contact
     :view  contact/contact}]])

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
