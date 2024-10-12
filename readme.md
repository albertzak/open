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

Notes from chats with [my advisor](https://i4c.at/goeschka/) are in code fences. (btw he was awesome that he just let me do anything, and nothing at the same time, and didn't force any academic 🚮 on me. 🫶)

---

```
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
```

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
- W^X: actually from a cost mem optimization, keep only one copy in ram, should not modify itself when - - - multiple users access same program

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








