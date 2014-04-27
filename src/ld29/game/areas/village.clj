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
  (make-commands))
