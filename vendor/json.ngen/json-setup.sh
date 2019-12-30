#!/bin/sh
set -e

if [ -z "$PROJECT_ROOT" -o -z "$PROJECT_BUILDDIR" -o -z "$PROJECT_DISTDIR" ]; then
    echo "$0: Must set PROJECT_ROOT, PROJECT_BUILDDIR< and PROJECT_DISTDIR."
    exit 1
fi

if [ ! -f "$PROJECT_DISTDIR/include/nlohmann/json.hpp" ]; then
    cp -r -v vendor/json/include/nlohmann "$PROJECT_DISTDIR/include/nlohmann"
    echo "Your json is $PROJECT_DISTDIR"
fi

