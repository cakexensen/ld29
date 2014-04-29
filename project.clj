(defproject ld29 "0.1.0-SNAPSHOT"
  :description "ludum dare 29 entry"
  :url "https://github.com/danxensen/ld29"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.incubator "0.1.3"]
                 [com.badlogicgames.gdx/gdx "1.0-SNAPSHOT"]
                 [com.badlogicgames.gdx/gdx-backend-lwjgl "1.0-SNAPSHOT"]
                 [com.badlogicgames.gdx/gdx-platform "1.0-SNAPSHOT"
                  :classifier "natives-desktop"]]
  :repositories [["gdx-nightlies" "https://oss.sonatype.org/content/repositories/snapshots/"]]
  :aot [ld29.gui.Game]
  :main ^:skip-aot ld29.core
  :target-path "target/%s")
