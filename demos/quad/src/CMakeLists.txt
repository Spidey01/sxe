add_executable(quad 
    QuadGame.cpp
    main.cpp)

find_package(Sxe REQUIRED)
target_link_libraries(quad SxeCore)

install(TARGETS quad EXPORT quad
    RUNTIME  DESTINATION ${CMAKE_INSTALL_BINDIR})
