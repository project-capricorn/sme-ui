(ns sme-ui.app.parse
  (:require [sme-ui.app.sym :as sym])
  (:require [sme-ui.app.util :as util]))

;; TODO:
;; There is some overlap in the functions providing predicate validation
;; These functions can be refactored

(defn operator?
  "True if the symbol represents an operator"
  [term] (some #(= % term) (keys sym/bin-op-sym)))

(defn min-terms?
  "True if there are at least 3 elements (operators, variables, scalars) in the predicate"
  [pred] (>= (count pred) 3))

(defn max-terms?
  "True if there are less than 6 elements (operators, variables, scalars) in the predicate"
  [pred] (<= (count pred) 5))

(defn count-ops
  "Returns the number of operators in the predicate"
  [pred] (count (filter (fn [x] (operator? x)) pred)))

(defn decomp-exps
  "Returns a vector consisting of the parts of a compound inequality"
  [pred] (let [first-exp (take 3 pred)
               second-exp (drop 2 pred)]
           [first-exp second-exp]))

(defn infix?
  "True if a binary operation has the operator in the second position and not the first"
  [pred] (and (not (operator? (first pred)))
              (operator? (second pred))))

(defn infixed?
  "True if the entire predicate contains infixed operations"
  [pred] (let [[first-exp second-exp] (decomp-exps pred)]
           (cond
             (and (= 3 (count pred)) (infix? first-exp)) true
             (and (= 5 (count pred)) (infix? second-exp)) true
             :else false)))

(defn max-ops?
  "True if there is one binary operator for a predicate containing a single inequality
and two binary operators for a compound inequality"
  [pred]  (let [sym-count (count pred) op-count (count-ops pred)]
            (cond (and (= sym-count 3) (= op-count 1)) true
                  (and (= sym-count 5) (= op-count 2)) true
                  :else false)))

(defn min-sub-x?
  "Enforces that the subject be added to the predicate"
  [pred] (>= (get (frequencies pred) \u0078) 1))

(defn applied-negation?
  "True if the negation symbol is not applied to a numeral"
  [pred] (not= (last pred) \u002D))

(def validators
  "A collection of functions used to validate the predicate"
  [min-terms? infixed? max-terms? max-ops? min-sub-x? applied-negation?])

(defn function-from [sym-list]
  "Returns a Clojure function built from a list of symbols representing an infixed binary operation."
  (let [[fir sec thi] sym-list]
    (if (util/numeric? fir)
      #(sec (js/parseInt fir) %)
      #(sec % (js/parseInt thi)))))

(defn set-from [set-sym])
