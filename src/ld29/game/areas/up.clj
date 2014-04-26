(ns ld29.game.areas.up
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defarea up
  "On your way towards the surface. "
  []
  (make-commands
   [[:go :up]
    (cond
     (get-area-state :shark :slime)
     (game-over "Just as you thought you were about to escape with your life, a slimy and ravenous shark-man with splinters in his teeth slithers up to you at incredible speed. You try to swim away, but he catches you and latches onto your leg. He shakes you and grinds his teeth slowly deeper into your flesh until your shredded tissue floats off, revealing a nearly clean bone before he goes for the rest of your limbs in turn. Your final score was 2.")
     (get-area-state :cave :rescue)
     (game-over "You're returned to your true form, but at what cost? You think of Marlune as you breathe the first breath of the rest of your journey above the surface. Your final score was 7. ")
     :else
     (game-over "You couldn't find it in your heart to care for the merfolk after all. There were even more idiots down below than there ever were up here. If you had to do it all again, you'd probably bring something more lethal than a toilet brush. Your final score was 0. "))]))
