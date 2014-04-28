(ns ld29.game.actions
  (:use [clojure.core.incubator])
  (:require [clojure.string :as string]))

; actions are functions used in command bodies
; they are used to easily effect changes on the game state
; they return functions of type state -> state

(declare current-area)
(declare current-area-id)
(declare entity-at?)

(defn message
  "appends to the next message in the game"
  [text]
  (fn [state] (update-in state [:message] str text " ")))

(defn set-entity-state
  "sets a state value associated with a particular entity"
  ([entity area key value]
     (if (= area :inventory)
       (fn [state]
         (assoc-in state [:inventory entity :state key] value))
       (fn [state]
         (assoc-in state [:areas area :entities entity :state key] value))))
  ([entity key value]
     (set-entity-state entity (current-area-id) key value)))

(defn set-area-state
  "sets a state value associated with a particular area"
  ([key value]
     (set-area-state (current-area-id) key value))
  ([area key value]
     (fn [state]
       (assoc-in state [:areas area :state key] value))))

(defn set-game-state
  "sets a state value associated with the entire game"
  [key value]
  (fn [state]
    (assoc-in state [:state key] value)))

(defn add-entity
  "adds an entity to the game"
  ([id entity-fn]
     (add-entity id entity-fn (current-area-id)))
  ([id entity-fn area]
     (when-not (entity-at? id area)
       (if (= area :inventory)
         (fn [state]
           (assoc-in state [:inventory id] (entity-fn)))
         (fn [state]
           (assoc-in state [:areas area :entities id] (entity-fn)))))))

(defn remove-entity
  "removes an entity from the game"
  ([id]
     (remove-entity id (current-area-id)))
  ([id area]
     (if (= area :inventory)
       (fn [state]
         (dissoc-in state [:inventory id]))
       (fn [state]
         (dissoc-in state [:areas area :entities id])))))

(defn move-entity
  "moves an entity from one place to another"
  ([id to]
     (move-entity id (current-area-id) to))
  ([id from to]
     (fn [state]
       (let [; inventory is in a different place than areas, so handle separate
             from-inventory? (= from :inventory)
             to-inventory? (= to :inventory)
             ; retrieve the entity data so we don't lose it
             entity (if from-inventory?
                      (get-in state [:inventory] id)
                      (get-in state [:areas from :entities id]))
             ; remove the entity from the 'from' location
             state ((remove-entity from id) state)
             ; add the entity to the 'to' location
             state (if to-inventory?
                     (assoc-in state [:inventory id] entity)
                     (assoc-in state [:areas to :entities id]))]
         state))))

(defn move-player
  "moves player to a new area"
  [to]
  (fn [state]
    (assoc-in state [:location state])))

(defn game-over
  "causes a game over"
  [message]
  (fn [state]
    (-> state
        (assoc-in [:message] (str message " Would you like to restart? y/n"))
        (assoc-in [:current-ui] :game-over))))

; action conditions - usable inside commands to check the state
; use var *state*, which is bound during command processing

(declare ^:dynamic *state*)

(defn current-area
  "gets the current area"
  []
  (let [{:keys [location areas]} *state*]
    (get areas location)))

(defn current-area-id
  "gets the current area id"
  []
  (let [{:keys [location]} *state*]
    location))

(defn entity-at?
  "checks if an entity is at a location"
  [id area]
  (let [{:keys [inventory areas]} *state*]
    (if (= area :inventory)
      (contains? inventory id)
      (contains? (get-in areas [area :entities]) id))))

(defn entity-here?
  "checks if an entity is at the current location"
  [id]
  (entity-at? id (:location *state*)))

(defn get-entity-state
  "gets a state value associated with a particular entity"
  ([entity area key]
     (if (= area :inventory)
       (get-in *state* [:inventory entity :state key])
       (get-in *state* [:areas area :entities entity :state key])))
  ([entity key]
     (get-entity-state entity (current-area-id) key)))

(defn get-area-state
  "gets a state value associated with a particular area"
  ([key]
     (get-area-state (current-area-id) key))
  ([area key]
     (get-in *state* [:areas area :state key])))

(defn get-game-state
  "gets a state value associated with the game"
  [key]
  (get-in *state* [:state key]))

(defn list-inventory
  "lists the descriptions of all entities in the inventory"
  []
  (let [inventory (:inventory *state*)
        entities (map second inventory) ; skip keys
        descriptions (map :description entities)
        description (string/join ", " descriptions)]
    description))
