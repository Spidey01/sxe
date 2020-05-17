# Linux

When building for Linux: I use [Debian](https://www.debian.org/) stable.

Different distributions should work fine, but are not actively tested.


## BUILD_SXE_SDK 

Currently: BUILD_SXE_SDK is only supported on Windows NT. Its purpose is to generate a one stop archive with SxE, and dependencies included.

On Linux: I'm currently compiling against the operating system's packages. See bootstrap.sh.

BUILD_SXE_SDK=OFF is less of an issue when you want to make a Debian package.

BUILD_SXE_SDK=ON is more of an issue when you're using Microsoft Visual C++.


## Debian Buster

Notes on Debian 10/Buster.


### Vulkan 

Version 1.1.97 of the vulkan and vulkan-hpp packages are too old.

Either install a Vulkan SDK, or compile without Vulkan support.


### Boost

SxE will use boost::filesystem when compiled with boost, and std::filesystem otherwise.

Buster uses Boost 1.67 which is too old to replace std::filesystem.

Either install a newer version of Boost, or lose other features that require Boost support.
