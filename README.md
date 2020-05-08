# SxE 2.0

This is an experiment in making a game engine. It's intended for real use but hacked in odd moments and occasionally for odd purposes, so don't expect master to always be in a well engineered state.

SxE is intended to be simple, modular, and able to deal with multiple platforms. ~~PC and Android are officially supported. Game consoles beyond that, not at the moment.~~

SxE is built using toolchains native to the target platform. This means:

  - GCC is used for GNU/Linux
  - MS Visual C++ is used for Windows NT.

Other configurations may work but are not frequently tested.

## Versions

SxE 2.x for C++ is developed on master branch, formerly 2.0-cpp.

SxE 1.0 for Java: checkout branch [1.0-java](https://github.com/Spidey01/sxe/tree/1.0-java).

## Setup

Working from the shell should be done using [envsetup](https://github.com/Spidey01/envsetup). Working from an editor or IDE, should have the same environment set for the host platform.

	windows> .\envsetup\envsetup.cmd

	linux$ . ./envsetup/envsetup.sh

For help on available commands use the _hmm_ command. But mostly you will be interested in the variables set in envsetup.{project,local}.{cmd,sh}.

See [Build](docs/Build.md) for more details.
