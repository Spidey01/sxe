#!/bin/sh

if [ -z "$PROJECT_ROOT" -o -z "$PROJECT_BUILDDIR" -o -z "$PROJECT_DISTDIR" ]; then
    echo "$0: Must set PROJECT_ROOT, PROJECT_BUILDDIR< and PROJECT_DISTDIR."
    exit 1
fi

DOT_VER="1.72.0"
DASH_VER="1_72_0"
BASE="boost_${DASH_VER}"
ZIP_FILE="${BASE}.zip"

ZIP_URL="https://dl.bintray.com/boostorg/release/${DOT_VER}/source/${ZIP_FILE}"
CACHE="${PROJECT_ROOT}/tmp/${ZIP_FILE}"

WHERE="tmp/${BASE}"
BOOTSTRAP="${WHERE}/bootstrap.sh"
B2="${WHERE}/b2"

COMPONENTS="--with-headers --with-filesystem"

set -e

cd "$PROJECT_ROOT"

if [ ! -f "${PROJECT_DISTDIR}/include/boost/config.hpp" ]; then

    if [ ! -f "$CACHE" ]; then
        echo "Downloading $ZIP_URL"
        ( cd "$(dirname $CACHE)" && wget "$ZIP_URL" )
    fi

    if [ ! -f "$BOOTSTRAP" ]; then
        unzip "$CACHE" -d "$(dirname $WHERE)"
    fi

    if [ ! -x "$B2" ]; then
        cd "$WHERE"
        ./bootstrap.sh
        cd -
    fi

    cd "$WHERE"
    ./b2 \
        --prefix="$PROJECT_DISTDIR" $COMPONENTS \
        --build-dir="$PROJECT_BUILDDIR/vendor/boost" \
        --build-type="minimal" --layout=system \
		--no-cmake-config \
        -j12 \
            address-model=64 \
                install
    echo "Your BOOST is $PROJECT_DISTDIR/include"
fi

