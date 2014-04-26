(defproject ld29 "0.1.0-SNAPSHOT"
  :description "ludum dare 29 entry"
  :url "https://github.com/danxensen/ld29"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.incubator "0.1.3"]
                 [com.badlogic.gdx/gdx "0.9.9-SNAPSHOT"]
                 [com.badlogic.gdx/gdx-backend-lwjgl "0.9.9-SNAPSHOT"]]
  :repositories [["libgdx" "http://libgdx.badlogicgames.com/nightlies/maven/"]]
  :aot [ld29.gui.Game]
  :main ^:skip-aot ld29.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
