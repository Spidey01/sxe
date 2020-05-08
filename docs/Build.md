# Build

This repository is designed to build and package an "SXE_SDK" that can be used for building games against. Tests and demos are included.

SxE is built using [cmake](https://cmake.org/) from an [envsetup](https://github.com/Spidey01/envsetup) session.


## Bootstrap

Running the bootstrap.{cmd, sh} will setup dependencies, and launch the recommended cmake configuration. You can then run the build process.

	windows> .\bootstrap.cmd
	windows> m

	linux$ ./bootstrap.sh
	linux$ . ./envsetup/envsetup.sh
	linux$ m

The bootstrap script will use several variables defined in envsetup.project.{cmd,sh}. Some can be overriden by creating an envsetup.local.{cmd,sh} script to override them when envsetup is first run.


## Configuration

There are two major points of cmake build configuration: 

  - BUILD_SHARED_LIBS
	+ SxE will be used as a shared library.
	+ Dependecies will be shared libs.
  - BUILD_SXE_SDK
	+ Goal is to build, install, and package the SDK.

bootstrap.{cmd,sh} will set recommended defaults for the target. Defaults come from envsetup.project.{cmd,sh}, and can often be overriden by creating envsetup.local.{cmd,sh} files.


### CMAKE_BUILD_TYPE

Recommended is "RelWithDebInfo". This provides the most balance between a Release like build, and still being able to get a stack trace for debugging.


### BUILD_SHARED_LIBS

Recommended for numerious reasons, and how SxE is developed and tested.


### BUILD_SXE_SDK

Indicates that the build is a software development kit that will be used to build a game, or if SxE should simply be built as a library package.

#### Windows NT

Assumes bootstrap.cmd was used. Meaning that vcpkg was used to install dependencies, which should be bundled with the SXE_SDK package for ease of development.

The Vulkan SDK and Microsoft Visual C++ compiler must be installed, and are not part of SXE_SDK.


#### Linux

Currently BUILD_SXE_SDK is only supported on Windows targets. The Linux build is in a state of flux, as I determine how I want this to work in relation to the distributions package manager.


### BUILD_DOCS

Whether or not documentation should be built, installed, and packaged.

Doxygen is required for this to do much.


## Launching the build

Once envsetup is done: you can use the 'm' command to build. It will pick up variables like PROJECT_BUILDDIR and make life easier.


#### Manual

	# mkdir build
	# cd build
	# cmake -G Ninja -DCMAKE_BUILD_TYPE=RelWithDebugInfo ...
	# ninja

Which should make sense if you've ever messed with cmake.


#### VS Code

Configuration is provided for [Visual Studio Code](https://code.visualstudio.com/). It's expected that you will be using the CMake Tools extension.

The default build task is set to run ninja in ./build, using whatever (cmake) configuration you've created.

