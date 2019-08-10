(ns learning-clojure.core
  ;; The `:as` keyword is the same `as` in PHP.
  ;; Useful to use only jetty/some-function instead of calling
  ;; the full namespace (e.g: ring.adapter.jetty/some-function)
  (:require [ring.adapter.jetty :as jetty]))

(defn welcome
  "A ring handler to process all requests sent to webapp"
  [request]
  (if (= "/" (:uri request))
    {:status 200
     :body "<h1>Hello, Clojure World</h1>
     <p>Welcome to our first Clojure app.
     This message is returned regardless of the request, sorry</p>"
     :headers {}}
    {:status 404
     :body "<h1>This is not the page you are looking for</h1>
     <p>Sorry, the page you requested was not found!></p>"
     :headers {}}))

;; Using a - at the start of the -main function is a naming convention,
;; helping you see which function is the entry point to your program.
;; Leiningen also looks for this -main function by default when running your application.
(defn -main
  "A very simple webserver using Ring & Jetty"
  [port-number]
  (jetty/run-jetty welcome
    ;; The Integer. function is a call to java.lang.Integer.
    ;; The . is a special form that tells Clojure to treat this name as a call to Java.
    {:port (Integer. port-number)}))