(ns learning-clojure.core
  ;; The `:as` keyword is the same `as` in PHP.
  ;; Useful to use only jetty/some-function instead of calling
  ;; the full namespace (e.g: ring.adapter.jetty/some-function)
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]))

(defn welcome
  "A ring handler to process all requests sent to webapp"
  [request]
  {:status 200
   :body "<h1>Hello, Clojure World</h1>
          <p>I now use defroutes to manage incoming requests</p>"
   :headers {}})

(defn goodbye
  "A song to wish you goodbye"
  [request]
  {:status 200
   :body "<h1>Walking back to happiness</h1>
          <p>Walking back to happiness with you</p>
          <p>Said, Farewell to loneliness I knew</p>
          <p>Laid aside foolish pride</p>
          <p>Learnt the truth from tears I cried</p>"
   :headers {}})

(defn about
  "Information about the website developer"
  [request]
  {:status 200
   :body "<h1>I am an awesome clojure developer!</h1>"
   :headers {}})

(defn hello
  "Say hello to someone using the request path parameter"
  [request]
  ;; get from the request (inside the :route-parameters the key :name)
  (let [name (get-in request [:route-params :name])]
  ;; instead of using get-in, I can also use:
  ;;   (let [name (:name (:route-params request))]
  ;;   or (let [name ((request :route-params) :name)]
  ;; does't matter the param order inside the parentesis.
    {:status 200
     :body (str "Hello " name " I'm getting your name from the url.")
     :headers {}}))

(defroutes app
  (GET "/" [] welcome)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request-info" [] handle-dump)
  (GET "/hello/:name" [] hello)
  (not-found "<h1>This is not the page you are looking for</h1>
              <p>Sorry, the page you requested was not found!</p>"))

;; Using a - at the start of the -main function is a naming convention,
;; helping you see which function is the entry point to your program.
;; Leiningen also looks for this -main function by default when running your application.
(defn -main
  "A very simple webserver using Ring & Jetty"
  [port-number]
  (jetty/run-jetty app
    ;; The Integer. function is a call to java.lang.Integer.
    ;; The . is a special form that tells Clojure to treat this name as a call to Java.
    {:port (Integer. port-number)}))

;; when putting -dev before -main, we are telling that we want to call
;; the main method on the 'dev' profile.
(defn -dev-main
  "A very simple web server using Ring & Jetty that reloads code changes via the development profile of Leiningen"
  [port-number]
  ;; The wrap-realod function needs the name of the function it should reload.
  ;; Using quote reader macro, #', in front of the welcome function name tells 
  ;; Clojure to skip evaluation of the funciton and just use the name of the name instead.
  (jetty/run-jetty (wrap-reload #'app)
    {:port (Integer. port-number)}))