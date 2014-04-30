(ns ld29.gui.Game
  (:gen-class :name ld29.gui.Game
              :extends com.badlogic.gdx.Game
              :state state
              :init init-game
              :constructors {[clojure.lang.Atom clojure.lang.Atom] []})
  (:import [com.badlogic.gdx Game Gdx Screen InputProcessor]
           [com.badlogic.gdx.graphics GL20]
           [com.badlogic.gdx.scenes.scene2d Stage])
  (:require [ld29.gui.uis
             [title :as title]
             [game :as game]
             [game-over :as game-over]
             [intro :as intro]])
  (:use [ld29.gui.music]))

(defn clear-screen
  "clears the screen"
  []
  (.glClearColor Gdx/gl 0.8 0.8 1 0)
  (.glClear Gdx/gl GL20/GL_COLOR_BUFFER_BIT))

(defn play-music
  "plays/changes the music"
  [{:keys [current-music music-instance musics state]}]
  (let [state @state ; deref game state
        state-music (:current-music state) ; music according to game
        ]
    ; if we aren't playing the music we should be playing
    (when-not (= state-music @current-music)
      ; update current music identifier
      (swap! current-music (fn [_] state-music))
      ; get the new music
      (let [music (if (nil? @music-instance)
                    (new-music (get musics state-music))
                    @music-instance)]
        ; update it in state
        (swap! music-instance (fn [_] music))
        ; play it
        (.play music)))))

(defn render
  "renders the current ui"
  [stage {:keys [current-ui] :as state} uis]
  ; get the current ui and render it
  ((get uis current-ui) stage state))

(defn make-screen
  "creates the screen for displaying stuff"
  [state]
  (let [stage (atom nil)]
    (proxy [Screen] []
      (resize [w h])
      (show [])
      (hide [])
      (render [delta]
        (clear-screen)
        ; dispose previous stage, make new stage
        (when-not (nil? @stage)
          (.dispose @stage))
        (reset! stage (Stage.))
        ; play the music
        (play-music state)
        ; render the game state
        (render stage @(:state state) (:uis state))
        (doto @stage
          (.act delta)
          (.draw)))
      (pause [])
      (dispose []
        (kill-music @(:music-instance state)))
      (resume []))))

(defn make-input-listener
  "makes a listener for input events"
  [inputs]
  (proxy [InputProcessor] []
    (keyDown [keycode] true)
    (keyUp [keycode] true)
    (keyTyped [character]
      (swap! inputs #(conj % character))
      true)
    (touchDown [x y pointer button] true)
    (touchUp [x y pointer button] true)
    (touchDragged [x y pointer] true)
    (mouseMoved [x y] true)
    (scrolled [amount] true)))

(defn -init-game
  "inits a Game instance"
  [shared-state shared-inputs]
  ; pass nothing to Game constructor; shared data to state
  [[] {:state shared-state
       :inputs shared-inputs
       :uis {:title title/render
             :game game/render
             :game-over game-over/render
             :intro intro/render}
       :current-music (atom nil) ; the music currently playing
       :music-instance (atom nil) ; the Music object for the current music
       :musics {:ambient-b "ambient_b_eastern_thought.mp3"} ; music -> filename
       }])

(defn -create [^Game this]
  ; set up screen, input listener
  (let [state (.state this)]
    (.setScreen this (make-screen state))
    (.setInputProcessor Gdx/input (make-input-listener (:inputs state)))))
