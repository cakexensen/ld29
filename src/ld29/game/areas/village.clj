(ns ld29.game.areas.village
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))


(defentity teenager
  ""
  (make-commands
   [[:look-at :teenager]
    (cond
     (get-entity-state :kid-beat-up)
     "A scrawny punk of a teen with a very punched face. Ow."
     :else
     "The scrawny teen squints back at you angrily.")]
   [[:beat-up :teenager]
    "You knock some sense into the kid's face."
    (set-entity-state :teenager :kid-beat-up true)]
   [[:talk :teenager]
    "The mer-teenager looks down at his feet and says,\"You don't deserve to have gills if you would ask to give them back.\""]
   [[:ask :about "punch"]
    "\"You're a jerk.\""]))

(defarea village
  "The village of the mer-folk"
  [teenager]
  (make-commands
   [[:look]
    "A quiet village full of merfolk. The biggest building here is the tabernacle, over to the east, which promises the holiness of the moon and the tides."
    (when (and (entity-here? :teenager) (get-area-state :tabernacle :asked-once))
      ["As you exit the tabernacle, a crying mer-teenager runs up to you and punches you in the junk. While you're doubled over in pain, the kid swims off to the North."
       (move-entity :teenager :seahorse)])]
   [[:look :north]
    "The adorable cottage you just visited stands just outside this village."]
   [[:look :tabernacle]
    "This is just the sort of place where someone questioning the practices of merfolk could probably find some answers."]
   [[:look :east]
    "The tabernacle is on the East side of the village."]
   [[:look :west]
    "This village is protected by that big cliff face."]
   [[:look :south]
    "There are more homes in various styles. None of them stand out as a place you need to go."]
   [[:go :north]
    (move-player :house)]
   [[:go :into :tabernacle]
    (move-player :tabernacle)]
   [[:go :east]
    (move-player :tabernacle)]
   ))
