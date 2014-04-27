(ns ld29.game.areas.wiz
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defn gill-removal-dialogue
  [begged]
  (cond
   (= begged 0) "\"Really? Why'd you wanna go and\" *fart* \"do a thing like that? I can remove them, but I don't think it's a good idea.\""
   (= begged 1) "*fart* \"You sure? It's a long swim up to the surface.\" *fart*"
   (= begged 2) "\"If I do this, you won't be able to come back.\" *fart*"
   (= begged 3) "\"I'm gonna ask one more time.\" *fart* \"Are you absolutely sure you want to lose your gills?\""
   (>= begged 4) ["\"Well, okay then.\" The wizard raises his hands, farts, and speaks a magic incantation. You feel your gills receding, and you suddenly cannot breathe. \"You better start swimming to the surface now. Get a move on!\" He kicks you upward, and you hear one last fart before you begin your ascent."
                  (move-player :up)]))

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
                  (if (get-area-state :shark :slime)
                    "\"Thanks for sliming that sharkman's place!\" *fart* \"Oh, I'd love to see the look on his face...\""
                    (if (get-entity-state :wizard :talked)
                      "*fart* \"Could you please take some slime from the sunken ship and spread it around the sharkman's hideout?\" *fart* \"He's been really getting on my nerves lately!\""
                      "\"I don't think I mentioned a favour, but since you're interested,\" *fart* \"take some slime from the sunken ship and spread it around the sharkman's hideout. He's been really getting on my nerves lately!\""))]
                 [[:ask :about :removing :gills | :ask :wizard :about :removing :gills]
                  (let [begged (get-entity-state :wizard :begged)]
                    (gill-removal-dialogue begged)
                    (set-entity-state :wizard :begged (inc begged)))]
                 [[:beg :about :removing :gills | :beg :wizard :about :removing :gills | :ask :nicely :about :removing :gills]
                  (let [begged (get-entity-state :wizard :begged)
                        ; inc begged more because you begged
                        begged (inc begged)]
                    (gill-removal-dialogue begged)
                    (set-entity-state :wizard :begged (inc begged)))])
  ; begged is a vague measure of how much begging the player has done
  ; it increases more when using words like "beg" instead of just "ask"
  {:begged 0})


(defarea wiz
  "The hut of The Great Sea Wizard"
  [wizard]
  (make-commands [[:look] "There is an old merman milling outside of a hut for no apparent reason. The merman sports a sagely beard and magic fedora. He is also rather flatulent."]))
