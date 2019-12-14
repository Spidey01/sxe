#
# Place project changes to envsetup.sh here.
# This includes environment variables, etc.
#


rdemo() { # :run a demo by name
    m ":demos:${1}:pc:run"
    # TODO: pass args to app as a property?
}


idemo() { # :installApp a demo by name
    if [ -d "$(gettop)/demos/${1}/pc" ]; then
        m ":demos:${1}:pc:installApp"
    else
        m ":demos:${1}:installApp"
    fi
}


irdemo() { # installdemo and execute a demo by name with following args.
    local demo install_path exe_name
    demo=$1
    shift
    idemo $demo

    if [ -d "$(gettop)/demos/${1}/pc" ]; then
        install_path="$(gettop)/demos/${demo}/pc/build/install/${demo}-pc"
        exe_name="${demo}-pc"
    else
        install_path="$(gettop)/demos/${demo}/build/install/${demo}"
        exe_name="${demo}"
    fi

    eval \
        env \
            XDG_DATA_DIRS="`gettop`/demos/$demo/pc/src/dist/share" \
            XDG_CONFIG_DIRS="${install_path}/etc" \
            XDG_DATA_HOME="`gettop`/tmp/share" \
            XDG_CONFIG_HOME="`gettop`/tmp/config" \
            XDG_CACHE_HOME="`gettop`/tmp/cache" \
            "`echo $demo | awk '{print toupper($0)}'`_PC_OPTS=\"'-Djava.library.path=${install_path}/lib/natives'\"" \
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

.cmd/cppunit-setup.sh
.cmd/boost-setup.sh
