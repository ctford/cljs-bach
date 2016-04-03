(defproject klang "0.1.0-SNAPSHOT"
  :description "A Clojurescript wrapper for the Web Audio API."
  :license {:name "MIT" }
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]]

  :min-lein-version "2.5.0"

  :plugins [[lein-cljsbuild "1.1.2"]]

  :clean-targets ^{:protect false} ["resources/public/klang/js/compiled"
                                    "target"
                                    "out"]

  :source-paths ["src"]
  :resource-paths ["resources" "target/cljsbuild"]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :compiler {:main "klang.synthesis"
                                   :optimizations :none
                                   :pretty-print true
                                   :output-to "resources/public/klang/js/compiled/app.js"
                                   :output-dir "resources/public/klang/js/compiled"
                                   :asset-path "js/compiled"}}]})
