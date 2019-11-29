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
set(BOOST_COMPONENTS headers)
include(cmake/VendorBoost.cmake)
message(STATUS "BOOSTER SEAT: ${Boost_INCLUDE_DIRS}")
# can't use find_package() 'cuz external project context
# include_directories(${Boost_INCLUDE_DIRS})


if (WIN32 OR (UNIX AND NOT APPLE))
    set(SXE_PC)
endif()
if (ANDROID_PLATFORM)
    set(SXE_ANDROID)
endif()

# add_subdirectory(core)

install(FILES cmake/SxeConfig.cmake DESTINATION ${CMAKE_INSTALL_DATADIR}/cmake/sxe)
