(ns ld29.game.areas.shark
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity shark-man
  ""
  (make-commands [[:look :shark-man]
                  (battle-vs-shark-man)]
                 [[:battle :shark-man]
                  (battle-vs-shark-man)]
                 [[:get :shark-man]
                  (battle-vs-shark-man)]))


(defn win-vs-shark-man
  []
  ["Before you get a chance to examine the shark-man or the surroundings, he attacks you. "
   "You stuff your super sturdy toilet brush into the shark-man's mouth as he tries to make canned tuna out of you. With his jaw now stuck open he swims away, presumeably to remove the offending brush from his mouth. "
   (move-entity :shark-man :up)
   (remove-entity :inventory :toilet-brush)])

(defn lose-vs-shark-man
  []
  (game-over "Before you get a chance to examine the shark-man or the surroundings, he attacks you. The shark-man makes it quick and painless, snapping your neck before devouring you. If only you had a weapon. Your final score was -5. "))

(defn battle-vs-shark-man
  []
  (cond
   (and (entity-here? :shark-man) (entity-at? :toilet-brush :inventory))
   (win-vs-shark-man)
   (entity-here? :shark-man)
   (lose-vs-shark-man)
   :else "The shark-man swam away. There's not much here now. "))




(defarea shark
  "An unfriendly shark-man's area. "
  [shark-man]
  (make-commands [[:look]
                  (battle-vs-shark-man)
                  (when (entity-here? :seal)
                    [ "The slippery seal sneaks away to the north. "
                      (move-entity :seal :ship)])]))
