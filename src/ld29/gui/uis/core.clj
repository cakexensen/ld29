(ns ld29.gui.uis.core
  (:import [com.badlogic.gdx.graphics Color]
           [com.badlogic.gdx.graphics.g2d BitmapFont]
           [com.badlogic.gdx.scenes.scene2d.ui Label Label$LabelStyle]
           [com.badlogic.gdx.backends.lwjgl LwjglFiles]))

(def style
  (delay ; delay because creating LabelStyle at compile time fails
   (Label$LabelStyle.
    (BitmapFont. (.internal (LwjglFiles.) "ume-mincho-32.fnt") false)
    (Color. 0.2 0.2 0.8 1))))

(def max-line-length 50)

(def line-pixel-height 32)

(def char-pixel-width 16)

(def first-line-y 448)

(defn adjust-for-split-word
  "adjusts a split-at result so that a word isn't split across the boundary"
  [line rest]
  (if (empty? rest)
    [line rest]
    (let [line-length (count line)
          line-last-space (.lastIndexOf line \space) ; break at last space
          [new-line pre-rest] (split-at (inc line-last-space) line)
          new-rest (concat pre-rest rest)] ; slap leftovers onto rest
      [new-line new-rest])))

(defn format-message
  "wraps the message to fit the screen"
  [message stage]
  (loop [message message
         line-number 2] ; start 2 line under the top
    (let [[line rest] (split-at max-line-length message)
          [line rest] (adjust-for-split-word line rest)
          line-label (Label. (apply str line) @style)]
      (.setY line-label (- first-line-y (* line-number line-pixel-height)))
      (.addActor @stage line-label)
      (when-not (empty? rest)
        (recur rest (inc line-number))))))
