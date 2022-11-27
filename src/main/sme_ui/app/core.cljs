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

(defn operator? [term] (some #(= % term) (keys sym/op-sym)))

(defn min-terms? [pred] (>= (count pred) 3))

(defn max-terms? [pred] (<= (count pred) 5))

(defn count-ops [pred] (count (filter (fn [x] (operator? x)) pred)))

(defn decomp-exps [pred] (let [first-exp (take 3 pred)
                               second-exp (drop 2 pred)]
                           [first-exp second-exp]))

(defn infix? [pred]  (and (not (operator? (first pred)))
                          (operator? (second pred))))

(defn infixed? [pred] (let [exps (decomp-exps pred)
                            first-exp (first exps)
                            second-exp (second exps)]
                        (cond
                          (and (= 3 (count pred)) (infix? first-exp)) true
                          (and (= 5 (count pred)) (infix? second-exp)) true
                          :else false)))

(defn max-ops? [pred]  (let [sym-count (count pred) op-count (count-ops pred)]
                         (cond (and (= sym-count 3) (= op-count 1)) true
                               (and (= sym-count 5) (= op-count 2)) true
                               :else false)))

(defn validate-pred [& fns] (if (every? true? (map #(% @predicate) fns))
                              :span.border-green
                              :span.border-red))

(def validators [min-terms? infixed? max-terms? max-ops?])

(defn set-builder [] [:div
                      [:h3 "{ x \u2208 "
                       [(validate-set-sym) {:title "Set"} @a-set] " | "
                       [(apply validate-pred validators) {:title "Predicate"}
                        (string/join " " @predicate)] " }"]])

(defn clear-pred-place [] (when (= (first @predicate)
                                   (first predicate-placeholder))
                            (reset! predicate [])))

(defn append-to-pred [x] (clear-pred-place) (swap! predicate conj x))

(defn concat-num [x] (let [num (last @predicate)]
                       (if (util/non-numeric? num)
                         (append-to-pred x)
                         (do
                           (clear-pred-place)
                           (swap! predicate pop)
                           (swap! predicate conj (str num x))))))

(defn keypad []
  [:div
   [:header
    [:h1 "SME Online"]]
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
   [:button.pad-keys {:title "Delete last charater"} "Backspace"]
   [:button.pad-keys {:title "Evaluate expression"} "Eval"]])

(defn render []
  (rdom/render [:div [keypad] [set-builder]] (.getElementById js/document "root")))

(defn ^:export main []
  (render))

(defn ^:dev/after-load reload! []
  (render))
