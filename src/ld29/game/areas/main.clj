(ns ld29.game.areas.main
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity coral
  "A piece of coral snatched from the ocean floor."
  (make-commands
   [[:look "coral"]
    "There are several growths of coral with squiggly reaching tendrils covering the ocean floor"]
   [[:get "coral"]
    (if (entity-here? :coral)
      ["You bury some of the leafy growth in your backpack, it could come in handy?"
       (move-entity :coral :inventory)]
      "You already plucked enough of this squishy goodness for now.")]))

(defentity top
  "A bikini top."
  (make-commands
   [[:look :top]
    "A couple of clam-shells and some string. Stereotypical mermaid-wear."]
   [[:get :top]
    (cond
     (entity-at? :top :main)
      ["You tuck the top away in your ultra classy backpack."
       (move-entity :top :inventory)]
      (entity-at? :top :inventory)
      "You mean the top that's already in your bag?"
      :else
      "What top?")]
   ))

(defarea main
  "The center of the big blue, as far as you know."
  [coral top]
  (make-commands [[:look]
                  "You are in a wide open area in the ocean. There is some coral on the ocean floor, and a box by your feet. There is a large shape to the east."
                  (when ( entity-at? :school :seahorse)
                    "There is a school of seahorses to the north.")
                  (when (entity-here? :seal)
                    "There is a slaphappy seal smiling stupidly. On a completely unrelated note, it seems a lot less murky around here.")
                  (when (entity-here? :thugs)
                    "There are a bunch of thugs hanging out here.")]
                 [[:look :north]
                  (if (entity-at? :school :seahorse)
                    "To the north is a wild school of seahorses."
                    "There's not much over there.")]
                 [[:look :east] "You see the tall outline of a sunken ship."]
                 [[:look :south]
                  (if (get-game-state :all-clear)
                    "You see a quaint cottage in that direction."
                    "It's too murky to see much that way.")]
                 [[:look :west]
                  "There's a sheer cliff face, it goes pretty far up, and you know the merfolk are down here somewhere. It's not a good idea to go that way."]
                 [[:look "box"]
                  (if (entity-here? :top)
                    "There is a swimsuit top in here."
                    "It's a smooth wooden box.")]
                 [[:go :north]
                  (move-player :seahorse)]
                 [[:go :east]
                  (move-player :ship)]
                 [[:go :south]
                  (if (get-game-state :all-clear)
                    (move-player :house)
                    "It's too murky to go that way right now")]
                 ))
