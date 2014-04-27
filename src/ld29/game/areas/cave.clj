(ns ld29.game.areas.cave
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity marlune
  ""
  (make-commands
   [[:look-at :mermaid | :look-at "marlune"]
    "The mermaid is gorgeous to behold, her fin strong and perfectly curved and her waist thin and toned. Her hair shines like blonde silk. "
    (cond
     (and (get-area-state :cave :rescue) (get-entity-state :marlune :name-known))
     "Marlune looks a lot more comfortable now. She appraises you with a big smile. "
     (and (get-area-state :cave :rescue))
     "She looks a lot more comfortable now. The mermaid appraises you with a big smile. "
     (and (get-area-state :cave :top-recovered) (get-entity-state :marlune :name-known))
     "Marlune looks a lot more comfortable now. "
     (get-area-state :cave :top-recovered)
     "She looks a lot more comfortable now. "
     (get-entity-state :marlune :name-known)
     "Marlune looks a bit distressed at your gaze. She holds her chest tightly, her ample cleavage spilling forth. Her top appears to be missing. "
     :else
     "She looks a bit distressed at your gaze. She holds her chest tightly, her ample cleavage spilling forth. Her top appears to be missing. ")]
   [[:talk :mermaid | :talk "marlune"]
    (cond
     (get-area-state :cave :rescue)
     "\"Thank you for all of your help. If there's anything I can do for you, please let me know.\" "
     (and (get-area-state :cave :top-recovered) (= (current-area-id) :cave))
     ["\"Thank you for giving me my top back. Those thugs took my top and I was on my way to.. Oh, I have to go.\" She runs off, heading south." (move-entity :marlune :cave :house)]
     (get-area-state :cave :top-recovered)
     "\"Thanks again for giving me my top back.\" "
     (get-entity-state :marlune :name-known)
     "Marlune doesn't say anything else. She looks down at her feet bereftly. "
     :else
     "The mermaid just looks at you.")]
   [[:ask :thugs]
    (cond
     (and (get-area-state :cave :rescue) (get-entity-state :marlune :name-known))
     "Marlune gives you a big heartfelt smile. \"Thank you for beating them up for me. You're my hero!\""
     (get-entity-state :marlune :name-known)
     "Marlune is positively grumpy when she says,\"They took my money and then stole my top so that I wouldn't chase after them. I think someone should beat them up.\""
     (get-area-state :cave :rescue)
     "\"Thank you for beating them up for me. They deserved it.\""
     :else
     "\"They took my money and then stole my top so that I wouldn't chase after them. I wish I could beat them up.\"")]
   [[:ask :top]
    (cond
     (get-area-state :cave :top-recovered)
     "\"Thanks for giving it back.\""
     (entity-at? :top :inventory)
     ["\"Oh, hey, you have my top! Can I have it back?\"" (set-entity-state :marlune :asked-for-top-back true)]
     :else
     "\"Right... it was stolen by some thugs.\"")]
   [[:y]
    (when (get-entity-state :marlune :asked-for-top-back)
      [ "\"Thank you very much!\" The mermaid quickly grabs the top from you and puts it on."
        (remove-entity :inventory :top)
        (set-area-state :cave :top-recovered true)
        (set-entity-state :marlune :asked-for-top-back false)])]
   [[:n]
    (when (get-entity-state :marlune :asked-for-top-back)
      ["\"Seriously?\" The mermaid scowls at you."
       (set-entity-state :marlune :asked-for-top-back false)])]
   [[:ask :name]
    "\"My name is Marlune.\""
    (set-entity-state :marlune :name-known true)]
   
   ))

(defarea cave
  "A cave near the shipwreck."
  [marlune]
  (make-commands [
                  [:look]
                  "A cold cavern is cut from the cliffside in this area. "
                  (when (entity-here? marlune)
                    "There is a mermaid leaning against the wall with her arms crossed over her chest. ")]))
