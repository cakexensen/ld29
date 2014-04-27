(ns ld29.game.areas.wiz
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))


(defentity wizard
  ""
  (make-commands [[:look-at :wizard]
                  "A rather old and eccentric mer-wizard. His beard hangs long and obscures the sight of some odd trinkets worn about his neck. A battered magic fedora crowns his head."]
                 [[:look-at :beard]
                  "A long and unkempt mane of facial hair. Nothing particularly noteworthy about it other than its length."]
                 [[:look-at :fedora]
                  "Brown, wool, sparkly. Yup, definitely a wizard's fedora. The wizard must be pretty crazy to wear such a thing down here."]
                 [[:talk :wizard]
                  (if (get-game-state :gills-removed)
                    "I think ye looked better with the gills, but to each their own."
                    ["Ah, how curious to see not just a human in my midst, but one blessed with gills! *fart* Would you be interested in doing me a little favour?"
                     (set-entity-state :wizard :talked true)])]
                 [[:ask :about :favour | :ask :wizard :about :favour]
                  (if (get-area-state :shark :slime)
                    "Thanks for sliming that sharkman's place! *fart* Oh, I'd love to see the look on his face..."
                    (if (get-entity-state :wizard :talked)
                      "*fart* Could you please take some slime from the sunken ship and spread it around the sharkman's hideout? *fart* He's been really getting on my nerves lately!"
                      "I don't think I mentioned a favour, but since you're interested, *fart* take some slime from the sunken ship and spread it around the sharkman's hideout. He's been really getting on my nerves lately!"))]
                 [[:ask :about :removing :gills | :ask :wizard :about :removing :gills]
                  "Really? Why'd you wanna go and *fart* do a thing like that? I can remove them, but I don't think it's a good idea."]))


(defarea wiz
  "The hut of The Great Sea Wizard"
  [wizard]
  (make-commands [[:look] "There is an old merman milling outside of a hut for no apparent reason. The merman sports a sagely beard and magic fedora. He is also rather flatulent."]))
