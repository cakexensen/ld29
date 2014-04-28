(ns ld29.game.areas.village
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))


(defentity teenager
  ""
  (make-commands))

(defarea village
  "The village of the mer-folk"
  [teenager]
  (make-commands
   [[:look]
    "A quiet village full of merfolk. The biggest building here is the tabernacle, over to the east, which promises the holiness of the moon and the tides."]
   [[:look :north]
    "The adorable cottage you just visited stands just outside this village."]
   [[:look :tabernacle]
    "This is just the sort of place where someone questioning the practices of merfolk could probably find some answers."]
   [[:go :north]
    (move-player :house)]
   [[:go :tabernacle]
    (move-player :tabernacle)]
   [[:go :east]
    (move-player :tabernacle)]
   ))
