(ns sme-ui.app.util)

(defn non-numeric? [x] (js/Number.isNaN (js/parseInt x)))

(def numeric? (complement non-numeric?))
