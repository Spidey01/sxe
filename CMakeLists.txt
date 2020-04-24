# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :
CMAKE_MINIMUM_REQUIRED(VERSION 3.13 FATAL_ERROR)

project(SXE_SDK
    VERSION 2.0
    LANGUAGES CXX)

set(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}/cmake/Modules" ${CMAKE_MODULE_PATH})
message(STATUS "CMAKE_MODULE_PATH: ${CMAKE_MODULE_PATH}")

# Reminder: options are "OFF" by default
option(BUILD_SHARED_LIBS "Build shared libraries" ON)
option(BUILD_VENDOR_LIBS "Build our own dependencies" ON)

# Whether using internal or external, it won't be built from here.
# To use internal: run bootstrap.{cmd,sh}.

IF(BUILD_VENDOR_LIBS)
    # PROJECT_DISTDIR set from envsetup; see bootstrap.{cmd,sh}
    set(CMAKE_PREFIX_PATH "$ENV{PROJECT_DISTDIR}" ${CMAKE_PREFIX_PATH})

    # need to override this for msvc, what about gcc/sys style?
    if (MSVC)
        set(BOOST_INCLUDEDIR $ENV{PROJECT_DISTDIR}/include/boost-1_72)
    endif()
    set(Boost_NO_SYSTEM_PATHS ON)

ELSE(BUILD_VENDOR_LIBS)
    include(FindPkgConfig)
ENDIF(BUILD_VENDOR_LIBS)

message(VERBOSE "CMAKE_PREFIX_PATH: ${CMAKE_PREFIX_PATH}")

add_subdirectory(sxe)

