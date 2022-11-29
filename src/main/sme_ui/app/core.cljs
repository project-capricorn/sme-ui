(ns sme-ui.app.core
  (:require [clojure.string :as string])
  (:require [sme-ui.app.sym :as sym])
  (:require [sme-ui.app.log :as log])
  (:require [sme-ui.app.util :as util])
  (:require [sme-ui.app.parse :as parse])
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(def predicate-placeholder ["P(x)"])

(def set-placeholder "Set")

(def a-set (r/atom set-placeholder))

(def predicate (r/atom predicate-placeholder))

(def log-pred (log/cons-log-atom predicate))

(defn buttons-from [mappings f] (reduce (fn [acc cur]
                                          (let [sym (key cur) desc (val cur)]
                                            (conj acc [:button.pad-keys
                                                       {:title desc :on-click #(f sym)}
                                                       sym]))) [:div] mappings))

(defn validate-pred [if-case else-case & fns] (if (every? true? (map #(% @predicate) fns)) if-case else-case))

(def border-pred (partial validate-pred :span.border-green :span.border-red))

(def disable-pred-eval (partial validate-pred false true))

(defn validate-set-sym [if-case else-case] (if (some #(= @a-set %) (keys sym/set-sym)) if-case  else-case))

(def border-set-sym (partial validate-set-sym :span.border-green :span.border-red))

(def disable-set-eval (partial validate-set-sym false true))

(def validators [parse/min-terms? parse/infixed? parse/max-terms? parse/max-ops?])

(defn disable-eval [] (or (disable-set-eval) (apply disable-pred-eval validators)))

(defn clear-pred-place! [] (when (= (first @predicate) (first predicate-placeholder))
                             (reset! predicate [])))

(defn append-to-pred! [x] (clear-pred-place!) (swap! predicate conj x))

(defn concat-num! [x] (let [num (last @predicate)] (if (and (not= num \u002D) (util/non-numeric? num))
                                                     (append-to-pred! x)
                                                     (do
                                                       (clear-pred-place!)
                                                       (swap! predicate pop)
                                                       (swap! predicate conj (str num x))))))

(defn header [] [:div [:header[:h1 "SME Online"]]])

(defn notes [] [:div 
                [:p "The SME Set Builder allows you to build sets by applying "
                 [:em "valid"] " predicates to integers."]
                [:p "The set builder notation " 
                 [:strong "{ x \u2208 S | P(x) } "] "is read formally as " 
                 [:em "The set of all elements x in S such that P(x) is true"]", where " 
                 [:strong \u2208] " denotes " 
                 [:em "is an element of, "]  
                 [:strong "S"] " represents a set, " 
                 [:strong "x"] " an element of that set, and " 
                 [:strong " P(x)"] " is a function that evaluates to true or false (a predicate). Finally, " 
                 [:strong "|"] "  means " 
                 [:em "such that "], "indicating the predicate should evaluate to true given x."]
                [:p "A valid predicate is one that can be evaluated (computed), although it may be obviously true or false. 
For instance, it is meaningful to apply the predicate " 
                 [:strong " 6 < 3 "] " to the set of natural numbers insofar as the predicate is always false. It is not meaningful to apply " 
                 [:strong "6 < "] " because the expression is incomplete."]
                [:p "Both the chosen set and the predicate will display a green border when valid. If the entire expression is valid the " 
                 [:em "Eval "] "button will be enabled to compute the set."]])

(defn keypad []
  [:div
   [:h3 "Sets"]
   (buttons-from sym/set-sym (fn [val] (reset! a-set val)))
   [:h3 "Binary Operators"]
   (buttons-from sym/bin-op-sym append-to-pred!)
   [:h3 "Unary Operators"]
   (buttons-from sym/un-op-sym append-to-pred!)
   [:h3 "Numerals"]
   (buttons-from sym/num-sym concat-num!)
   [:h3 "Subjects"]
   (buttons-from sym/sub-sym append-to-pred!)
   [:h3 "Predicate controls"]
   [:button.pad-keys {:title "Reset the predicate"
                      :on-click #((reset! a-set set-placeholder)
                                  (reset! predicate predicate-placeholder))} "Reset"]
   [:button.pad-keys {:title "Delete last charater"
                      :on-click #(swap! predicate pop)} "Backspace"]
   [:button.pad-keys {:title "Evaluate expression"
                      :disabled (disable-eval)} "Eval"]])

(defn set-builder [] [:div
                      [:h3 "{ x \u2208 "
                       [(border-set-sym) {:title "Set"} @a-set] " | "
                       [(apply border-pred validators) {:title "Predicate"}
                        (string/join " " @predicate)] " }"]])

(defn render []
  (rdom/render [:div [header] [notes] [keypad] [set-builder]] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
