/*-
 * Copyright (c) 2020-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "RockBlasterGame.h"

#include <sxe/GameEngine.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/logging.hpp>
#include <sxe/resource/ResourceManager.hpp>
#include <sxe/scene/SceneManager.hpp>

using std::cout;
using std::endl;
using std::make_unique;
using std::to_string;
using sxe::input::InputCode;

/* Modest hardware can probably handle thousands but good look fitting that on screen. */
constexpr size_t InsaneMaxRocks = 100;
constexpr size_t DefaultMaxRocks = 12;

namespace demos {

const RockBlasterGame::string_type RockBlasterGame::TAG = "RockBlasterGame";

RockBlasterGame::RockBlasterGame()
    : mRandomDevice()
    , mRandomEngine(mRandomDevice())
    , mRockHeadingDistribution(1, 360)
    , mRockSpeedDistribution(100, 500)
    , mRockPositionDistribution(-1.0f, 1.0f)
    , mPlayer(nullptr)
    , mMaxRocks(DefaultMaxRocks)
    , mShownIntro(false)
    , mReady(false)
{
    Log::xtrace(TAG, "RockBlasterGame()");
}

RockBlasterGame::~RockBlasterGame()
{
    Log::xtrace(TAG, "~RockBlasterGame()");
}

RockBlasterGame::string_type RockBlasterGame::getName() const
{
    return TAG;
}

bool RockBlasterGame::start()
{
    Log::xtrace(TAG, "start()");

    if (!sxe::Game::start())
        return false;

    Log::v(TAG, "Setting resources path");
    /* Relative to $XDG_DATA_DIRS. */
    string_type setting = getName() + ".resources.path";
    getGameEngine().getSettings().setString(setting, getName());

    mMaxRocks = getGameEngine().getSettings().getInt("maxrocks");
    if (mMaxRocks > InsaneMaxRocks) {
        Log::e(TAG, to_string(mMaxRocks) + " is an insane amount of rocks, you blaster!");
    }
    if (mMaxRocks == 0 || mMaxRocks > InsaneMaxRocks) {
        Log::w(TAG, "Forcing maxrocks=" + to_string(DefaultMaxRocks));
        mMaxRocks = DefaultMaxRocks;
    }

    Log::v(TAG, "Binding global keys");
    sxe::input::KeyListener inputCallback = std::bind(&RockBlasterGame::onKeyEvent, this, std::placeholders::_1);
    getInputFacet().addKeyListener(InputCode::IC_ENTER, inputCallback);
    getInputFacet().addKeyListener(InputCode::IC_ESCAPE, inputCallback);
    getInputFacet().addKeyListener(InputCode::IC_Q, inputCallback);

    Log::v(TAG, "Creating the Player.");
    mPlayer = make_unique<demos::Player>(getGameEngine());

    return true;
}

void RockBlasterGame::stop()
{
    Log::xtrace(TAG, "stop()");

    mPlayer.reset();
    mRocks.clear();

    sxe::Game::stop();
}

void RockBlasterGame::updateMainThread()
{
    Game::updateMainThread();

    if (getState() == State::STARTING) {
        if (!mShownIntro) {
            cout << "Press Enter to start the game." << endl;
            mShownIntro = true;
        }
        if (getGameEngine().getSettings().getBool("auto")) {
            cout << "Auto starting game" << endl;
            setState(State::RUNNING);
            return;
        }
    }

    if (getState() == State::RUNNING) {
        if (!mReady && mPlayer) {
            cout << "GET READY !!!" << endl;

            mReady = setupPlayer(*mPlayer.get());
            if (!mReady) {
                Log::wtf(TAG, "setupPlayer() failed!");
                // NORETURN
            }

            Log::v(TAG, "Creating the Rocks.");
            for (size_t i = 0; i < mMaxRocks; ++i) {
                Log::xtrace(TAG, "Creating rock " + to_string(i) + " to the scene");
                mRocks.emplace_back(make_unique<demos::Rock>(getGameEngine()));
                setupRock(*mRocks[i]);
            }

            cout << "GO, GO, GO, BLAST OFF !!!" << endl;
        }

        if (mPlayer)
            mPlayer->think();
    }

    if (getState() == State::STOPPING) {
        if (mShownIntro) {
            cout << "It's full of stars!" << endl;
            mShownIntro = false;
        }
    }
}

bool RockBlasterGame::setupRock(demos::Rock& rock)
{
    Log::xtrace(TAG, "setupRock()");

    float x = mRockPositionDistribution(mRandomEngine);
    float y = mRockPositionDistribution(mRandomEngine);

    /* Make sure starting positon isn't too close to player stat position 0,0. */
    while (x > -0.1f && x < 0.1f)
        x = mRockPositionDistribution(mRandomEngine);
    while (y > -0.1f && y < 0.1f)
        y = mRockPositionDistribution(mRandomEngine);

    rock.position().x = x;
    rock.position().y = y;

    rock.yaw(static_cast<float>(mRockHeadingDistribution(mRandomEngine)));
    rock.speed(mRockSpeedDistribution(mRandomEngine));

    getGameEngine().getSceneManager().addEntity(rock.getEntity());

    return true;
}

bool RockBlasterGame::setupPlayer(demos::Player& player)
{
    Log::xtrace(TAG, "setupPlayer()");

    auto& sceneManager = getGameEngine().getSceneManager();
    auto& inputManager = getInputFacet().manager();

    if (!player.setupInput(inputManager)) {
        Log::e(TAG, "Failed to setup player input!");
        return false;
    }

    sceneManager.addEntity(mPlayer->getEntity());

    return true;
}

bool RockBlasterGame::onKeyEvent(sxe::input::KeyEvent event)
{
    if (!event.isKeyUp())
        return false;

    if (event.getKeyCode() == InputCode::IC_ESCAPE || event.getKeyCode() == InputCode::IC_Q) {
        cout << "Goodbye!" << endl;
        requestStop();
        return true;
    }

    if (!event.isKeyUp())
        return false;

    if (getState() == State::STARTING) {
        if (event.getKeyCode() == InputCode::IC_ENTER) {
            setState(State::RUNNING);
            return true;
        }
    }

    return false;
}

}
