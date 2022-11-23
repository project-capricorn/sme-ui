(ns sme-ui.app.core
  (:require [sme-ui.app.sym :as sym])
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(def predicate-placeholder ["P(x)"])

(def set-placeholder "Set")

(def a-set (r/atom set-placeholder))

(def predicate (r/atom predicate-placeholder))

(defn buttons-from [mappings f] (reduce
                                 (fn [acc cur]
                                   (let [sym (key cur) desc (val cur)]
                                     (conj acc [:button.pad-keys
                                                {:title desc :on-click #(f sym)}
                                                sym]))) [:div] mappings))

(defn set-builder [] [:div
                      [:h3 "{ x \u2208 "
                       [:span.border-red {:title "Set"} @a-set] " | "
                       [:span.border-red {:title "Predicate"}
                        (apply str @predicate)] " }"]])

(defn clear-pred-place [] (when (= (first @predicate)
                                   (first predicate-placeholder))
                            (reset! predicate [])))

(defn append-to-pred [x]
  (clear-pred-place)
  (swap! predicate conj x)
  (js/console.log @predicate))

(defn keypad []
  [:div
   [:h3 "Sets"]
   (buttons-from sym/set-sym (fn [val] (reset! a-set val)))
   [:h3 "Operators"]
   (buttons-from sym/op-sym append-to-pred)
   [:h3 "Numerals"]
   (buttons-from sym/num-sym append-to-pred)
   [:h3 "Subjects"]
   (buttons-from sym/sub-sym append-to-pred)
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
