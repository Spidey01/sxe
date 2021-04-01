# Windows

My build environment is Windows 10. Older versions may work but are not supported.

## Microsoft Visual C++

vswhere is used to select the MSVC version to compile, and set to use the newest version.

If you don't like the result than open your Developer Command Prompt (or what have you), and cd to the working directory, and call the envsetup/envsetup.cmd batch file. See ./envsetup.project.cmd and bootstrap.cmd for details on x86 vs x64 target.

### Command Line Build Tools

If you only installed the command line build tools instead of the IDE, vswhere won't tell envsetup.project.cmd where to find the compiler.

This can lead to weird and crazy things happening when building from the prompt. Open a Developer Command Prompt before setting up the SxE development environment.

## libarchive

When cmake generates our targets file it uses the path to where archive.lib was compiled rather than imported from. This is an issue for installing and packaging, such as per BUILD_SXE_SDK=ON.

I assume cmake's bundled FindLibArchive gets this from find_library() when we find_package() it. Or that libarchive's cmake has relocation issues.
