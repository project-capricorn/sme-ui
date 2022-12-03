(ns ^{:author "Andrew Brown"
      :doc "Provides mappings between Unicode symbols, their names, and the functions 
they correspond to if applicable. Generally symbols are given as part of a sorted map,
in order of increasing value."}
 sme-ui.app.sym
  (:require [sme-ui.app.util :as util]))

(def set-sym
  "Unicode symbols associated with number sets."
  (sorted-map
   \u2115 "Natural numbers: {1, 2, 3, ...}"
   \u2124 "Integers: {..., -1, 0, 1, ...}"))

(def un-op-sym
  "Unicode symbols associated with unary operators."
  {\u002D "Negation"})

(def bin-op-sym
  "Unicode symbols associated with binary operators."
  (sorted-map \u003C "Less than"
              \u003D "Equal to"
              \u003E "Greater than"
              \u2260 "Not equal to"
              \u2264 "Less than or equal to"
              \u2265 "Greater than or equal to"))

(def num-sym
  "Unicode symbols associated with numerals."
  (sorted-map \u0030 "Zero"
              \u0031 "One"
              \u0032 "Two"
              \u0033 "Three"
              \u0034 "Four"
              \u0035 "Five"
              \u0036 "Six"
              \u0037 "Seven"
              \u0038 "Eight"
              \u0039 "Nine"))

(def sub-sym
  "Unicode symbols associated with symbols used as variables."
  {\u0078 \u0078})

(def op-to-func
  "Maps Unicode symbols to Clojure functions."
  (zipmap (keys bin-op-sym) [< = > not= <= >=]))

(def nat-numbers
  "A lazy seq representing the natural numbers"
  (iterate inc 1))

(def neg-ints
  "A lazy seq representing the negative integers"
  (iterate dec 0))
