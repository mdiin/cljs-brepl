cljs-brepl
==========

Minimal ClojureScript template with a browser-connected REPL.


How to use this?
================

In one terminal type ```lein ring server-headless 3000``` and in another terminal type ```lein trampoline cljsbuild repl-listen```. Then point your browser at ```http://localhost:3000/repl```. You now have a ClojureScript REPL connected to the browser!