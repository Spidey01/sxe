# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :

project(diagterm
    VERSION 0.1
    LANGUAGES CXX)

add_executable(diagterm
    src/GameStub.cpp
    src/main.cpp)

set_property(TARGET diagterm PROPERTY CXX_STANDARD 17)
target_include_directories(diagterm PRIVATE SxE ${CMAKE_BINARY_DIR}/sxe/include)
target_link_libraries(diagterm PRIVATE SxE)

install(FILES diagterm.cfg DESTINATION ${CMAKE_INSTALL_SYSCONFDIR}/xdg)

install(TARGETS diagterm
    EXPORT diagtermTargets
    RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
    ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}")

