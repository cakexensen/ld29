(ns ld29.gui.music
  (:import [com.badlogic.gdx Gdx Audio]
           [com.badlogic.gdx.files FileHandle]
           [com.badlogic.gdx.backends.lwjgl LwjglFiles]))

(defn new-music
  "creates a new Music"
  [music-file]
  (let [music (.newMusic Gdx/audio (.internal (LwjglFiles.) music-file))]
    (.setLooping music true)
    music))

(defn kill-music
  "kills a Music instance"
  [music]
  (when-not (nil? music)
    (.stop music)
    (.dispose music)))
