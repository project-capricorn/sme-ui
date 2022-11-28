(ns sme-ui.app.sym)

(def set-sym (sorted-map \u2115 "Natural numbers: {1, 2, 3, ...}"
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


