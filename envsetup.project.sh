#
# Place project changes to envsetup.sh here.
# This includes environment variables, etc.
#


rdemo() { # :run a demo by name
    m ":demos:${1}:pc:run"
    # TODO: pass args to app as a property?
}


idemo() { # :installApp a demo by name
    m ":demos:${1}:pc:installApp"
}


irdemo() { # installdemo and execute a demo by name with following args.
    local demo
    demo=$1
    shift
    idemo $demo
    eval \
        env \
            XDG_DATA_DIRS="`gettop`/demos/$demo/pc/src/dist/share" \
            XDG_CONFIG_DIRS="`gettop`/demos/$demo/pc/src/dist/etc" \
            XDG_DATA_HOME="`gettop`/tmp/share" \
            XDG_CONFIG_HOME="`gettop`/tmp/config" \
            XDG_CACHE_HOME="`gettop`/tmp/cache" \
            "`echo $demo | awk '{print toupper($0)}'`_PC_OPTS=\"'-Djava.library.path=$(gettop)/demos/${demo}/pc/build/install/${demo}-pc/lib/natives'\"" \
            \
                "$(gettop)/demos/${demo}/pc/build/install/${demo}-pc/bin/${demo}-pc" "$@"
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

