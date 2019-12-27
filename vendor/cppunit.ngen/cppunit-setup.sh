#!/bin/sh
set -e

if [ -z "$PROJECT_ROOT" -o -z "$PROJECT_BUILDDIR" -o -z "$PROJECT_DISTDIR" ]; then
    echo "$0: Must set PROJECT_ROOT, PROJECT_BUILDDIR< and PROJECT_DISTDIR."
    exit 1
fi

top="$PROJECT_ROOT"
source="${PROJECT_ROOT}/vendor/cppunit"
build="${PROJECT_BUILDDIR}/vendor/cppunit"
dist="$PROJECT_DISTDIR"

if [ ! -f "$dist/lib/libcppunit.a" -a ! -f "$dist/lib/libcppunit.so" ]; then
    echo "cppunit missing from $dist"
    mkdir -p "$dist"

    # Huh, don't see any after ake install.
    # echo "cppunit missing from $build"
    mkdir -p "$build"

    echo "Running autogen.sh"
    cd "$source"
    ./autogen.sh
    cd $top

    echo "Running configure"
    cd $build
    "$source/configure" --prefix="$PROJECT_DISTDIR" \
        --enable-shared --enable-static

    echo "Running make"
    make
    echo "Running make install"
    make install
    cd "$top"

    echo "Cleaning up"
    cd "$source"
    rm -rf autom4te.cache

    echo "Your cppunit is $dist"
fi


## from configure --help
#    --disable-option-checking  ignore unrecognized --enable/--with options
#   --disable-FEATURE       do not include FEATURE (same as --enable-FEATURE=no)
#   --enable-FEATURE[=ARG]  include FEATURE [ARG=yes]
#   --enable-silent-rules   less verbose build output (undo: "make V=1")
#   --disable-silent-rules  verbose build output (undo: "make V=0")
#   --enable-dependency-tracking
#                           do not reject slow dependency extractors
#   --disable-dependency-tracking
#                           speeds up one-time build
#   --enable-shared[=PKGS]  build shared libraries [default=yes]
#   --enable-static[=PKGS]  build static libraries [default=yes]
#   --enable-fast-install[=PKGS]
#                           optimize for fast installation [default=yes]
#   --disable-libtool-lock  avoid locking (might break parallel builds)
#   --disable-doxygen       Disable documentation generation with doxygen
#   --disable-dot           Disable graph generating using 'dot'
#   --disable-html-docs     Disable HTML generation with doxygen
#   --enable-latex-docs     Enable LaTeX generation with doxygen
#   --enable-debug          Build with debug symbols.
#   --disable-werror        Treat all warnings as errors, useful for development
