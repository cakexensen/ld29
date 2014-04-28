(ns ld29.gui.music
  (:import [com.badlogic.gdx Gdx Audio]
           [com.badlogic.gdx.files FileHandle]
           [com.badlogic.gdx.backends.lwjgl LwjglFiles]))

(defn new-music
  "creates a new Music"
  [music-file]
  (.newMusic Gdx/audio (.internal (LwjglFiles.) music-file)))

(defn kill-music
  "kills a Music instance"
  [music]
  (when-not (nil? music)
    (.stop music)
    (.dispose music)))
