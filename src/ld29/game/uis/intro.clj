(ns ld29.game.uis.intro
  (:use [ld29.game.uis.core]))

(defn process-state
  "processes the intro"
  [state inputs]
  (let [intro-message "You are an adventurer fresh off an escapade into an ancient merfolk ruin in Saudi Arabia. You had delved to the heart of the ruin and had opened a chest expecting great treasure, but instead you were cursed with unsightly gills all down your neck. While being able to breathe underwater is a nice touch, it gets very uncomfortable when you're on land. You grab your most water-proof backpack and you take off on an adventure to the heart of the ocean. You've tracked the merfolk to this area with the hints they left behind. It's time to do something about these gills."
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
                        state (assoc-in state [:input] "look")]
                    state)
          (recur state (rest inputs)))))))
