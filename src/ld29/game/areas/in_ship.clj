(ns ld29.game.areas.in-ship
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]
        [ld29.game.areas.seahorse :only [school-in-ship-door-opened
                                         school-in-ship-door-closed]]))


(defentity toilet-brush
  "A toilet brush"
  (make-commands [[:look :toilet-brush]
                  "This is an incredibly sturdy toilet brush. It could even be used as a weapon. "]
                 [[:get :toilet-brush]
                  (cond (entity-here? :toilet-brush)
                    ["You snatch that brush up and weild it fiercely. You are a tiger, a lion, a... sea lion? The point is, it makes you feel pretty darn tough. "
                     (move-entity :toilet-brush :inventory)]
                    (entity-at? :toilet-brush :inventory)
                    "You mean this toilet brush you have right here?"
                    :else
                    "What toilet brush?")]
                 ))




(defarea in-ship
  "Inside of the weedy ruins of a sunken ship."
  [toilet-brush]
  (make-commands
   [[:look | :look :latrine] "There are plentiful weeds and slimy mosses covering all of the surfaces in here as well. They don't look like they'd be as easy to grab up though."
    (cond
     (and (get-area-state :door-opened) (entity-here? :school))
     `(school-in-ship-door-opened)
     (entity-here? :school)
     `(school-in-ship-door-closed)
     (and (get-area-state :door-opened) (entity-here? :toilet-brush))
     "All that's left in the latrine is the toilet brush."
     :else
     "There is nothing left here."
     )]
   [[:open :door] "As you open the door the silly seahorses begin storming down the drain of the latrine you just accessed for them. "(set-area-state :door-opened true)]
   [[:leave | :leave :ship | :go | :go :west]
    (move-player :ship)]
   ))
