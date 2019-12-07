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

#include "sxe/core/Game.hpp"
#include "sxe/logging.hpp"
#include "sxe/stdheaders.hpp"

using std::string;
using std::to_string;

namespace sxe { namespace core {

const string Game::TAG = "Game";
const size_t Game::mMaxTickRate = 250;

Game::Game()
    : mGameEngine(nullptr)
    , mState(State::STARTING)
    , mStopRequested(false)
    , mStopDone(false)
    , mTickCounter("Ticks")
{
    Log::log(Log::DEBUG, "Game", "Hello, world!");
}

Game::~Game()
{
}


string Game::getPublisher() const
{
    return "";
}


bool Game::start(GameEngine* engine)
{
    Log::v(TAG, "start() called");

    if (!engine) {
        Log::e(TAG, "start(): engine is nullptr!");
        return false;
    }

    mGameEngine = engine;
    mState = State::STARTING;

    return true;
}

void Game::stop()
{
    if (mStopDone) {
        Log::d(TAG, "Game was already stopped");
        return;
    }
    requestStop();
    mStopDone = true;
    Log::v(TAG, "stop() done");
}


bool Game::isStopRequested() const
{
    return mStopRequested;
}


bool Game::isStopped() const
{
    return mStopDone;
}


void Game::requestStop()
{
    Log::v(TAG, "requestStop() called");
    mStopRequested = true;
    mState = State::STOPPING;
}


int Game::getMaxFpsRate() const
{
    return mMaxTickRate;
}


int Game::getMaxTickRate() const
{
    return mMaxTickRate;
}


int Game::getTickRate() const
{
    // it's the client codes job to set this more appropriately.
    return mMaxTickRate;
}

void Game::tick()
{
    mTickCounter.update();
}


GameEngine& Game::getGameEngine() const
{
    if (!mGameEngine) {
        string why = "getGameEngine() called before start() set mGameEngine!";
        Log::w(TAG, why);
        throw std::runtime_error(TAG + "::" + why);
    }

    return *mGameEngine;
}

} }

