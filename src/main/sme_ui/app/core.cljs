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

(def operator-symbols (sorted-map "Less than" \<
                                  "Less than or equal to" \u2265
                                  "Equal to" \=
                                  "Not equal to" \u2260
                                  "Greater than or equal to" \u2265
                                  "Greater than" \>))

(defn buttons-from [mappings f] (reduce
                                 (fn [acc cur]
                                   (let [desc (key cur) sym (val cur)]
                                     (conj acc [:button.pad-keys {:title desc :on-click #(f sym)}
                                                sym]))) [:div] mappings))

(defn set-builder [] [:div
                      [:h3 "{ x \u2208 "
                       [:span.border-red @a-set] " | "
                       [:span.border-red (apply str (reverse @predicate))] " }"]])

(defn clear-predicate-placeholder [] (if (= (first @predicate)
                                            (first predicate-placeholder))
                                       (swap! predicate rest)))

(defn keypad []
  [:div
   [:h3 "Common sets"]
   (buttons-from set-symbols (fn [val] (reset! a-set val)))
   [:h3 "Relational operators"]
   (buttons-from operator-symbols (fn [val]
                                    (clear-predicate-placeholder)
                                    (swap! predicate conj val)))
   [:h3 "Predicate controls"]
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
