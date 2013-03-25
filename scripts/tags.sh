#!/bin/sh

find . | grep -E '.java$' | ctags -L-
