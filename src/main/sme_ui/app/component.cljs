(ns sme-ui.app.component)

(defn series
  "Produces a placeholder for a series using the given tag, symbol,
and n number terms"
  [tag sym terms]
  (reduce #(conj %1 sym [:sub %2] " ") [tag] (range 1 terms)))

(defn buttons-from
  "Returns a collection of styled buttons from a map of names and descriptions.
:on-click behavior is derived from the supplied function and contents of maps keys."
  [mappings f]
  (reduce (fn [acc cur]
            (let [sym (key cur) desc (val cur)]
              (conj acc [:button.pad-keys
                         {:title desc
                          :on-click #(f sym)} sym]))) [:div] mappings))
