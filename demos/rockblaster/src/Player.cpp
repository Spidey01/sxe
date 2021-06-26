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

#include "Player.h"

#include <sxe/graphics/VertexVertexMesh.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/logging.hpp>
#include <sxe/resource/ResourceHandle.hpp>
#include <sxe/scene/SceneManager.hpp>

using std::bind;
using std::logic_error;
using std::make_shared;
using std::make_unique;
using std::to_string;
using sxe::graphics::VertexVertexMesh;
using sxe::resource::ResourceHandle;

namespace demos {

const Player::string_type Player::TAG = "Player";

static int instance_count = 0;
Player::Player(sxe::GameEngine& engine)
    : mSprite(engine, engine.getSettings().getString("Player.resource"), &VertexVertexMesh::resourceFilter, std::bind(&Player::onDraw, this), {})
    , mLastOnDraw(clock_type::now())
    , mLastThink(clock_type::now())
    , mScaleFactor(engine.getSettings().getFloat("Player.scale"))
    , mSpeed(0)
    , mBoosting(false)
    , mHeading(0)
    , mYawRate(engine.getSettings().getFloat("Player.yaw_rate"))
    , mVelocityMultiplier(engine.getSettings().getFloat("Player.velocity_multiplier"))
    , mBoostAccelerationRate(engine.getSettings().getInt("Player.boost_acceleration_rate"))
    , mBoostDecelerationRate(engine.getSettings().getInt("Player.boost_deceleration_rate"))
    , mBoostSpeedLimit(engine.getSettings().getInt("Player.boost_speed_limit"))
{
    Log::xtrace(TAG, "Player()");

    if (!mScaleFactor)
        Log::w(TAG, "No Player.scale - invsibile space ship!");
    vec2 scale(mScaleFactor, mScaleFactor);
    mSprite.graphics()->scaleModelMatrix(scale);
}

Player::~Player()
{
    Log::xtrace(TAG, "~Player()");
}

Player::Entity::shared_ptr Player::getEntity() const
{
    return mSprite.entity();
}

bool Player::setupInput(sxe::input::InputManager& controller)
{
    Log::xtrace(TAG, "setupInput()");

    sxe::input::InputFacet::shared_ptr input = mSprite.input();
    if (!input) {
        Log::e(TAG, "setupInput(): no input manager!");
        return false;
    }

    input->addKeyListener(InputCode::IC_UP_ARROW, bind(&Player::onUpArrow, this, std::placeholders::_1));
    input->addKeyListener(InputCode::IC_LEFT_ARROW, bind(&Player::onLeftArrow, this, std::placeholders::_1));
    input->addKeyListener(InputCode::IC_RIGHT_ARROW, bind(&Player::onRightArrow, this, std::placeholders::_1));
    input->addKeyListener(InputCode::IC_SPACE, bind(&Player::onSpaceBar, this, std::placeholders::_1));

    return true;
}

bool Player::onUpArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onUpArrow(): event.toString(): " + event.toString());

    if (event.isKeyUp()) {
        mBoosting = false;
        return true;
    }

    mBoosting = true;
    return true;
}

bool Player::onLeftArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onLeftArrow(): event.toString(): " + event.toString());

    yaw(-mYawRate);
    mSprite.graphics()->rotate(mYawRate, vec3(0, 0, 1));

    vec3 dir = direction(mHeading);
    Log::xtrace(TAG, "heading " + to_string(mHeading) + " degrees; direction: " + sxe::graphics::vec_to_string(dir));

    return true;
}

bool Player::onRightArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onRightArrow(): event.toString(): " + event.toString());

    yaw(+mYawRate);
    mSprite.graphics()->rotate(-mYawRate, vec3(0, 0, 1));

    vec3 dir = direction(mHeading);
    Log::xtrace(TAG, "heading " + to_string(mHeading) + " degrees; direction: " + sxe::graphics::vec_to_string(dir));

    return true;
}

bool Player::onSpaceBar(KeyEvent event)
{
    Log::xtrace(TAG, "onSpaceBar(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "PEW, PEW, PEW" << std::endl;

    return true;
}

void Player::onDraw()
{
    Log::test(TAG, "onDraw()");

    /* Scale by time rather than framerate. */
    time_point now = clock_type::now();
    auto delta = std::chrono::duration_cast<std::chrono::milliseconds>(now - mLastOnDraw);
    mLastOnDraw = now;

    float velocity = static_cast<float>(delta.count()) * (mVelocityMultiplier * speed());
    vec3& pos = position();
    vec3 dir = direction(mHeading);

    if (velocity > 0) {
        Log::xtrace(TAG, "pos: " + sxe::graphics::vec_to_string(pos) + " + dir: " + sxe::graphics::vec_to_string(dir) + " * vel: " + to_string(velocity));
    }
    pos += dir * velocity;

    /* Flying off the screen should wrap. */

    if (pos.y > 1.0f)
        pos.y = -1.0f;
    if (pos.y < -1.0f)
        pos.y = 1.0;

    if (pos.x > 1.0f)
        pos.x = -1.0f;
    if (pos.x < -1.0f)
        pos.x = 1.0;
}

void Player::think()
{
    Log::test(TAG, "think()");

    /*
     * Boosters acellerate by 100 units per tick. Natural decay is in 25 units
     * per tick.
     * 
     * Technically this should be influnced by time for the same reasons that
     * rendering is.
     */

    if (mBoosting) {
        mSpeed += mBoostAccelerationRate;
    } else if (mSpeed > 0) {
        mSpeed -= mBoostDecelerationRate;
    }

    /* Not to infinity and meyond. */
    if (mSpeed > mBoostSpeedLimit)
        mSpeed = mBoostSpeedLimit;

    if (mSpeed > 0)
        Log::xtrace(TAG, "think(): speed: " + to_string(speed()) + " boosting: " + to_string(mBoosting));
}

int Player::speed() const
{
    return mSpeed;
}

void Player::speed(int vel)
{
    mSpeed = vel;
}

Player::vec3& Player::position() const
{
    return mSprite.graphics()->position();
}

Player::vec3 Player::direction(float heading) const
{
    // X axis: radians, or the +/- differences will confuse you.
    float x = sin(glm::radians(heading));

    // Y axis: radians, ditto.
    float y = cos(glm::radians(heading));

    // Z axis: unmodified -- this game is 2D.
    float z = 0.0f;

    return vec3(x, y, z);
}

void Player::yaw(float offset)
{
    mHeading += offset;

    if (mHeading >= 360.0f)
        mHeading -= 360.0f;
    if (mHeading <= -360.0f)
        mHeading += 360.0f;
}

}
