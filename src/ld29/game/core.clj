(ns ld29.game.core
  (:use [ld29.game.command]
        [ld29.game.dictionary]
        [ld29.game.areas main seahorse ship shark wiz cave house in-ship tabernacle village])
  (:require [ld29.game.uis
             [title :as title]
             [game :as game]
             [game-over :as game-over]
             [intro :as intro]]))

(defn new-game
  "creates a new game state"
  []
  {:current-ui :title ; the current ui to use
   :uis {:title title/process-state
         :game game/process-state
         :game-over game-over/process-state
         :intro intro/process-state} ; all the uis in the game
   :partial-input [] ; the input the player is still typing
   :input "" ; the input after player hits enter
   :message "" ; the message to display on the screen
   :animated-message "" ; the partially-animated portion of the message
   :animated-index 0 ; the animation index
   :state {} ; any global state values
   :areas {:main (main)
           :seahorse (seahorse)
           :ship (ship)
           :shark (shark)
           :wiz (wiz)
           :cave (cave)
           :house (house)
           :in-ship (in-ship)
           :tabernacle (tabernacle)
           :village (village)} ; all the areas in the game
   :location :main ; the current area id
   :moving false ; true when moving between areas
   :inventory {} ; player's inventory of entities
   :commands (make-commands [[:look :backpack]
                             "This backpack is one of your favourite possessions. It is a deluxe starfish backpack with premium eel stitching. Current contents: "
                             (list-inventory)])
   :dictionary (make-dictionary) ; dictionary shared throughout game
   :unknown-command (fn [state]
                      (assoc-in state [:message]
                                (str "You can't " (:input state))))
   :current-music nil ; the song to play currently
   })

(defn get-inputs
  "gets the new inputs"
  [input-buffer]
  ; input-buffer is the shared-inputs atom,
  ; it will contain a collection of inputs to be processed this frame
  (let [inputs @input-buffer] ; grab the inputs
    (swap! input-buffer (fn [_] [])) ; clear the buffer
    inputs))

(defn process-state
  "processes the state and inputs"
  [{:keys [current-ui uis] :as state} inputs]
  ; get the current ui and execute it
  (let [state ((get uis current-ui) state inputs)]
    ; handle game-over reset
    (if (= state :new-game)
      (new-game)
      state)))

(defn continue?
  "determines if the game should continue running"
  [state]
  ; if the game is finished, process should leave a nil state
  (not (nil? state)))

(defn take-a-break
  "sleeps thread until the next frame should be processed"
  []
  (Thread/sleep (/ 1000 60)))

(defn run-game
  "runs the core game loop"
  [shared-state shared-inputs]
  (loop [state (new-game)] ; start with a new game state
    (when (continue? state) ; check if we should continue processing
      (let [input (get-inputs shared-inputs) ; get inputs
            next-state (process-state state input)] ; process state
        (swap! shared-state (fn [_] next-state)) ; update shared-state
        (take-a-break)
        (recur next-state))))) ; recur loop with new state

