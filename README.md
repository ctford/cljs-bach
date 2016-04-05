CLJS Bach
=========

A Clojurescript wrapper for the Web Audio API, extracted from [Klangmeister](http://ctford.github.io/klangmeister/).

[![Build Status](https://travis-ci.org/ctford/cljs-bach.png)](https://travis-ci.org/ctford/cljs-bach)

[![Clojars Project](http://clojars.org/cljs-bach/latest-version.svg)](http://clojars.org/cljs-bach)

Usage
-----

See [Klangmeister](http://ctford.github.io/klangmeister/) for examples of how to build synthesisers.
Once you have one, use `run-with` to give it an audio context, a time to run at and a duration.

    (def context (audio-context))

    (defn ping [freq]
      (connect->
        (square freq) ; Try a sawtooth wave.
        (percussive 0.01 0.4)
        (gain 0.1)))

    (run-with (ping freq) context (.-currentTime context) 1.0)

Make sure you create an audio context once and store it somewhere. You should only need one, and if you keep creating
them the browser will run out and error on you.

Design
------

CLJS Bach is purely functional. To make synthesiser composition easier, synthesisers are actually functions that take
an audio context, a time and a duration and return the actual synthesis notes. That's why you need `run-with` to make
sound actually happen.
