#!/bin/sh

if [ $# -eq 0 ]; then
    echo "Select a demo, e.g. $0 snakegame"
    exit 1
fi

demo="$1"
shift

"../${demo}/pc/build/install/${demo}-pc/bin/${demo}-pc.bat" "$@"
