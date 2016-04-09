CLJS Bach
=========

A Clojurescript wrapper for the Web Audio API, extracted from [Klangmeister](http://ctford.github.io/klangmeister/).

[![Build Status](https://travis-ci.org/ctford/cljs-bach.png)](https://travis-ci.org/ctford/cljs-bach)

Importing it into your project
------------------------------

CLJS Bach is a Clojurescript library. To include it in your Clojurescript project, you need to
include the following in your `project.clj` or `build.boot`.

[![Clojars Project](http://clojars.org/cljs-bach/latest-version.svg)](http://clojars.org/cljs-bach)

Once you've done that, you can use it like any other library.

Usage
-----

Firstly, create an audio context. You only need one, and if you keep creating them the browser will run out and error on you.

    (defonce context (audio-context))

See [Klangmeister](http://ctford.github.io/klangmeister/) for examples of how to build synthesisers. Here's a simple
one. Note the use of `connect->` to join together simple parts together.

    (defn ping [freq]
      (connect->
        (square freq)         ; Try a sawtooth wave.
        (percussive 0.01 0.4) ; Try varying the attack and decay.
        (gain 0.1)))          ; Try a bigger gain.

Once you have a synthesiser, connect it to `destination` and use `run-with` to give it an audio context, a time to run at
and a duration.

    ; Play the ping synthesiser now, at 440 hertz.
    (-> (ping 440)
        (connect-> destination)
        (run-with context (current-time context) 1.0)))

If you forget to connect a synthesiser to `destination`, then you'll here no sound, because nothing will be sent to the speakers.

Getting a REPL
--------------

CLJS Bach relies on the Web Audio API, so it will only work if your javascript environment supports that. Fortunately, all
major browsers except Internet Explorer do (and it will on next major release).

If you have this project checked out locally, you can use the [Figwheel](https://github.com/bhauman/lein-figwheel) REPL. Firstly,
start Figwheel.

    lein figwheel

Figwheel will tell you where's running, so you can open it in your browser.

    Figwheel: Starting server at http://localhost:3449
    ....
    Prompt will show when Figwheel connects to your application

Once you've done that, the REPL will come to life.

    To quit, type: :cljs/quit
    cljs.user=>

From then on, you can type commands into the REPL, and they'll be executed by your browser.

Design
------

CLJS Bach is purely functional. To make synthesiser composition easier, synthesisers are actually functions that take
an audio context, a time and a duration and return the actual synthesis notes. That's why you need `run-with` to make
sound actually happen.
