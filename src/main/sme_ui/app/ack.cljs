(ns sme-ui.app.ack)

(defn acks [] [:p "This site is built using " 
               [:a {:href "https://clojurescript.org/" :target "_blank"} "ClojureScript, "]
               [:a {:href "https://reagent-project.github.io/" :target "_blank"} "Reagent, "]
               [:a {:href "https://github.com/metosin/reitit" :target "_blank"} "reitit, and "]
               [:a {:href "https://getbootstrap.com/" :target "_blank"} "Bootstrap."]])