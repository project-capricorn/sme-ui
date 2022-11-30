(ns sme-ui.app.sym
  (:require [sme-ui.app.util :as util]))

(def set-sym (sorted-map 
                         \u2115 "Natural numbers: {0, 1, 2, 3, ...}"
                         \u2124 "Integers: {..., -1, 0, 1, ...}"))

(def un-op-sym {\u002D "Negation"})

(def bin-op-sym (sorted-map \u003C "Less than"
                        \u003D "Equal to"
                        \u003E "Greater than"
                        \u2260 "Not equal to"
                        \u2264 "Less than or equal to"
                        \u2265 "Greater than or equal to"))

(def num-sym (sorted-map \u0030 "Zero"
                         \u0031 "One"
                         \u0032 "Two"
                         \u0033 "Three"
                         \u0034 "Four"
                         \u0035 "Five"
                         \u0036 "Six"
                         \u0037 "Seven"
                         \u0038 "Eight"
                         \u0039 "Nine"))

(def sub-sym {\u0078 \u0078})

(def op-to-func (zipmap (keys bin-op-sym) [< = > not= <= >=]))

(def nat-numbers (iterate inc 0))

(defn function-from [sym-list]
  (let [fir (first sym-list)
        sec (get op-to-func (second sym-list))
        thi (util/third sym-list)]
    (print fir sec thi)
    (if (util/numeric? fir)
      #(sec (js/parseInt fir) %)
      #(sec % (js/parseInt thi)))))
