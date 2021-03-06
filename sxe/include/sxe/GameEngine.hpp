#ifndef SXE_GAMEENGINE__HPP
#define SXE_GAMEENGINE__HPP
/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

#include <sxe/api.hpp>
#include <sxe/common/Subsystem.hpp>
#include <sxe/sys/Platform.hpp>
#include <sxe/sys/Xdg.hpp>

namespace sxe {

    class Game;
    namespace config {
        class Settings;
    }
    namespace graphics {
        class DisplayManager;
    }
    namespace input {
        class InputManager;
    }
    namespace logging {
        class LoggingManager;
    }
    namespace resource {
        class ResourceManager;
    }
    namespace scene {
        class SceneManager;
    }

    class SXE_PUBLIC GameEngine
        : public common::stdtypedefs<GameEngine>
    {
      public:

        using Game_ptr = std::shared_ptr<Game>;
        using Settings_ptr = std::unique_ptr<config::Settings>;

        using TODO_placeholder = common::Subsystem; // placeholder.
        using DisplayManager_ptr = std::unique_ptr<graphics::DisplayManager>;
        using SceneManager_ptr = std::unique_ptr<scene::SceneManager>;
        using InputManager_ptr = std::unique_ptr<input::InputManager>;
        using ResourceManager_ptr = std::unique_ptr<resource::ResourceManager>;
        using LoggingManager_ptr = std::unique_ptr<logging::LoggingManager>;

        /** Just enough for unit tests. */
        GameEngine();

        /** Initializes the engine for use.
         *
         * This is the primary chunk-o-code constructor.
         *
         * @param game Game implementation.
         * @param args Command line arguments.
         * @param display Display complicata.
         * @param scene  Manager of the scene.
         * @param input All that input related stuff.
         * @param resources Resource management.
         * @param logging Log management.
         * @param settings Platform specific settings.
         * @param platform Platform specific information.
         */
        GameEngine(Game_ptr game, Settings_ptr&& args,
                   DisplayManager_ptr&& display, SceneManager_ptr&& scene,
                   InputManager_ptr&& input, ResourceManager_ptr&& resources,
                   LoggingManager_ptr&& logging, Settings_ptr&& settings,
                   sys::Platform platform);

        virtual ~GameEngine();

        /** Start up the game.
         *
         * Takes care of initializing the games engine to run the game in the
         * current context. It will call the start() method of your Game
         * implementation accordingly.
         */
        bool start();

        /** Stop the game.
         *
         * Will ensure Game::stop() is called. Shuts down the display, etc.
         */
        void stop();

        /** Convenience method that can serve as a simple main loop. */
        void mainLoop();

        /** Convenience method that provides a default main hook.
         * 
         * This will start() the engine, run the mainLoop(), and then stop()
         * the engine when the loop has exited.
         * 
         * You can essentially implement your main() with three steps:
         * 
         *   1. Setup GameEngine for your platform.
         *   2. Fail if result == nullptr.
         *   3. return g->main().
         * 
         * Helper functions are provided for step 1, and the remainder are
         * simple.
         * 
         * @returns EXIT_SUCESS, or EXIT_FAILURE as appropriate.
         * @see pc::PcConfiguration, testing::NullConfiguration.
         */
        int main();

        /** Determine if the Game environment is running. */
        bool isRunning() const;

        /** Update Game engine state.
         *
         * <ol>
         *  <li>Polls the InputManager.</li>
         *  <li>Updates the SceneManager.</li>
         *  <li>Updates the DisplayManager.</li>
         * </ol>
         *
         * Game implementations update independantly of GameEngine, as it runs on a
         * seperate thread.
         */
        void update();

        /** Bootstrap debugging.
         * 
         * Creates a log sink called "sxe.debug". By default log level is
         * ASSERT.
         * 
         * Principally this is to allow debugging of early system init. Game
         * implementations can just use the regular 'debug=true' or
         * 'debug.things=values' method from command line or configuration
         * file.
         * 
         * Use the sxe.debug command line option to assign this a log level. An
         * environment variable named "sxe.debug", or "SXE_DEBUG" will have the
         * same effect.
         * 
         * Value is defined by Log::stringToLevel(). E.g. sxe.debug=TRACE or
         * sxe.debug=10.
         */
        void sxe_debug();

        /* Load the settings files.
         *
         * XDG_CONFIG_DIRS will be searched for a system wide settings file.
         * XDG_CONFIG_HOME will be searched for user specific settings file.
         *
         * For both locations: a file named for Game::getName() with a ".cfg"
         * extension will be tested. If this file does not exist the test will
         * be retried with a ".xml" extension before moving on.
         *
         * Searching XDG_CONFIG_DIRS stops as soon as a system wide settings
         * file is found. I.e. at most one system wide settings file will be
         * loaded.
         *
         * File format is mapped as follows:
         *
         *   - .cfg => sxe::config::SettingsFile.
         *   - .xml => sxe::config::SettingsXMLFile.
         *
         */
        void loadSettingsFiles();

        /** Push some helpful defaults to runtime settings.
         */
        void configure();

        /** Push some helpful defaults to this settings.
         */
        void configure(config::Settings& s);

        std::weak_ptr<Game> getGame() const;

        /** @returns the Game::getName().
         */
        string_type getGameName() const;

        /** Accessor for settings.
         *
         * @returns runtime settings.
         */
        config::Settings& getSettings() const;

        input::InputManager& getInputManager() const;
        graphics::DisplayManager& getDisplayManager() const;
        resource::ResourceManager& getResourceManager() const;
        scene::SceneManager& getSceneManager() const;

      private:
        static const string_type TAG;

        sys::Xdg mXdg;

        Game_ptr mGame;

        DisplayManager_ptr mDisplayManager;
        SceneManager_ptr mSceneManager;
        InputManager_ptr mInputManager;
        ResourceManager_ptr mResourceManager;
        LoggingManager_ptr mLoggingManager;

        /** Master source of Settings.
         *
         * From lowest to highest priority, this will contain the following sources
         * of Settings data.
         *
         * - Platform settings.
         * - System settings file.
         * - User settings file.
         * - Environment variables.
         * - Command line arguments
         */
        Settings_ptr mRuntimeSettings;

        /** Platform settings loaded from the ctor. */
        Settings_ptr mPlatformSettings;

        /** System settings loaded from $XDG_CONFIG_DIRS. */
        Settings_ptr mSystemSettings;

        /** User settings loaded from $XDG_CONFIG_HOME. */
        Settings_ptr mUserSettings;

        /** Environment variables from SettingsEnvironment. */
        Settings_ptr mEnvironmentSettings;

        /** Settings provided at the command line. */
        Settings_ptr mCommandLineSettings;

        sys::Platform mPlatform;
    };

}

#endif // SXE_GAMEENGINE__HPP
