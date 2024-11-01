![mock up slides animation showing the proposed repl interactions of querying live values across a distributed system and inlining them in code](./2023-11-03-replrepl.gif)

slide notes for a talk i prepared but didn't give.

was planned at dc-res seminar @ TU Wien for 2023-11-03.

this is the first scrappy draft of these self-rewriting question mark query macros/expressions (though they didn't have that name back then).

what i called "tickets" i don't think are a good idea, you'd just inline-def to further inspect a value.


# REPLs - Asynchronous, Distributed, Multiplexed

no live demo today, just faked "screenshots" (slides really)

couldn't get it to work smoothly enough yet for a live demo

but hopefully next time.


## so what are repls?

you may know this. every dynamic lang has it.

enter code, get evaluation result.

read

print

eval

basically chat with an interpreter process.


## if you squint a little

your local shell or ssh session is the same, except...

now you've added latency and failures

natural extension

also, while we're at it, why not talk to multiple processes/machines from within one session

let's replace that one proc with an arbitrary number of n servers

already your old python shell can't really do that;

save for just replicating commands to n machines ~ but not really

maybe some multi-ssh clients?

lastly that 70s teletype, why again can't we just select and paste things in here? let's replace that with a proper editor...

## that's the goal

build and inspect a whole dist sys from the comfort of your nice text editor.

experiment with it, grow it, debug it, while it's all running.

LISP/smalltalk repls could do parts of that at least since the early seventies


## so what are characteristics of a state-of-the-art REPL system?

read-eval-print-loop

sync request/response

1:1

ad-hoc editor integration

exception handling

## examples

Glamorous Toolkit, Smalltalk (Girba+)

REBL, Clojure (Miller+)

SLIME, Common Lisp (Eller+)

Calva, Clojure (Strömberg+)

## what i want

pull, as-you-type

async streams

1:n

explicit textual state

fault handling

stable time basis

state of the art missing

side effect safety

what-if overlay

(ocap) security data viz transactions


## contribution: context, tickets

`(? (+ 1 2) 3)`

`(? (+ 2 2) 4)`

pull: also seen last time, install fns, install "wishes", nodes pull fns themselves

more interesting ~ over network

`(read co2-sensor-livingroom)`

`(read co2-sensor-livingroom) => Promise< ~ >` (Calva)

```
(->
  (read co2-sensor-livingroom)
  (then (fn [v] (println v)))
```

```
(->
  (read co2-sensor-livingroom)
  (then (fn [v] (println v))) => Promise< ~ >
```

```
(read co2-sensor-livingroom)
(? (:value (ticket "e2d9j9d30")) :?/pending)
```

```
(read co2-sensor-livingroom)
(? (:value (ticket "e2d9j9d30")) 530.13)
```

```
(read co2-sensor-livingroom)
(? (:value (ticket "q0pznicd2")) :?/pending)
(? (:value (ticket "e2d9j9d30")) 530.13)
```

```
(read co2-sensor-livingroom)
(? (:value (ticket "q0pznicd2")) 538.90)
(? (:value (ticket "e2d9j9d30")) 530.13)
```

```
(read co2-sensor-livingroom)
(? (:value (ticket "q0pznicd2")) 538.90)
(? (:value (ticket "e2d9j9d30")) 530.13)
(? (ticket "e2d9j9d30")
  {:value 530.13
   :requested-at #inst "2023-11-03 12:13:01"
   :requested-by "3440d4j901"
   :resolved-at #inst "2023-11-03 12:13:04"
   :resolved-by "cijoeqqd20f"})
```

also a little bit of fault tolerance at least, but we'll get to that

consider this line again - glossed over where binding comes from?

```
(context [livingroom kitchen office]
  (read co2-sensor))
```

```
(context [livingroom kitchen office]
  (read co2-sensor))
(? (:value (ticket "s3de0d"))
  [:?/pending :?/pending :?/pending])
```

```
(context [livingroom kitchen office]
  (read co2-sensor))

(? (:value (ticket "s3de0d"))
  [:?/pending :?/pending 404.32])
```

```
(context [livingroom kitchen office]
  (read co2-sensor))
(? (:value (ticket "s3de0d"))
  [569.21 :?/pending 404.32])
```

```
(context [livingroom kitchen office]
  (read co2-sensor))
(? (:value (ticket "s3de0d"))
  [569.21 801.91 404.32])
```

```
(context [livingroom kitchen office]
  (read co2-sensor))
(->
  (? (:value (ticket "s3de0d"))
    [569.21 801.91 404.32])
  (? average 591.81))
```

```
(context [livingroom kitchen office]
  (-> (read co2-sensor)
    (? average 591.89)))
  (->
    (? (:value (ticket "s3de0d"))
      [569.21 801.91 404.32])
    (? average 591.81))
```


```
(context [livingroom kitchen office]
  (-> (read co2-sensor)
    (? average 593.14)))
  (->
    (? (:value (ticket "s3de0d"))
      [569.21 801.91 404.32])
    (? average 591.81)
```


## future work

sigil-probe/eval arbitrary subexpressions

"under construction" sigil (+theming)

security: object capability emacros

nondestructive preview overlays: history, what-if
