# Settings

SxE has many Settings classes in the sxe::config namespace. Various file and in memory formats, etc.

sxe::GameEngine maintains a concept of "Runtime" settings. Calls to sxe::GameEngine::getSettings() always return the runtime settings. This is a Settings instance where all active settings are merged to create one authoritive view.

The order in which Settings are merged is as follows:

  1. Platform settings passed to GameEngine's ctor.
  2. System settings loaded from XDG_CONFIG_DIRS.
  3. User settings loaded from XDG_CONFIG_HOME.
  4. Environment variables.
  5. Command line settings passed to GameEngine's ctor.

An artifact of this means a setting is a setting is a setting. Regardless of whether it was specified on the command line, in a configuration file, or some platform specific bundle of joy.

Typically settings are namespaced by dots. Such that "quux.foo" and "quux.bar" suggest a relationship. Internally however all settings are a flat key/value pair.

When Settings sources are merged into the "Runtime" settings: change notifications are triggered. For environment variables only those documented here will be dispatched.

## Special Settings

Some settings are predefined by the engine. Typically these are prefixed with "sxe" or "GAMENAME".

Settings prefixed with GAMENAME use the value of Game::getName(). Thus if your Game implementation returns "foobar" then subtitute "foobar" for "GAMENAME" in the below.

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
  - Use Vulkan rendering if possible.
- OpenGLES
  - Use OpenGL (ES) rendering.

### sxe.graphics.method

Specifies by name the DrawingTechnique to be used.

By default it is the first technique configured by a DisplayManager.

### GAMENAME.mode

String setting defining the Display Mode. Expressed as "{height} x {width} x {bit depth} @{refresh}".

Both height and width are required when specifying a Display Mode. The bit depth and refresh rate are optional: when undefined the engine reserves the right to of undefined behavior.

White space between field markers (x, @) is optional and will be ignored by the Display Mode parser.

### Examples

- "GAMENAME.mode=640 x 480 x 32 @60"
- GAMENAME.mode=1280x720x32@60
- "GAMENAME.mode=1920x1080

### GAMENAME.fullscreen

Boolean setting indicating if the game should run in fullscreen (true) or windowed (false) mode.

On PCs this tries to creates a fullscreen window and set the monitor to GAMENAME.mode.

### GAMENAME.fps

Boolean setting that toggles tracking Frames Per Second.