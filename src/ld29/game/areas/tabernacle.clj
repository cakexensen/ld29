(ns ld29.game.areas.tabernacle
  (:use [ld29.game.command]
        [ld29.game.actions]
        [ld29.game.util]))

(defentity clergyman
  ""
  (make-commands
   [[:look-at :clergyman]
    "The clergyman wears a long robe, which to you looks rather odd with his fish-like tail waving it to and fro from the bottom. "]
   [[:talk :clergyman]
    "The clergyman tells you of the lunar tides and the great wave that brought life to the oceans and then to the Earth. He makes a surprisingly convincing argument for water being the all-powerful creator of the world. "]
   [[:ask :about :gills | :ask :clergyman :about :gills]
    "The clergyman tells you your gills are a divine blessing granted only to the greatest among men, and that since you have found the village of the Merfolk he can grant to you a tail that would complete your transformation. "]
   [[:ask :about :removing :gills | :ask :clergyman :about :removing :gills]
    "The man frowns. He knows you would not ask if you were not serious. He tells you of The Great And Powerful Wizard that lives in the hut to the north, he can remove the gift of gills and help you return to the surface world. "]
   [[:ask :about :fin | :ask :clergyman :about :fin]
    (cond
     (and (get-area-state :asked-once) (get-game-state :rescue) (get-game-state :slime) (entity-at? :coral :inventory))
     (game-over "The clergyman lifts his hands and chants a song to the moon and the tides and your transformation is complete. You begin a new life among the merfolk and are soon wed to Marlune. The Great And Powerful Wizard blesses your marriage after the shark-man falls ill from the slime and you live a very long and happy life deep below the surface. That coral you picked up also came in handy one day. Your final score was 10 out of 10. ")
     (and (get-area-state :asked-once) (get-game-state :rescue) (get-game-state :slime))
     (game-over "The clergyman lifts his hands and chants a song to the moon and the tides and your transformation is complete. You begin a new life among the merfolk and are soon wed to Marlune. The Great And Powerful Wizard blesses your marriage after the shark-man falls ill from the slime and you live a very long and happy life deep below the surface. Your final score was 9 out of 10. ")
     (and (get-area-state :asked-once) (get-game-state :rescue))
     (game-over "The clergyman lifts his hands and chants a song to the moon and the tides and your transformation is complete. You begin a new life among the merfolk and are soon wed to Marlune. You live a short yet happy life deep below the surface. Your final score was 8 out of 10. ")
     (and (get-area-state :asked-once) (get-game-state :slime))
     (game-over "The clergyman lifts his hands and chants a song to the moon and the tides and your transformation is complete. The Great And Powerful Wizard blesses your life after the shark falls ill from the slime and you live a very long life deep below the surface. Your final score was 7 out of 10. ")
     (get-area-state :asked-once)
     (game-over "The clergyman lifts his hands and chants a song to the moon and the tides and your transformation is complete. You live a short and peaceful life deep below the surface. Your final score was 6 out of 10. ")
     :else
     ["The man smiles. \"I would be happy to grant to you a fin and complete your transformation.\" "
      (set-area-state :asked-once true)])]
                 ))

(defarea tabernacle
  "A lunar tabernacle"
  [clergyman]
  (make-commands
   [[:look]
    "It is a lunar tabernacle. There is a clergyman here."]
   [[:leave | :leave :tabernacle | :go | :go :west]
    (move-player :village)]
   ))
