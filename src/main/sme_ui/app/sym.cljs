(ns sme-ui.app.sym)

(def set-sym (sorted-map "Real numbers" \u211D
                         "Integers" \u2124
                         "Natural numbers" \u2115))

(def op-sym (sorted-map "Less than" \<
                              "Less than or equal to" \u2265
                              "Equal to" \=
                              "Not equal to" \u2260
                              "Greater than or equal to" \u2265
                              "Greater than" \>))

(def num-sym (sorted-map "Zero" \0
              "One" \1
              "Two" \2
              "Three" \3
              "Four" \4
              "Five" \5
              "Six" \6
              "Seven" \7
              "Eight" \8
              "Nine" \9))

(def sub-sym {"x" \x})
