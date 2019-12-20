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

#include "sxe/core/GameEngine.hpp"
#include "sxe/logging.hpp"

using std::string;

namespace sxe { namespace core {

const string GameEngine::TAG = "GameEngine";

GameEngine::GameEngine()
{
    // placeholder
    auto sink = std::make_shared<sxe::core::logging::LogSink>();
    sxe::core::logging::Log::add(sink);
}

GameEngine::~GameEngine()
{
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
    // Log.i(TAG, "XDG_DATA_DIRS=\""+Utils.join(Xdg.XDG_DATA_DIRS, ':')+"\"");
    // Log.i(TAG, "XDG_CONFIG_DIRS=\""+Utils.join(Xdg.XDG_CONFIG_DIRS, ':')+"\"");


    #if 0 // 1.x did:
    if (!mDisplay.create()) {
        return false;
    }

    mGameThread = new GameThread(this, mGame);
    mGameThread.start();
    #endif

    return true;
}


void GameEngine::stop()
{
    #if 0 // 1.x
    mGame.stop();
    mGameThread.interrupt(); // should this be overriden to do Game.stop()?
    mDisplay.destroy();
    #endif
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
    return true;
    #if 0 // 1.x
        return (!mGame.isStopRequested()
                && !mDisplay.isCloseRequested()
                && mGameThread.isAlive());
    #endif
}


void GameEngine::update()
{
    #if 0 // 1.x
    mInputManager.poll();
    mSceneManager.update();
    mDisplay.update();
    #endif
}

} }

