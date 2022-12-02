(ns sme-ui.app.parse
  (:require [sme-ui.app.sym :as sym])
  (:require [sme-ui.app.util :as util]))

(defn operator? [term] (some #(= % term) (keys sym/bin-op-sym)))

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

(defn min-sub-x? [pred] (>= (get (frequencies pred) \u0078) 1))

(defn function-from [sym-list]
  "Returns a function built from a list of symbols representing 
an infixed binary operation."
  (let [fir (first sym-list)
        sec (get sym/op-to-func (second sym-list))
        thi (util/third sym-list)]
    (if (util/numeric? fir)
      #(sec (js/parseInt fir) %)
      #(sec % (js/parseInt thi)))))
