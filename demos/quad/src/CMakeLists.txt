add_executable(quad 
    QuadGame.cpp
    main.cpp)

find_package(Sxe REQUIRED)
target_link_libraries(quad SxeCore)
