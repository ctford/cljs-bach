(defproject cljs-bach "0.4.0-SNAPSHOT"
  :description "A Clojurescript wrapper for the Web Audio API."
  :license {:name "MIT" }
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [cljs-ajax "0.5.5"]]

  :min-lein-version "2.5.0"

  :plugins [[lein-cljsbuild "1.1.2"]
            [lein-figwheel "0.5.0-2"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"
                                    "out"]

  :source-paths ["src"]
  :resource-paths ["resources" "target/cljsbuild"]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main "cljs_bach.synthesis"
                                   :optimizations :none
                                   :pretty-print true
                                   :output-to "resources/public/js/compiled/bach.js"
                                   :output-dir "resources/public/js/compiled"
                                   :asset-path "js/compiled"}}

                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:main "cljs_bach.synthesis"
                                   :static-fns true
                                   :optimizations :advanced
                                   :pretty-print false
                                   :optimize-constants true
                                   :output-to "resources/public/js/compiled/bach.js"
                                   :asset-path "js/compiled"}}]})
