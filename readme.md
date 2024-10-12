# 🌱 [open practice](https://www.youtube.com/watch?v=MJzV0CX0q8o)
a.k.a. **NEW COMMIT EVERY MINUTE**

STATUS: **Highly experimental.** (have fun following along if you want.)

<br>

_"So I'm trying to incubate a personal dynamic..."_ nah

<br>

I just want to to **look inside** and see the data

or tell **that machine** over there to **run this** piece of code.

<br>

_that's basically it. how hard could it be?_




### TODO

- [ ] get this thing working well enough to give a fun presentation in 1 week and 1 day.
- [ ] **CURRENT** share huge apple notes
- [ ] upload slides (+ text?) of presentations
- [ ] upload codebases as-is (except check gitignore before)
- [ ] think of what to demo in 14min
- [ ] add [gary bernhardt talk "a whole new world"](https://www.destroyallsoftware.com/talks/a-whole-new-world) to related work. **it is sooo good and nobody has seen it**

#### After LIVE@SPLASH

- [ ] make it nice
- [ ] can we do it in [<26 loc](https://buttondown.com/tensegritics-curiosities/archive/writing-the-worst-datalog-ever-in-26loc/)? see data and run fns?
- [ ] make it run my website
- [ ] blog post: dream clojure notes (see below)


## Observations

- `2024-10-12 15:35` writing live like this is so so so fun



## Random Notes

From 2020-09 until 2024-08 I got paid to daydream (🚮). This is all I have to show for it.

I collected random interesting internet finds and a few own ideas in Apple Notes. Order is somewhat chronological, newest at the top. Sadly only very few entries are timestamped or properly sourced. Lightly edited (typos, clarity, formatting) and small parts translated from German.

|    | Legend |
| --- | --- |
|  🕺  | [my advisor](https://i4c.at/goeschka/) (btw he was awesome: just let me do anything, or nothing, no academic 🚮) |
|  🧼  | shower thoughts / own ideas (they get better from bottom to top) |
| **bold** | this is the good stuff (i think) |

---



leo noel

missionary

discrete time: streams = succession of similar events, requires backpressure (all evts need to be processed in order)  (mouse clicks, log entries, db transactions) - only exists at time of event
continous time: signal = the state of an entity, requires lazy sampling (mouse position, clock time, db state (why?) spreadsheet cells (why? - they are always defined, can take an immutable snapshot at a point in time, can also watch them. derived computations are also continuous signals)

- it's about backpressure vs lazy sampling.
  - in CT you get lazy sampling,
  - in DT you get backpressure.

```
(what?) (one) (many)
data: values | sequences
effects: task | flow
```

  - tasks are effects that produce one value
  - flows are effects that produce multiple values



- functional effect and streaming system
  - you can use CT and DT with both effects (dual and complementary)
  - many effect systems are either CT or DT, but you'd want both since some effects are inherently discrete or continuous and they have different requirements
  - language extension, alternative to monads



---


https://merveilles.town/@nakst/110904552263386432?utm_source=substack&utm_medium=email

(via omar rizwan folk newsletter)

@nakst@merveilles.town

"everything is a file"

**applications can dynamically register global, stateless APIs that are inspectable by the end-user and accessible from any programming language**

[running] applications can [as root] dynamically register [with some work] global, stateless APIs [either str foo() or void foo(str bar)] that are inspectable by the end-user [but confusable with real files] and accessible from any programming language [but awkward to use in e.g. C because of the text parsing, or gathering events]

Aug 17, 2023 at 12:46

@nakst

It's noteworthy how while typical APIs have data types like int, bool and string, **filesystem-based APIs have data types like text, images and others that can be dynamically registered** by applications.
I also think it would be **cool if there were ".bool" and ".int" files, and when you open them you get a window with a lone checkbox or slider** respectively.



---

what i want is basically console ninja in prod

https://github.com/wallabyjs/console-ninja/tree/main#logpoints

---

### April 2023

🧼 IDEA: **sigils**

dynamic scope **wrapping forms wrapped and topform-eval'd by a single character stroke**

definable inline (maybe)

(note from 2024) yes! the bang is a sigil per this definition because it _does_ something the moment you type it out to the (top level) form right after it. the dynamic scoping part is not important.




🧼 IDEA: multi repl over mqtt

- one topic per "object"
  - **inspired by alan kays "every object should have an url/ip address"**
   - topic name is a capability token
- node listens to its (root?) cap token channel


🧼 IDEA: repl sections in plaintext ui delimited by `--`
  - evaluation results go on empty lines between section delimiters


🧼 IDEA: Plain text UI for EAV forms, inspired by imp

```
; create/upsert entity by id
#"entityid"34a12fd893e4da1"
:person/first-name "Joe"
:person/last-name "Erl"

; update existing entity by query
[:= :person/last-name "Patsch"]
:access/banned true

; create entity with new random id
; editor would need to fill new id
```



🧼 IDEA: unison + clojure - bloat = nij

- lisp, clojure data literals,
- same namespaces: no mutable globals
- transactional reified changes to the whole codebase


---


mech lang by cmontella from eve

https://www.hytradboi.com/2022/i-tried-rubbing-a-database-on-a-robot

<33333


---

https://github.com/davidbullado/blog/blob/main/we-need-a-new-versioning-notation.md

Ending Dependency Chaos: A Proposal for Comprehensive Function Versioning

````
@moduleVersion 2
 ...
@funcVersion 16
export function addFunction(a: number, b: number): string {
  return a + b;
}
```

hmmm dunno

---

<3

https://xtdb.com/blog/trucks-tubes-truth/

Differentiate into categories:

- Commands
  - "passive-aggressive events"
  - **if an event implies anything at all will happen when it is read, it isn’t an event**
- Application/Domain Events
  - CQRS-like, eg. FundsTransferred
- System Event
  - aka change data capture CDC
  - opaque computational observations ("accountUpdated")
- document events
  - pure stateful observations, a declaration of state at a point in time, RESTful event-carried state transfer



---

from the epic Future of Coding log by Steve Krouse

https://futureofcoding.org/log

"steve krouse OS"

"From my chat with JE [Jonathan Edwards] I realized I've been secretly dreaming of a dataflow OS, not a strictly Conal-DCTP-one. [Conal Elliott's Denotational Continuous Time Programming, what FRP should have meant]. Architecture feels a lot like CycleJS actually

"It’s more related to algebraic effect handlers, which I think Conal would say are like monads in that they import the imperitive way of thinking into FP instead of building better abstractions on top"

"**Programming is setting up computation over time.** Unfortunately this means that many important **insights about your program aren't apparent until future times**  Live programming is about **bringing insights from future times to the time of programming**"

"I've actually been asking for a definition of "programming" that is better than "telling a computer what to do" and now I like "telling what a computer what it should do later""

Eric Green New Deal Now Gade @ecgade



**"How many language features can be replaced with editor ones? (stub outline)**

  - lets, closures, imports (Cyrus Omar of Hazel talks about this)
  - it seems like most things can be editor features, especially if the editor is recognizing patterns and making syntactic sugar out of them (a la lamdu)
  - it’s the things you want in the AST (stored as metadata) that maybe should be in the language semantics?
"


"Show the (inner) data (and it’s structure/type). BV says it best, “Some people believe that spreadsheets are popular because of their two-dimensional grid, but that’s a minor factor. Spreadsheets rule **because they show the data**.... If you are serious about creating a programming environment for learning, the number one thing you can do – more important than live coding or [ … or … or … ] or anything else – is to **show the data**.”"

"But despite these shortcoming’s spreadsheets succeed because of the above discussed adherence to “**show the data**” as well as embodying what I would argue is the second most important programming environment characteristic: create by reacting, which is distinct from by is enabled by live coding."

"But again, to be clear, I want to focus on **show the data as my only focus first, and maybe after that’s mostly done, I’ll see if I can slip in a create by reacting.** (The jury is still out on whether live coding is absolutely require. My gut says it is, but I don’t want to make the mistake again of thinking more things than are necessary are necessary.)"

"Another key insight: **the default behavior of FRP applications should be to persist data for forever unless programmed otherwise. The resetting of app’s data on page reload is a wacky accident!**"


---

https://rxmarbles.com/#max

https://rxviz.com

https://rxfiddle.net

---

eli parra

homoiconic spreadsheets + clojure

  - "code is evaluated data"
  - "data is quoted code"

since 2018, excel and sheets have a literal representation of cells as text, aka dynamic arrays (as opposed to static/autofilled ones)
```
a1 b1
a2 b2 ===
usually by rows === {a1, b1; a2, b2}
can be by cols === {{a1; a2}, {b1, b2}}
comma , next column (like csv)
semicolon ; next row (like sentences, like newline)
```

  - (can only create NxM tables, must be rectangle)
  - BUT they break the fundamental value rule of spreadsheets (a cell can only effect its own value) by spilling over right and bottom cells.
    - (excel required an entire rewrite of its formula engine)
  - in practice, it only bends the rules. array spills need space (empty cells) - if the cells contain a value, the ={} expression is an error and no values are filled



---

<3 deno

https://matklad.github.io/2023/02/12/a-love-letter-to-deno.html

---

jamie again

https://www.scattered-thoughts.net/log/0021/

**EVERY TOP LEVEL DEF HAS AN ID hash23412321**

After taking a step back and thinking about the problem from scratch I had an epiphany - **why is there even a database? If you have a declarative language which can describe data and computation, why use that to compute an imperative action to apply to a totally different system for describing the same data. This is implementation driven thinking.**

Let's just mutate the source code instead.

The history of the program lives in a sqlite database. You can use the cli tools to spit out the current version of the program into a text file, edit it in a text editor and then commit the diff back into the database.

```
#2280651848495541
parent("Bob", "Charlie").

#3378495017200132
ancestor(x, z) <-
  parent(x, y),
  ancestor(y, z).

#3531648068531767
ancestor(x, y) <-
  parent(x, y).
```


---

expressions of change:

- change as language primitive, design a formalization,
- reify modifications in the program
- custom editor+pl

i'm not sold on the approach, seems abandoned.

haha comment from audience: it's just catamorphism+anamorphism (speaker asks to get explanation later)


---

jamie 0020


Fossil

Fossil is the one of the few local-first apps I know that is actually used in anger. It started out as just a DVCS but over time grew a wiki, issue tracker, forum and various other embedded apps. All of which run offline and can be pushed/pulled between repos and even forked.
So I was curious to find out how it worked under the hood.
The underlying data-structure is content-addressed append-only set of artefacts. Forum threads, wiki pages, issues etc are built by summing up the effects of special event artefacts.
Forums are effectively OR-sets - all you can do to a post once it has been made is delete it, leaving a tombstone in the tree.
Wiki pages do last-write-wins. I expected to at least get a merge conflict, but no.
Issues are bags of key-value pairs, where each pair does last-write-wins.
In one sense, it's disappointing that there is so little handling of conflicts.
But on the other hand, there is very little handling of conflicts and it seems like it's fine in practice. So maybe many problems can fall to being broken down into atomic facts and doing last-write-wins for each fact?

+

Self-hosting

https://www.scattered-thoughts.net/log/0020/


I lurked in various discussions of self-hosting recently. One point that seemed rarely challenged is that self-hosting is hard.

That has been my experience for many pieces of software. But self-hosting fossil is really easy.

What makes most software hard to self-host?
  - **Too many moving pieces**
  - **Too many configs to learn**
  - Non-trivial backup and restore

What makes fossil easy to self-host?
  - Single executable
  - Builtin web server (fossil serve)
  - Single file database (easy backup and recovery)
  - Sync to other devices (your local working copy of a fossil repo is also a backup)
  - Config stored in the database
  - Config edited by builtin web interface (fossil ui)

Other things we could add:
  - Automatic updates
      - Notifies me first if update requires more than a few seconds downtime, manual action (eg migration) or might break something. (Notifying people is often the part I'm most worried about breaking and is often hard to configure eg synapse wants a separate email gateway set up. But fossil will talk directly to fastmail for me.)
  - **Self-check**
      - Notify me if not reachable from the internet
      - Notify me if running out of disk space
  - Simplified hosting
      - Give them a binary and a database, they run it (and restart it if necessary)
      - **Notify me about crashes/restarts**
      - Ship a minimal OS that does nothing but run my binary
      - Deal with OS upgrades (shouldn't cause problems if not depending on dynamic libraries, userland services etc)
  - **Put a console/repl in the web interface so I can do bulk edits (eg banning users)**

Why isn't this more common? I suspect because most software is optimized for **industrial use, not personal use.** For industrial uses the operations overhead is not a big deal compared to the development and operational efficiency gained by breaking things up into communicating services. **But for personal uses the overwhelming priority is reducing complexity so that nothing fails.**


---

fosdem2022

The relational model in the modern development age. Schema migrations suck. The state of the art has barely changed since the first SQL databases. Vitess has put a ton of effort into fixing this. (?)

---

jamie on instrospecing async

I'm still thinking about this async gui pattern.

If I convert code that uses explicit state machines to use async/await then it's easier to read, easier to write, and I can use defer to manage lifetimes. But I can no longer just print out the state, or make debugging tools that tell me eg which users have requests that are currently waiting on database io. **Are there any implementations of async that let you inspect the closed-over state of awaited futures/promises/frames?**

---

from jamie 0021:

Typed Image-based Programming with Structure Editing.

Dealing with type/schema migration by recording changes to types in a structural editor and using an OT-like process to reconcile conflicts. I'm not sold on this approach - it spends a lot of complexity dealing with changes to anonymous product types, but given that you're editing in a structural editor already there is no need for anonymous product types - **just insert ids under the hood.** But I think the paper is still valuable for elucidating the problem. **Version control of code and schema migration of persistent data are clearly two facets of the same problem but our current tools treat them as entirely separate domains.**


---

ideas from subtext 10 design doc:

user mode (can only change data, value types must stay fixed, limited errors possible) vs programmer mode (can change functions, more errors possible)

function run forwards are pure, ref transparent etc; but when run backwards, they have imperative effects: user can edit the output, which feeds backwards through the fn to change inputs.

fns are defined with concrete values that serve as examples of their types

Subtext has no syntax for describing types: it only talks about values. Function inputs are defined with a default value, so no type needs be specified. Likewise error messages never talk about types — instead they point to a mismatch between values at two code locations, additionally referencing the code locations where they were defined.

We believe that type systems are an essential formalism for language theoreticians and designers, but that many language users would prefer to obtain their benefits without having to know about them and write about them.

_FIXME: simpler: names are nominal, everything else is structural. Field names can be nominal because we can bind them contextually, even in constructors, because of argument defaults. _
In PL theory terms, Subtext mixes aspects of structural and nominal type systems. It is structural in that x = array{0} and y = array{1} have the same type. It is nominal in that x = record {a: 0} and y = record {a: 0} have different types. Every time a block item is defined a globally unique ID is assigned to it. There is a workspace-wide dictionary that maps these item IDs to their current names. Renaming a block item just changes that dictionary entry. Type equality requires that block item IDs be equal, not that their names are currently spelled the same.




---


https://twitter.com/Mappletons/status/1561357946960990213

Mini apps and bits of software you’ve built just for yourself and/or friends and family.

Things that don’t scale, aren’t meant for others to use, & valuable just for your specific, snowflake use case.

---

on headers <3

**open-ended metadata, "allow all, bless some"** (clj maps philosophy <3)

https://subconscious.substack.com/p/if-headers-did-not-exist-it-would

"We really do build entire apps just to tag files with a bit of metadata.
Way back in 1990, BeOS took this insight and ran with it. Files in BeOS could be tagged with any key-value metadata you liked. The metadata could be displayed as columns in the file finder."
￼
In fact, many BeOS "apps" were really just finder windows with specific metadata columns. You could customize or create your own "apps" by modifying the columns in a window.


---

pitfalls of edn

https://nitor.com/en/articles/pitfalls-and-bumps-clojures-extensible-data-notation-edn


---

jamie brandon again, imp/preimp

imp live repl, evals whole buffer on every keystroke

language co-design needed to give semantics to partial programs without structural editing

(cut buffer in half, evaluate up to cursor point is still a valid expression)

has only tiny fixes like auto closing parens

brandon proposes: reify watches.

select expression to watch, hit key to reify as (expression34241),

then compare with other watch expr


**🧼 IDEA: reify watches by sigil/edwards-probe, give id**


---

jamie brandon again

the program is the database is the interface

https://www.scattered-thoughts.net/writing/the-program-is-the-database-is-the-interface

**OOOOH MY MIND IS BLOWN, THIS IS THE _LITERAL_ "self modifying code" INTERACTION MODEL I WAWS DREAMING ABOUT**

https://www.scattered-thoughts.net/log/0022

Continuing with the 'the program is the database' theme from last month, I made a clojurescript dialect (called preimp) where **the only source of mutable state is the source code itself**. It works well enough for spreadsheet-sized datasets:


jamie data soup problems

https://www.scattered-thoughts.net/log/0020#data-soup

**"my computer usage is full of tiny CRUD problems that are typically solved either with single-purpose apps or with adhoc manual effort. Here's a random selection off the top of my head:**


---


jamie brandon (eve, logicblox, hytradboi...) <3

https://www.scattered-thoughts.net/writing/the-shape-of-data/ <3

- handles are the better pointers
  - Another way to think about it is that a pointer combines an id with the context for interpreting that id. The id 42 is just a number, but the pointer 42 is something we can dereference to find out what it refers to.
  - At some point we have to interpret ids, so we have to have pointers or something like them for providing context. But there are hefty advantages to restricting our application-level data model to be a tree and using ids to model the graph-like parts.


refset response on reddit:

There are plenty of good insights in there, so it's worth a proper skim, but I'd summarise it for this audience as "This is not a blog post about Clojure, **however Clojure+edn gets a tonne of things very right compared with most languages and is really only missing the-codebase-is-data and the-execution-is-data**". There are explanations of these concepts plus a wishlist towards the end.


**Initiatives that I suspect can help Clojure's ecosystem greatly on those two fronts...**
  1. **Durable, transactional REPL**: https://github.com/repl-acement/repl-acement (explanation here https://github.com/repl-acement/editors)
  2. UI toolkits: https://github.com/HumbleUI/HumbleUI and https://github.com/phronmophobic/membrane
  3. **Alternative runtime paradigms**: https://github.com/babashka/sci and https://github.com/Convex-Dev/convex (not Clojure...but close enough)
  4. (any others to suggest?)



---

  - omniscient debugger (can access multiple points in time simultaneously)
  - time travel debugging (can move forward/backwards)
  - robert o callahan
  - chromometer=amber
  - rr = for c/c++
  - pernosco


  - how time travel debuggers work:
    - a: state snapshots (git -- actually not, noone does this in practice but its only the presented logical model)
    - b: change tracking (sun, amber, odb? -- usually mixed with periodic snapshots)
    - c: deterministic replay (rr, panda)

robert: [on connecting debuggers to a running system] **if your program is actually 100s of processes running on 100s of machines in the cloud, you don't know which one to atttach to ahead of time**, and if you stop it, you kind of break the whole system [...] so these things have to keep running -> record and replay is a better fit.


  1. debugging has been trending on a downward spiral:
  2. **infrastructure doesn't even support attaching debuggers (anymore)**
  3. people don't use debuggers anymore, whatever language/tooling support there was kind of rots away
  4. fall back to log debugging
  5. eventually people come to assume that dedicated debug tools don't work, can't work


---

eve dev diary http://incidentacomplexity.com/archive/

<3

---

certain details in the way it is usually applied only work with compiled languages and, when applied to interpreted languages, result in the trivialization of theory noted by Wand's classic paper, "The Theory of Fexprs is Trivial".

Mitchell Wand. The Theory of Fexprs is Trivial. Lisp and Symbolic Computation, 10:189--199, 1998.
Abstract: We provide a very simple model of a reflective facility based on the pure lambda-calculus, and we show that its theory of contextual equivalence is trivial: two terms in the language are contextually equivalent iff they are alpha-congruent.

ummmmmmmm


---

peter saxton

https://petersaxton.uk/log/

typed structural editor, hot code reloading, built on gleam = (erlang, js)

actor primitives, config is code (client random id) for cluster mgmt

effect types

<3

---

Show HN: We built a developer-first open-source Zapier alternative (trigger.dev)

https://www.reddit.com/r/ProgrammingLanguages/comments/10gylhm/are_there_any_languages_with_transactions_as_a/


https://flora.sourceforge.net/aboutTR.html

Flora-2, an object oriented logic language, has first-class support for transaction logic


---

Paper ideas 2023-01-16
Survey: Side-by-side Code/REPL state visualization tools/approaches: REPLugger, Clerk, Proto REPL

---

🕺 help advisor i don't want to write

Hi Michael,

The tinkering is coming along pretty well, but I'm starting to feel a bit uncomfortable because I just can't seem to get into writing. Do you have any ideas on whether/how some of this could be packaged as a smaller paper?

**Identity of top-level definitions** More theoretical: what are the tradeoffs when functions (or other top-level definitions) are identified either by hash or by unique ID? In reality, both forms are rare; most often, only the name exists anyway. What hybrid forms make sense and when?
Hash: Unison; Unique ID: cap'n'proto, homeassistant

**Survey of the previous versions of Eve** The Eve project has apparently produced 34 completely different programming environments. Besides a GitHub repo, there isn't much information about it—so this would be more of an archaeology study, one I'd rather read than write.

**Pause execution on exceptions, inspect bindings, resume with new code—without support from continuations** A poor approximation of Smalltalk's "does not understand" or Common Lisp's condition system, except that it does without continuations in the host runtime, using only indirection+promises at every function call.
But it doesn't really work reliably, is a weird hack, only works for simple code without closures/callbacks, "poisons" call sites with promises, and inspecting or modifying the call stack will never work (only local bindings). And updating/resuming doesn't work yet either.
I'd be too embarrassed to publish it at the moment—maybe when it's more useful and together with a workflow/editor integration, or at least "distilled" API primitives.

**Editor integration via language server protocol** I can now finally show simple autocomplete, hover tooltips, and read-only text over top-level definitions.
Essentially, custom mini-editor UIs that are defined inline with the code.
It's probably still too early to settle on a specific workflow or built-ins, but these small UI additions are already quite helpful, for example, when editing an existing function.
Todo: refine the API, find more use cases, abstract recurring usage patterns.
I find it promising and interesting but don't yet see a paper in it.

**Current test project for the system:** live reading of my electricity/gas consumption.

**The Good** working with real data right away: tapping the IR port via serial, ring buffer code, message header/length/footer parsing, AES decryption, byte offsets/bytes to int, and even live writing to MQTT and debugging was incredibly fun. I rarely had so much flow while programming, all without waiting for compile/restart.

**The Bad** it still needs much more editor support and more interactivity in the editor itself; at the moment, the editor is just input, and the viewer window is just output—that's "too far apart." I also still had to restart often and lost state, but that wasn't too bad here.

**The Ugly** single-writer EAVT as a "one data model to rule them all" is nonsense, at least in direct use (i.e., everything is global). It clearly needs normal primitive state containers (atom) and also implicit local state in closures. State in atoms can be mapped well to EAVT; but closures (equivalent to object instances) not at all. Dynamic software update of function instances/objects is probably the biggest unsolved question mark in this field for the last 60 years (yes CLOS but no); I hope to handle it well enough with the other tools, like "if the system has to restart or crash, then that's okay too."

Or something from the completely untouched construction sites from our last conversation? Surface syntax, namespacing, when-then production rules, data constraints -- Since there's nothing really new here, probably only in combination with the rest.

Thanks ✨


---

a few programming language features i'd like to see

https://neilmadden.blog/2023/01/18/a-few-programming-language-features-id-like-to-see/

teleo-reactive programs

```
to make_tea:
  when perfect(tea) -> done
  when brewed(tea) -> remove_teabag; add_milk
  when hot(water) -> pour_into(cup); add_teabag
  when cold(water) and full(kettle) -> boil_kettle
  when empty(kettle) -> fill(kettle)
```

+ design by contract

```
to make_tea:
  achieves full_of(cup, tea)
  requires not empty(tea_box)
```


+ STRIPS planner

```
achieve full_of(cup, tea)
```


---

c.a.r. hoare's turing speech 1980

the emperors new clothes

The first principle was security: The principle that every syntactically incorrect program should be rejected by the compiler and that every syntactically correct program should give a result or an error message that was predictable and comprehensible in terms of the source language program itself. Thus no core dumps should ever be necessary. It was logically impossible for any source language program to cause the computer to run wild, either at compile time or at run time. **A consequence of this principle is that every occurrence of every subscript of every subscripted variable was on every occasion checked at run time against both the upper and the lower declared bounds of the array.** Many years later we asked our customers whether they wished us to provide an option to switch off these checks in the interests of efficiency on production runs. Unanimously, they urged us not to - they already knew how frequently subscript errors occur on production runs where failure to detect them could be disastrous. **I note with fear and horror that even in 1980, language designers and users have not learned this lesson. In any respectable branch of engineering, failure to observe such elementary precautions would have long been against the law.**


The story of the Mariner space rocket to Venus, lost because of the lack of compulsory declarations in FORTRAN, was not to be published until later.

The way to shorten programs is to use procedures, not to omit vital declarative information.

At last, there breezed into my office the most senior manager of all, a general manager of our parent company, Andrew St. Johnston. I was surprised that he had even heard of me. "You know what went wrong?" he shouted - he always shouted - "You let your programmers do things which you yourself do not understand." I stared in astonishment. He was obviously out of touch with present day realities. How could one person ever understand the whole of a modern software product like the Elliott 503 Mark II software system?

I realized later that he was absolutely right; he had diagnosed the true cause of the problem and he had planted the seed of its later solution.

[granger adds in "against the current": this is basically incompatible with open source software]

I gave desperate warnings against the obscurity, the complexity, and overambition of the new design, but my warnings went unheeded. I conclude that there are two ways of constructing a software design: One way is to make it so simple that there are obviously no deficiencies and the other way is to make it so complicated that there are no obvious deficiencies.

But to me, each revision of the document simply showed how far the initial Flevel implementation had progressed. Those parts of the language that were not yet implemented were still described in free-flowing flowery prose giving promise of unaltoyed delight. In the parts that had been implemented, the flowers had withered; they were choked by an undergrowth of explanatory footnotes, placing arbitrary and unpleasant restrictions on the use of each feature and loading upon a programmer the responsibility for controlling the complex and unexpected side-effects and interaction effects with all the other features of the language.


---

granger: "a really fast db is still a really slow programming language"

we (eve) could put 2,000 balls on the screen (at 60 fps), rust could do 4,000,000

over the course of the eve project they built
  - 34 programming environments
  - 24 compilers
  - 16 storage engines
  - 9 interpreters
  - dozens of parsers and standard libraries


eve bibliography

https://github.com/witheve/eve-experiments/blob/master/design/bibliography.md

https://www.cl.cam.ac.uk/~afb21/publications/HCC02a.pdf

Makes a strong argument that programming is inherently non-direct


---

Abraham Coetzee's Masters thesis

"Combining reverse debugging and live programming towards visual thinking in computer programming"

The thesis is full of great insights and gives a great summary of other work.

algojammer + related work
https://github.com/ChrisKnott/Algojammer


---
http://glench.com/NonprogrammersPaper/

glench redesign of

Studying the Language and Structure in Non-Programmers' Solutions to Programming Problems by Pane, Ratanamahatana, and Myers


--

protorepl: save macro takes a unique identifier (y not containing fn name) and recognizes local binding values


Glench: REPLugger temporary overrides of values, fake into if statements, save and name overrides to edit them also if we're in a different part of the code -- maybe unnecess w scratch namespacing only pulling together relevant fns


---

http://worrydream.com/MagicInk/#interactivity_considered_harmful


That is, this software is normally "used" by simply looking at it, with no interaction whatsoever. In contradiction to the premise of interaction design, this software is at its best when acting non-interactively.

Accordingly, all interactive mechanisms—the buttons and bookmarks list—are hidden when the mouse pointer is outside the widget. Unless the user deliberately wants to interact with it, the widget appears as a pure information graphic with no manipulative clutter. (Tufte uses the term "administrative debris.")


Generality. If we think of a computer as a machine that runs software, then in some sense, all data handled by a computer platform must be "software." The data making up a JPEG image, for example, can be thought of as the encoding of a program that describes a picture. (This is sometimes called the "data is code" equivalence.) But the limitations of the JPEG platform result in severely lobotomized "programs"—they cannot animate, respond to context, incorporate new compression techniques, or otherwise take any advantage of the computer beyond what JPEG explicitly allows. A crippled platform cripples a designer's means of expression.


In order for a designer to take full advantage of the medium, a good platform must provide safe access to everything that is technologically possible. A platform for information software must offer: inputs from the environment (that is, communication with other software and physical sensors), from history (that is, storage), and from the user (that is, interaction); computational resources with which to respond to inputs; and unrestricted graphical output. Anything less robs information software of its full potential. The proper way to prevent destructive behavior is a well-designed security model, not arbitrarily amputating the computer's capabilities.


**"The platform must make it possible to create information software. The tool must make it easy"**


---

Beautiful Software

Christopher Alexander's research initiative on computing and the environment

"...the character of the computer environment of the future needs to become more childish,
and more human, if it is to help human beings to genuinely extract the best of themselves...
this change may well affect activities which are apparently technical, not only those that one
broadly classifies as 'creative'."

In the future we'll only understand how to build good software,
if we focus, now, on creating software that does good ...

"It is a view of programming as the natural genetic infrastructure of a living world which
you/we are capable of creating, managing, making available, and which could then have the result
that a living structure in our towns, houses, work places, cities, becomes an attainable thing.
That would be remarkable. It would turn the world around, and make living structure the norm once again,
throughout society, and make the world worth living in again." -- Christopher Alexander

https://beautiful.software


---

https://worrydream.com/SeeingSpaces/

bret victor

seeing spaces

you need to be able to:
1. see inside
2. see across time
3. see across possibilities

---

🧼 demo agenda

slides: take problem tree from scaling the repl experience (2021-09-09)

- cljs devtool file writing client
- cljs devtool browser / visualization reading thing
- cljs runtime / server
- cljs runtime / client node tool

editor interactions:

- write db query form, save,
- forms get id if they don't already have one
- results get written in next form (append after current top level)
- write fn -> save -> fn gets installed

show:

- bootstrap node with cap key
- hello world blinky
- server has full access to db
- clients can transact via server
  - (but still have a local only db, which they may post parts fo to the server for debugging if requested by a data item)
- paused exceptions: programmer workflow to resolve by transacting data
- server has tx database, shares all data to all clients.
- editor is a client that can query data, send txs to main server



### November 2022

🧼 I HAVE A FEELING INTERACTIVE PROGRAMMING IS A GREAT FIT FOR CAPABILITY SYSTEMS

- how to interact with capabilities in an interactive programming system?
  - create
  - attenuate
  - delegate

- scope: JUST rosalind-like line of business apps,
  - one central master controller with full authority,
  - many dumb clients
  - and some special devices
    - (sms, imaging, scanners, door locks, leds, sensors)
    - few named known snowflake pet servers
  - <1000 users
  - NOT mutually suspicious parties (packages, signatures, pubkeys)
  - NOT end user programming (customize, control caps of device)


---

🧼 INTERACTON STYLE IDEA: PAUSED LIVE EXCEPTIONS

  - What's the programmer's workflow?
    - Don't unwind the stack
      - but give user the opportunity to unwind to known-good stack location (refresh ui)
  - + ui to attach user messages
    - "chat with programmer"
      - what I was doing,
      - programmer asking for clarifications
      - user responding while their app gets fixed
    - subsumes github issues
      - support chat no longer living in a separate infra
        - through this has obvious drawbacks re stability



  - Pause at exception site
   - until "live" programmer fixes the issue
     - usually within a few minutes

  - Similar to Smalltalk's didNotUnderstand message
    - or Dark's 404
  - the programmer looks at live exceptions
    - the system groups similar errors (like Sentry)
      - but shows CL-like unwind-protect debugger
      - and call data inline with callee code


  - this also elegantly "solves" hanging closures error
    - and errors when hot upgrades would have failed
    - instead of failing, they just hang with all open closures
     - + data/state visible to the programmer
       - can edit data; can replace code; can resume or abort

Editor UI ideas for lexical wrapping / define "blast membranes"

```
(local ...)
(node # { aaa }  ...)
(temp ...) - retracted when deleted
(??) -simulate side effects
```

---

🧼 paper idea: CLOJURESCRIPT ON SES (or even better within Jessie subset of SES?)

https://es.discourse.group/t/what-is-the-current-status-of-ses-tinyses-and-jessie/1306/10

  - seems like jessie, jessica, ses, are abandoned
  - hardnened js moniker is stale
    - can we really just use sci?
      - how can the js world not produce a cap safe subset in 20 years
        - but borkdude can?
          - while building maintaining 20 other hard core libs?
            - 🧠

---

paula gearon - not your mother's datalog

https://www.youtube.com/watch?v=Ug__63h_qm4

Datalog is not Predicate logic. (but looks like it is).

Graphs can be viewed as Predicate logic:

`predicate(node1, node2).`   - vs -    `[node1 predicate nodde2]`
`attribute(entity, value)`   - vs -    `[entity attribute value]`

(extrinsic data: what we put in) vs. (intrinsic data: generated by rule evaluation)

- is datomic datalog?
  - *yes*
   - has intrinsic database
   - has extrinsic database via rules
   - has rule recursion
   - has termination guarantee
   - has exensions (negation)
  - (however, the graph query language for datomic is a graph query language)
     - (it is not _the_ datalog language)



---

🧼 Redell's caretaker pattern in Clojure

```
(defn
  make-caretaker
  "Redell's caretaker pattern"
  [o]
  (let [ok? (atom true)
        o' (fn [& args]
             (if @atom
               (apply o args)
               (throw :unauthorized)))
        gate (fn [new-ok?] (reset! ok? new-ok?))]
    [o' gate]))


(defn main [{:keys [carol bob] :as sys}]
  (let [carol (:carol sys)
        [carol2 carol2gate] (make-caretaker carol)]
    ((:bob sys) carol2)
    (carol2gate false)))
```

---

🧼 thinking about a subset of clojure that is cap-safe, impl'd as a library within clojure

- rework namespace mechanism:
  - no more mutable map
  - require modules at runtime by hash
  - **requiring modules is a capability**

- open world:
  - just ocap safe subset of clojure
  - **inspectable atoms, defns**
  - code in global db
    - but fns may not access global db
      - unless given access


- **~ASSUME A SINGLE CENTRAL SERVER~**
  - and trusted clients
  - this is for **building personal scale software**
    - this simplifies everything
  - a place to store logs/defn-invokes/atom-changes
  - a serializer for global time


---

🧼 some ideas for possible self-contained papers

  - `deffn`
    - a wrapper for (could also monkeypatch core/defn - haha) top level functions, and saves anonymous closures under its top level entry ID (best effort match?)
    - definitions to give them IDs / track evolution of a single fn over time, and evolution of a namespace over time to save call/return data and perf metrics (and any other custom metrics defined inline) to display in editor **(using custom viz functions, also defined inline)**.
      - where to save/stream metrics to?
        - save locally in persistent fact db, that's all
        - data layer should take care to replicate this somewhere else

possible editor UI:

```
(deffn mimi []) ; full color when available in all

; missing from: prod-a _eval_
(deffn bar []) ; greyed out because it is not present in all connected(monitored) nodes' namespaces
```

  - `drop-in atom`: record state changes of nodes, stream back to editor, experiment with prod data

  - `Polajure`:
    - A POLA capability subset of clojure
    - like SES/Agoric/Jessie, **remove effectful fns from stdlib**
    - **hell i'd use that immediately**
      - hmm maybe **SCI already does that**
        - yep of course ❤️


---

what's the difference between edit/developer mode and user mode?
user mode: POLA is in effect, only given references are available in scope, effects happen (!)
dev mode: POLA is off, all refernces are visible to the dev, can look into any fn params/state/reconfigure code, effects are simulated (!)

in other words, master dev has a capability to see/change everything (an access cap to every fn defined by them)
another dev may have a capability to see [args/returns of] or change a particular function used in my system?

HOW to unify a global freely accessible db with ocap/POLA?
a. db reads are side effect free and unrestricted (if given the db as arg) - no cap needed except db value (and pure query fn)
b. local db simulations / ephemeral data overlay (with db db' ...) are free and unrestricted, again just db value and pure fn needed
c. persistent db writes to local vat/node - effectful cap needed (closure with bound swap!) (fn update! [new-db] (reset! db new-db)) or better because more confined: (fn append-tx! [tx] (swap! db d/apply-tx tx))

while a, b seem like no caps because they are just values (but give free access to db value), they are caps as well (?) - the read only cap to an value
it's just a static value. quote miller: "Data provides only irrevocable knowledge, so don't bother wrapping it (in a caretaker/membrane)"

"the right to exercise access carries with it the right to grant access". [capmyths, (Gong's citation [1] is Boebert's 1984 paper, which corresponds to our citation [2]).]


---

clerk

As top-level form to change the document defaults

Independently of what defaults are set via your ns form, you can use a top-level map as a marker to override the visibility settings for any forms following it.

Example: Code is hidden by default but you want to show code for all top-level forms after a certain point:

```
(ns visibility
  {:nextjournal.clerk/visibility {:code :hide}})

(+ 39 3) ;; code will be hidden
(range 25) ;; code will be hidden

{:nextjournal.clerk/visibility {:code :show}}

(range 500) ;; code will be visible
(rand-int 42) ;; code will be visible
```

This comes in quite handy for debugging too!


---

landau / eros/capros

http://www.charlielandau.com/EROS_Interface_Design.html

**Separate functionality from human interface.**

This principle seems obvious when you consider that it is easier to write a program to take a program-friendly interface and translate it into a human-friendly interface, than vice-versa, because the translator is a program not a human.

---

"Novel problems arise while fixing bugs in confined programs."

notes on debugging keykos http://cap-lore.com/CapTheory/KK/Debug.html

---

The Can Opener

Often some family of brands have no proprietary interests to protect from each other and we invented the can opener for such a family. A can opener will try to open a domain by invoking each domain creator whose brander is in the family. If it succeeds it instals a DDT and reports the real type of the opened object.
An unimplemented recent idea is to include creators outside the family. The opener recalls that such creators are not of the family and reports the type by numeric code, probably the alleged key type.

keykos / cap-lore

branding ~= nominal typing

family ~= (super-)class hierarchy ~= certificate chain


---

Perspective

Security and Privacy, and even reliability, rely on the inabilities of programs, not so much the abilities. Unix fails the capability test because **there are too many ways for programs to have effects, or receive information. Manuals list ways to do things. You don't find in manuals even claims such as "the above are all those ways".** A kernel programmer is usually pleased that he has provided a new way to move information around. I worry about the Mac's 115 kernel extensions.
Another observation is that connection begets connection; message may convey capabilities. More importantly: Only connection begets connection.


---

**The art of upgrade is to preserve state amid change and to enable change amid state. --with apologies to Alfred North Whitehead**

The original being "The art of progress is to preserve order amid change and to preserve change amid order." from Alfred North Whitehead in "Science and the Modern World", Macmillan, 1925 or 1929.
from http://erights.org/data/serial/jhu-paper/intro.html



---

Miller's thesis -- OMG related works

"**Global namespaces create intractable political problems.** Froomkin's Toward a Critical Theory of Cyberspace [Fro03] examines some of the politics surrounding ICANN. In a world using key-centric rather than name-centric systems, these intractable political problems would be replaced with tractable technical problems."

**"Data provides only irrevocable knowledge, so don't bother wrapping it (in a caretaker/membrane)"**

"Lauer and Needham's On the Duality of Operating System Structures [LN79] contrasts "message-oriented systems" with "procedure-oriented systems." Message-oriented systems consist of separate process, not sharing any memory, and communicating only by means of messages. The example model presented in their paper uses asynchronous messages. Procedure-oriented systems consist of concurrently executing processes with shared access to memory, using locking to exclude each other, in order to preserve the consistency of this memory. . The example model presented in their paper uses monitor locks [Hoa74]. Their "procedure-oriented systems" corresponds to the term "shared-state concurrency" as used by Roy and Haridi [RH04] and this dissertation."



OCaps: Small step from pure objects

+ Memory safety and encapsulation+ Effects only by using held references + No powerful references by default

(miller http://soft.vub.ac.be/events/mobicrant_talks/talk1_ocaps_js.pdf)

Dr. SES - Distributed Resilient Secure EcmaScript

Stretch reference graph between event loops & machine

Crypto analog of memory safety

**Unguessable URLs as Crypto-Caps**


---

Potluck

https://www.inkandswitch.com/potluck/

**"application state lives in the text"**

"no hidden metadata; searches are just a function of the text."

**structured personal micro-syntax**

**computational freeform text**

**results displayed in overlays (next/atop/over), text stays editable**

buttons edit text (and cause recomputation) - this avoids loops


"Text editors are generic and refined tools that have many built-in features like copy/paste and undo/redo. Having state directly in the text gives us these features for free. For example, you can copy a document to a different text editor to edit and then paste it back into Potluck, and it retains all of its behavior. By using text as the source of truth, Potluck inherits the affordances and powers of text.
Originally we tried allowing users to manually highlight entities in the text. We abandoned this approach mainly because manual highlighting was tedious, but also because it created hidden state outside the text that was hard to reason about."
"In some cases, our demos violate this general principle by storing ephemeral state which isn't stored in the text. For example, our default timer widget doesn't store the remaining time in the text, so a running timer won't survive a copy-paste. This wasn't a particularly principled decision though; in theory, any state that can be encoded as text can be stored in the document itself."

"The text-based todo list app TaskTXT has a good solution to storing timer state in the document. When a timer is started, it records the start time into the text document in a human-friendly format. The result is that even a running timer can survive a cut-paste."

---

Programming Portals

https://maggieappleton.com/programming-portals

"Small, scoped areas within a graphical interface that allow users to read and write simple programmes"



---

**Why we need lisp machines**

**A personal mind dump on operating systems**

https://fultonsramblings.substack.com/p/why-we-need-lisp-machines?r=1dlesj&s=w&utm_campaign=post&utm_medium=web

"UNIX was designed as a self-contained system. you simply didn't have other computers you would rely on. You had your department's computer, and you would sometimes send messages and files to their department computers. That's the full extent of UNIX's intended networking abilities."

"**A modern UNIX system isn't self-contained.** I have 4 UNIX systems on my desk (Desktop, laptop, iPhone, iPad) I'm contentiously using the cloud (iCloud for photos, GitHub for text files, Dropbox for everything else) to sync files between these machines. The **cloud is just a workaround for UNIX's self-contained nature**"

https://liam-on-linux.dreamwidth.org/80795.html


---

Hell Is Other REPLs: Being Mutable and Avoiding Bad Faith Programming

https://hyperthings.garden/posts/2021-06-20/hell-is-other-repls.html



---

Monte lang (python+ocaps)

https://monte.readthedocs.io/en/latest/intro.html

"While "arbitrary code execution" is a notorious security vulnerability, Monte enables the fearless yet powerful use of multi-party limited-trust mobile code."

---

emakers

Emakers have an important security property: they come to life contained in a world with no authority. Such a world is even more restrictive than the Java sandbox used for applets. However, this world is more flexible than the sandbox because authority can be granted and revoked during operation using capabilities


---

cap'n proto schema definitions

top level defns have random IDs


"A Cap'n Proto file must have a unique 64-bit ID, and each type and annotation defined therein may also have an ID. Use capnp id to generate a new ID randomly. ID specifications begin with @:"

```
@0xdbb9ad1f14bf0b36; # file id

struct Foo @0x8db435604d0d3723 {
  # ...
}

enum Bar @0xb400f69b5334aab3 {
  # ...
}
```

If you omit the ID for a type or annotation, one will be assigned automatically. This default ID is derived by taking the first 8 bytes of the MD5 hash of the parent scope's ID concatenated with the declaration's name (where the "parent scope" is the file for top-level declarations, or the outer type for nested declarations). You can see the automatically-generated IDs by "compiling" your file with the -ocapnp flag, which echos the schema back to the terminal annotated with extra information, e.g. capnp compile -ocapnp myschema.capnp. In general, you would only specify an explicit ID for a declaration if that declaration has been renamed or moved and you want the ID to stay the same for backwards-compatibility.


IDs exist to provide a relatively short yet unambiguous way to refer to a type or annotation from another context. They may be used for representing schemas, for tagging dynamically-typed fields, etc. Most languages prefer instead to define a symbolic global namespace e.g. full of "packages", but this would have some important disadvantages in the context of Cap'n Proto:

  - Programmers often feel the need to change symbolic names and organization in order to make their code cleaner, but the renamed code should still work with existing encoded data.

  - It's easy for symbolic names to collide, and these collisions could be hard to detect in a large distributed system with many different binaries using different versions of protocols.

  - Fully-qualified type names may be large and waste space when transmitted on the wire.

Note that IDs are 64-bit (actually, 63-bit, as the first bit is always 1). Random collisions are possible, but unlikely – there would have to be on the order of a billion types before this becomes a real concern. Collisions from misuse (e.g. copying an example without changing the ID) are much more likely.

Evolving Your Protocol

A protocol can be changed in the following ways without breaking backwards-compatibility, and without changing the canonical encoding of a message:

  - New types, constants, and aliases can be added anywhere, since they obviously don't affect the encoding of any existing type.
  - New fields, enumerants, and methods may be added to structs, enums, and interfaces, respectively, as long as each new member's number is larger than all previous members. Similarly, new fields may be added to existing groups and unions.
  - New parameters may be added to a method. The new parameters must be added to the end of the parameter list and must have default values.
  - Members can be re-arranged in the source code, so long as their numbers stay the same.
  - Any symbolic name can be changed, as long as the type ID / ordinal numbers stay the same.


---

on stupidity of performance over everything (1990)

To Editors, Communications of the ACM

Dear Sirs:

The paper, "An Empirical Study of the Reliability of Unix Utilities", by Miller, Fredriksen and So, in the December, 1990 issue of the Communications, provides extremely valuable empirical feedback on the state of the art in commercial software today. Unfortunately, the state that it exhibits is somewhat embarrassing, mostly because the programming errors discussed in the paper are the direct result of programming styles recommended by popular texts on C programming, and aided and abetted by today's RISC architectures.

Much more ominous is the current practise of demonstrating high speed on benchmarks with all run-time checks turned off. Because computer hardware (and sometimes software) is sold on the basis of execution speed, it is inevitable that every corner will be cut in achieving the maximum speed on these artificial benchmarks. The customer wants the same code that was benchmarked, so the code is delivered with all run-time checks disabled. Unfortunately, the costs of recovering from a disaster due to array-bounds or pointer violations usually far exceeds the savings from the slightly increased execution performance.

Some of these benchmarked systems are "mission-critical" embedded systems, in which bad programming style can kill people. The run-time checks generated automatically by Ada compilers are often turned off to gain a few additional percent in execution speed. Thus, rather than having the software discover its own errors in a benign way, the discovery of such errors is left to the FAA or a Congressional committee.

Software engineering has long stressed the need for defensive programming styles which constantly check for array bounds and null pointer violations (among other things), but because the C language does not provide for these checks to be automatically generated, programmers often leave them out. Furthermore, the code is "more elegant" when the checks are suppressed. This is a good example of a variation on Whorf's hypothesis, which states that language affects thinking; in the case of programming, the language used does affect the quality of the code produced.

Today's untagged RISC architectures put the burden on optimizing compilers to generate efficient run-time array-bounds and pointer checks. Unfortunately, on a serial architecture these additional checks do take additional time. While clever optimizing compilers can move many checks out of inner loops, the availability and quality of the average compiler leaves a lot to be desired. As a result, benchmarkers continue to find it productive to remove such run-time checks. Perhaps the new "superscaler" architectures capable of executing several instructions simultaneously will eliminate most of the cost of these run-time checks. The existence of such architectures will do nothing, however, to correct a whole generation of computer software which was written without these checks.

Engineers optimize what can be quantified; that is their job. Since execution performance is readily quantified, it is most often measured and optimized--even when increased performance is of marginal value; viz., the mandatory "performance results" section in most published papers. Quality and reliability of software is much more difficult to measure, and is therefore rarely optimized. It is human nature to "look for one's keys under the street-lamp, because that is where the light is brightest".

Scientists, on the other hand, determine models and methods of quantification. It should be a high priority among computer scientists to provide models and methods for quantifying the quality and reliability of software, so that these can be optimized by engineers, as well as performance. If these measures provide the correct information, then programs such as buggy Unix utilities can be more easily classified as "poor quality", so that purchasers of such software can base their decisions on more information than the running speed of some benchmark.

Sincerely,

Henry G. Baker, Ph.D.

https://plover.com/~mjd/misc/hbaker-archive/letters/CACM-DubiousAchievement.html


---

🧼 refining node bootstrapping and capability system. feels like the first time some coherent picture emerges. it is surprisingly close to what was actually implemented in 2024.

**setup a node: generates its secret keypair**.

**default main actor fn: identity**: `(fn [all-caps] all-caps)`

all caps: `pubkey, privkey, transact! + platform-dependent effectful globals: document, require, GPIO, fs, fetch,`

initial code: `(transact! [:main :allow-updates-from "pubkeyOfMeAsExternalAdmin"])`

this gives full control over the node to another one's pubkey (me)

**I can now send transact! messages to the node, building up the core incrementally.**

(what if I want to relinquish my rights? just send a retraction: `(transact! [:main :allow-updates-from "me" false])`)

(what **if I send a messed up fn?** The root actor is special in that it always receives _all_ caps as args, not just the previously-returned actor state. if i mess up, i can always start from a **fresh empty actor with all global caps still there**)


**so i want to display something: `(transact! [:main :fn (fn [document] (set! (.-innerText body) "yo"])`**


**can we get rid of explicit transactions and just declare what code we want to run, and let any interested clients subscribe?**

sure, just transact to your own log: `(transact! ["42fjeqir" :fn (fn [{:keys [body]] (set! innerText body "hi")])`

`42fjeqir` is **a cap, a secret (?) identifier for that project.**

it is just data and does nothing on the machine it was transacted to.

we need to instantiate an actor running that code to see anything. **to instantiate an actor, we can replace the root fn:**

`(transact! [:root :fn (fn [{:keys [db document]}] (get-in db [:eav "42fjeqir" :fn]))])`

how did the `42fjeqir` data get to the node? two parts are needed for data to appear:

the **consuming node declares an interest in receiving** tuples matching some pattern: `(transact! [:subscription/23 :e "42fjeqir"])`

and the **source node must declare an intent to publish** data matching some pattern: `(transact! [:publication/695 :e "42fjeqir"])`

the network will **eventually replicate** the facts intersecting requested and allowed. (right now nodes just replicate everything, selective replication is future work)

global node db contains all actor's states (or state dbs?), actors can only access their tiny slice of the db (?) elaborate some time...


open design questions:

should tuples be multi-valued or have namespaced entity ids? `[:subscriptions :e "323da4f"]` vs. `[:subscription/433 :e "323da4f"]` - maybe #2 is better as it allows attaching additional constraints to a sub, eg authored by whom, and also matching an `:a` or `:v`

...nah that would work as compound query anyways, eg `[:subscription/43243 :query [:and [:= :by "pubkey"] [:= :e "12343"]]]`

or should entity ids be capabilities, ie random unguessable strings?

that would be like #2 except then we'd need a more explicit way for magic like `:root`, `:subscription`, `:publication` etc. which may be better as it's less magic.

**subbing/pubbing should be a capability, right?**

what if the cap was not function (that'd be too imperative and not play nice with delcarative data updating) but if the cap was a string, the one you'd use as entity id to create subs like so in a main fn:

`(fn [{:keys [subscriptions main transact!]}] (transact! [subscriptions :query ]))`


---

🧼 some notes of when i thought secure scuttlebutt might serve as data layer. contains glimpses of node bootstrapping.

make backend:

principle: value orientation.

problem with dapps: url centralization

ssp propose to put js of app in blobs; but can we map programming primitives to ssb concepts directly/more fine grained, control over side effects, control who can update what dependency...

decentralized "app store", run apps from friends. apps are identified by their initial code hash, updates are replies to that post by the original trusted author to their own code. (see also ssb browser turtle demo)


replication: stream all facts everywhere, authenticated by producer (network of untrusted peers, scuttlebutt style, Selective Complete Log Replication, with facts being pushed for faster convergence).

"SSB primarily uses social dynamics to determine which data should flow to whom, not how it should flow to where." [tarr, lavoie]

practically infinite s3 style value "space" [see also: hickey values, free from historic memory constraints].

garbage collection is future work.

delay tolerance: from ssb: "The delay tolerance allows routing layers that can optimize for different tradeoffs, for example by minimizing the bandwidth required to disseminate updates rather than minimizing latency." [tarr, lavoie 2019]

timestamps: untrusted best effort timestamps, only ordered within node. no global clock, no coordination. assumes node clocks to be within some ntp tolerance.

interaction: just create values, append them to your own log. values can be functions, data, logs. interested nodes can subscribe to your log.

(?) node exposes a single api method: transact! accepts a collection of assertions and retractions

(signed by whom? the same privkey as is booted with the node? yes, unless the node has in its inital facts some facility to accept transactions from a different keypair.)

(initial) node setup: should a node be interested in someone's functions (pull); or should someone specify node ids to run their code on? (push) - both can be supported, depending on the evaluation model.

nonono actually a node should decide who to trust, it could delegate this decision to another pubkey

first boot: generate keypair, start node with privkey and initial facts (persistence). eg. whom to trust=whose actor/suptree definitions to spawn.

boot: ensures previously needed capabilities are available. (re)creates actors with their persisted state, and gives them capabilities.


capabilities: ssb handshake where the server (other's) pubkey is a capability (! - so the pubkey is actually a secret, and cannot be inferred from the handshake) (after handshake, client/server distinction goes away and a shared secret is established) [secret handshake, tarr 2015] ?

capabilities delegation: server exposes ssb functions to actors ?? how/where/whodecides?

implies some notion of root actor? OR is a supervision tree not an actor (yeah, it's its own ether like thing in between actors, like in erlang, a concern of the host) -

actor system: how are message inboxes represented? who "owns" them? likely the host cap again, and it's a cap to put msgs into an inbox.
note there's a symmetry between nodes and actors: should each actor have its own pubkey/communication-capability??


starts listening to transact! via some arbitrary method (file watcher, http api, i2c, etc)

node vs actor: node is the supervision tree. actors can spawn actors (via capability).

evaluation model: compilation/evaluation unit is the top level definition (like in cljs)

recording evaluation metadata of top level definitions only motivates users to keep them short, as e.g. function input/output values are only recorded on top level defn level - guides towards better factored design of small functions with a focused purpose - the smaller the functions, the more debug information is available.

async code support: likely just transparent via macros, as in cljs/promesa

backend implementation: within browser/wasm: one browser=one hardware node, easy simulation, allows us to run code right there


---

🧼 IDEA for an editor plugin:

**your buffer is like a terminal/repl.**

**deleting code does not delete anything in the system**, its like clearing your terminal.

each top level defn has an id.

make sure to not accidentally change some fn defn into something else entirely, as any references to it will be changed

v1: **top level things get an id** and it is collapsed by default

**type "(changes)" to see current "changes staging area" printed below**

text based interactions: local (single caret), fancy (multi caret?)

**mapping to real world filesystem**: each tx is a file, txid/timestamp as append-only



---

bazant hierarchical spreadsheet tool building

https://www.researchgate.net/publication/326141821_A_non-tabular_spreadsheet_with_broad_applicability

Hierarchical spreadsheet demo video

---

List of structural/projectional editors

https://github.com/futureofcoding/futureofcoding.org/issues/52

---

plain text misfeatures

shalabh <3

https://shalabh.com/programmable-systems/plain-text-misfeatures.html

- linear order
- requires us to consider presentation aspects [less so in lisps]
- no rich exploratory features
- denormalized links


---

holonforth <3

http://holonforth.com/holonforth.html

+ also see wolf wejgaards papers

http://holonforth.com/papers.html

1989 - Not Screens nor Files but Words

The benefits of storing source code in a database.

1990 - The Beauty of Separate Systems

Why Forth is at its best when host and target are connected by an umbilical link.

1994 - A Taste of Direct Programming

About immediacy and direct, instant, "hands-on" programming.

1995 - A Fresh Look at the Forth Dictionary

Let the editor create the dictionary!

1996 - The Invisible Language

Why computer science ignores Forth.

1997 - Objects in Holon

About objecttypes. Keeping it simple with separated messages and methods.

2016 - Planet Holonforth

Where the text space of Forth is celebrated.

OH YES!
prev work on smalltalk like code browser: extended basic in sinclair, burroughs b20

---

🧼 IDEA what if this is all controlled via text? eg write `(ed pat-modal)` => editor pops up below, eval to save


---

ssb [lavoie 20] https://www.youtube.com/watch?v=rvaM74AgCmM

eventually-consistent replication via secure scuttlebutt

- async (devices can be offline)
- authenticated (verified authorship of events)
- secure (immutable events)
- causal (single writer, earlier events from same author received first)
- not dependent on servers, but can leverage them for faster propagation when available

---

joyride to script vscode with cljs <3


---

🧼 IDEA: sigils for "pausing" function while editing.

we don't want all editing actions to be live.

when we start editing a top level defn, we add a sigil in front that says execution in prod should continue with the previous version

but stream any calls and (and previous calls) into the editor for experimentation

```
%(defn funfun [...])
```

when editing is done, see all paused/edited defs in a list and commit the changeset to prod by removing the sigils.

clients will update to the new fn eventually.



related work: sidekick live prod debugging

similar: lightrun, rookout


---

proton/hyperfiddle update

https://old.reddit.com/r/Clojure/comments/vizdcc/hyperfiddlephoton_progress_update/

uses missionary event streaming/effect system below:

https://github.com/leonoel/missionary - wtf!


BORING ACADEMIC PAPER IDEA: evaluate proton/hyperfiddle for network failure cases

---

https://lambdaisland.com/blog/2022-06-23-the-repl-is-not-enough

"sessions are forever" common lisp repl persists state

"build up your program over time, at the cost of some state ambiguity"


---

hickey, bozhidar batsov et al discussion on nested repls/nrepl!=repl

https://groups.google.com/g/clojure-dev/c/Dl3Stw5iRVA/m/IHoVWiJz5UIJ

repl is a misnomer, again environment matters

nested repls "homework problem"

stream based vs. discrete messages


---

Dynamicland Geokit

https://omar.website/posts/notes-from-dynamicland-geokit/

---


Joel Jakubovic on GameMaker

https://programmingmadecomplicated.wordpress.com/2019/07/03/post-tutorial-tour-of-gamemaker/


---

Granger on Eve

We've certainly taken a lot of inspiration from Smalltalk, but I think the semantics we've arrived at make a really nice programming environment, with some surprising properties you may not think are possible.

Eve has a similar philosophy to Red/Rebol - that programming is needlessly complex, and by fixing the model we can make the whole ordeal a lot nicer. We start with a uniform data model - everything in Eve is represented by records (key value pairs attached to a unique ID). This keeps the language small, both implementation-wise and in terms of the number of operators you need to learn.
Programs in Eve are made up of small blocks of code that compose automatically. In each block you query records and then specify transforms on those records. Blocks are short and declarative, and are reactive to changes in data so you don't worry about callbacks, caching, routing, etc.

Due to this design, we've reduced the error footprint of the language -- there are really only 3 kinds of errors you'll ever encounter, and those mostly relate to data missing or being in the wrong shape that you expect. What's more, we'll actually be able to catch most errors with the right tooling. You'll never experience your app crashing or errors related to undefined/nil values.

We've made sure your program is transparent and inspectable. If you want to monitor a value in the system, you can just write a query that displays it, as the program is running. I like to think of this as a "multimeter for code". You can do this for variables, memory, HTTP requests, the code itself ... since everything is represented as records, everything is inspectable.

Because at its core Eve is a database, we also store all the information necessary to track the provenance of values. This is something most languages can't do, because the information just isn't there. So for instance if a value is wrong, you can find out exactly how it was generated.

There's a lot more work to do, but we have big plans going forward. We plan to make the runtime distributed and topology agnostic, so you can potentially write a program that scales to millions of users without having to worry about provisioning servers or worrying about how your code scales.

We're also planning on multiple interfaces to the runtime. Right now we have a text syntax, but there's no reason we couldn't plug in a UI specific editor that integrates with text code. We've explored something like this already.

Anyway, those are future directions, but what we have right now is a set of semantics that allow for the expression of programs without a lot of the boilerplate and hassle you have to go through in traditional languages, and which provide some unique properties you won't find in any other language (at least none that I've used).

---

Litt: Cambria: store document as version-tagged edit log.

allows you to interpret writes later in a different way


---

🧼 IDEA: DREAM CLOJURE

- keep: data structures
  - clojure provide high perf immutable data structures, keep using them unchanged. / hamt by bagwell
- keep: update via commutation functions as opposed to read-modify-write
- ignore: stm. nor used in practice, just atom/cas, atoms dont support coordinated updates, but thats ok
- extend: move from concretions to abstractions, eg. instead of cons (a concretion), build against ISeq, an abstraction that supports first, next, nil but leaves open the implementation
- **extend reader**: keep support for data structures, (maybe?) extend **with function replacement/state keeping sigils?** -- BUT thismay break the read-eval-print, as the clojure reader still just reads not only lists  but also vectors and maps -- does it break code==data?
- keep: lisp-1, non-interning reader
- implement: continuations (hahaha)
- implement: conditions (ummmm, one can dream, right?)
- **keep: wire-based "just data" service interop (! that's huge) to go on and off the wire at will**
- keep: where semantics match, eg. strings (+data structures, atoms maybe)
- extend: interactive programming from lisp. see processes, agents, state, experiment
  - ^^ **hickey says that there still remains growth there for clojure, seek out some of the facilities that were common in lisps and smalltalks from their heyday, we don't have rich environments yet, a lot more tool work to be done to bring us back to where we were" (!!)**
- add: safety/ocap?
- **remove: top level code execution. top level is only for definitions and local notebook-style evaluation**
- need a library that provides state management, function namespace management, effects
- embedded in clojure, not a layer on top (like clojure is embedded into the host language, not a layer on top)  -- basically a language in a language, a library, like dustingetz photon
- can we adapt clojure's runtime polymorphism to use as function replacement layer? (dispatch on data and types)


next idea: fantasy repl session in this dream lang

--

one char markers:
see the state, "printf"
experiment on the state
REVELATION while watching complexity of hello world in fsx:
REALIZE this function replacement strategies via CLOJURE METADATA



---

daniel bittman - twizzler

Twizzler is a research operating system designed around the data-centric programming model. It is built from scratch with a new kernel focused on simplicity to facilitate direct access to persistent data for applications with minimal OS involvement and interposition. Twizzler is motivated by the convergence of the memory hierarchy through the introduction of large-scale byte-addressable non-volatile memory. Twizzler is developed by the Center for Research in Storage Systems at UC Santa Cruz.

https://twizzler.io



---

Datalog in JavaScript

https://news.ycombinator.com/item?id=31154039

---

🧼 IDEA: code lines should not behave as objects (ie have identity) but be _data_, loosely referenced by identifiers.
so each written entity gets an id that is generated by the editing environment.


---

non-c operating systems

https://news.ycombinator.com/item?id=30851955

(also includes alan kay tron tidbit)


https://www.scattered-thoughts.net/writing/the-shape-of-data/


http://catern.com/compdist.html

Your computer is a distributed system


https://github.com/samsquire/ideas

esp. 96. Encyclopedic Literate Desktop


lennier's comment on desktop environments

https://tech.slashdot.org/comments.pl?sid=2585722&cid=38456084



---

google images: "frustrating" - only computers (jon blow via markus)

---

https://oh4.co/site/on-abstraction.html

The concept of abstraction - The term abstraction has a long history of senses, it derives from the Latin abstractus originally connoting asceticism and meaning "to withdraw from worldly affairs". Etymologically it is derived from the combination of the word-forming element "ab-" meaning "off, away from" and the verb "trahere" meaning "to drag, draw" and translating as "to detach, to pull away".


---

persistent OS

This kind of system will suffer from the ratchet problem. A single bug that negatively impacts state is no longer fixable by rebooting. Instead, you have to format/reinstall.
In theory, this sort of setup would be nice in a perfect world, but in the real world of buggy software and faulty hardware and cosmic rays, it's a disaster waiting to happen.
(hn)

What about some middle ground? I'd like my IDE to remain constant between reboots, my browser to some degree is already (if it crashes, it reopens all current tabs on start, which is effectively the same).
I would have thought some clever hacks on an existing kernel would be reliable enough rather than an entirely new OS just for one feature, though.

Something like "crash-only software"; programs are expected to be killed and relaunched. Software should cope with it, as there is no swap space and ideally the user shouldn't be managing memory. An app may create to some degree the appearance of persistence but it's not persistence. Users, also, can easily force kill apps. Ongoing background operations like downloads, uploads and some media playback is delegated to the operating system. Such operations continue when your process is killed, and you check up on them when your process starts. I hope it's not TMI, but the standard library includes atomic file saves.


---

🧼

IDEA: **each sub/expression gets its own unique id, normally hidden/collapsed in editor view but copyable as text**
auto generated while typing, displayed maybe as "u32...fe3"
allows tracking edits


IDEA: **edit time functions that modify their call sites like [/] in org mode**

```
(done (34%) (2 of 4))
(TODO wash dishes)
(DONE cook dinner)
```


```
; type 1: return value fully replaces args. called when subtree changes.
(defe done [subtree]
  '((count (filter "done" subtree)) (count subtree)))
```

```
; type 2: read args and replace. called when subtree changes.
(defe done [subtree current]
  '(current +1))
```

```
; type 2: read args and replace. called when subtree changes OR current args change
(defe done [subtree current]
  '(current +1))
```

```
; type 3: explicit update callback to enable async update triggered by external events?
(defe done [subtree update! current])
```

how is this compatible with events/ocap? likely via dynamic scoping+event handlers

orthogonal feature: render graphical ui / to be combined with type 1 or 2

```
(let [size (slider 1 100 0.5 60))]) ; step size 0.5, curr value 60
(defe slider [_subtree update! from to step curr] ; update callback to change editor value while editing
  [:slider {from, to, step, curr, on-change: (fn [new]  (update! from to step new)}])
```

may also be implemented **as meta data on the returned**


OR IDEA: these are just functions. but there's an orthogonal concept of LEVELS OF IMMEDIACY
when does that function run? where? what must it poke to compute? data? other fns? user? programmer?


---

OH YES OOH YES

https://www.youtube.com/watch?v=8htgAxJuK5c

"Adding Interactive Visual Syntax to Textual Code" + Leif Andersen / Felleisen

https://visr.pl

https://dl.acm.org/doi/abs/10.1145/3428290

racket + cljs https://github.com/LeifAndersen/interactive-syntax-clojure


---

eshell has some ideas that could be pulled out and explored further

ehell pipes go through buffers (not fast but useful)

howard abrams: https://www.youtube.com/watch?v=RhYNu6i_uY4

visual / interactive composition, ido-, remote/tramp,

---

https://karl-voit.at/2017/02/10/evolution-of-systems/

towards org mode

emacs not an editor but an extensible environment

https://irreal.org/blog/?p=8809

---

literate devops howard emacs org mode

http://howardism.org/Technical/Emacs/literate-devops.html

https://www.youtube.com/watch?v=dljNabciEGg

OH LORD YES THIS IS IT THE TRUE WAY TO DEVELOP (?)

summary https://gist.github.com/Profpatsch/abda97a7b635f876e29f544a26840666

---

"A computer is like a violin. You can imagine a novice trying first a phonograph and then a violin. The latter, he says, sounds terrible. That is the argument we have heard from our humanists and most of our computer scientists. Computer programs are good, they say, for particular purposes, but they aren't flexible. Neither is a violin, or a typewriter, until you learn how to use it."
Marvin Minsky, "Why Programming Is a Good Medium for Expressing Poorly-Understood and Sloppily-Formulated Ideas" in Design and Planning, (1967)


---

JOSS first "repl" 1963 https://en.wikipedia.org/wiki/JOSS

precursor to MUMPS <3

https://en.wikipedia.org/wiki/FOCAL_(programming_language)#Direct_and_indirect_modes

"allowed users to type in commands in "direct mode", which were performed immediately, or to prefix them with a line number, in which case they were added to the program if they were unique, or overrode existing code if the same number had previously been used.

The method of operation is similar to BASIC's "immediate mode" vs. "program mode""


https://lispcookbook.github.io/cl-cookbook/editor-support.html

---


functional effect streams Functional effect and streaming systems in Clojure - Léo Noel - reClojure 2021

---

http://akkartik.name/post/division-of-labor

10 years against division of labor in software



---

talk: how to be a repl sorcerer

https://www.youtube.com/watch?v=lR2vbwuzrIM

nice description overview of repls, esp. clj

prepl = unstructured input (string) but structured output { tag val ns form ms }, tag says whether this is a return/print/tap value

unrepl, a bit more extensible. can be ejected into socket repl to upgrade it

nREPL: give id to each evaluation, to allow async/multiple return values [also lang agnostic and most popular under the hood]

nrepl accepts structured input: { form, id, op: eval }

olical/propel


---

https://hirrolot.github.io/posts/why-static-languages-suffer-from-complexity

awesome template metaprogramming meme <3

"Static languages enforce compile-time checks; this is good. But they suffer from feature biformity and inconsistency – this is bad. Dynamic languages, on the other hand, suffer from these drawbacks to a lesser extent, but they lack compile-time checks. A hypothetical solution should take the best from the both worlds.

Programming languages ought to be rethought"

+ hn discussion: https://news.ycombinator.com/item?id=29996240

---

dijkstra: "Progress is possible only if we train ourselves to think about programs without thinking of them as pieces of executable code."

---

disessa boxer with nice images

https://eurologo.web.elte.hu/lectures/dis.htm

**pokability**

---

repairable user accessible schematics

telephone österreichische post KS1952

https://interactionmagic.com/Design-for-repair

kapsch und söhne

---

plan9 successors: inferno, octopus

https://lsub.org/octopus/

"decentralize by centralizing"


---

"we need more operating system"

awesome discussion https://news.ycombinator.com/item?id=4992845


---

why relational data models are better

https://news.ycombinator.com/item?id=29964911

You're right: object orientation and relational data don't mesh well together.

Where you're wrong, and it may take more experience to realize, is that the problems in the mismatch lie far more on the OOP side than on the relational side.
Object oriented designs - the navigable graphs of objects, leaving aside polymorphism - usually privilege a particular perspective of the data, a perspective which is suited to a specific application. For example, a store with items, customers and orders: the application is focused on creating transactions. Relational models, on the other hand, support multiple perspectives "out of the box" and are expected to support multiple applications; for example, reports that roll up by item, by category and by customer; reusing the same customers for a different application; and so on.

Different applications and perspectives usually require different object models. The alternate application reusing the same customer entities won't want to couple with a store application and decorate every customer with an irrelevant list of orders. A reporting app is better off with a visual query builder that creates SQL to hand off to the database than trying to iterate over object graphs performing aggregations and sorts. And so on.

For applications with ambitions for a longer lifespan, start with the database schema. Keep application models fairly thin; focus on verbs; try to ensure only one piece of code owns mutating a logical database entity with a set of verbs; and keep entanglements like UI out of those verb implementations so that you reuse the verbs, rather than being too tempted to reimplement direct database modifications or push too much logic into models.

Object oriented models come and go, applications are rewritten, but database migrations happen at a much slower pace, and data lasts for a very long time.


---

https://docs.microsoft.com/en-us/dotnet/api/microsoft.office.interop.excel._application.run?view=excel-pia

```_Application.Run(Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object) Method```

note that "you cannot pass objects".

---

literate is misunderstood http://akkartik.name/post/literate-programming

commenter proposes experimentation with cap systems <3

https://dev.to/redbar0n/features-of-a-dream-programming-language-cio

https://malleable.systems/catalog/

best ocap survey https://fossandcrafts.org/episodes/20-hygiene-for-a-computing-pandemic.html

https://www.expressionsofchange.org/homoiconicity-revisited/

https://www.hytradboi.com "HAVE YOU TRIED RUBBING A DATABASE ON IT?"

nice intro and reasons

cambria/litt: schema evolution https://www.inkandswitch.com/cambria/#distributed-schema-evolution-is-a-shared-problem


---

gary benhardt 2012 "a whole new world" a.k.a "an editor" talk

https://www.destroyallsoftware.com/talks/a-whole-new-world


editor: overlays - **overlay crashes from production in editor, highlight lines that have ever been involved (yellow) or caused (red) a crash**. scans for git hash and fast-forwqrds to that commit while viewing (medium okayish solution - how to do that better?)

**performance layer** (obtained via parsing regular language profiler dump)

type layer: overlay types inline before function definition

exprtype: overlay next to each expression its observed type

shorten layer: make variaable names single/two letter, like haskell, to see structure via type layer only

**none of the information is obtained via static analysis, only by observing the system (!!!!)**

**scope layers to fn/lines and within a few seconds see the live prod profiling data inline in your layer <3333**

(note: type inference is expensive/graph walk, while displaying recorded types is linear)


---

🧼

chronicles instead of strict append-only?

**undefined semantics are just data = good**

unidirectional editing flow by only overwriting. maybe add new stuff to the top?


https://mobile.twitter.com/meekaale/status/1468188278331518977

Mikael Brockman: "whoa I just got this weird but fascinating idea to use Lisp itself as an issue tracker and work journal"


---

Effects as capabilities: effect handlers and lightweight effect polymorphism (OOPSLA 2020)

by Jonathan Brachthäuser, Philipp Schuster, and Klaus Ostermann


quil on effects https://robotlolita.me/diary/2018/10/why-pls-need-effects/
awesome effects : https://github.com/yallop/effects-bibliography


quil on bloom: https://www.quora.com/What-are-some-mind-refreshing-programming-paradigms-not-FP-OOP-procedural-declarative-concatenative-term-rewrite/answer/Quildreen-Motta


---

🧼 Q: what does a language look like when its runtime is reified? smalltalk lisp, but deployment/distribution?

---

multiple dispatch (correct) vs function overloading (bad approiximation)

https://www.youtube.com/watch?v=kc9HwsxE1OY karpinski

md supports generic code reuse / composition of arbitrary types+code like in julia

example with measurement errors package and physics equations package, they just work.

example catsdogspets/c++ gets fallback every time

**"pro tip: to write highly generic code, just leave off all the types"**

---

wp on bracha: It has been proposed by Bracha that choice of type system be made independent of choice of language; that a type system should be a module that can be "plugged" into a language as required. He believes this is advantageous, because what he calls mandatory type systems make languages less expressive and code more fragile. The only language designed with pluggable type system in mind from the beginning is Newspeak.

pluggable types variously called optional typing, type hinting, type annotations or gradual type checking

---

MIRROR-BASED REFLECTION

Gilad Bracha, David Ungar — Reflection breaks pretty much every security property a programming language could hope to provide. Mirror-based reflection allows one to use reflection as a distinct capability, and finally reconcile reflection and security. Sadly not adopted by… almost every programming language out there—Dart is possibly the only mainstream one (it has Bracha in the design team), even though JavaScript tried to get them as well… but without removing the non-mirror based part.


---

quil on typed/untyped: https://www.quora.com/What-is-fascinating-about-dynamically-typed-programming-languages/answer/Quildreen-Motta

**untyped as tool for exploration and creativity**

  - functional ocap & algebraic effects, typed
  - global relational fact database
  - controlled adaption mechanism for reuse/sharing/modifications: open/exposing/override

https://robotlolita.me/diary/2021/04/why-crochet-is-oop/

overriding is a capability, prevents prototype pollution

---

livelits / omar

https://www.youtube.com/watch?v=OFAYcHyG_NI

"evaluation interlaced with editing is live coding"

"as it turns out, GUIs and symbolic code can live together harmoniously"

andrew blinn: livelits allow interleaved and compositional text and graphical editors. here, i insert a percent slider within a color picker, and drive the picker both through direct manipulation, and programatically through the upstream baseline slider:

https://twitter.com/disconcision/status/1408155813768830983


---

zookos pet names

http://www.skyhunter.com/marcs/petnames/IntroPetNames.html


---

epic list of pointers https://news.ycombinator.com/item?id=10926038

---

http://www.cs.cmu.edu/~NatProg/index.html


https://github.com/dmbarbour/awelon/blob/master/AwelonProject.md


https://pchiusano.github.io/2013-05-22/future-of-software.html


https://www.researchgate.net/profile/Lennart-Loevstrand/publication/221515740_User-tailorable_systems_Pressing_the_issues_with_buttons/links/5721020f08ae5454b230fbec/User-tailorable-systems-Pressing-the-issues-with-buttons.pdf?origin=publication_detail


https://www.geoffreylitt.com/2020/07/19/tools-over-apps-for-personal-notetaking.html


---

[moseley + marks: out of the tar pit]

---

laprie 2008

"As ubiquitous systems are under continuous changes or evolutions, a central property they should exhibit, via appropriate technology, is evolvability, i.e., the ability to successfully accommodate changes. Within evolvability, an important topic is **adaptivity, i.e., the capability of evolving while executing**.
As our definition of resilience retains the notion of justified confidence, assessability, in both senses of verification and evaluation, comes immediately second. Classically, verification and evaluation are performed off- line, pre-deployment. Such an approach falls obviously short in the case of evolving systems, for which assessment has to be performed at run-time, during operation.
Computing systems have already pervaded all activities of our life, and this will still be even more true with ubiquitous systems, hence the importance of usability. Ubiquitous systems being highly complex systems, heterogeneity and diversity are naturally present."



---

https://www.youtube.com/watch?v=6Cs-gFn8r_E

montiarc / deployarc -- mapping virtual deployment onto physical instances, dsl to describe deployments for distributed cps

- architecture lang: logical arch, functionality, model behavior
- platform lang: model systems (?)
- deployment lang: assign components to systems

(smells like bs to me)

---

🧼

`>> eval (defn :foo/f (fn [x] ...))` to add quasi top level form to codebase

`>> (edit :foo)` macro dumps out form it was originally defined in. used with eval+replace with expanded macro

quote me on this: _"i don't think accretion (of unused snippets=code) is a problem"_


---

residential programming

[Residential Programming without Mutable State - Thomas Getgood](https://www.youtube.com/watch?v=Kgw9fblSOx4)

"language have nothing to say until after code is written"

"assumptions about files and file systems are baked into our language, build tools, and editors" [example: ns require, missing underscore throws file not found]

"source code of a program is a value in another program"

"source code is shared mutable state"

"most languages have nothing to say about how changes to source code are coordinated"

dereference names _when you know what they mean_

reify history of source code changes

impose coordination on changes



🧼 conclusion:
  - maybe not because of wokflow challenges, "build master", but rather see the editor as part of the distributed/situated system.
  - maybe yes as internal representation for code db



### December 2021

DC-RES Talk Abstract: **Human-first interactive programming systems**

Cyber-physical systems are situated in our irregular world, interacting with humans and other machines, exchanging information, accreting memory over time, and are subject to faults and change.

Research and experimentation over the past 60 years yielded ideas and systems that allowed gradual refinement and experimentation on a running system, notably Lisp, Multics, Smalltalk, GNOSIS/KeyKOS, Emacs, Lisp Machines, Plan9, STEPS, Eve, and Dark among others.

The aim of the proposed project, inspired by such systems, is to find a data model based on facts with awareness of time and history, to represent code, ephemeral and long-term state, and changes to both, together with a minimal set of interactions that allow inspection of state, transient experimentation, and maintenance at runtime of a pervasively late-bound, distributed actor production system.


---

ALWAYS: WHAT IS THE POINT THE POINT THE POINT THE POINT THE POINT THE POINT???????

i am interested in finding better ways to program

reduce incidental complexity

reduce feedback time

especially a subset of cps, what hickey calls situated programs (Hickey 2017)

~

different kinds of programs - 'entangled' with the world

situate - meaning to put in or on a particular site or place

time: execute often continuously and remain in use for

deal with information

have time-extensive memory / remeber/recall from databases

deal with real-world irregularities

~

why current programming problematic?

mutable state, high number of truly concurrent processors,

distribution, associated complexities

proliferation of languages, parsing complex syntax for no reason

weak introspection and little dynamism

genreally no notions of memory over time, no going back or experimenting safely

insecurity, mutation without memory/history,

uncontrolled side effects, ambient authority

~

...was not always the case / past 6 decades

there were computer systems with OS written in memory safe HLL

or just an OS, the precursor our present day unix
introduced a vast number of features we tage for granted today

~

on types:

they don't solve the big problems, testing in production does (haha what a claim)

names dominate semantics and always will, eg. reverse :: [a] -> [a] (hickey fanboy speak yes)

types help with performance

types help with maintenance

---

on hugeness of task:

"Two things seem clear. First, that designing a complete infrastructure for managing user rights, roles and rules is an essentially open-ended task. Second that building a simple, open-ended framework for the same domain can probably be completed with very little effort."

---

programming is not a solved problem

logic should be one of my tools, not my almighty master

solve problems instead of puzzles

i want to **encourage design at the system level, outside of what was considered in-scope of languages**

---

On Pragmatism

There are a class of "ideal attractors" in engineering, concepts like "everything is an object," "homoiconicity," "purely functional," "pure capability system," etc.

Engineers fall into orbit around these ideas quite easily. Systems that follow these principles often get useful properties out of the deal.

However, **going too far in any of these directions is also a great way to find a deep reservoir of unsolved problems**, which is part of why these are popular directions in academia.

In the interest of shipping, we are consicously steering around unsolved problems, even when it means we lose some attractive features. For instance:

---

🧼

goal

- **find a data model**
  - to represent code
  - and state (ephemeral and long-term)
    - time-aware
    - represent changes to both
- and **find an editing ui** for that data model
  - to create, read, update, find, visualize such data structures
  - that allow inspection and safe experimentation/evolution on a running distributed production system.


via editor macros, prefab style

demo use cases: sms gateway: one clinic, multiple clinics, many carriers/SIMs, live swap, quotas, auto swap, queues, reply, access control + storage management + home automation / security



---
🕺

proficiency exam in march

1. what's the topic? improve programming. what do you want do? (todo)
2. state of the art - last 50 years most inspired
3. planned contribution: demarcate.articulate. research question. how does the dissertation look when done? concrete research questions. how to validate claims? (ugh) use case: embedded + distributed.
4. how to validate that the concept is good? improvement is methodical validation. (i disagree) look at design science research in literature (smells like 🚮) it's good for engineering, not really accepted by hardcore scientists: build idea, possibly enhance with embedded quantitative/empirical things. it is qualitative, that's always weaker when it comes to replicability. embed some quantitative stuff: simulate, compare, think of quant studies, up to lab sessions with students. interviews. how do i want to use what methods. what artefacts will emerge? evaluate. change requirements and point of view, try again.

exam is ~20min

next time: more precise research questions. how do they build on each other. plan a timeline. next 1-2 Qs very concretely, next ones more diffuse.

3 years left = 2 Qs per year

Topics:
 - simulation/interactivity
 - semi-structural editing
 - controlled side effects
 - logic-/rule-based programming

Differentiation:
 - **NOT visual progamming** (UML, LabView, https://blueprintsfromhell.tumblr.com)
 - **focus on sw-engineering of Hickeys "situated programs" instead of pure visualization/exploration/algorithms/computation a la Victor**
   - https://github.com/matthiasn/talk-transcripts/blob/master/Hickey_Rich/EffectivePrograms.md

---

Obvious Related Work:

Lisp, Smalltalk

Hancock 2003: Archer/hose analogy

Bret Victor's works:

Ten Brighter Ideas - 2010 http://worrydream.com/TenBrighterIdeas/

Scrubbing Calculator - 2011 http://worrydream.com/ScrubbingCalculator/

Up and Down the Ladder of Abstraction - 2011 - http://worrydream.com/LadderOfAbstraction/

Kill Math - 2011 - http://worrydream.com/KillMath/

Predator/Prey Relationship - Bret Victor 2011 https://vimeo.com/23839605

die ersten 35min von Inventing on Principle - Bret Victor, CUSEC 2012 https://www.youtube.com/watch?v=EGqwXt90ZqA

Learnable Programming - 2012 - http://worrydream.com/LearnableProgramming/

Media for Thinking the Unthinkable - Bret Victor, MIT Media Lab 2013 https://www.youtube.com/watch?v=oUaOucZRlmE

(quick summary on his works, includes good quotes https://notes.fringeling.com/slides/SLS_BretVictor_2020-03-05.pdf)

Light Table - Granger 2012

APX - McDirmid 2015 file:///Users/albertzak/Dropbox/FH/PhD/mcdirmid15/index.html

Eve - Granger 2015

Dark - Biggar, Chisa 2018

Seymour - Kasibatla/Warth 2017 https://harc.github.io/seymour-live2017/



---

i always memorize my likes for the first slide only. the rest is easy.

i am interested in finding better ways to program,
that reduce incidental complexity
and reduce feedback time.

gain resilience through dynamism.

when we write code,
mostly we still simulate in our heads - edit - save - compile - restart

in computing, dynamism and interactivity
always been controversial topics

this image is of sketchpad, ivan suth phd thesis from 1963
you could use that light pen on a crt screen to draw shapes,
attach constraints like this should be a right angle, and even design
entire physical structures like bridges
and run weight/load simulations with gravity

this was at a time when... computer time expensive, batch oriented
hci, cad, foreshadowings of object orientation

**i want to be able to programmatically sketch entire
systems almost as fast as i can think of them.**

especially a subset of cps what hickey calls situated systems ...


---

**plan9**

plan9 distribution: With the small kernel size mentioned above, it is worth pausing a bit and reflecting on what these kernels can actually do. First of all, the filesystem in Plan 9/Inferno runs on a network protocol (9P). Whether the file in question is actually on the local computer, or somewhere on the web, is irrelevant. Nat- urally a solid security framework is built deep into the system to ensure that all such transactions are safe, again whether or not such transactions happen locally or via the internet, is irrelevant, they are secured regardless (Plan 9 does not have root or setuid problems, and neither does it implicitly trust foreign kernels, like UNIX does). What this means, is that Plan 9/Inferno is network and security agnostic. Any program running on these systems gets these things for free.
In addition, each process in Plan 9/Inferno has its own private view of the filesystem, or "names- pace." So in effect, all processes run inside their own mini-jails. It is easy to control just how much, or how little, access each process should have to the system. But this technique was not devised primarily to isolate resources, but to distribute them. For example, if you want to run a Mips binary on a PC, just import the cpu from a Mips machine. If you want to debug a system that crashes during startup, just import its /proc on a working machine, and run the debugger from there, etc. Since all resources are files, and all processes have their own private view of files, and networks and security are transparent, you can freely mix and mash any resources on the net as you see fit. In fact, Plan 9 was designed to run as a single operating system, spread out across multiple physical machines on a network. No other operating system, that I am aware of, is even close to providing such capabilities. In recent years modern UNIX systems have begun incorporating unicode, jails and snapshots, technologies that Plan 9 had invented in the early 90's, but their implementations have been clunky, clumsy and laborious to learn in comparison.


plan9 gui: The difference in focus between these two developments of graphics were paramount. While X tried to develop a massive Windows like system full of new GUI programs, and more or less ignored the termi- nal, Blit was designed for the purpose of running terminal windows. The original UNIX team exploited graphics in many interesting ways to augment the text terminal. For example while X went to great lengths to emulate the physical limitations of teletypes in xterm, Blits terminals behaved much like a regular GUI text editor. You could freely copy-paste and edit text using simple mouse actions, like you would in any graphical editor. This meant that many interactive features of the text terminal, such as substitutions, history and line editing ect, were unnecessary, and subsequently dropped.
One external desktop did peek their interests though, that of the Oberon operating system. At the sur- face the Oberon desktop looks like a regular tiling window manager, but its approach to GUI's is radically different and unique. Text can be written anywhere inside the GUI and executed with mouse actions. This


design is simple and ingenious, any command line program is automatically available in the GUI, and tech support is simply a matter of emailing the correct instructions to the user and asking him to click on them. This design greatly inspired the acme text editor in Plan 9.




---

PDF: Prefab: What if Every GUI Were Open-Source? Morgan Dixon and James Fogarty. (2010). Proceedings of the SIGCHI Conference on Human Factors in Computing Systems. CHI '10. ACM, New York, NY, 851-854.

https://web.archive.org/web/20150714010936/http://homes.cs.washington.edu/~mdixon/publications/mdixon-general-purpose-target-chi2012-final.pdf

Morgan Dixon's Research Statement:

https://web.archive.org/web/20160322221523/http://morgandixon.net/morgan-dixon-research-statement.pdf

Community-Driven Interface Tools

Today, most interfaces are designed by teams of people who are collocated and highly skilled. Moreover, any changes to an interface are implemented by the original developers and designers who own the source code. In contrast, I envision a future where distributed online communities rapidly construct and improve interfaces. Similar to the Wikipedia editing process, I hope to explore new interface design tools that fully democratize the design of interfaces. Wikipedia provides static content, and so people can collectively author articles using a very basic Wiki editor. However, community-driven interface tools will require a combination of sophisticated programming-by-demonstration techniques, crowdsourcing and social systems, interaction design, software engineering strategies, and interactive machine learning.


---

odoyle <> rum

precept <> clara -- updating facts is a pain

dynadoc

---

https://www.youtube.com/watch?v=iBaqOK75cho

guix

emacs org mode to write out scm files via org-babble-tangle


https://ncase.me/joy/demo/turtle/?drawing=flower


### November 2020

**People currently think of text as information to be consumed. I want text to be used as an environment to think in (victor 2011)**

http://www.vpri.org/writings.php

https://www.mail-archive.com/fonc@vpri.org/msg04222.html

https://github.com/d-cook/SomethingNew/blob/master/Emails/CommonMechanism.md


Combine concatenative lang (as vm) with unison distribution/code sharing - because refactoring point free style is trivial
http://evincarofautumn.blogspot.com/2012/02/why-concatenative-programming-matters.html

---

metamine: "completely declarative lang" "equations instead of statements"

gh: https://web.archive.org/web/20201101065701/https://github.com/ymte/metamine

snake: http://web.archive.org/web/20201014024057if_/https://www.youtube.com/watch?v=RvRc3c9TQ6c&gl=US&hl=en

reddit: http://web.archive.org/web/20201130115327/https://www.reddit.com/r/programming/comments/j94lgd/metamine_a_completely_declarative_programming/

``out = draw objects

objects = [ circle(mouse.x, mouse.y, 10) ]```


---

https://web.media.mit.edu/~lieber/PBE/Your-Wish/

http://acypher.com/wwid/FrontMatter/index.html#Foreword


---

🧼

maybe i can implement:
 - based on js/cljs:
 - hash fns, distribute to nodes via eav db
   - (or is a merkle tree sync better=more efficient, nah stay simple and just replicate all)
 - pure interpreter that exposes a few obj capabilities


open Qs:
  - what is even code that always runs?
  - how to reprensent changes to the codebase, especially with regards to captured bindings in scope of lexical closures?
  - can we do without closures if we represent all logic as relations that make their dependencies explicit?

btw IF we had a generic change algo, we could represent all state as "code" (data structures) - state incl. deployment, "processes" nodes etc

would need custom editor + source control

need live traffic at edit time

---


dark: **"using live traffic as an assistant as you're writing code"**

hn pbiggar: **Dark has "live values" which is where at any point in your program, we show you the actual value for the expression you're on. We use "traces" for this, which means the input (usually a HTTP request) as well as saved results of every expressions.**
we need a language where we can mark which functions are pure, where we can instrument the runtime, and where we can play the runtime back in our editor, filling in the saved trace values.
Similarly, we want your program to deploy instantly. That means that we need a technical way to deploy in 50ms (not sure that's available in python), but we also need language features that support that (such as **feature flags built in, and the ability to safely make changes to programs**). Otherwise, you'd find it's super easy to break your program with a typo.

  - very small compilation units: you're editing a single function at a time, and so nothing else needs to be parsed.
  - no parser: The editor directly updates the AST, so you don't have to read the whole file (there isn't a "file" concept). Even in JS, that means an update takes a few milliseconds at the most.
  - extremely incremental compilation: making a change in the editor only changes the exact AST construct that's changing.

This is really about compilation. One thing you can do, which is what we do, is have an interpreter. Now, interpreters are slow, but we simply have a different goal with the language, which is to run HTTP requests quickly. We do run into problems with the limit of the interpreter, but we plan to add a compiler later to deal with this.

"We built a structural editor for an OCaml-like language (Darklang). Our first version was very AST-based, with all movement using the AST. The feedback we got was that AST-based editing is hard to grok and that you need line-based editing too. So we re-implemented and now we have both (although we broke and are re-adding a bunch of the editor refactorings)."


re hn herebebeasties: "Your vision is extremely compelling and well articulated.
I'm not sure I buy the "coalescing multiple things into one makes things simpler" argument - the simplicity comes at the expense of expressiveness, flexibility and optionality. **A large, highly-opinionated monolith, right the way across the stack, is bound to have made some design trade-offs and decisions that are either plain wrong (we all make mistakes) or don't fit with however I want to actually use it.**
To do this at the AST graph/language level strikes me as both genius and absurd - not only is language design very hard, but you clearly have a massive uphill struggle to provide sufficient library and framework-level code on top of that to be able to start to compete with more mature options. Not to mention things like code review tooling (it's neither text-based, nor even in any form of conventional revision control system, so likely start from scratch). Security controls, auditability, vulnerability management for any library ecosystem that springs up around this, etc. etc.

By **rejecting text you are having to reinvent and support a compiler, an IDE, github/gitlab (or equivalent, with their massive functionality set including protected branches and security controls and the like), package management maybe, debugger tooling (on the web), release/rollback systems/UIs, perhaps even monitoring and alerting** (because you're promising the users they don't need to run infrastructure/services like Prometheus and the like and your "roll out" process looks so different).

Does Dark really have sufficient expertise around all of that to want to make this stuff totally monolithic? Is it even possible for a startup company to really compete in that sort of global (massively open source) scene?
Convincing people to bet their company on your company, both as an ongoing enterprise and as a place that can get all the inherent trade-offs here well-matched to their individual use-cases is going to be an almost impossible sell. Regardless of how much you assert that you believe in your mission and don't want to ever pivot. You can't blame people for being sceptical, especially when the opening paragraphs essentially say "this is a silver bullet"."

audio lisp dsl w/ strucutral editing http://kevinmahoney.co.uk/ocellator/

structured editing talk: https://youtu.be/CnbVCNIh1NA
https://hazel.org

hazel "we hope that this will allow Hazel to function not only as a structured programming environment, but also as a structured document authoring environment!"

dark videos/docs/sample app: https://docs.darklang.com/tutorials/tutorial-intro/

https://michaelfeathers.silvrback.com/10-papers-every-developer-should-read-at-least-twice


---

🕺

yes **i also feel the academic system isn't a good fit for you** (🚮)

collect related work (+ edit it nicely)

10 pages for proficiency exam

sort by topic or concept

call it "technical report"

be critical

but with evidence for claims (your feeling that uml, codegen is bad)

write down superset of aaaaaaalllll questions

then: these go in, these dont

find 10 journals/confs/symposia/workshops

you show at the prof exam: you read up on these topics, understand the open questions in the field, and chose a handful of them as your goal

commission expects: you can demonstrate credibly that you've got a set of plausible questions, and you have some overview

plausible: never has human time been so expensive as now, plan to create the last programming language that will ever be used by humans. what would be the perfect programming language for humans? at the same time never was computing/storage/networking that cheap.

**usability engineering for programming languages**

if you don't really want it, then you will not succeed



---


🕺

okay use those summer months to recharge.

if you are worried all the time,

that's a waste of energy.


you need to make a decision.

deadline for your decision: end of summer semester.

no matter what, you will stay employed over the summer so you can relax some more.

then it's just 2 more years.

you can do that.

but whatever decision you make,

write down good and thorough reasons.

so that you don't regret that decision in the future.

<3


---

🧼

how do i quit this phd program somewhat gracefully?

i think the topics are very interesting

but i don't feel at home in this academic setting

papers, titles, fame and glory i don't care about

it's a litte too intense

i'd rather do it as a hobby for a few hours per week

thank you, i enjoyed grappling with these topics

i enjoyed having a mentor who is also interested in this

i put too much pressure on myself to find out something grand.

i'm pretty sure i don't want to finish this program

---

dokuwiki

---

Monads and macros

https://news.ycombinator.com/item?id=28723680

isaacimagine

I've done a lot of work with effect systems, continuations, and concurrency. I guess I'd like to rant about it a bit.

An effect signals an interruption of the call stack to do some work. Like an exception, it 'unwinds' to the nearest handler (think catch block), and executes the effect; after this point, the effect can either resume, or continue from the catching handler.

This ability to resume is exactly a continuation. In fact, a lot of effectful languages (Koka, Effekt) model it as exactly that. It's important that this continuation is only valid within the scope of the handler; this is equivalent to a delimited continuation.

Full, as opposed to delimited continuations, on the other hand, can be invoked from any scope. At the least, this requires a copy of the stack, or that plus a subset of the heap. (Functional languages with immutable datatypes kinda get a free pass on this one, but only barely.)

Because delimited continuations can only be invoked in nondestructive contexts, they do not require making a copy of the stack (in fact, we don't even need to unwind the stack at all.)
Except in most cases, we only resume once, and this—a single-shot delimited continuation—is exactly a coroutine, a feature common in many languages.

But there's more: remember that to execute an effect, a handler has to be located. This handler is found by searching backward through the stack. If this sounds familiar, it's because this type of name resolution is known as dynamic scoping (as opposed to lexical scoping).

The ties here run deep. We all know that closures are a poor man's objects. That's easy too wrap your head around. But I think we'll soon realize that effects are just a poor man's coroutines are just a poor man's dynamic scoping are just a poor man's delimited continuations are just a poor man's resumable exceptions, and I'm not sure where the strange loop ends.

But there's a light at the end of the tunnel. What do effect systems have to offer above these other approaches? I'd say there are 3 things:

  1. Static typing. Unlike coroutines, resumable exceptions, etc. The type of effects used in a function can be automatically inferred. A lot of these other systems operate under a dynamic assumption, or require an explicit annotation of types at some point. With algebraic effects, the row of used effects can be statically determined with no additional annotations by the programmer.
  2. Row-based composition. Building off our last point, effects build a sort of open enumeration over the possible effects raisable at a given point, a row. This row can be generic over further effects, which means that effectful higher-order functions can be composed. This full row can be known at compile time, so that the programmer can know the full set of potential effects in scope at each point in the program. Because these row-based constructions are usually built around a single monad (i.e. the free monad), different effects can be composed without running into traditional monadic composition issues.
  3. System Injection. What happens when an effect does not have a handler in scope? This could just be an error at compile time, but this opens up another possibility. Instead of raising an error, unhandled effects are handed off to the host runtime for evaluation. Quite sensibly, effect systems are a really neat way not only to model concurrency, but actual honest-to-goodness side effects. An effect-based virtual machine is really just a glorified effect generator. The host runtime can also expose additional APIs, like an FFI, IO, or access to threads. Most importantly, because these 'syscalls,' so to speak, are just effects, they can be overridden. You could create a handler that rolls native threading requests into single threads, or redirects output to stdout to log files or the network.
  This has been quite the rambly rant. I've just been thinking about this a lot recently and need to get it all out of my system


"If you want to mix and match effects without regard to order of composition, that's what Lawvere theories are for (commonly known as 'algebraic effects'). You're right that monads don't give you this, but there are ways of describing these patterns without resorting to "procedural" idioms."


Effects are NOT Dependency Injection (maximal push) but (minimal) pull

"In the same way, I felt Frank was completely the wrong approach, because I don't want to push a maximal bundle of effects in, I want to pull a minimal bundle of effects out"


---

https://github.com/pen-lang/pen

"System injection" https://pen-lang.org/advanced-features/system-injection.html

---

[summer 2021]

**Debug mode is the only mode**

https://gbracha.blogspot.com/2012/11/debug-mode-is-only-mode.html

Jason Olson: I think we can take inspiration from other creative endeavors. In many ways, it's important to think about the process of going from idea (sketching) to completion (finishing). Since I'm a music guy I'll use that as an example.

What I find intriguing in the paper you link to (on Gradual Abstraction) is the idea that, over time, you can introduce more constraints as you come closer to the final form. When you are still sketching, the things that aid you in finalizing the design are hindrances. So why not simply remove those constraints while you are sketching out your ideas?

Sean McDirmid: Unfortunately, live programming and similar efforts are still not very well understood with many attempts limited to demo-ware, fancier LISP-like REPLs, or Smalltalk-like fix-and-continue IDEs, which while useful, lack true live feedback. This essay presents a new live programming experience called APX (A Programming eXperience, a play on Iverson's APL) that aims to overcome these challenges with a language, type system, rich code editor, and virtual time machine designed for useful live feedback. We frame our discussion of live programming design and technology challenges in a description of APX.
SMcD works for Microsoft exploring how programming experiences can be re-invented and significantly improved by considering them holistically (e.g. via live programming) rather than the typical piecemeal approach that considers language and tooling separately.

Gilad Bracha: add constraints gradually -> minimum builtin constraints, warnings instead of errors
I have reservations about multiple notations, but I do agree with adding constraints gradually. These two considerations imply a minimum of built-in constraints in the language, and rich tool support to gently and controllably highlight potential errors.An example is optional typing. More generally, in Dart, we've changed most compilation errors to warnings for this reason - so as to avoid constricting the programmer's workflow.


Michael J. Forster: I wonder about the possibility of extending the notion of a time-traveling debugger-as-editor to include the future by using aspects type inference and QuickCheck-like property testing. I have a hazy vision of defining a method body, the system suggesting property tests based on the inferred types as I do so, and, myself, then refining the tests--all in a very tight think, make, explore loop.Could optional/gradual typing, then, simply be a means of refining the type-inferred property tests?
Bracha: That's a really interesting take I had not given any thought to. The relation to types goes both ways - one can use live data to infer types, and use types to generate exemplar data. And one can use tests to generate data. Your suggestion adds to that by letting types help generate tests.

Dmitry Ponyatov: It is very strange that semantic AI is not under wide research to be used as a base technology for software development. I mean things like representing the whole software system as a huge data structure in a homoiconic manner and manipulate it dynamically without programming languages (one part of structure manipulates another part of the structure).

Bracha: Your example, to me, is a visual DSL. As long as there is a clear visual metaphor (in this case the network as a graph) that can work very well. Once things get more involved or abstract, not so much.

hn Sean McDirmid: Should programming be the act of taking well-defined/understood requirements, a nice clean environment (and dependencies), careful thought, and producing a program that is correct by construction and has little need for debugging? Or is programming a messy affair of poorly defined requirements, crazy environments, and more exploratory, where debugging would then consume more effort than actual coding?
I believe more in the latter school, though of course, they are both extremes and I'm a moderate. The mud is unavoidable and we might as well build winches to pull ourselves out rather than spend time trying to carefully avoid it.

Debugging is as close to experimental science as we get in computer science. It is the act of understanding a complex system, and even if we built that system entirely by ourselves (unlikely), it eventually "gets away from us" and takes on a life of its own. It is impossible to understand everything, and debugging is a great way of allowing us to forget details and uncover them later.



---

AST/LIVE EDITING

summary of hn comments on visual programming

https://drossbucket.com/2021/06/30/hacker-news-folk-wisdom-on-visual-programming/


http://www.lamdu.org "scaling the repl experience"

(edwards 2005 "subtext-lang") <3 https://alarmingdevelopment.org

(hancock 2003 subexpression annotations) + chiusano, omar's hazel

JetBrains MPS - mpeddr http://mbeddr.com

Lively (ingalls/harc) - awesome object live programming thingy + lifting: augmenting and orchestrating systems

https://lively-next.org/users/robert/welcome.html


http://joeduffyblog.com/2016/02/07/the-error-model/


---

CAPS

the problem that **caps solve, beautifully explained "like medicine 1870"**

intro rant: http://habitatchronicles.com/2016/10/software-crisis-the-next-generation/

more on caps: http://habitatchronicles.com/2017/05/what-are-capabilities/

kentonv & urbit on capablities

https://news.ycombinator.com/item?id=16091975

With that out of the way, what would a pure capability system look like, if we exposed it directly in a user interface?

Let's say you want to give access to Bob.

In a pure capability system, you don't assign a role to "Bob". Instead, you create a capability for the role, and you send that capability to Bob, via some arbitrary communications mechanism. Bob uses the capability, which grants him access.

Crucially, there is no need for the system to have any notion of how to authenticate "Bob". It doesn't care if the user is really "Bob", it only cares that the user presents the correct capability. This is where capability systems are powerful -- they avoid the need for any centralized list of principals (user identities) and avoid the need for a way to authenticate those principals. This is especially helpful when you need to, say, delegate some responsibility to an automated system that shouldn't be treated as a full user.

But does this mean that when someone accesses the capability, the system actually has no idea who they are, and so can't attribute the changes to anyone?

No. In a capability system, we can take a different approach to auditability.

When you create a capability to send to Bob, you can arrange so that any actions performed using the capability are logged as, e.g., "via Bob". Note that this may be a mere text string. The system still doesn't need to know what "Bob" means, but you can now see in the logs which actions were done by Bob. If Bob further delegates his capability to Carol, he may want to add a second label, "via Carol". Now when you look at the logs, you might see "via Bob; via Carol". This means: "Bob claims that Carol performed this action." No one other than Bob actually needs to know who "Carol" is, much less how to authenticate her. Carol could very well be Bob's imaginary friend. Since the assertion in the audit log says "via Bob" first, we know to hold Bob responsible first. We only care about Carol to the extent that we trust Bob.

Now, again, I don't actually endorse creating a UX like this, because not many users are equipped to understand it. But if you think about it, it does emulate real-life interactions. If I lend my car to Bob, and then the car ends up crashed, I will blame Bob. Bob can say "Oh, I lent it to my friend Carol, she was the one who crashed it," but I don't care, I'm going to hold Bob responsible. At no point in this process do I need to check Bob or Carol's government-issued ID to find out who really crashed my car.


---

Bagel lang

https://www.brandons.me/blog/the-bagel-language

  1. A hard, enforced separation between stateless functional code and stateful procedural code
  2. Reactivity as a first-class citizen
  3. LSP Language Server Protocol


---

"bricoleur science" <3 https://drossbucket.com/2017/04/08/im-a-bricoleur-scientist/

erlang supervision trees https://adoptingerlang.org/docs/development/supervision_trees/


---

Ideas:

Victor 2021

OS in rough chronological order:

lispm / genera / macsyma

b5000

eros/e

plan9

hp/400

STEPS

mu

wasm capsec?

---

🕺

phd == 3 good papers

todo: fill gaps

saltzer e2e argument 1985

preempt how is is different from "low code/no-code"

idea: programming = visualized interaction

scope problem in depth: "caps + code db + relations + distributed deployment"

write a history paper about multics? 1000s of references. write now, use shortened version for thesis. publish as survey + add "btw we've got this" (nah would rather read than write that paper)

need: write report for proficiency exam: it's mostly state of the art

write a tiny "research challenge paper" and submit it to student track / doctoral symposium (early stage ok)

use case: not the artefact but the process of programming is very important. show: how does it look usually? how does it look with this system?

industrial control + security. maintainability/visibility of iot is baaaaad

get concreteeeeeeeee, iterate challenge+approach

---

Genode sculpt https://genode.org/documentation/articles/sculpt-21-10

---

🕺

problems:
- traditional programming is not interactive
- too many closed systems with weak composition

we've got too many closed-world systems with their own little languages and semantics. I feel like in the 2000s: video camera, gameboy, usb stick, pocket calculator, mp3 payer, clock, gps -> one smartphone.

operating systems, shells, filesystems, files, compilers, packages, package managers, languages, databases, web servers, reverse proxies, config files (in ~infinitely many formats), ssh, programs don't compose, communication: sockets/http/tls/files/raw bytes/textfiles/permissions

last straw: security. code reuse, packages pola? WHY CAN I DO EVERYTHING MY "USER" CAN DO?

-convergence

"Most programs are not write-once. They are reworked and rewritten again and again in their lives. Bugs must be debugged… During this process, human beings must be able to read and understand the original code; it is therefore more important by far for humans to be able to understand the program than it is for the computer." - Matsumoto Yukihiro (matz) 2007

test code? discovery - test response of web service? play around?

-> interactive computing


claim: layers of systems on top of out os: OS are at a local maximum

yes, OS should manage hw resources, but it's also a platform on which we build systems.

world of programming languages and databases collide, and they have categorical similarities.


---

want: "one set of mechanisms to deal with abstraction, modularity, naming, security/access control" together, see "problems as a single fabric" (miller 2006)


---

- 60s prog
- 70s db
- 80s sql
- 90s web

---

B5000 Algol, virtual memory, inspired forth (?)

Still sold as Unisys ClearPath/MCP

---

Multics 1965

"Multiplexed information and computing service"

(ARPA, MIT, Bell labs, GE, precursor project MAC '62 / Licklider "internet" "On-Line Man Computer Communication")

- PL/1, 1MB RAM, NSA >1998
- expensive process creation: 3 procs per user, later 1 proc.
- W^X: actually from a cost mem optimization, keep only one copy in ram, should not modify itself when multiple users access same program

- 70s: Algol, Cobol, Fortran
- Mid-70s: Maclisp Macsyma, APL interpreter
- 80s: C compiler (note: but how is that memory safe then?), pointer magic, null pointer arithmetic etc,
- 1984 dynamic reconfiguration: CPU, mem, drives, peripherals (Schell 1084)

"Bigger, slower, less reliable than planned" - 60-70 sites, last site closed in 1998

- Myths:
- Not first timesharing
- not first virtual memory
- Thompson's unix "Unics" re-write was the smallest possible timesharing on pdp-7 assembly
- Super secure? Per-user
- Ritchie/Thompson: "Overengineered"

- van horn: supervisor, pdp-1 1969

---

lampson: capOS cal-tss protection+refinement

---

Intel i432

between 6 and 321 bits

---

Smalltalk

simula 67 (objects, classes, inheritance)

---

POLA / least privilege

- 1975 GNOSIS/KeyKOS "cloud computing" (before dial up)
- resume processes within 30s / demo pull plug
- 20kloc
- <100kb kernel ram usage / microkernel, never allocates mem (only mutates processes' memory, unbuffered messagepassing)
- capabilities (naming+locating+accesrights)
- slm/persistent virtual mem/orthogonal persistence
- no serialized on disk/deserialized in mem
- persistent objects: each file/process/supervisor is an object
- home location on disk, optional transparently in ram
- checkpointing, CoW, "opportunistic scheduling"
- network+keyboard journaled (nicht ganz transparente persistency)


- "directory" assocs name with keys
- service keys for debugging, closely held
- "sensory key" solves problem of getting access to a r/w key via a read only key by downgrading all keys fetched via the sensory key to sensory keys.
- discreetness compartments: an actor (domain) with no keys to outside of the domain, avoids exfiltration


- domains=cpu/segments=mem/meters=powersupply


myths: inability to enforce/cannot solve confinement

"just a bit string"

---

seL4 / 10k lines microkernel, formally verified capability system

---

lispm

- Hardware Type Checking. Special type bits let the type be checked
  efficiently at run-time.
- Hardware Garbage Collection.
- Fast Function Calls.
- Efficient Representation of Lists.
- System Software and Integrated Programming Environments.


STEPS

---

Eve

literate programming (knuth 1984)

eav / relational

virualizations, bidirectional mapping, "why not drawing?"


---

Unison

"haskell+erlang"

? semantic equivalence: (+ 1 x 1) vs. (+ x 2)

---

Rein Gottschalk (@multix_labs)

https://multix.substack.com/

wtf? but also intriguing. don't really understand

---

OS Family Tree https://eylenburg.github.io/pics/Eylenburg_Operating_System_Timeline_Family_Tree.svg








