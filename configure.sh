#!/bin/sh

if [ -z "$PROJECT_ROOT" ]; then
    echo "Please run ./bootstrap.sh and source the envsetup first."
    exit 1
fi

echo "Running cmake."
cmake \
    -G "${PROJECT_BUILDSYSTEM}" \
    -B "${PROJECT_BUILDDIR}" \
    -S "${PROJECT_ROOT}" \
    -DCMAKE_BUILD_TYPE="${PROJECT_BUILDTYPE}" \
    -DBUILD_SHARED_LIBS=ON \
    -DBUILD_SXE_SDK=OFF
