(ns ld29.gui.uis.intro
  (:import [com.badlogic.gdx.scenes.scene2d.ui Label])
  (:use [ld29.gui.uis.core]))

(defn render
  "renders the game state"
  [stage state]
  (let [intro (Label. "Introduction" @style)
        prompt (Label. "Press enter..." @style)
        message (:animated-message state)]
    (.addActor @stage prompt)
    (.setY intro first-line-y)
    (.addActor @stage intro)
    (format-message message stage)))
