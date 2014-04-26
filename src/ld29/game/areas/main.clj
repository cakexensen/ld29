(ns ld29.game.areas.main
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity coral
  (make-commands [[:look "coral"] "There are several growths of coral with squiggly reaching tendrils covering the ocean floor"]
                 [[:get "coral"] (if (entity-here? :coral) ["You bury some of the leafy growth in your backpack, it could come in handy?" (move-entity :coral :main :inventory)] "You already plucked enough of this squishy goodness for now.")]))

(defentity corpses
  (make-commands [[:look :corpses] "These look kinda fresh, kinda soggy, and quite blue. Misdelivered mail-order babies."]
                 [[:get :corpses] "Those wont fit in your backpack, also they look kinda gross."]))

(defarea main
  "The center of the big blue, as far as you know."
  [coral corpses]
  (make-commands [[:look] "You are in a wide open area in the ocean. There is some coral on the ocean floor, and a box by your feet."
                  (when ( entity-at? :school :seahorse) "There is a school of seahorses to the north")]
                 [[ :look "north"] "To the north is a wild school of seahorses"]
                 [[:look "box"] "It is full of dead human babies"]
                 ))
