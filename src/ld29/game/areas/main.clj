(ns ld29.game.areas.main
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity coral
  (make-commands [[:look "coral"] "There are several growths of coral with squiggly reaching tendrils covering the ocean floor"]
                 [[:get "coral"] (if (entity-here? :coral) ["You bury some of the leafy growth in your backpack, it could come in handy?" (move-entity :coral :main :inventory)] "You already plucked enough of this squishy goodness for now.")]))

(defentity backpack
  (make-commands [[:look :backpack] "Your backpack is your favourite posession. It is a deluxe starfish backpack with premium eel stitching."]))

(defarea main
  "The center of the big blue, as far as you know."
  [coral]
  (make-commands [[:look] "You are in a wide open area in the ocean. There is some coral on the ocean floor, and a box by your feet."
                  (when ( entity-at? :school :seahorse) "There is a school of seahorses to the north")]
   [[ :look "north"] "To the north is a wild school of seahorses"]))
