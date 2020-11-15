# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :

project(quad
    VERSION 0.1
    LANGUAGES CXX)

add_executable(quad
    src/Quad.cpp
    src/QuadGame.cpp
    src/main.cpp)

set_property(TARGET quad PROPERTY CXX_STANDARD 17)

target_include_directories(quad PRIVATE SxE ${CMAKE_BINARY_DIR}/sxe/include)
target_link_libraries(quad PRIVATE SxE)

install(FILES share/Quad.dat DESTINATION ${CMAKE_INSTALL_DATADIR}/QuadGame)
install(FILES QuadGame.cfg DESTINATION ${CMAKE_INSTALL_SYSCONFDIR}/xdg)
install(DIRECTORY ../../sxe/glsl DESTINATION ${CMAKE_INSTALL_DATADIR}/QuadGame)
install(TARGETS quad
    EXPORT quadTargets
    RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
    ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}")

