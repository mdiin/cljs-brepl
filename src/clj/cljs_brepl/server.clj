(ns cljs-brepl.server
  (:use compojure.core
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup
             [page :refer [html5]]
             [page :refer [include-js]]
             [element :refer [javascript-tag]]]))

; When using {:optimizations :whitespace}, the Google Closure compiler combines
; its JavaScript inputs into a single file, which obviates the need for a "deps.js"
; file for dependencies.  However, true to ":whitespace", the compiler does not remove
; the code that tries to fetch the (nonexistent) "deps.js" file.  Thus, we have to turn
; off that feature here by setting CLOSURE_NO_DEPS.
;
; Note that this would not be necessary for :simple or :advanced optimizations.
(defn- run-clojurescript [path init]
  (list
    (javascript-tag "var CLOSURE_NO_DEPS = true;")
    (include-js path)
    (javascript-tag init)))

(defn repl-page []
  (html5
   [:head
    [:title "REPL Demo"]]
   [:body
    [:h1 "REPL Demo"]
    [:hr]
    "This page is meant to be accessed by running this in one terminal:"
    [:pre "lein ring server-headless 3000"]
    "And then this in a different terminal:"
    [:pre "lein trampoline cljsbuild repl-launch firefox http://localhost:3000/repl-demo"]
    [:hr]
    "Alternately, you can run:"
    [:pre "lein ring server-headless 3000 &
lein trampoline cljsbuild repl-listen"]
    "And then browse to this page manually."]
   (run-clojurescript
    "/js/main-dev.js"
    "cljs_brepl.repl.connect();")))

(defroutes main-routes
  (GET "/" [] "")
  (GET "/repl" [] (repl-page))
  (route/resources "/")
  (route/not-found "Not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))
