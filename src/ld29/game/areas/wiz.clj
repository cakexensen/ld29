(ns ld29.game.areas.wiz
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defn gill-removal-dialogue
  [begged]
  (cond
   (= begged 0)
   "\"Really? Why'd you wanna go and\" *fart* \"do a thing like that? I can remove them, but I don't think it's a good idea.\""
   (= begged 1)
   "*fart* \"You sure? It's a long swim up to the surface.\" *fart*"
   (= begged 2)
   "\"If I do this, you won't be able to come back.\" *fart*"
   (= begged 3)
   "\"I'm gonna ask one more time.\" *fart* \"Are you absolutely sure you want to lose your gills?\""
   (>= begged 4)
   (let [wiz-msg "\"Well, okay then.\" The wizard raises his hands, farts, and speaks a magic incantation. You feel your gills receding, and you suddenly cannot breathe. \"You better start swimming to the surface now. Get a move on!\" He kicks you upward, and you hear one last fart before you begin your ascent. "]
     (cond
      (get-game-state  :slime)
      (game-over (str wiz-msg "Just as you thought you were about to escape with your life, a slimy and ravenous shark-man with splinters in his teeth slithers up to you and grabs your leg. He eats you. Your final score was 2."))
      (get-game-state  :rescue)
      (game-over (str wiz-msg "You're returned to your true form, but at what cost? You think of Marlune as you breathe the first breath of the rest of your journey above the surface. Your final score was 7. "))
      :else
      (game-over (str wiz-msg "You couldn't find it in your heart to care for the merfolk after all. There were even more idiots down below than there ever were up here. If you had to do it all again, you'd probably bring something more lethal than a toilet brush. Your final score was 0. "))))))

(defentity wizard
  ""
  (make-commands [[:look-at :wizard]
                  "A rather old and eccentric mer-wizard. His beard hangs long and obscures the sight of some odd trinkets worn about his neck. A battered magic fedora crowns his head."]
                 [[:look-at :beard]
                  "A long and unkempt mane of facial hair. Nothing particularly noteworthy about it other than its length."]
                 [[:look-at :fedora]
                  "Brown, wool, sparkly. Yup, definitely a wizard's fedora. The wizard must be pretty crazy to wear such a thing down here."]
                 [[:talk :wizard]
                  "\"Ah, how curious to see not just a human in my midst, but one blessed with gills!\" *fart* \"Would you be interested in doing me a little favour?\""
                  (set-entity-state :wizard :talked true)]
                 [[:ask :about :favour | :ask :wizard :about :favour]
                  (cond
                   (get-game-state :slime)
                   "\"Thanks for sliming that sharkman's place!\" *fart* \"Oh, I'd love to see the look on his face...\""
                   (get-entity-state :wizard :talked)
                   "*fart* \"Could you please take some slime from the sunken ship and spread it around the sharkman's hideout?\" *fart* \"He's been really getting on my nerves lately!\""
                   :else
                   "\"I don't think I mentioned a favour, but since you're interested,\" *fart* \"take some slime from the sunken ship and spread it around the sharkman's hideout. He's been really getting on my nerves lately!\"")]
                 [[:ask :gills | :ask :about :removing :gills | :ask :wizard :about :removing :gills]
                  (let [begged (get-entity-state :wizard :begged)]
                    [(gill-removal-dialogue begged)
                     (set-entity-state :wizard :begged (inc begged))])]
                 [[:beg :gills | :beg :about :removing :gills | :beg :wizard :about :removing :gills | :ask :nicely :about :removing :gills]
                  (let [begged (get-entity-state :wizard :begged)
                        ; inc begged more because you begged
                        begged (inc begged)]
                    [(gill-removal-dialogue begged)
                     (set-entity-state :wizard :begged (inc begged))])])
  ; begged is a vague measure of how much begging the player has done
  ; it increases more when using words like "beg" instead of just "ask"
  {:begged 0})


(defarea wiz
  "The hut of The Great Sea Wizard"
  [wizard]
  (make-commands
   [[:look] "There is an old merman milling outside of a hut for no apparent reason. The merman sports a sagely beard and magic fedora. He is also rather flatulent."]
   [[:look :north]
    "There's nothing interesting North of the wizard's hut."]
   [[:look :east]
    "There's nothing of particular interest to the East of the wizard's hut."]
   [[:look :south]
    (cond
     (and (entity-at? :school :seahorse) (entity-at? :teenager :seahorse))
     "You see that teenager that punched you hanging out with some seahorses."
     (entity-at? :school :seahorse)
     "You see seahorses milling about."
     (entity-at? :teenager :seahorse)
     "You see that teenager that punched you in the balls."
     :else
     "There's nothing much over there now that the seahorses left.")]
   [[:look :west]
    "The cliff face extends even further this way."]
   [[:go :south]
    (move-player :seahorse)]))
