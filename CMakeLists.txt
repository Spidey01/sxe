# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :
CMAKE_MINIMUM_REQUIRED(VERSION 3.13 FATAL_ERROR)

# BUILD_SDK relies on vcpkg.
if(DEFINED ENV{VCPKG_ROOT} AND NOT DEFINED CMAKE_TOOLCHAIN_FILE)
    set(CMAKE_TOOLCHAIN_FILE "$ENV{VCPKG_ROOT}/scripts/buildsystems/vcpkg.cmake"
        CACHE STRING "")
endif()
# Set by envsetup.project.{sh,cmd}
if(DEFINED ENV{PROJECT_TARGET_TRIPLET} AND NOT DEFINED VCPKG_TRIPLET)
    set(VCPKG_TRIPLET "$ENV{PROJECT_TARGET_TRIPLET}"
        CACHE STRING "")
endif()

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

# Reminder: options are "OFF" by default
option(BUILD_SHARED_LIBS "Build shared libraries" ON)
option(BUILD_SXE_SDK "Build SxE as a Software Development Kit." ON)
option(BUILD_DOCS "Build SxE documentation" ON)
option(BUILD_TESTS "Build SxE test runner." ON)
option(BUILD_DEMOS "Build SxE demo apps." ON)


# Whether using internal or external, it won't be built from here.
# To use internal: run bootstrap.{cmd,sh} to deal with vcpkg.

if (BUILD_SXE_SDK)
    file(TO_CMAKE_PATH "$ENV{VCPKG_ROOT}" VCPKG_ROOT)
    message(DEBUG "VCPKG_ROOT: ${VCPKG_ROOT}")
    message(DEBUG "VCPKG_TRIPLET: ${VCPKG_TRIPLET}")

    install(DIRECTORY ${VCPKG_ROOT}/installed/${VCPKG_TRIPLET}/bin DESTINATION "${CMAKE_INSTALL_BINDIR}/../")
    install(DIRECTORY ${VCPKG_ROOT}/installed/${VCPKG_TRIPLET}/include DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/../")
    install(DIRECTORY ${VCPKG_ROOT}/installed/${VCPKG_TRIPLET}/lib DESTINATION "${CMAKE_INSTALL_LIBDIR}/../")
    install(DIRECTORY ${VCPKG_ROOT}/installed/${VCPKG_TRIPLET}/share DESTINATION "${CMAKE_INSTALL_DATADIR}/../")
    # how does this work on Linux?
    if (MSVC)
        install(DIRECTORY ${VCPKG_ROOT}/installed/${VCPKG_TRIPLET}/debug DESTINATION "${CMAKE_INSTALL_BINDIR}/../")
        install(DIRECTORY ${VCPKG_ROOT}/installed/${VCPKG_TRIPLET}/misc DESTINATION "${CMAKE_INSTALL_BINDIR}/../")
        install(DIRECTORY ${VCPKG_ROOT}/installed/${VCPKG_TRIPLET}/tools DESTINATION "${CMAKE_INSTALL_BINDIR}/../")
    endif(MSVC)
endif(BUILD_SXE_SDK)

add_subdirectory(sxelog)
add_subdirectory(sxe)

if (BUILD_TESTS)
    add_subdirectory(tests)
endif (BUILD_TESTS)

if (BUILD_DEMOS)
    add_subdirectory(demos)
endif (BUILD_DEMOS)

if (BUILD_DOCS)
    add_subdirectory(docs)

    install(FILES 
        README.md
        NOTICE
        COPYING
        DESTINATION ${CMAKE_INSTALL_DOCDIR})

endif(BUILD_DOCS)

include(CPack)
