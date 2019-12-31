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

#include "sxe/GameEngine.hpp"

#include <sxe/Game.hpp>
#include <sxe/common/Utils.hpp>
#include <sxe/config/Settings.hpp>
#include <sxe/config/SettingsFile.hpp>
#include <sxe/config/SettingsMap.hpp>
#include <sxe/config/SettingsXMLFile.hpp>
#include <sxe/graphics/Display.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/sys/Platform.hpp>
#include <sxe/logging.hpp>

using std::string;

namespace sxe {

const string GameEngine::TAG = "GameEngine";

GameEngine::GameEngine()
{
#if 0
    // placeholder
    auto sink = std::make_shared<sxe::logging::LogSink>();
    sxe::logging::Log::add(sink);
#endif
}

GameEngine::GameEngine(Game_ptr game, Settings_ptr&& args,
                       DisplayManager_ptr&& display, SceneManager_ptr&& scene,
                       InputManager_ptr&& input, ResourceManager_ptr&& resources,
                       LoggingManager_ptr&& logging, Settings_ptr&& settings,
                       sys::Platform platform)
    : mXdg()
    , mGame(game)
    , mDisplayManager(std::move(display))
    , mSceneManager(std::move(scene))
    , mInputManager(std::move(input))
    , mResourceManager(std::move(resources))
    , mLoggingManager(std::move(logging))
    , mRuntimeSettings(std::make_unique<config::SettingsMap>())
    , mPlatformSettings(std::move(settings))
    , mSystemSettings(nullptr)
    , mUserSettings(nullptr)
    , mCommandLineSettings(std::move(args))
    , mPlatform(platform)
{

    if (mGame == nullptr) {
        throw std::invalid_argument(TAG + string("game parameter can't be nullptr!"));
    }

    // placeholder
    auto sink = std::make_shared<sxe::logging::LogSink>("placeholder", Log::TRACE, std::cout);
    sxe::logging::Log::add(sink);

    {
        if (!mRuntimeSettings)
            Log::wtf(TAG, "Programmer failure");

        // Used to have a fancier SettingsFile stuff for k/v vs xml - start off with what we have now.

        const string cfgName = mGame->getName() + ".cfg";
        auto makeCfg = [](auto p) -> Settings_ptr { return std::make_unique<config::SettingsFile>(p); };

        const string xmlName = mGame->getName() + ".xml";
        auto makeXml = [](auto p) -> Settings_ptr { return std::make_unique<config::SettingsXMLFile>(p); };

        using factory = std::function<Settings_ptr(sys::FileSystem::path)>;

        const std::tuple<string, factory> names[] = {
            std::make_tuple( cfgName, makeCfg ),
            std::make_tuple( xmlName, makeXml ),
        };

        /*
         * System settings:
         *
         *      Look for the first GameName.cfg file in $XDG_CONFIG_DIRS.
         *      If not found, try GameName.xml by same method.
         */

        for (const auto& tuple : names) {
            string n;
            factory make;
            std::tie(n, make) = tuple;

            auto p = mXdg.getConfigDir(n);

            if (sys::FileSystem::exists(p)) {
                mSystemSettings = make(p);
                break;
            }
        }

        /*
         * User settings:
         *
         *      Look for $XDG_CONFIG_HOME/|game name|cfg.
         *      If not found, try $XDG_CONFIG_HOME/|game name|.xml
         */

        for (const auto& tuple : names) {
            string n;
            factory make;
            std::tie(n, make) = tuple;

            auto p = mXdg.getConfigHomeDir(n);

            if (sys::FileSystem::exists(p)) {
                mUserSettings = make(p);
            }
        }
    }

    /*
     * These initialize functions will subscribe to whatever runtime
     * settings they want. As well as perform any pre start() setup.
     */

    if (mLoggingManager)
        mLoggingManager->initialize(*this);
    if (mDisplayManager)
        mDisplayManager->initialize(*this);
    if (mSceneManager)
        mSceneManager->initialize(*this);
    if (mInputManager)
        mInputManager->initialize(*this);
    if (mResourceManager)
        mResourceManager->initialize(*this);

    /*
     * Process the various sources of Settings.
     */
    if (mPlatformSettings != nullptr)      mRuntimeSettings->merge(*mPlatformSettings);
    if (mSystemSettings != nullptr)        mRuntimeSettings->merge(*mSystemSettings);
    if (mUserSettings != nullptr)          mRuntimeSettings->merge(*mUserSettings);
    if (mCommandLineSettings != nullptr)   mRuntimeSettings->merge(*mCommandLineSettings);

    configure();
}


GameEngine::~GameEngine()
{
    if (mResourceManager)
        mResourceManager->uninitialize();
    if (mInputManager)
        mInputManager->uninitialize();
    if (mSceneManager)
        mSceneManager->uninitialize();
    if (mDisplayManager)
        mDisplayManager->uninitialize();
    if (mLoggingManager)
        mLoggingManager->uninitialize();
}


bool GameEngine::start()
{
    Log::v(TAG, "start()");

    // XXX don't have platform settings yet.
    // Log.i(TAG, mPlatform);

    Log::i(TAG, "APP_HOME=\"" + mXdg.APP_HOME.string() + "\"");
    Log::i(TAG, "XDG_DATA_HOME=\"" + mXdg.XDG_DATA_HOME.string() + "\"");
    Log::i(TAG, "XDG_CONFIG_HOME=\"" + mXdg.XDG_CONFIG_HOME.string() + "\"");
    Log::i(TAG, "XDG_CACHE_HOME=\"" + mXdg.XDG_CACHE_HOME.string() + "\"");
    Log::i(TAG, "XDG_RUNTIME_DIR=\"" + mXdg.XDG_RUNTIME_DIR.string() + "\"");
    /* Implicitly converting path to string is sometimes painful vs explicit */
    {
        using seq = std::vector<string>;
        using path = sys::FileSystem::path;

        seq dataDirs;
        for (const path& p : mXdg.XDG_DATA_DIRS)
            dataDirs.push_back(p.string());

        seq configDirs;
        for (const path& p : mXdg.XDG_CONFIG_DIRS)
            configDirs.push_back(p.string());

        Log::i(TAG, "XDG_DATA_DIRS=\"" + common::Utils::join(dataDirs, dataDirs.size(), ':') + "\"");
        Log::i(TAG, "XDG_CONFIG_DIRS=\"" + common::Utils::join(configDirs, configDirs.size(), ':') + "\"");
    }

    if (!mDisplayManager) {
        Log::e(TAG, "No Display implementation!");
        Log::w(TAG, "If your platform or app has none: use NullDisplay.");
        return false;
    }

    if (!mDisplayManager->create()) {
        Log::e(TAG, "Display::create() failed!");
        return false;
    }

    #if 0 // 1.x did:
    mGameThread = new GameThread(this, mGame);
    mGameThread.start();
    #endif
    // Placeholder until GameThread is a thing.
    mGame->start(this);

    return true;
}


void GameEngine::stop()
{
    Log::xtrace(TAG, "stop()");

    if (mGame)
        mGame->stop();

    #if 0 // 1.x
    mGameThread.interrupt(); // should this be overriden to do Game.stop()?
    #endif

    if (mDisplayManager)
        mDisplayManager->destroy();

    Log::v(TAG, "stop() done");
}


void GameEngine::mainLoop()
{
    try {
        while (isRunning()) {
            update();
        }
    } catch(std::exception& ex) {
        Log::e(TAG, "Unhandled exception in mainLoop().", ex);

        /*
         * Don't call stop. We don't know if it's that recoverable. Dump a
         * stack trace and force an exit NOW.
         */
        #if 0 // 1.x
        e.printStackTrace();
        #endif
        std::exit(1);
    }
}


bool GameEngine::isRunning() const
{
    if (mGame && mGame->isStopRequested())
        return false;

    if (mDisplayManager && mDisplayManager->isCloseRequested())
        return false;

    return true;
    #if 0 // 1.x
        return (!mGame.isStopRequested()
                && !mDisplay.isCloseRequested()
                && mGameThread.isAlive());
    #endif
}


void GameEngine::update()
{
    if (mInputManager)
        mInputManager->update();
    #if 0 // 1.x
    mSceneManager.update();
    #endif
    if (mDisplayManager)
        mDisplayManager->update();

    // Placeholder until GameThread is a thing.
    mGame->tick();
}


void GameEngine::configure()
{
    configure(*mRuntimeSettings);
}


void GameEngine::configure(config::Settings& s)
{
    /* Shortcut key used for general debugging. */
    if (s.getBool("debug")) {
        // Make sure that we have a log file.
        if (!s.contains("debug.log_to"))
            s.setString("debug.log_to", "debug.log");
        if (!s.contains("debug.log_level"))
            s.setInt("debug.log_level", Log::DEBUG);
    }
}


std::weak_ptr<Game> GameEngine::getGame() const
{
    return mGame;
}



config::Settings& GameEngine::getSettings() const
{
    assert(mRuntimeSettings != nullptr);

    return *mRuntimeSettings;
}


input::InputManager& GameEngine::getInputManager() const
{
    assert(mInputManager != nullptr);

    return *mInputManager;
}


graphics::Display& GameEngine::getDisplayManager() const
{
    assert(mDisplayManager != nullptr);

    return *mDisplayManager;
}


}

