# Settings

SxE has many Settings classes in the sxe::config namespace. Various file and in memory formats, etc.

sxe::GameEngine maintains a concept of "Runtime" settings. Calls to sxe::GameEngine::getSettings() always return the runtime settings. This is a Settings instance where all active settings are merged to create one authoritive view.

The order in which Settings are merged is as follows:

  1. Platform settings passed to GameEngine's ctor.
  2. System settings loaded from XDG_CONFIG_DIRS.
  3. User settings loaded from XDG_CONFIG_HOME.
  4. Command line settings passed to GameEngine's ctor.

An artifact of this means a setting is a setting is a setting. Regardless of whether it was specified on the command line, in a configuration file, or some platform specific bundle of joy.

Typically settings are namespaced by dots. Such that "quux.foo" and "quux.bar" suggest a relationship. Internally however all settings are a flat key/value pair.


## Special Settings

### debug

Boolean setting that works as a shortcut to general logging.

debug=true is equal to:
  - debug.log_to=debug.log
  - debug.log_level=DEBUG

This will be initialized when the LoggingManager creates the LogSinks.


### sxe.debug

Shortcut to configure debug as early as possible.

Value is an enum value as per sxe::logging::Level; either the integeral value or the obvious string. E.g. sxe.debug=1 and sxe.debug=ERROR are equivalent.

When set: GameEngine will create a LogSink for standard output with the specified default log level, and the name "sxe.debug". It will be done before any other initialization begins.


### sxe.graphics.api

Requests a specific graphics API.

  - Vulkan
    + Use Vulkan rendering if possible.
  - OpenGLES
    + Use OpenGL (ES) rendering.

