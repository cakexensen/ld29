(ns ld29.game.areas.ship
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defarea ship
  "Outside of the weedy ruins of a sunken ship."
  (make-commands
   [[:look] "There are plentiful weeds and slimy mosses covering what looks like it was once a wooden schooner."
    (when (entity-here? :school)
      [ "You see the seahorses entering the ruin through a splintered hole in the side of the ship."
        (move-entity :school :in-ship)])]
                 ))
