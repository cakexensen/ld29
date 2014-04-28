(ns ld29.core
  (:gen-class)
  (:use [ld29.game.core :only [run-game]]
        [ld29.gui.core :only [run-gui]]))

(defn -main
  "starts the game"
  [& args]
  ; create the buffers shared by the main game process and the gui
  (let [shared-state (atom nil)
        shared-inputs (atom nil)]
    ; start the gui and the game with access to the buffers
    (run-gui shared-state shared-inputs "To The Depths!" 800 480)
    ; start game in separate thread; when it finishes shutdown thread agents
    @(future (run-game shared-state shared-inputs))
    (shutdown-agents)))
