(ns ld29.gui.core
  (:import [com.badlogic.gdx Game]
           [com.badlogic.gdx.backends.lwjgl LwjglApplication LwjglApplicationConfiguration]))

(defn continue?
  "determines if the gui should continue running"
  [state]
  ; if the game is finished, process should leave a nil state
  (not (nil? state)))

(defn run-gui
  "runs the gui application"
  [shared-state shared-inputs window-title window-width window-height]
  ; create the game application
  (let [game (ld29.gui.Game. shared-state shared-inputs)
        config (LwjglApplicationConfiguration.)]
    ; disgustingly set the configuration parameters
    (set! (. config resizable) false)
    (set! (. config title) window-title)
    (set! (. config width) window-width)
    (set! (. config height) window-height)
    (let [app (LwjglApplication. game config)
          window-title window-width window-height true]
      ; close the app when state processing should no longer continue
      ; using a watch because i can't find a good way to hook this up
      (add-watch shared-state nil
                 (fn [key ref old new]
                   (when-not (continue? new)
                     (.stop app)))))))
