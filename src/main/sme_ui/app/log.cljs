(ns sme-ui.app.log)

(defn cons-log-atom [state] #(js/console.log @state))
