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
      (school-by-ship))
    (when (entity-here? :seal)
      ["A seal passes by heading westward."
       (move-entity :seal :main)
       (set-area-state :main :all-clear true)])]
   [[:go :west]
    (move-player :main)]
   [[:go :south]
    (move-player :shark)]
   [[:go :north]
    (if (get-area-state :main :all-clear)
      (move-player :cave)
      "It's too murky to go that way right now.")]
   [[:look :west]
    (if (entity-at? :thugs :main)
      "There are a bunch of merfolk over there."
      "There's not much over there except a bunch of coral.")]
   [[:look :south]
    (if (entity-at? :shark-man :shark)
      "There's a shark-man standing over there."
      "It looks completely abandoned over there.")]
   [[:look :north]
    (if (get-area-state :main :all-clear)
      "You can see a cave up North from here."
      "It's too murky to see much that way.")]
                 ))
