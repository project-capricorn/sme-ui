(ns sme-ui.app.home)

(defn home []
  [:div
   [:p "Project Capricorn is interested in creating mutually beneficial relationships between:"
    [:ul
     [:li "Programmers and programmers"]
     [:li "Programmers and subject matter (domain) experts"]]]
   [:p "For Subject Matter Experts (SMEs), we hope to provide an opportunity to create or improve on existing software by being paired with programmers."]
   [:p "For programmers - this site focuses on Clojure and ClojureScript. We hope it offers an opportunity to learn, teach, and ultimately increase industry adoption."]])
