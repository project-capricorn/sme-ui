(ns sme-ui.app.core
  (:require [clojure.string :as string])
  (:require [sme-ui.app.sym :as sym])
  (:require [sme-ui.app.log :as log])
  (:require [sme-ui.app.util :as util])
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(def predicate-placeholder ["P(x)"])

(def set-placeholder "Set")

(def a-set (r/atom set-placeholder))

(def predicate (r/atom predicate-placeholder))

(def log-pred (log/cons-log-atom predicate))

(defn buttons-from [mappings f] (reduce
                                 (fn [acc cur]
                                   (let [sym (key cur) desc (val cur)]
                                     (conj acc [:button.pad-keys
                                                {:title desc :on-click #(f sym)}
                                                sym]))) [:div] mappings))

(defn validate-set-sym [] (if (some #(= @a-set %) (keys sym/set-sym))
                            :span.border-green
                            :span.border-red))

(defn min-terms? [pred] (> (count pred) 2))

(defn validate-pred [& fns] (if (every? true? (map #(% @predicate) fns))
                              :span.border-green
                              :span.border-red))

(defn set-builder [] [:div
                      [:h3 "{ x \u2208 "
                       [(validate-set-sym) {:title "Set"} @a-set] " | "
                       [(validate-pred min-terms?) {:title "Predicate"}
                        (string/join " " @predicate)] " }"]])

(defn clear-pred-place [] (when (= (first @predicate)
                                   (first predicate-placeholder))
                            (reset! predicate [])))

(defn append-to-pred [x] (clear-pred-place) (swap! predicate conj x) (log-pred))

(defn concat-num [x] (let [num (last @predicate)]
                       (if (util/non-numeric? num)
                         (append-to-pred x)
                         (do
                           (clear-pred-place)
                           (swap! predicate pop)
                           (swap! predicate conj (str num x))
                           (log-pred)))))

(defn keypad []
  [:div
   [:h3 "Sets"]
   (buttons-from sym/set-sym (fn [val] (reset! a-set val)))
   [:h3 "Operators"]
   (buttons-from sym/op-sym append-to-pred)
   [:h3 "Numerals"]
   (buttons-from sym/num-sym concat-num)
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
