add_executable(nulldemo 
    NullDemo.cpp
    main.cpp)

find_package(Sxe REQUIRED)
target_link_libraries(nulldemo SxeCore)

