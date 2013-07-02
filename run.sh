#!/bin/sh

if [ $# -eq 0 ]; then
    echo "Select a demo, e.g. $0 helloworld"
    exit 1
fi

demo="$1"
shift

XDG_CONFIG_HOME="`pwd`/tmp"
export XDG_CONFIG_HOME

./gradlew --daemon ":demos:${demo}:pc:installApp" || exit $?
"demos/${demo}/pc/build/install/${demo}-pc/bin/${demo}-pc" "$@"
