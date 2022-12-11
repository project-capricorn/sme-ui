(ns sme-ui.app.about)

(defn about
  "Returns a reagent component containng the contents of the about page"
  []
  [:div
   [:h3 "The Author"]
   [:p "Andrew Brown is a software developer from Minnesota with industry experience in hospitality, 
                     e-commerce, healthcare, and manufacturing."]
   [:h3 "The Name"]
   [:p [:strong "Project Capricorn "] "is named in honor of Jocelyn Brown's "
    [:a {:href "https://www.restorationlandandlivestock.com/" :target "_blank"} "prescribed grazing company"]
    ", which uses livestock (mainly goats) to reduce the risk of wildfire and mitigate the spread of invasive species in California."]
[:h3 "The Goal"]
[:p [:strong "Project Capricorn "] " hopes to generate interest in, and reinforce, functional programming 
skills through solving domain specific problems presented by experts in those domains."]
[:h3 "The Rational"]])
