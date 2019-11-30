cmake_minimum_required(VERSION 3.16)
#list(APPEND CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)

project(sxe
    VERSION 2.0
    DESCRIPTION "My experimentation in developing a game engine."
    LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED 14)

# how does this interact building libs for Android?
include(GNUInstallDirs)

set(BOOST_DOT_VERSION 1.71.0)
set(BOOST_PREFIX ${CMAKE_CURRENT_BINARY_DIR}/boost)
set(BOOST_COMPONENTS headers filesystem)

if (WIN32 OR (UNIX AND NOT APPLE))
    set(SXE_PC)
endif()
if (ANDROID_PLATFORM)
    set(SXE_ANDROID)
endif()

# Different paths are taken depending on External Project issues.
#
# Because I hate CMake very much, and patches are welcome if they:
#  - Do the external project builds before the SxE stuff builds
#  - Populate the FindWhatever related vars needed in same pass.
#  - Don't require running build > 1 to go from fresh checkout to all done.
#
if (NOT EXISTS ${CMAKE_BINARY_DIR}/external.done)
    message(STATUS "ExternalProject Build")

    # build them
    include(cmake/VendorBoost.cmake)

    file(TOUCH ${CMAKE_BINARY_DIR}/external.done)

else()
    message(STATUS "SxE Build")

    # find them.
    include(cmake/VendorBoost.cmake)

    message(STATUS "BOOSTER SEAT: ${Boost_INCLUDE_DIRS}")
    include_directories(${Boost_INCLUDE_DIRS})

    add_subdirectory(core)
    install(FILES cmake/SxeConfig.cmake DESTINATION ${CMAKE_INSTALL_DATADIR}/cmake/sxe)

    # Our externals need to come too.

    # stat(2) would cry at this thing's implementation.
    #
    # Efforts to not suffer cmake's need to decide for itself whether the
    # destination files are up to date, combined with how the
    # configure->generate->build thing works makes it hard to tell CMake not to
    # do this if something exists (because CMAKE_INSTALL_INCLUDEDIR isn't set
    # in that context, it doesn't care about cached or touched file set inside
    # an if over that here, etc)
    #
    # And I've had enough of this bullcrap. So for now it's wait 10~12 seconds
    # of console spam as CMake hammers my shinny solid state drive, and make
    # plans to NOT let CMake handle installing things.
    #
    # I.e. either cmake won't be handling our externals / installs no more, or
    # cmake itself is going to get deep sixed. For now, just wait forever.
    #
    install(DIRECTORY ${BOOST_PREFIX}/include/boost-1_71/ DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})
    install(DIRECTORY ${BOOST_PREFIX}/lib DESTINATION ${CMAKE_INSTALL_LIBDIR})

endif(NOT EXISTS ${CMAKE_BINARY_DIR}/external.done)

