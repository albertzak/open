#!/bin/bash

set -e

# rsync --stats package.json pi@10.0.0.36:~
# rsync --stats package.json pi@10.0.0.54:~


# rsync --stats package.json pi@10.0.0.54:~/conf24
# rsync --stats node-node.js pi@10.0.0.54:~/conf24

rsync --stats node-node.js pi@10.0.0.36:~/conf24

rsync --stats -r "build" pi@10.0.0.36:~/conf24

# rsync --stats -r "build" pi@10.0.0.54:~/conf24

# rsync --stats -r --include="build/*" --include="package.json" --exclude "*" pi@10.0.0.36:~/conf24

