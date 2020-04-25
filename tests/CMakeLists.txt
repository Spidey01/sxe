# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :
CMAKE_MINIMUM_REQUIRED(VERSION 3.13 FATAL_ERROR)


add_executable(sxe-test-runner
    cmds/CommandTest.cpp
    common/NotificationManagerTest.cpp
    common/SubsystemTest.cpp
    config/SettingsExpanderTest.cpp
    config/SettingsFileTest.cpp
    config/SettingsFixture.cpp
    config/SettingsListenerTest.cpp
    config/SettingsMapTest.cpp
    config/SettingsXMLFileTest.cpp
    graphics/DisplayModeTest.cpp
    input/InputCodeTest.cpp
    input/InputFacetTest.cpp
    input/InputManagerTest.cpp
    resource/ArchiveStreamTest.cpp
    sys/PlatformTest.cpp
    sys/XdgTest.cpp
    main.cpp)

set_property(TARGET sxe-test-runner PROPERTY CXX_STANDARD 17)

target_include_directories(sxe-test-runner PRIVATE SxE)
target_link_libraries(sxe-test-runner PRIVATE SxE)

find_package(CppUnit REQUIRED)
target_include_directories(sxe-test-runner PRIVATE CppUnit)

# FindCppUnit.cmake + msbuild't cppunit isn't so friendly.
#
#   - cppunit.lib = static release
#   - cppunitd.lib = static debug
#   - cppunit_dll.lib = shared release
#   - cppunitd_dll.lib = shared debug
#
# And I doubt anyone really loves the +[d]_[dll,static] convention some libs use on MSVC.
#
get_filename_component(where_is_cppunit_lib ${CPPUNIT_LIBRARY} DIRECTORY)

if(BUILD_SHARED_LIBS AND MSVC AND CPPUNIT_FOUND)
    if(CMAKE_BUILD_TYPE STREQUAL "Debug")
        set(what_is_cppunit_lib cppunitd_dll.lib)
    else()
        set(what_is_cppunit_lib cppunit_dll.lib)
    endif()
    set(CPPUNIT_LIBRARY ${where_is_cppunit_lib}/${what_is_cppunit_lib})
    set(CPPUNIT_DEFINITIONS CPPUNIT_DLL)
    if (NOT CppUnit_FIND_QUIETLY)
        MESSAGE(STATUS "Override CPPUNIT_LIBRARY: ${CPPUNIT_LIBRARY}")
        MESSAGE(STATUS "Override CPPUNIT_DEFINITIONS: ${CPPUNIT_DEFINITIONS}")
    endif()
    target_link_libraries(sxe-test-runner PUBLIC ${CPPUNIT_LIBRARY})
    target_compile_definitions(sxe-test-runner PUBLIC ${CPPUNIT_DEFINITIONS})
else(BUILD_SHARED_LIBS AND MSVC AND CPPUNIT_FOUND)
    # probably need to change to cppunitd.lib if CMAKE_BUILD_TYPE is Debug.
    target_link_libraries(sxe-test-runner PUBLIC CppUnit)
    target_compile_definitions(sxe-test-runner PUBLIC CppUnit)
endif(BUILD_SHARED_LIBS AND MSVC AND CPPUNIT_FOUND)


# Test assets to include in $datarootdir/sxe/
install(FILES test.txt test.zip DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/sxe)

install(TARGETS sxe-test-runner
    EXPORT sxe-test-runnerTargets
    RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
    ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}")

