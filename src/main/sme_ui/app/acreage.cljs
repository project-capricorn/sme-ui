(ns sme-ui.app.acreage)

(defn test-polygon [] [:div 
                       [:h3 {:style {:color "red"}} "In flight"]
                       [:svg {:height "500" :width "500"}
                       [:polygon {:points "220,10 300,210 170,250 123,234"}]]])
