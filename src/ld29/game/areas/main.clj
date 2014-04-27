(ns ld29.game.areas.main
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity coral
  "A piece of coral snatched from the ocean floor."
  (make-commands [[:look "coral"] "There are several growths of coral with squiggly reaching tendrils covering the ocean floor"]
                 [[:get "coral"] (if (entity-here? :coral) ["You bury some of the leafy growth in your backpack, it could come in handy?" (move-entity :coral :inventory)] "You already plucked enough of this squishy goodness for now.")]))

(defentity top
  "A bikini top."
  (make-commands [[:look :top] "A couple of clam-shells and some string. Stereotypical mermaid-wear."]
                 [[:get :top] "You tuck the top away in your ultra classy backpack."
                  (move-entity :top :inventory)]))

(defarea main
  "The center of the big blue, as far as you know."
  [coral corpses]
  (make-commands [[:look] "You are in a wide open area in the ocean. There is some coral on the ocean floor, and a box by your feet."
                  (when ( entity-at? :school :seahorse) "There is a school of seahorses to the north.")]
                 [[:look :north]
                  (if (entity-at? :school :seahorse)
                    "To the north is a wild school of seahorses."
                    "There's not much over there.")]
                 [[:look :east] "You see the tall outline of a sunken ship."]
                 [[:look "box"] "There is a swimsuit top in here."]
                 [[:go :north] (move-player :seahorse)]
                 [[:go :east] (move-player :ship)]  ))
