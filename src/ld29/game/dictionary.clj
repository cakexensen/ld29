(ns ld29.game.dictionary
  (:use [ld29.game.command]))

(defn make-dictionary
  "creates the game dictionary"
  []
  (make-command-dictionary
   [:look ["look" | "view" | "examine" | "inspect" | "check" "out" | "perceive"]]
   [:look-at [:look | "look" "at"]]
   [:get ["get" | "take" | "acquire" | "attain"]]
   [:school ["school" | "seahorses" | "school" "of" "seahorses" | "seahorse"]]
   [:seal ["seal" | "silly" "seal"]]
   [:open ["open" | :look "inside"]]
   [:box ["box" | "cardboard" "box"]]
   [:open-door [:open :door | "turn" :door-knob | "open" :door-knob]]
   [:door ["door" | "portal" | "doorway"]]
   [:door-knob ["door" "knob" | "door" "handle" | "knob" | "handle"]]
   [:ship ["ship" | "sunken" "ship" | "ship" "ruin" | "sunken" "ship" "ruin" | "ship" "ruins" | "sunken" "ship" "ruins"]]
   [:toilet-brush ["toilet" "brush" | "brush"]]
   [:drain ["drain" | "toilet" "drain"]]))
