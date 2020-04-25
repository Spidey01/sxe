# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :

project(nulldemo
    VERSION 0.1
    LANGUAGES CXX)

add_executable(nulldemo
    src/main.cpp
    src/NullDemo.cpp)

set_property(TARGET nulldemo PROPERTY CXX_STANDARD 17)
target_include_directories(nulldemo PRIVATE SxE)
target_link_libraries(nulldemo PRIVATE SxE)

install(FILES nulldemo.cfg DESTINATION ${CMAKE_INSTALL_SYSCONFDIR}/xdg)

install(TARGETS nulldemo
    EXPORT nulldemoTargets
    RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
    ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}")

