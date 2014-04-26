(ns ld29.game.uis.game-over
  (:use [ld29.game.uis.core]))

(defn process-state
  "processes the game over state"
  [state inputs]
  (if (empty? inputs)
    (animate-message state)
    (let [input (first inputs)]
      (case input
        \y :new-game
        \Y :new-game
        \n nil
        \N nil
        (recur state (rest inputs))))))
