cmake_minimum_required(VERSION 3.16)

project(sxe
    VERSION 2.0
    DESCRIPTION "My experimentation in developing a game engine."
    LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED 14)

# how does this interact building libs for Android?
include(GNUInstallDirs)

if (WIN32 OR (UNIX AND NOT APPLE))
    set(SXE_PC)
endif()
if (ANDROID_PLATFORM)
    set(SXE_ANDROID)
endif()

add_subdirectory(core)

install(FILES cmake/SxeConfig.cmake DESTINATION ${CMAKE_INSTALL_DATADIR}/cmake/sxe)
