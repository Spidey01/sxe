# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :
CMAKE_MINIMUM_REQUIRED(VERSION 3.13 FATAL_ERROR)

project(SXE_SDK
    VERSION 2.0
    LANGUAGES CXX)

set(CPACK_PACKAGE_VERSION_MAJOR ${PROJECT_VERSION_MAJOR})
set(CPACK_PACKAGE_VERSION_MINOR ${PROJECT_VERSION_MINOR})
set(CPACK_PACKAGE_VERSION_PATCH ${PROJECT_VERSION_PATCH})
set(CPACK_RESOURCE_FILE_LICENSE "${CMAKE_CURRENT_SOURCE_DIR}/COPYING")
set(CPACK_RESOURCE_FILE_README "${CMAKE_CURRENT_SOURCE_DIR}/README.md")
set(CPACK_GENERATOR "ZIP;TGZ")

set(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}/cmake/Modules" ${CMAKE_MODULE_PATH})
message(STATUS "CMAKE_MODULE_PATH: ${CMAKE_MODULE_PATH}")

include(GNUInstallDirs)

# Doesn't work.
include(DefaultBuildType)

# Reminder: options are "OFF" by default
option(BUILD_SHARED_LIBS "Build shared libraries" ON)
option(BUILD_VENDOR_LIBS "Build our own dependencies" ON)

# Whether using internal or external, it won't be built from here.
# To use internal: run bootstrap.{cmd,sh}.

if(BUILD_VENDOR_LIBS)
    # PROJECT_DISTDIR set from envsetup; see bootstrap.{cmd,sh}

    # this conflicts with other -I config. Just set the roots for each dep.
    #set(CMAKE_PREFIX_PATH "$ENV{PROJECT_DISTDIR}" ${CMAKE_PREFIX_PATH})

    set(Boost_NO_SYSTEM_PATHS ON)

    # not working?
    set(Boost_ROOT $ENV{PROJECT_DISTDIR})
    if (MSVC)
        set(BOOST_INCLUDEDIR ${Boost_ROOT}/include/boost-1_72)
    endif(MSVC)

    set(nlohmann_json_ROOT $ENV{PROJECT_DISTDIR})

    set(glfw3_ROOT $ENV{PROJECT_DISTDIR})

    set(ZLIB_ROOT $ENV{PROJECT_DISTDIR})

    set(LibArchive_ROOT $ENV{PROJECT_DISTDIR})

    set(CppUnit_ROOT $ENV{PROJECT_DISTDIR})

    set(SXE_SDK_DISTDIR $ENV{PROJECT_DISTDIR})

    #
    # When building with vendor libs, the deps need to be packaged as wel
    #
    # XXX: windows style paths make this angry, so hard coding dist instead of
    # the distdir var from above.
    #
    # And it gets angry if install prefix is used, so gotta do bits and pieces, in a fragle way.
    # N.B. boost is huge, so skip include if just want to test w/o having a real package.

    install(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/dist/bin DESTINATION "${CMAKE_INSTALL_BINDIR}/../")
    install(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/dist/include DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/../")
    install(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/dist/lib DESTINATION "${CMAKE_INSTALL_LIBDIR}/../")
    install(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/dist/share DESTINATION "${CMAKE_INSTALL_DATADIR}/../")


else(BUILD_VENDOR_LIBS)
    include(FindPkgConfig)
endif(BUILD_VENDOR_LIBS)

add_subdirectory(sxe)

add_subdirectory(tests)

add_subdirectory(demos)

include(CPack)
