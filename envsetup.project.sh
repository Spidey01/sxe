#
# Place project changes to envsetup.sh here.
# This includes environment variables, etc.
#

run_from_dist() {
    env \
        XDG_DATA_DIRS="${PROJECT_DISTDIR}/share" \
        XDG_CONFIG_DIRS="${PROJECT_DISTDIR}/etc/xdg" \
        XDG_DATA_HOME="${PROJECT_ROOT}/tmp/share" \
        XDG_CONFIG_HOME="${PROJECT_ROOT}/tmp/config" \
        XDG_CACHE_HOME="${PROJECT_ROOT}/tmp/cache" \
        LD_LIBRARY_PATH="${PROJECT_DISTDIR}/lib" \
        \
            "$@"
}

rdemo() { # run a demo by name
    local demo

    demo="$1"
    shift

    run_from_dist "${PROJECT_DISTDIR}/bin/$demo" "$@"
}


idemo() { # build a demo by name
    ngen && ninja demos/$1
}


irdemo() { # build and run a demo by name with following args.
    idemo $1 || return
    rdemo $*
}


rtest() { # run test runner with following args
    run_from_dist "${PROJECT_DISTDIR}/bin/sxe-test-runner" -o "${PROJECT_ROOT}/tmp/test.log" "$@"
}


itest() { # build test runner
    ngen && ninja tests
}


irtest() { # build and run test runner with following args.
    itest || return
    rtest $*
}


_sxe_complete_demos() { ## bash completion function for demo names.
    local cur prev opts
    COMPREPLY=()
    cur="${COMP_WORDS[COMP_CWORD]}"
    prev="${COMP_WORDS[COMP_CWORD-1]}"
    opts="$(ls $(gettop)/demos | sed -e 's/\///')"

    COMPREPLY=($(compgen -W "$opts" -- ${cur}))  

    return 0

}

# complete demo names for these commands.
complete -F _sxe_complete_demos rd rdemo idemo irdemo

PROJECT_ROOT=$(gettop)

## assuming gcc, and that it's native.
PROJECT_TOOLCHAIN=gcc
PROJECT_TARGET_ARCH=$(uname -m)
PROJECT_TARGET_TRIPLET=$(g++ -dumpmachine)

# defaults
#PROJECT_BUILDDIR="${PROJECT_ROOT}/tmp/${PROJECT_TARGET_TRIPLET}"
PROJECT_BUILDDIR=${PROJECT_ROOT}/build
PROJECT_DISTDIR="${PROJECT_ROOT}/dist"

export PROJECT_ROOT PROJECT_BUILDDIR PROJECT_DISTDIR

