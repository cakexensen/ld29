(ns ld29.game.areas.shark
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity shark-man
  ""
  (make-commands [[:look :shark-man]
                  `(battle-vs-shark-man)]
                 [[:battle :shark-man]
                  `(battle-vs-shark-man)]
                 [[:get :shark-man]
                  `(battle-vs-shark-man)]))


(defn win-vs-shark-man
  []
  ["Before you get a chance to examine the shark-man or the surroundings, he attacks you. "
   "You stuff your super sturdy toilet brush into the shark-man's mouth as he tries to make canned tuna out of you. With his jaw now stuck open he swims away, presumeably to remove the offending brush from his mouth. "
   (move-entity :shark-man :up)
   (remove-entity :toilet-brush :inventory)])

(defn lose-vs-shark-man
  []
  (game-over "Before you get a chance to examine the shark-man or the surroundings, he attacks you. The shark-man makes it quick and painless, snapping your neck before devouring you. If only you had a weapon. Your final score was -5. "))

(defn battle-vs-shark-man
  []
  (if (entity-at? :toilet-brush :inventory)
   `(win-vs-shark-man)
   `(lose-vs-shark-man)))




(defarea shark
  "An unfriendly shark-man's area."
  [shark-man]
  (make-commands [[:look]
                  (when (entity-here? :shark-man)
                      `(battle-vs-shark-man))
                  (when (entity-here? :seal)
                    [ "The slippery seal sneaks away to the north."
                      (move-entity :seal :ship)])]
                 [[:look :north]
                  "You see the mossy sunken ship back the way you came from."]
                 [[:look :west]
                  "You see a well-kept cottage."]
                 [[:look :east]
                  "You see the ocean in its entirety. The sheer size is overwhelming. There's not anything of interest to the immediate east, though."]
                 [[:look :south]
                  "There isn't anything of interest to the south of here."]
                 [[:go :north]
                  (move-player :ship)]
                 [[:go :west]
                  (move-player :house)]
                 [[:use :slime]
                  (if (entity-at? :slime :inventory)
                    [ "You smear the goo out of your pack and the whole area becomes a gooey mess. " (set-game-state :slime true) (remove-entity :slime :inventory)]
                    "What slime? you have no slime!")]
                 ))
