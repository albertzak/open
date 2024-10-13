This talk demonstrates the versatility of a simple programming environment integrating plain text, liveness, inspection, and object capabilities.

Programmers assign functions to nodes. ("What should run where?") A process is a function running on a node. Each node reconciles its local process state to match the latest specification.

Processes are just (asynchronous) functions that receive all platform capabilities as arguments and specify their supervision strategy. Data passing through functions, on any node, can be inspected live by hot swapping that function. Collected data is then streamed back to the editing environment and displayed inline near trace points. Annotations at the expression level preserve node-local state across code changes.

Examples range from notes in "personal mini-syntax" (Litt et al 2022) through computational documents, up towards open-world integration with shells, hardware, servers, browsers, and third-party dependencies to build and run small distributed systems.


Title: Declarative distributed process supervision in plain text
