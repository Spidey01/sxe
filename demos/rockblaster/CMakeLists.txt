# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :

project(rockblaster
    VERSION 0.1
    LANGUAGES CXX)

add_executable(rockblaster
    src/Player.cpp
    src/RockBlasterGame.cpp
    src/Rock.cpp
    src/main.cpp)

set_property(TARGET rockblaster PROPERTY CXX_STANDARD 17)

target_include_directories(rockblaster PRIVATE SxE ${CMAKE_BINARY_DIR}/sxe/include)
target_link_libraries(rockblaster PRIVATE SxE)

install(FILES content/player.mesh DESTINATION ${CMAKE_INSTALL_DATADIR}/RockBlasterGame)
install(FILES content/bigrock.mesh DESTINATION ${CMAKE_INSTALL_DATADIR}/RockBlasterGame)
install(DIRECTORY ../../sxe/glsl DESTINATION ${CMAKE_INSTALL_DATADIR}/RockBlasterGame)

install(TARGETS rockblaster
    EXPORT rockblasterTargets
    RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
    ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}")

