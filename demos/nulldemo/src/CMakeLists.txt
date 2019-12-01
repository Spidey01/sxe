add_executable(nulldemo 
    NullDemo.cpp
    main.cpp)

find_package(Sxe REQUIRED)
target_link_libraries(nulldemo SxeCore)

install(TARGETS nulldemo EXPORT nulldemo
    RUNTIME  DESTINATION ${CMAKE_INSTALL_BINDIR})
