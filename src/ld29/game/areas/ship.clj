(ns ld29.game.areas.ship
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]
        [ld29.game.areas.seahorse :only [school-by-ship]]))

(defarea ship
  "Outside of the weedy ruins of a sunken ship."
  []
  (make-commands
   [[:look] "There are plentiful weeds and slimy mosses covering what looks like it was once a wooden schooner."
    (when (entity-here? :school)
      (school-by-ship))]
                 ))
