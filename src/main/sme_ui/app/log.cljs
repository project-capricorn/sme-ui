(ns sme-ui.app.log)

(defn cons-log [x] (js/console.log x))

(defn cons-log-atom [state] #(js/console.log @state))
