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
        src/config/SettingsEnvironment.cpp
        src/config/SettingsExpander.cpp
        src/config/SettingsFile.cpp
        src/config/SettingsListener.cpp
        src/config/SettingsMap.cpp
        src/config/SettingsXMLFile.cpp
        src/gl/FragmentShader.cpp
        src/gl/ImmediateModeTechnique.cpp
        src/gl/OpenGLVersion.cpp
        src/gl/Program.cpp
        src/gl/Shader.cpp
        src/gl/VertexArrayTechnique.cpp
        src/gl/VertexShader.cpp
        src/graphics/DisplayManager.cpp
        src/graphics/DisplayMode.cpp
        src/graphics/DrawingTechnique.cpp
        src/graphics/FrameEndedListener.cpp
        src/graphics/FrameStartedListener.cpp
        src/graphics/GraphicsFacet.cpp
        src/graphics/ShaderType.cpp
        src/graphics/VertexVertexMesh.cpp
        src/input/ConsoleInputManager.cpp
        src/input/InputCode.cpp
        src/input/InputFacet.cpp
        src/input/InputManager.cpp
        src/input/KeyEvent.cpp
        src/logging/LoggingManager.cpp
        src/pc/PcConfiguration.cpp
        src/pc/PcDisplayManager.cpp
        src/pc/PcInputManager.cpp
        src/resource/ResourceHandle.cpp
        src/resource/ResourceManager.cpp
        src/scene/Entity.cpp
        src/scene/SceneManager.cpp
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
    include/sxe/common/mutextypedefs.hpp
    include/sxe/common/stdtypedefs.hpp
    include/sxe/config/Settings.hpp
    include/sxe/config/SettingsEnvironment.hpp
    include/sxe/config/SettingsExpander.hpp
    include/sxe/config/SettingsFile.hpp
    include/sxe/config/SettingsListener.hpp
    include/sxe/config/SettingsMap.hpp
    include/sxe/config/SettingsXMLFile.hpp
    include/sxe/cxxversion.hpp
    include/sxe/dllexport.hpp
    include/sxe/dllimport.hpp
    include/sxe/filesystem.hpp
    include/sxe/gl/FragmentShader.hpp
    include/sxe/gl/ImmediateModeTechnique.hpp
    include/sxe/gl/OpenGLVersion.hpp
    include/sxe/gl/Program.hpp
    include/sxe/gl/Shader.hpp
    include/sxe/gl/VertexArrayTechnique.hpp
    include/sxe/gl/VertexShader.hpp
    include/sxe/graphics/DisplayManager.hpp
    include/sxe/graphics/DisplayMode.hpp
    include/sxe/graphics/DrawingTechnique.hpp
    include/sxe/graphics/FrameEndedListener.hpp
    include/sxe/graphics/FrameListener.hpp
    include/sxe/graphics/FrameStartedListener.hpp
    include/sxe/graphics/GraphicsFacet.hpp
    include/sxe/graphics/RenderingApi.hpp
    include/sxe/graphics/ShaderType.hpp
    include/sxe/graphics/Vertex.hpp
    include/sxe/graphics/VertexVertexMesh.hpp
    include/sxe/haveboost.hpp
    include/sxe/input/ConsoleInputManager.hpp
    include/sxe/input/InputCode.hpp
    include/sxe/input/InputFacet.hpp
    include/sxe/input/InputManager.hpp
    include/sxe/input/KeyEvent.hpp
    include/sxe/input/KeyEventManager.hpp
    include/sxe/input/KeyListener.hpp
    include/sxe/logging.hpp
    include/sxe/logging/LoggingManager.hpp
    include/sxe/pc/PcConfiguration.hpp
    include/sxe/pc/PcDisplayManager.hpp
    include/sxe/pc/PcInputManager.hpp
    include/sxe/resource/ArchiveStream.hpp
    include/sxe/resource/ResourceHandle.hpp
    include/sxe/resource/ResourceManager.hpp
    include/sxe/resource/basic_archive_istream.hpp
    include/sxe/resource/basic_archive_streambuf.hpp
    include/sxe/resource/basic_archive_typedefs.hpp
    include/sxe/scene/Entity.hpp
    include/sxe/scene/SceneManager.hpp
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
# And for some reason, I"m having to do add_ for objs here + target_ for things that need to be PUBLIC INTERFACE.
if(MSVC)
    add_compile_options(/wd4251 /wd4275 )
    add_compile_definitions(_CRT_SECURE_NO_WARNINGS)
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
    add_compile_definitions(SXE_DLL)
endif(BUILD_SHARED_LIBS)

if (MSVC)
    # TODO: should have a option to not expose this.
    target_compile_options(SxE PUBLIC INTERFACE /wd4251 /wd4275)
endif(MSVC)

target_include_directories(SxE PUBLIC
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
    $<INSTALL_INTERFACE:include/sxe>)

# This would cause SxE_HEADERS to be installed, but makes sxe/foo/bar.hpp become sxe/bar.hpp on install(TARGETS).
# set_target_properties(SxE PROPERTIES PUBLIC_HEADER "${SxE_HEADERS}")
# But this works.
install(DIRECTORY include/sxe DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})

target_link_libraries(SxE PUBLIC INTERFACE sxelog)
target_link_libraries(SxE PRIVATE sxelog)

if (BUILD_SXE_SDK)
    find_package(nlohmann_json REQUIRED)
else (BUILD_SXE_SDK)
    # from the OS.
    find_path(nlohmann_json_INCLUDE_DIR nlohmann/json.hpp)
    add_library(nlohmann_json::nlohmann_json INTERFACE IMPORTED)
    message("nlohmann_json_INCLUDE_DIR:${nlohmann_json_INCLUDE_DIR}")
    set_target_properties(nlohmann_json::nlohmann_json PROPERTIES
        INTERFACE_INCLUDE_DIRECTORIES ${nlohmann_json_INCLUDE_DIR})

endif (BUILD_SXE_SDK)
target_link_libraries(SxE PUBLIC INTERFACE nlohmann_json::nlohmann_json)

find_package(glm REQUIRED)
target_link_libraries(SxE PUBLIC INTERFACE glm)


set(Boost_USE_STATIC_LIBS ON)
set(Boost_USE_MULTITHREADED ON)
set(Boost_USE_STATIC_RUNTIME OFF)

find_package(Boost 1.72
    COMPONENTS filesystem)
if (Boost_FOUND)
    target_link_libraries(SxE PUBLIC Boost::filesystem)
    set(SXE_HAVE_BOOST ON)
endif(Boost_FOUND)

if (NOT SXE_HAVE_BOOST)
    if (CMAKE_CXX_COMPILER_ID STREQUAL "GNU")
        message("HELLO")
        # GNU's std::filesystem needs a libstdc++fs, and cmake doesn't add it.
        # Nether cmake nor g++ can be trusted to add it.
        target_link_libraries(SxE PUBLIC stdc++fs)
    endif()
endif()


find_package(glfw3 REQUIRED)
target_link_libraries(SxE PUBLIC glfw)
target_compile_definitions(SxE PRIVATE GLFW_INCLUDE_NONE)

find_package(ZLIB REQUIRED)
target_link_libraries(SxE PUBLIC ZLIB::ZLIB)


# Cmake's bundled FindLibArchive.cmake wants LibArchive_*.
find_package(LibArchive)
if (LibArchive_FOUND)
    set(SXE_HAVE_LIBARCHIVE ON)
    # XXX what about when not vendor libs?
    target_link_libraries(SxE PUBLIC ${LibArchive_LIBRARIES})
endif (LibArchive_FOUND)

find_package(Vulkan)
if (Vulkan_FOUND)
    set(SXE_HAVE_VULKAN ON)
    # We dynamically load Vulkan at runtime. So take the headers not the libs
    target_include_directories(SxE PUBLIC ${Vulkan_INCLUDE_DIRS})
endif (Vulkan_FOUND)

find_package(glbinding)
if (glbinding_FOUND)
    set(SXE_HAVE_OPENGL ON)
    target_link_libraries(SxE PUBLIC glbinding::glbinding)
endif (glbinding_FOUND)


install(TARGETS SxE
    EXPORT SxETargets
    RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
    ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}"
    INCLUDES DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/sxe"
    PUBLIC_HEADER DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/sxe")


include(CMakePackageConfigHelpers)


# Create, and install a Config.cmake file for find_package().
configure_package_config_file(SxEConfig.cmake.in
    ${CMAKE_CURRENT_BINARY_DIR}/SxEConfig.cmake
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/SxE
)
install(FILES ${CMAKE_CURRENT_BINARY_DIR}/SxEConfig.cmake
    DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/SxE)

# Generate, and install a ConfigVersion.cmake file for find_package().
write_basic_package_version_file(
    SxEConfigVersion.cmake
    VERSION ${PACKAGE_VERSION}
    COMPATIBILITY SameMajorVersion
    )
install(FILES ${CMAKE_CURRENT_BINARY_DIR}/SxEConfigVersion.cmake
    DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/SxE)

# Generate, and install a Targets.cmake file.
install(EXPORT SxETargets
        FILE SxETargets.cmake
        NAMESPACE SxE::
        DESTINATION lib/cmake/SxE)

# config.h
# Balance of evils:
# - [ ] Put config.h in source tree, rude to people using submodules.
# - [X] Add this dir to each of our modules CMakeLists.txt.
set(CONFIG_H_DIR ${CMAKE_BINARY_DIR}/sxe/include)
configure_file(config.hpp.in ${CONFIG_H_DIR}/sxe/config.hpp @ONLY)
install(FILES ${CONFIG_H_DIR}/sxe/config.hpp DESTINATION ${CMAKE_INSTALL_INCLUDEDIR}/sxe)
target_include_directories(SxE PRIVATE ${CONFIG_H_DIR})


