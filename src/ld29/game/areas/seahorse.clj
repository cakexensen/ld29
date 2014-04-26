(ns ld29.game.areas.seahorse
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defn school-in-ship-door-opened
  []
  ["You grab your chin off of the floor as the last seahorse swirls down the drain. Did they just intentionally flush themselves? " (remove-entity :school)])

(defn school-in-ship-door-closed
  []
  "You see the seahorses gathering by a door on your left. Their insistent bubbles request its opening.")

(defentity school
  ""
  (make-commands [[:look :school]
                  (case (current-area-id)
                    :seahorse
                    ["The seahorses scatter as you get closer. " (move-entity :school :ship)]
                    :ship
                    ["The seahorses enter the ruin through a splintered hole in the side of the ship. " (move-entity :school :in-ship)]
                    :in-ship
                    (if (get-area-state :door-opened)
                      (school-in-ship-door-opened)
                      (school-in-ship-door-closed)) 
                    "They've already gone elsewhere. ")]
                 [[:get :school]
                  (if (entity-here? :school)
                    ["As you reach out toward the seahorses they scatter in all directions, leaving you all alone. " (move-entity :school :ship)]
                    "They've already gone elsewhere. ")]))


(defarea seahorse
  "A great place for seahorses to gather. "
  [school]
  (make-commands [[:look] (cond
                           (entity-here? :school) "You watch the seahorses from a distance. They awkwardly bob up and down at irregular intervals, as though each one is in step with a different song. "
                           (entity-here? :seal) "A silly seal smiles. "
                           :else "The seahorses seem to've departed. There's not much here. ")]))
