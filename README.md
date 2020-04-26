This is an experiment in making a game engine. It's intended for real use but hacked in odd moments and occasionally for odd purposes, so don't expect master to always be in a well engineered state.

SxE is intended to be simple, modular, and able to deal with multiple platforms. ~~PC and Android are officially supported. Game consoles beyond that, not at the moment.~~

SxE is built using toolchains native to the target platform. This means:

  - GCC is used for GNU/Linux
  - MS Visual C++ is used for Windows NT.

Other configurations may work but are not frequently tested.

## Setup

Working from the shell should be done using [envsetup](https://github.com/Spidey01/envsetup). Working from an editor or IDE, should have the same environment set for the host platform.

	windows> .\envsetup\envsetup.cmd

	linux$ . ./envsetup/envsetup.sh

For help on available commands use the _hmm_ command. But mostly you will be interested in the variables set in envsetup.{project,local}.{cmd,sh}.

See Build for more details.

## Build

This repository is designed to build and package an "SXE_SDK" that can be used for building games against. Tests and demos are included. The primary build method is [cmake](https://cmake.org/); the alternative is using [ngen](https://github.com/Spidey01/ngen) to generate a [ninja](https://ninja-build.org/) build.


There are two major points of build configuration: 

  - BUILD_SHARED_LIBS
	+ SxE will be used as a shared library.
	+ Dependecies will be shared libs.
  - BUILD_VENDOR_LIBS
	+ Use our own builds of third party code instead of the host.

These are controlled as usual for cmake variables. When using ngen the assumption is that both are set. I typically build with both set to true.

### CMAKE_BUILD_TYPE

The recommended is "RelWithDebInfo". This provides the most balance between a Release like build, and still being able to get a stack trace for debugging.

For cmake this is configurable. For ngen, it is assumed to be like RelWithDebInfo.

### BUILD_SHARED_LIBS

Recommended for numerious reasons, and how SxE is developed and tested. For the ngen build: it is assumed to be true.

### BUILD_VENDOR_LIBS

This will assume that $PROJECT_DISTDIR has been bootstrapped with our dependencies. It is assumed that BUILD_SHARED_LIBS=true will be used, and where applicable the C runtime is /MD. E.g. CMAKE_BUILD_TYPE=Release|RelWithDebugInfo.

To use BUILD_VENDOR_LIBS: you must first bootstrap things.

	windows> .\bootstrap.cmd

	linux$ ./bootstrap.sh

The process will take you a very long time, as tasks like loading the Boost C++ libraries into $PROJECT_DISTDIR will occur. The default location is ./dist/.

### Launching the build


From the shell:

#### cmake

	# mkdir build
	# cd build
	# cmake -G Ninja -DCMAKE_BUILD_TYPE=RelWithDebugInfo ..
	# ninja

Which should make sense if you've ever messed with cmake.

#### ngen

	# ngen
	# ninja

#### VS Code

Configuration is provided for [Visual Studio Code](https://code.visualstudio.com/). It's expected that you will be using the CMake Tools extension, or manually running ngen.

The default build task is set to run ninja in ./build, using whatever (cmake) configuration you've created.
