#
# Place project changes to envsetup.sh here.
# This includes environment variables, etc.
#


rdemo() { # :run a demo by name
    cd "$PROJECT_DISTDIR/bin"
    "$@"
    cd -
}


idemo() { # :installApp a demo by name
    ngen
    if [ -d "$(gettop)/demos/${1}" ]; then
        ninja "demos/$1"
    else
        ninja demos/
    fi
}


irdemo() { # installdemo and execute a demo by name with following args.
    local demo install_path exe_name
    demo=$1
    shift
    idemo $demo

    install_path="$PROJECT_DISTDIR"
    exe_name="$demo"

    # TODO: update these for 2.x
    eval \
        env \
            XDG_DATA_DIRS="`gettop`/demos/$demo/pc/src/dist/share" \
            XDG_CONFIG_DIRS="${install_path}/etc" \
            XDG_DATA_HOME="`gettop`/tmp/share" \
            XDG_CONFIG_HOME="`gettop`/tmp/config" \
            XDG_CACHE_HOME="`gettop`/tmp/cache" \
            \
                "${install_path}/bin/${exe_name}" "$@"
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
PROJECT_BUILDDIR="${PROJECT_ROOT}/tmp/${PROJECT_TARGET_TRIPLET}"
PROJECT_DISTDIR="${PROJECT_ROOT}/dist"

export PROJECT_ROOT PROJECT_BUILDDIR PROJECT_DISTDIR

