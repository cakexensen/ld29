(ns ld29.game.dictionary
  (:use [ld29.game.command]))

(defn make-dictionary
  "creates the game dictionary"
  []
  (make-command-dictionary
   [:look ["look" | "view" | "examine" | "inspect" | "check" "out" | "perceive"]]
   [:look-at [:look | "look" "at"]]
   [:get ["get" | "take" | "acquire" | "attain"]]))
