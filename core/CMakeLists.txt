project(sxecore VERSION 0.1 LANGUAGES CXX)

add_library(SxeCore
    src/Game.cpp
    src/GameEngine.cpp
    src/logging/LogSink.cpp
)

target_include_directories(SxeCore PUBLIC
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
    $<INSTALL_INTERFACE:include>
    PRIVATE src)

# vars from GNUInstallDirs
install(TARGETS SxeCore EXPORT SxeCoreConfig
    ARCHIVE  DESTINATION ${CMAKE_INSTALL_LIBDIR}
    LIBRARY  DESTINATION ${CMAKE_INSTALL_LIBDIR}
    RUNTIME  DESTINATION ${CMAKE_INSTALL_BINDIR}
    INCLUDES DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})

# Why isn't that enough to install our .hpp and .cmake files?
install(DIRECTORY include/ DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})
install(EXPORT SxeCoreConfig DESTINATION ${CMAKE_INSTALL_DATADIR}/cmake/sxe)
export(TARGETS SxeCore FILE SxeCoreConfig.cmake)

