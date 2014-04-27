(ns ld29.game.areas.house
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity seal
  ""
  (make-commands
   [[:look-at :seal]
    "It is a silly seal that smiles and sashays and sallies."]))


(defarea house
  "An adorable underwater cottage."
  [seal]
  (make-commands
   [[:look]
    (if (entity-here? :seal)
      ["You come to an adorable underwater cottage-style home complete with a well-manicured lawn. You see a silly seal sashaying eastward." (move-entity :seal :shark)])]
   [[:look :east]
    "You see the area where the shark-man once stood. You get the feeling he mugs people there often."]
   [[:look :south]
    "You see the mer-village over that way, complete with a tabernacle."]
   [[:go :east]
    (move-player :shark)]
   [[:go :south]
    (move-player :village)]
   ))
