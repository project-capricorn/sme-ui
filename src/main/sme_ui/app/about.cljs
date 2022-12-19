(ns sme-ui.app.about)

(defn about
  "Returns a reagent component containng the contents of the about page"
  []
  [:div
   [:div  {:class "panel panel-default"}
    [:div.panel-heading
     [:h3 "The Author"]]
    [:div.panel-body
     [:p "Andrew Brown is a software developer from Minnesota with industry experience in hospitality, e-commerce, healthcare, and manufacturing."]]]
   [:div {:class "panel panel-default"}
    [:div.panel-heading
     [:h3 "The Name"]]
    [:div.panel-body
     [:p [:strong "Project Capricorn "] "is named in honor of Jocelyn Brown's "
      [:a {:href "https://www.restorationlandandlivestock.com/" :target "_blank"} "prescribed grazing company"]
      ", which uses livestock (mainly goats) to reduce the risk of wildfire and mitigate the spread of invasive species in California."]]]
   [:div {:class "panel panel-default"}
    [:div.panel-heading
     [:h3 "The Goal"]]
    [:div.panel-body
     [:p [:strong "Project Capricorn "] " hopes to generate interest in, and reinforce, functional programming 
skills through solving domain specific problems presented by experts in those domains."]]]
   [:div {:class "panel panel-default"}
    [:div.panel-heading
     [:h3 "Incentives"]]
    [:div.panel-body
     [:p "Functional programming languages enjoy a fairly small industry footprint when compared to their
object-oriented cousins. Naturally, limited opportunities to practice functional programming (FP) professionally reduce the pool of skilled candidates for the positions that do exist. Project Capricorn attempts to incentivize programmers through catalyzing job growth in the field."]
[:p "For SMEs the incentive is clear - tap into a resource that can help you create and leverage software."]]]])
