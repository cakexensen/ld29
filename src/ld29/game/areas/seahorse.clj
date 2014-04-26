(ns ld29.game.areas.seahorse
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity school
  (make-commands [[:look :school] (if (entity-here? :school) [ "The seahorses scatter as you get closer." (move-entity :school :seahorse :ship)] "They've already gone else.")]
                 [[:get :school] (if (entity-here? :school ["As you reach out toward the seahorses, they scatter in all directions, leaving you all alone." (move-entity :school :seahorse :ship)] "They've already gone elsewhere."))]))


(defarea seahorses
  "A great place for seahorses to gather."
  [school]
  (make-commands [[:look] (if (entity-here? :school) "You watch the seahorses from a distance. They awkwardly bob up and down at irregular intervals, as though each one is in step with a different song. To the north there is an odd " "The seahorses seem to've departed. There's not much here")]))
