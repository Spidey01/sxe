# vim: set filetype=cmake tabstop=4 shiftwidth=4 expandtab :
CMAKE_MINIMUM_REQUIRED(VERSION 3.13 FATAL_ERROR)

# honor visiblity even when building static libs.
cmake_policy(SET CMP0063 NEW)

project(SxE
    VERSION 2.0
    LANGUAGES CXX)

set(CXX_STANDARD 17)
set(CXX_REQUIRED 17)

option(BUILD_SHARED_LIBS "Build shared libraries" AUTO)

include(GNUInstallDirs)

set(SxE_SOURCES
        src/Console.cpp
        src/Game.cpp
        src/GameEngine.cpp
        src/cmds/Command.cpp
        src/cmds/EchoCommand.cpp
        src/cmds/QuitCommand.cpp
        src/cmds/SetCommand.cpp
        src/common/Subsystem.cpp
        src/common/Utils.cpp
        src/config/Settings.cpp
        src/config/SettingsExpander.cpp
        src/config/SettingsFile.cpp
        src/config/SettingsListener.cpp
        src/config/SettingsMap.cpp
        src/config/SettingsXMLFile.cpp
        src/graphics/DisplayManager.cpp
        src/graphics/DisplayMode.cpp
        src/graphics/FrameEndedListener.cpp
        src/graphics/FrameStartedListener.cpp
        src/input/ConsoleInputManager.cpp
        src/input/InputCode.cpp
        src/input/InputFacet.cpp
        src/input/InputManager.cpp
        src/input/KeyEvent.cpp
        src/logging/Log.cpp
        src/logging/LogSink.cpp
        src/logging/LoggingManager.cpp
        src/logging/RateCounter.cpp
        src/pc/PcConfiguration.cpp
        src/pc/PcDisplayManager.cpp
        src/pc/PcInputManager.cpp
        src/resource/ResourceManager.cpp
        src/sys/FileSystem.cpp
        src/sys/Platform.cpp
        src/sys/Xdg.cpp
        src/testing/NullConfiguration.cpp
        src/testing/NullDisplayManager.cpp
        src/testing/NullInputManager.cpp
        src/vk/Vulkan.cpp
)

set(SxE_HEADERS
    include/sxe/Console.hpp
    include/sxe/Game.hpp
    include/sxe/GameEngine.hpp
    include/sxe/api.hpp
    include/sxe/cmds/Command.hpp
    include/sxe/cmds/EchoCommand.hpp
    include/sxe/cmds/QuitCommand.hpp
    include/sxe/cmds/SetCommand.hpp
    include/sxe/common/Initializable.hpp
    include/sxe/common/NotificationManager.hpp
    include/sxe/common/Subsystem.hpp
    include/sxe/common/Utils.hpp
    include/sxe/common/stdtypedefs.hpp
    include/sxe/config/Settings.hpp
    include/sxe/config/SettingsExpander.hpp
    include/sxe/config/SettingsFile.hpp
    include/sxe/config/SettingsListener.hpp
    include/sxe/config/SettingsMap.hpp
    include/sxe/config/SettingsXMLFile.hpp
    include/sxe/cxxversion.hpp
    include/sxe/dllexport.hpp
    include/sxe/dllimport.hpp
    include/sxe/filesystem.hpp
    include/sxe/graphics/DisplayManager.hpp
    include/sxe/graphics/DisplayMode.hpp
    include/sxe/graphics/FrameEndedListener.hpp
    include/sxe/graphics/FrameListener.hpp
    include/sxe/graphics/FrameStartedListener.hpp
    include/sxe/graphics/RenderingApi.hpp
    include/sxe/haveboost.hpp
    include/sxe/input/ConsoleInputManager.hpp
    include/sxe/input/InputCode.hpp
    include/sxe/input/InputFacet.hpp
    include/sxe/input/InputManager.hpp
    include/sxe/input/KeyEvent.hpp
    include/sxe/input/KeyEventManager.hpp
    include/sxe/input/KeyListener.hpp
    include/sxe/logging.hpp
    include/sxe/logging/Log.hpp
    include/sxe/logging/LogSink.hpp
    include/sxe/logging/LoggingManager.hpp
    include/sxe/logging/RateCounter.hpp
    include/sxe/pc/PcConfiguration.hpp
    include/sxe/pc/PcDisplayManager.hpp
    include/sxe/pc/PcInputManager.hpp
    include/sxe/resource/ArchiveStream.hpp
    include/sxe/resource/ResourceManager.hpp
    include/sxe/resource/basic_archive_istream.hpp
    include/sxe/resource/basic_archive_streambuf.hpp
    include/sxe/resource/basic_archive_typedefs.hpp
    include/sxe/stdheaders.hpp
    include/sxe/sys/ExitCodes.hpp
    include/sxe/sys/FileSystem.hpp
    include/sxe/sys/Platform.hpp
    include/sxe/sys/Xdg.hpp
    include/sxe/testing/NullConfiguration.hpp
    include/sxe/testing/NullDisplayManager.hpp
    include/sxe/testing/NullInputManager.hpp
    include/sxe/vk/Vulkan.hpp
)


# XXX: some of these need to be BEFORE add_library() to take effect.
# Public ones usually need target_what(SxE PUBLIC INTERFACE ...).
if(MSVC)
    add_compile_options(/wd4251 /wd4275 )
    add_compile_definitions(_CRT_SECURE_NO_WARNINGS)

    if (BUILD_SHARED_LIBS)
    else(BUILD_SHARED_LIBS)
    endif(BUILD_SHARED_LIBS)

endif(MSVC)


add_library(SxE ${SxE_SOURCES})

# Setting CXX_* not enough to make cmake provide the flag to our compile!
set_property(TARGET SxE PROPERTY CXX_STANDARD 17)


# Wanted on Linux/GCC because Windows/MSVC is also supported.
# And CMAKE's default exports macro differs from ours.
set_target_properties(SxE PROPERTIES
    ENABLE_EXPORTS 1
    CXX_VISIBILITY_PRESET hidden
    DEFINE_SYMBOL SXE_DLL_EXPORTS)

if(BUILD_SHARED_LIBS)
    target_compile_definitions(SxE PUBLIC INTERFACE SXE_DLL)
endif(BUILD_SHARED_LIBS)

if (MSVC)
    # TODO: should have a option to not expose this.
    target_compile_options(SxE PUBLIC INTERFACE /wd4251 /wd4275)
endif(MSVC)

target_include_directories(SxE PUBLIC
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
    $<INSTALL_INTERFACE:include/sxe>)

set_target_properties(SxE PROPERTIES PUBLIC_HEADER "${SxE_HEADERS}")
install(TARGETS SxE
    EXPORT SxETargets
    INCLUDES DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}"
    PUBLIC_HEADER DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/SxE"
    RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
    ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}")


include(CMakePackageConfigHelpers)
write_basic_package_version_file(
    SxEConfigVersion.cmake
    VERSION ${PACKAGE_VERSION}
    COMPATIBILITY AnyNewerVersion
    )
install(EXPORT SxETargets
        FILE SxETargets.cmake
        NAMESPACE SxE::
        DESTINATION lib/cmake/SxE)

find_package(nlohmann_json REQUIRED)
if (NOT SXE_SDK_DISTDIR)
    # target_include_directories(SxE PUBLIC ${nlohmann_json_INCLUDE_DIRS})
else()
    install(DIRECTORY ${nlohmann_json_ROOT}/nlohmann DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})
endif (NOT SXE_SDK_DISTDIR)
# target_link_libraries(SxE PUBLIC INTERFACE nlohmann_json::nlohmann_json)


set(Boost_USE_STATIC_LIBS OFF)
set(Boost_USE_MULTITHREADED ON)
set(Boost_USE_STATIC_RUNTIME OFF)

find_package(Boost 1.72
    COMPONENTS filesystem)
if (Boost_FOUND)
    target_link_libraries(SxE PUBLIC Boost::filesystem)

    target_compile_definitions(SxE PUBLIC INTERFACE SXE_HAVE_BOOST)
endif(Boost_FOUND)


find_package(glfw3 REQUIRED)
target_link_libraries(SxE PUBLIC glfw)

find_package(ZLIB REQUIRED)
target_link_libraries(SxE PUBLIC ZLIB::ZLIB)


# Cmake's bundled FindLibArchive.cmake wants LibArchive_*.
find_package(LibArchive REQUIRED)
# XXX what about when not vendor libs?
target_link_libraries(SxE PUBLIC ${LibArchive_LIBRARIES})

find_package(Vulkan REQUIRED)
# We dynamically load Vulkan at runtime. So take the headers not the libs
target_include_directories(SxE PUBLIC ${Vulkan_INCLUDE_DIRS})
# target_link_libraries(SxE PUBLIC Vulkan)
