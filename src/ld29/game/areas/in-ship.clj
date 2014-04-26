(ns ld29.game.areas.in-ship
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]
        [ld29.game.areas.seahorse :only [school-in-ship-door-opened
                                         school-in-ship-door-closed]]))

(defarea in-ship
  "Inside of the weedy ruins of a sunken ship."
  (make-commands
   [[:look] "There are plentiful weeds and slimy mosses covering all of the surfaces in here as well."
    (cond
     (and (get-area-state :door-opened) (entity-here? :school))
     (school-in-ship-door-opened)
     (entity-here? :school)
     (school-in-ship-door-closed)
     (and (get-area-state :door-opened) (entity-here? :toilet-brush))
     "All that's left in the latrine is the toilet brush."
     )]
   [[:open :door] "As you open the door the silly seahorses begin storming down the drain of the latrine you just accessed for them. "(set-area-state :door-opened true)]                 ))
