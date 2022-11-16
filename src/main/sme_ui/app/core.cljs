(ns sme-ui.app.core
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(def predicate-placeholder ["P(x)"])

(def set-placeholder "Set")

(def a-set (r/atom set-placeholder))

(def predicate (r/atom predicate-placeholder))

(def set-symbols (sorted-map "Real numbers" \u211D
                             "Integers" \u2124
                             "Natural numbers" \u2115))

(defn buttons-from [mappings f] (reduce
                      (fn [acc cur]
                        (let [desc (key cur)
                              sym (val cur)]
                          (conj acc [:button.pad-keys {:title desc
                                                       :on-click #(f sym)}
                                     sym]))) [:div] mappings))

(defn set-builder [] [:div
                      [:h3 "{ x \u2208 "
                       [:span.border-red @a-set] " | "
                       [:span.border-red (apply str @predicate)] " }"]])

(defn keypad []
  [:div
   [:h3 "Common sets"]
   (buttons-from set-symbols (fn [val] (reset! a-set val)))
   [:h3 "Relational operators"]
   [:button.pad-keys {:title "Less than" :on-click #(swap! predicate conj \<)} \<]
   [:button.pad-keys {:title "Less than or equal to" :on-click #(swap! predicate conj \u2264)} \u2264]
   [:button.pad-keys {:title "Equal to"} \=]
   [:button.pad-keys {:title "Not equal to"} \u2260]
   [:button.pad-keys {:title "Greater than or equal to"} \u2265]
   [:button.pad-keys {:title "Great than"} \>]
   [:h3 "Predicate Controls"]
   [:button.pad-keys {:title "Reset the predicate"
                      :on-click #((reset! a-set set-placeholder)
                                  (reset! predicate predicate-placeholder))} "Reset"]
   [:button.pad-keys {:title "Delete last charater"} "Backspace"]])

(defn render []
  (rdom/render [:div [keypad] [set-builder]] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
