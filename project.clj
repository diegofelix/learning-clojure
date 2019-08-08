(defproject learning-clojure "0.1.0-SNAPSHOT"
  :description "Simple project to learn clojure."
  :url "http://github.com/diegofelix/learning-clojure"
  :license {:name "Creative Commons Attribution Share-Alike 4.0 International"
            :url "https://creativecommons.org"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.7.1"]]

  ;; This code will call a function called `-main` on core as soon as
  ;; we call `lein run`.
  ;; Useful when we want to run the code automatically.
  :main learning-clojure.core)
