#!/bin/sh

if [ ! -f "$0" ]; then
    "Can't find $0. Wrong directory?"
    exit 127
fi
croot=`dirname $0`

if [ $# -eq 0 ]; then
    echo "Select a demo, e.g. $0 helloworld"
    exit 1
fi

demo="$1"
shift

XDG_CONFIG_HOME="`pwd`/tmp"
export XDG_CONFIG_HOME

# The run scripts expect to be called from it's bin directory and sets
# java.library.path accordingly. So we need to overrule it here.
# 
eval "`echo $demo | awk '{print toupper($0)}'`_PC_OPTS=\"'-Djava.library.path=${croot}/demos/${demo}/pc/build/install/${demo}-pc/lib/natives'\""
eval "export `echo helloworld | awk '{print toupper($0)}'`_PC_OPTS"


$croot/gradlew --daemon ":demos:${demo}:pc:installApp" || exit $?
"demos/${demo}/pc/build/install/${demo}-pc/bin/${demo}-pc" "$@"
