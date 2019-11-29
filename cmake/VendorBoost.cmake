
if (NOT BOOST_DOT_VERSION)
    message(STATUS "BOOST_DOT_VERSION should be a value like 1.71.0")
    message(FATAL_ERROR "No BOOST_DOT_VERSION set.")
endif()
if (NOT BOOST_PREFIX)
    message(STATUS "BOOST_PREFIX should be a value like CMAKE_CURRENT_BINARY_DIR/boost.")
    message(FATAL_ERROR "No BOOST_PREFIX set.")
endif()
if (NOT BOOST_COMPONENTS)
    message(FATAL_ERROR "No BOOST_COMPONENTS set. See cmake/VendorBoost.cmake")
endif()

# Possible BOOST_COMPONENTS:
#
#    - atomic
#    - chrono
#    - container
#    - context
#    - contract
#    - coroutine
#    - date_time
#    - exception
#    - fiber
#    - filesystem
#    - graph
#    - graph_parallel
#    - headers
#    - iostreams
#    - locale
#    - log
#    - math
#    - mpi
#    - program_options
#    - python
#    - random
#    - regex
#    - serialization
#    - stacktrace
#    - system
#    - test
#    - thread
#    - timer
#    - type_erasure
#    - wave
#

# unix b2 allows ---with-libraries=foo,bar
# win32 b2 requires --with-foo --with-bar ...
# Cute.
foreach(component ${BOOST_COMPONENTS})
    list(APPEND BOOST_BUILD_COMPONENTS --with-${component})
endforeach()

# where to expect the BoostConfig.cmake file.
set(BOOST_WHERE_CONFIG ${BOOST_PREFIX}/lib/cmake/Boost-${BOOST_DOT_VERSION})

# 1.71.0 -> 1_71_0.
string(REPLACE "." "_" BOOST_UNDERSCORE_VERSION ${BOOST_DOT_VERSION})

#
# cmake downloads to ${BOOST_BUILD_PREFIX}/src/${file form url} and extracts it to
# ${BOOST_BUILD_PREFIX}/{first word of ExternalProject_add()}. The paths for the
# commands are relative to a root, so these need to be pretty explicit or it'll
# say can't find the command.
#
##### XXX can't find the shit?
if (WIN32)
    set(BOOST_BOOTSTRAP_CMD ${BOOST_PREFIX}/src/Boost/bootstrap.bat)
    set(BOOST_BUILD_CMD ${BOOST_PREFIX}/src/Boost/b2.exe)
else()
    set(BOOST_BOOTSTRAP_CMD ${BOOST_PREFIX}/src/Boost/bootstrap.sh)
    set(BOOST_BUILD_CMD ${BOOST_PREFIX}/src/Boost/b2)
endif()

# First check for the booster seat.

find_package(Boost ${BOOST_DOT_VERSION}
    PATHS ${BOOST_WHERE_CONFIG} NO_DEFAULT_PATH)

# We can use ExternalProject_Add() to go download and do the magic.
# We can even use find_package() to only do it once!
# But reason ExternalProject_Add() doesn't run until after configure ->
# generate, so we can't find_package() twice.
if (NOT Boost_FOUND)
    message(WARNING "Will go get boost but cmake won't know about its config.")
    message(WARNING "Run the build command again.")

    # Adapted from https://cmake.org/pipermail/cmake/2017-October/066377.html
    # But tweaked to use release tarball instead of git.
    include (ExternalProject)
    ExternalProject_Add(Boost
        PREFIX ${BOOST_PREFIX}
        URL "https://dl.bintray.com/boostorg/release/${BOOST_DOT_VERSION}/source/boost_${BOOST_UNDERSCORE_VERSION}.zip"
        # GIT_REPOSITORY git at github.com:boostorg/boost.git
        # GIT_TAG boost-${BOOST_DOR_VERSION}
        # GIT_PROGRESS 1
        UPDATE_COMMAND ${BOOST_BOOTSTRAP_CMD}
        CONFIGURE_COMMAND ""
        BUILD_COMMAND ${BOOST_BUILD_CMD} --prefix=${BOOST_PREFIX} ${BOOST_BUILD_COMPONENTS} install
        BUILD_IN_SOURCE true
        INSTALL_COMMAND ""
        LOG_DOWNLOAD 1
        LOG_UPDATE 1
        LOG_CONFIGURE 1
        LOG_BUILD 1
        LOG_INSTALL 1)

endif()

