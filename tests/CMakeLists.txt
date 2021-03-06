# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :
CMAKE_MINIMUM_REQUIRED(VERSION 3.13 FATAL_ERROR)


add_executable(sxe-test-runner
    cmds/CommandTest.cpp
    common/NotificationManagerTest.cpp
    common/SubsystemTest.cpp
    common/UtilsTest.cpp
    config/SettingsAdapterTest.cpp
    config/SettingsEnvironmentTest.cpp
    config/SettingsExpanderTest.cpp
    config/SettingsFileTest.cpp
    config/SettingsFixture.cpp
    config/SettingsListenerTest.cpp
    config/SettingsMapTest.cpp
    config/SettingsXMLFileTest.cpp
    graphics/DisplayModeTest.cpp
    graphics/RectangleTest.cpp
    graphics/SystemMemoryPoolTest.cpp
    graphics/SystemMemoryTest.cpp
    graphics/VertexVertexMeshTest.cpp
    input/InputCodeTest.cpp
    input/InputFacetTest.cpp
    input/InputManagerTest.cpp
    resource/ArchiveStreamTest.cpp
    sum/Adler32Test.cpp
    sum/CRC32Test.cpp
    sys/PlatformTest.cpp
    sys/XdgTest.cpp
    main.cpp)

set_property(TARGET sxe-test-runner PROPERTY CXX_STANDARD 17)

target_include_directories(sxe-test-runner PRIVATE SxE ${CMAKE_BINARY_DIR}/sxe/include)
target_link_libraries(sxe-test-runner PRIVATE SxE)


# cppunit doesn't really do cmake.
# this is how vcpkg says use its version.
find_package(CppUnit CONFIG REQUIRED)
target_link_libraries(sxe-test-runner PRIVATE CppUnit)

message(DEBUG "CppUnit_INCLUDE_DIR:${CppUnit_INCLUDE_DIR}")
message(DEBUG "CppUnit_LIBRARY:${CppUnit_LIBRARY}")

# Test assets to include in $datarootdir/sxe/
install(FILES sxe/test.txt sxe/test.zip DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/sxe)

install(TARGETS sxe-test-runner
    EXPORT sxe-test-runnerTargets
    RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
    ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}")

