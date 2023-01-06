(ns sme-ui.app.util)

(defn non-numeric? [x] (js/Number.isNaN (js/parseInt x)))

(def numeric? (complement non-numeric?))

(defn third [x] (second (next x)))

(defn between? [val start end]
  "True if val is in the non-inclusive range of start to end"
  (if (<= start end)
    (and (> val start) (< val end))
    (and (< val start) (> val end))))

(defn appr [delta expected actual]
  "Tests equality within a givin tolerance"
  (< (Math/abs (- expected actual)) delta))

