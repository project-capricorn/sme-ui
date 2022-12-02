(ns sme-ui.app.core
  (:require [clojure.string :as string])
  (:require [sme-ui.app.sym :as sym])
  (:require [sme-ui.app.log :as log])
  (:require [sme-ui.app.util :as util])
  (:require [sme-ui.app.parse :as parse])
  (:require [sme-ui.app.component :as component])
  (:require [reagent.dom :as rdom])
  (:require [reagent.core :as r]))

(def predicate-placeholder
  "The default expression shown for the predicate on app startup and reset"
  ["P(x)"])

(def set-placeholder
  "The default string shown for the set on app startup and reset"
  "Set")

(def eval-placeholder
  "The default series shown for the evaluated expression on app startup and reset"
  (component/series :h3 "n" 6))

(def a-set
  "A reference to a character representing the set to which the predicate is applied"
  (r/atom set-placeholder))

(def predicate
  "A reference to a vector representing the predicate. Composed of infixed operations"
  (r/atom predicate-placeholder))

(def eval-pred
  "A reference to the evaluated predicate"
  (r/atom eval-placeholder))

(def log-pred
  "Logs the predicate to the console"
  (log/cons-log-atom predicate))

(defn valid-pred?
  "Takes a collection of functions and applies each function to the predicate. Returns true
if all functions evaluate to true"
  [fns] (every? true? (map #(% @predicate) fns)))

(defn apply-to-pred
  "Returns if-case if predicate is valid otherwise else-case"
  [if-case else-case & fns] (if (valid-pred? fns) if-case else-case))

(def border-pred
  "Styles the predicate given its validity"
  (partial apply-to-pred :span.border-green :span.border-red))

(def disable-pred-eval
  "Disables predicate evaluation given its validity"
  (partial apply-to-pred false true))

(defn valid-set?
  "Returns true if a set has been selected from the list of set symbols"
  [] (some #(= @a-set %) (keys sym/set-sym)))

(defn apply-to-set
  "Returns if-case if set is valid otherwise else-case"
  [if-case else-case] (if (valid-set?) if-case  else-case))

(def border-set-sym
  "Styles the set given its validity"
  (partial apply-to-set :span.border-green :span.border-red))

(def disable-set-eval
  "Disables set evaluation given its validity"
  (partial apply-to-set false true))

(def validators
  "A collection of functions used to validate the predicate"
  [parse/min-terms? parse/infixed? parse/max-terms? parse/max-ops? parse/min-sub-x?])

(defn disable-eval
  "Returns true if either the chosen set or predicate are not valid"
  [] (or (disable-set-eval) (apply disable-pred-eval validators)))

(defn clear-pred-place!
  "Removes the default element from the predicate collection"
  [] (when (= (first @predicate) (first predicate-placeholder)) (reset! predicate [])))

(defn append-to-pred!
  "Adds a user selected symbol to the predicate collection"
  [x] (clear-pred-place!) (swap! predicate conj x))

(defn concat-num!
  "If the last position of the predicate is numeric that position is popped and replaced
with the currently selected numeral bound to the previous one. Otherwise it is assumed
the current numeral is beginning a new number and that numeral is conj'd with the vector."
  [x] (let [num (last @predicate)]
        (if (and (not= num \u002D) (util/non-numeric? num))
          (append-to-pred! x)
          (do
            (clear-pred-place!)
            (swap! predicate pop)
            (swap! predicate conj (str num x))))))

(defn eval-expression [])

(defn header [] [:div.jumbotron [:header [:h1 "SME Online"]]])

(defn notes [] [:div.col-sm-6
                [:p "The SME Set Builder allows you to build sets by applying "
                 [:em "valid"] " predicates to integers."]
                [:p "The set builder notation "
                 [:strong "{ x \u2208 S | P(x) } "] "is read formally as, "
                 [:em "The set of all elements x in S such that P(x) is true."]]
                 [:p "Here,  "
                 [:strong \u2208] " denotes "
                 [:em "is an element of, "]
                 [:strong "S"] " represents a set, "
                 [:strong "x"] " an element of that set, and "
                 [:strong " P(x)"] " is a function that evaluates to true or false (a predicate). Finally, "
                 [:strong "|"] "  means "
                 [:em "such that "], "indicating the predicate should evaluate to true given x."]
                [:ul 
                 [:li "Predicates may consist of two or three terms and one or two operators respectively"]
                 [:li "Predicates must contain at least one subject"]]

                [:p "Both the chosen set and the predicate will display a green background when valid. If the entire expression is valid the "
                 [:em "Eval "] "button will be enabled to compute the set."]])

(defn keypad []
  [:div
   [:h3 "Sets"]
   (component/buttons-from sym/set-sym (fn [val] (reset! a-set val)))
   [:h3 "Binary Operators"]
   (component/buttons-from sym/bin-op-sym append-to-pred!)
   [:h3 "Unary Operators"]
   (component/buttons-from sym/un-op-sym append-to-pred!)
   [:h3 "Numerals"]
   (component/buttons-from sym/num-sym concat-num!)
   [:h3 "Subjects"]
   (component/buttons-from sym/sub-sym append-to-pred!)
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
  (rdom/render [:div
                [header]
                [notes]
                [:div.col-sm-6
                 [keypad]
                 [set-builder]
                 [:h3 @eval-pred]]]
               (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
