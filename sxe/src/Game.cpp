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

#include "sxe/Game.hpp"

#include <sxe/GameEngine.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using std::to_string;

namespace sxe {

const string Game::TAG = "Game";
const size_t Game::mMaxTickRate = 250;

Game::Game()
    : mGameEngine()
    , mState(State::STARTING)
    , mStopRequested(false)
    , mStopDone(false)
    , mThread()
    , mMainThreadId()
    , mGameThreadId()
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


bool Game::initialize(GameEngine& engine)
{
    Log::xtrace(TAG, "initialize(): getName(): " + getName());

    mGameEngine = engine;

    return true;
}


bool Game::uninitialize()
{
    Log::xtrace(TAG, "initialize(): getName(): " + getName());

    mGameEngine.reset();

    return true;
}


bool Game::start()
{
    Log::v(TAG, "start() called");

    mStopDone = false;
    mState = State::STARTING;
    mMainThreadId = std::this_thread::get_id();
    mThread = std::thread(&Game::runGameThread, this);

    return true;
}


void Game::stop()
{
    if (mStopDone) {
        Log::d(TAG, "Game was already stopped");
        return;
    }

    requestStop();

    if (mThread.joinable())
        mThread.join();
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


void Game::update()
{
    Log::test(TAG, "update()");
    mTickCounter.update();

    auto context = std::this_thread::get_id();

    if (context == mMainThreadId)
        updateMainThread();
    else if (context == mGameThreadId)
        updateGameThread();
    else
        throw std::logic_error("Game::update() called from unknown thread.");
}


GameEngine& Game::getGameEngine() const
{
    if (mGameEngine.has_value())
        return mGameEngine.value();

    throw std::logic_error("Game::getGameEngine() called before Game::initialize().");
}


void Game::updateMainThread()
{
    Log::test(TAG, "updateMainThread()");
}


void Game::updateGameThread()
{
    Log::test(TAG, "updateMainThread()");
}


void Game::runGameThread()
{
    Log::xtrace(TAG, "runGameThread(): mThread starting.");
    mGameThreadId = std::this_thread::get_id();

    using std::chrono::duration_cast;
    using std::chrono::milliseconds;
    using std::chrono::seconds;
    using std::chrono::steady_clock;

    while (!isStopRequested()) {
        auto startTime = steady_clock::now();

        /*
         * We trap exceptions the exception and shutdown, because ticking a
         * never endless stream of exceptions doesn't end better than wtf.
         */

        try {
            update();
        } catch(std::exception ex) {
            Log::wtf(TAG, "Game thread has died!", ex);
            break;
        }

        auto endTime = steady_clock::now() - startTime;

        auto rate = getTickRate();
        if (rate == 0)
            continue;

        auto sleepTime = milliseconds(1000 / rate) - duration_cast<milliseconds>(endTime);

        if (sleepTime <= milliseconds::zero()) {
            // hard clip to the max tick rate
            sleepTime = milliseconds(1000 / getMaxTickRate());
        }

        std::this_thread::sleep_for(sleepTime);
    }

    Log::xtrace(TAG, "runGameThread(): mThread exiting.");
}

}

