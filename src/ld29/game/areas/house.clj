(ns ld29.game.areas.house
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity seal
  ""
  (make-commands
   [[:look-at :seal]
    "It is a silly seal that smiles and sashays and sallies."]
   [[:get :seal]
    "He is a slippery sort. You shant slide him into your satchel."]
   [[:talk :seal]
    "He slaps his flippers together as though he were... schooled in slapping."]))


(defarea house
  "An adorable underwater cottage."
  [seal]
  (make-commands
   [[:look]
    (if (entity-here? :seal)
      ["You come to an adorable underwater cottage-style home complete with a well-manicured lawn. You see a silly seal sashaying eastward." (move-entity :seal :shark)]
      "You come to an adorable underwater cottage-style home complete with a well-manicured lawn.")]
   [[:look :east]
    "You see the area where the shark-man once stood. You get the feeling he mugs people there often."
    (when (entity-at? :seal :shark)
      "That seal seems to've gone over there.")]
   [[:look :south]
    (if (get-game-state :all-clear)
      "You see the mer-village over that way, complete with a tabernacle."
      "It's too murky to see much right now.")]
   [[:look :north]
    (cond
     (entity-at? :thugs :main)
     "There are a bunch of merfolk over there."
     (get-game-state :all-clear)
     "That seal is shilly-shallying."
     :else
     "It's too murky to see much right now.")]
   [[:look :west]
    "It's a sheer cliff face. Best not to be bothering with it."]
   [[:go :east]
    (move-player :shark)]
   [[:go :south]
    (if (get-game-state :all-clear)
      (move-player :village)
      "It's too murky to go that way right now.")]
   [[:go :north]
    (if (get-game-state :all-clear)
      (move-player :main)
      "It's too murky to go that way right now.")]
   ))
