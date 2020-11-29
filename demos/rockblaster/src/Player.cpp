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
Player::Player()
    : mEntity(make_shared<sxe::scene::Entity>())
    , mInputFacet(nullptr)
    , mGraphicsFacet(nullptr)
    , mLastOnDraw(clock_type::now())
    , mLastThink(clock_type::now())
    , mSpeed(0)
    , mBoosting(false)
    , mHeading(0)
    , mYawRate(15.0f)
{
    Log::xtrace(TAG, "Player()");
}

Player::~Player()
{
    Log::xtrace(TAG, "~Player()");

    Log::v(TAG, "mInputFacet.reset()");
    if (mEntity)
        mEntity->setInputFacet(nullptr);
    mInputFacet.reset();
}

Player::Entity::shared_ptr Player::getEntity() const
{
    return mEntity;
}

bool Player::setupResources(sxe::resource::ResourceManager& loader, sxe::scene::SceneManager& scene)
{
    Log::xtrace(TAG, "setupResources()");

    try {
        Log::v(TAG, "Loading player.mesh");
        ResourceHandle::unique_ptr res = loader.load(string_type("player.mesh"));
        if (!res) {
            Log::w(TAG, "ResourceManager::load() returned nullptr!");
            return false;
        }

        Log::v(TAG, "Getting player.mesh vertices");
        VertexVertexMesh::unique_ptr mesh = res->asVertexVertexMesh();
        if (!mesh) {
            Log::w(TAG, "ResourceHandle::asVertexVertexMesh() returned nullptr!");
            return false;
        }
        if (mesh->vertices().empty()) {
            Log::e(TAG, "No vertices loaded from player.mesh!");
            return false;
        }

        mesh->solidFill({0, 1, 0, 1});

        Log::v(TAG, "Setting up mGraphicsFacet.");
        sxe::graphics::FrameListener onDrawCallback = std::bind(&Player::onDraw, this); 
        mGraphicsFacet = make_shared<sxe::graphics::GraphicsFacet>(scene.camera(), mesh->vertices(), onDrawCallback);

        vec2 scale(0.03f, 0.03f);
        mGraphicsFacet->scaleModelMatrix(scale);

        mEntity->setGraphicsFacet(mGraphicsFacet);
    } catch (std::exception& ex) {
        Log::e(TAG, "setupResources() failed", ex);
        return false;
    }

    return true;
}

bool Player::setupInput(sxe::input::InputManager& controller)
{
    Log::xtrace(TAG, "setupInput()");

    mInputFacet = make_shared<sxe::input::InputFacet>(controller);

    mInputFacet->addKeyListener(InputCode::IC_UP_ARROW, bind(&Player::onUpArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_LEFT_ARROW, bind(&Player::onLeftArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_RIGHT_ARROW, bind(&Player::onRightArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_SPACE, bind(&Player::onSpaceBar, this, std::placeholders::_1));

    mEntity->setInputFacet(mInputFacet);

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
    mGraphicsFacet->rotate(mYawRate, vec3(0, 0, 1));

    vec3 dir = direction(mHeading);
    Log::xtrace(TAG, "heading " + to_string(mHeading) + " degrees; direction: " + sxe::graphics::vec_to_string(dir));

    return true;
}

bool Player::onRightArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onRightArrow(): event.toString(): " + event.toString());

    yaw(+mYawRate);
    mGraphicsFacet->rotate(-mYawRate, vec3(0, 0, 1));

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

    float velocity = static_cast<float>(delta.count()) * (0.0000002f * speed());
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
        mSpeed += 100;
    } else if (mSpeed > 0) {
        mSpeed -= 25;
    }

    /* Not to infinity and meyond. */
    if (mSpeed > 10000)
        mSpeed = 10000;

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
    if (!mGraphicsFacet) {
        Log::wtf(TAG, "position() called before graphics facet constructed.");
    }
    return mGraphicsFacet->position();
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
