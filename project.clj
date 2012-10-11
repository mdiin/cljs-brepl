(defproject cljs-brepl "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.0.4"]
                 [hiccup "1.0.0"]]
  :source-paths ["src/clj"]
  :ring {:handler cljs-brepl.server/app}
  :cljsbuild {:builds {:dev {:source-path "src/cljs"
                             :compiler {:output-to "resources/public/js/main-dev.js"
                                        :optimizations :whitespace
                                        :pretty-print true}}
                       :prod {:source-path "src/cljs"
                              :compiler {:output-to "resources/public/js/main.js"
                                         :optimizations :advanced
                                         :pretty-print false}}}
              :repl-listen-port 9000
              :repl-launch-commands {"chromium" ["chromium-browser"
                                                 :stdout ".repl-chromium-out"
                                                 :stdin ".repl-chromium-in"]}})
                             
