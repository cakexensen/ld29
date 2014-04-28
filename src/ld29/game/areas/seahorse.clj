(ns ld29.game.areas.seahorse
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))


(defentity school
  ""
  (make-commands
   [[:look :school]
    (case (current-area-id)
      :seahorse
      ["The seahorses scatter as you get closer. " (move-entity :school :ship)]
      :ship
      (school-by-ship)
      :in-ship
      (if (get-area-state :door-opened)
        (school-in-ship-door-opened)
        (school-in-ship-door-closed)) 
      "They've already gone elsewhere. ")]
   [[:get :school]
    (if (entity-here? :school)
      ["As you reach out toward the seahorses they scatter in all directions, leaving you all alone. "
       (move-entity :school :ship)]
      "They've already gone elsewhere. ")]))

(defn school-in-ship-door-opened
  []
  ["You grab your chin off of the floor as the last seahorse swirls down the toilet hole. Did they just intentionally flush themselves? " (remove-entity :school)])

(defn school-in-ship-door-closed
  []
  "You see the seahorses gathering by a door on your left. Their insistent bubbles request its opening.")

(defn school-by-ship
  []
  ["The seahorses enter the ruin through a splintered hole in the side of the ship. "
   (move-entity :school :in-ship)])


(defarea seahorse
  "A great place for seahorses to gather. "
  [school]
  (make-commands
   [[:look]
    (cond
     (entity-here? :school)
     "You watch the seahorses from a distance. They awkwardly bob up and down at irregular intervals, as though each one is in step with a different song. "
     (entity-here? :teenager)
     "That teenage mermaid that kicked you in the sack is here."
     :else
     "The seahorses seem to've departed. There's not much here. ")]
   [[:look :north]
    (if (get-game-state :all-clear)
      "You can see a hut over there."
      "It's too murky to see much over there.")]
   [[:look :south]
    (if (entity-at? :thugs :main)
      "There are some merfolk milling around."
      "There's a big bunch of coral over there.")]
   [[:look :east]
    (if (get-game-state :all-clear)
      "There's a cave over that way."
      "It's too murky to see much over there.")]
   [[:look :west]
    "You see an inescapable cliff face. Best not to bother with."]
   [[:go :north]
    (if (get-game-state :all-clear)
      (move-player :wiz)
      "It's too murky to go that way right now.")]
   [[:go :south]
    (move-player :main)]
   [[:go :east]
    (if (get-game-state  :all-clear)
      (move-player :cave)
      "It's too murky to go that way right now.")]))
