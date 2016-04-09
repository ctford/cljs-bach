CLJS Bach
=========

A Clojurescript wrapper for the Web Audio API, extracted from [Klangmeister](http://ctford.github.io/klangmeister/).

[![Build Status](https://travis-ci.org/ctford/cljs-bach.png)](https://travis-ci.org/ctford/cljs-bach)

[![Clojars Project](http://clojars.org/cljs-bach/latest-version.svg)](http://clojars.org/cljs-bach)

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

Once you have a synth, connect it to `destination` and use `run-with` to give it an audio context, a time to run at and a duration.

    ; Play the ping synthesiser now, at 440 hertz.
    (-> (ping 440)
        (connect-> destination)
        (run-with context (current-time context) 1.0)))

If you forget to connect a synth to `destination`, then you'll here no sound, because nothing will be sent to the speakers.

Design
------

CLJS Bach is purely functional. To make synthesiser composition easier, synthesisers are actually functions that take
an audio context, a time and a duration and return the actual synthesis notes. That's why you need `run-with` to make
sound actually happen.
