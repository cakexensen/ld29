(ns ld29.game.areas.ship
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]
        [ld29.game.areas.seahorse :only [school-by-ship]]))


(defentity slime
  "A handful of mucky yucky gooiness."
  (make-commands
   [[:look-at :slime]
    "A handful of this crap could muck up a huge area. It's so concentrated."]))

(defarea ship
  "Outside of the weedy ruins of a sunken ship."
  [slime]
  (make-commands
   [[:look] "There are plentiful weeds and slimy mosses covering what looks like it was once a wooden schooner."
    (when (entity-here? :school)
      `(school-by-ship))
    (when (entity-here? :seal)
      ["A seal passes by heading westward."
       (move-entity :seal :main)
       (set-game-state :all-clear true)])]
   [[:get :slime]
    (if (entity-at? :slime :inventory)
      "You already have enough slime."
      ["You grab a handful of slime and tuck it away in your bag. You try to keep it from touching your other things. It touches your other things." (move-entity :slime :inventory)])]
   [[:go :west]
    (move-player :main)]
   [[:go :south]
    (move-player :shark)]
   [[:go :north]
    (if (get-game-state :all-clear)
      (move-player :cave)
      "It's too murky to go that way right now.")]
   [[:go :ship]
    (move-player :in-ship)]
   [[:look :west]
    (if (entity-at? :thugs :main)
      "There are a bunch of merfolk over there."
      "There's not much over there except a bunch of coral.")]
   [[:look :south]
    (if (entity-at? :shark-man :shark)
      "There's a shark-man standing over there."
      "It looks completely abandoned over there.")]
   [[:look :north]
    (if (get-game-state  :all-clear)
      "You can see a cave up North from here."
      "It's too murky to see much that way.")]
   [[:look :east]
    "There are a lot of big and blue things over there. Oh wait, it's just a bunch of ocean. Nothing of particular interest."]
                 ))
