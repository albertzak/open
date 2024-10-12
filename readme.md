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

#### After LIVE@SPLASH

- [ ] make it nice
- [ ] can we do it in [<26 loc](https://buttondown.com/tensegritics-curiosities/archive/writing-the-worst-datalog-ever-in-26loc/)? see data and run fns?
- [ ] make it run my website
- [ ]


## Observations

- `2024-10-12 15:35` writing live like this is so so so fun



## Random Notes

From 2020-09 until 2024-08 I got paid to daydream (🚮). This is all I have to show for it.

I collected random interesting internet finds and a few own ideas in Apple Notes. Order is somewhat chronological, newest at the top. Sadly only very few entries are timestamped or properly sourced. Lightly edited (typos, clarity, formatting) and small parts translated from German.

**Legend:**

🕺 chats with [my advisor](https://i4c.at/goeschka/) (btw he was awesome that he just let me do anything, and nothing at the same time, and didn't force any academic 🚮 on me. 🫶)

🧼 shower thoughts

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
In addition, each process in Plan 9/Inferno has its own private view of the filesystem, or "names- pace." So in effect, all processes run inside their own mini-jails. It is easy to control just how much, or how little, access each process should have to the system. But this technique was not devised primarily to isolate resources, but to distribute them. For example, if you want to run a Mips binary on a PC, just import the cpu from a Mips machine. If you want to debug a system that crashes during startup, just import its /proc on a working machine, and run the debugger from there, etc. Since all resources are files, and all processes have their own private view of files, and networks and security are transparent, you can freely mix and mash any resources on the net as you see fit. In fact, Plan 9 was designed to run as a single operating system, spread out across multiple physical machines on a network. No other operating system, that I am aware of, is even close to providing such capabilities. In recent years modern UNIX systems have begun incorporating unicode, jails and snapshots, technologies that Plan 9 had invented in the early 90’s, but their implementations have been clunky, clumsy and laborious to learn in comparison.


plan9 gui: The difference in focus between these two developments of graphics were paramount. While X tried to develop a massive Windows like system full of new GUI programs, and more or less ignored the termi- nal, Blit was designed for the purpose of running terminal windows. The original UNIX team exploited graphics in many interesting ways to augment the text terminal. For example while X went to great lengths to emulate the physical limitations of teletypes in xterm, Blits terminals behaved much like a regular GUI text editor. You could freely copy-paste and edit text using simple mouse actions, like you would in any graphical editor. This meant that many interactive features of the text terminal, such as substitutions, history and line editing ect, were unnecessary, and subsequently dropped.
One external desktop did peek their interests though, that of the Oberon operating system. At the sur- face the Oberon desktop looks like a regular tiling window manager, but its approach to GUI’s is radically different and unique. Text can be written anywhere inside the GUI and executed with mouse actions. This


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

---

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

“bricoleur science” <3 https://drossbucket.com/2017/04/08/im-a-bricoleur-scientist/

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

“Most programs are not write-once. They are reworked and rewritten again and again in their lives. Bugs must be debugged… During this process, human beings must be able to read and understand the original code; it is therefore more important by far for humans to be able to understand the program than it is for the computer.” - Matsumoto Yukihiro (matz) 2007

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








