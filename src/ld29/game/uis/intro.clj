(ns ld29.game.uis.intro
  (:use [ld29.game.uis.core]))

(defn process-state
  "processes the intro"
  [state inputs]
  (let [intro-message ""
        add-message? (not= (:message state) intro-message)
        state (if add-message?
                (assoc-in state [:message] intro-message)
                state)]
    (if (empty? inputs)
      (animate-message state)
      (let [input (first inputs)]
        (case input
          ; start the game when the user presses enter and execute look command
          \return (let [state (assoc-in state [:current-ui] :game)
                        state (assoc-in state [:input] "look")
                        ; play music - just this one track throughout game
                        state (assoc-in state [:current-music] :ambient-b)]
                    state)
          (recur state (rest inputs)))))))
