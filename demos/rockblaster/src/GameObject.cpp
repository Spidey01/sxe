/*-
 * Copyright (c) 2021-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "GameObject.h"

#include <sxe/Game.hpp>
#include <sxe/GameEngine.hpp>
#include <sxe/graphics/VertexVertexMesh.hpp>
#include <sxe/logging.hpp>
#include <sxe/resource/ResourceHandle.hpp>
#include <sxe/scene/SceneManager.hpp>

using std::bind;
using std::to_string;
using sxe::graphics::VertexVertexMesh;
using sxe::resource::ResourceHandle;

namespace demos
{

    const GameObject::string_type GameObject::TAG = "GameObject";

    GameObject::GameObject(sxe::GameEngine& engine, const string_type& name)
        : mName(name)
        , mSettings(engine.getSettings(), {engine.getGameName() + string_type(".") + mName, mName})
        , mSprite(engine, mSettings.getString("resource"), &VertexVertexMesh::resourceFilter, std::bind(&GameObject::onDraw, this), {})
        , mScaleFactor(mSettings.getFloat("scale"))
        , mLastOnDraw(clock_type::now())
        , mLastThink(clock_type::now())
        , mSpeed(0)
        , mHeading(0)
        , mVelocityMultiplier(mSettings.getFloat("velocity_multiplier"))
    {
        if (!mScaleFactor)
            Log::w(TAG, mName + "No scale setting - invisible object!");
        vec2 scale(mScaleFactor, mScaleFactor);
        mSprite.graphics()->scaleModelMatrix(scale);
    }

    GameObject::~GameObject()
    {
        Log::xtrace(TAG, "~GameObject()");
    }

    GameObject::Entity::shared_ptr GameObject::getEntity() const
    {
        return mSprite.entity();
    }

    void GameObject::onDraw()
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
            Log::test(TAG, "pos: " + sxe::graphics::vec_to_string(pos) + " + dir: " + sxe::graphics::vec_to_string(dir) + " * vel: " + to_string(velocity));
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

    void GameObject::think()
    {
        Log::test(TAG, "think()");

        time_point now = clock_type::now();
        mLastThink = now;
    }

    int GameObject::speed() const
    {
        return mSpeed;
    }

    void GameObject::speed(int vel)
    {
        mSpeed = vel;
    }

    GameObject::vec3& GameObject::position() const
    {
        return mSprite.graphics()->position();
    }

    GameObject::vec3 GameObject::direction(float heading) const
    {
        // X axis: radians, or the +/- differences will confuse you.
        float x = sin(glm::radians(heading));

        // Y axis: radians, ditto.
        float y = cos(glm::radians(heading));

        // Z axis: unmodified -- this game is 2D.
        float z = 0.0f;

        return vec3(x, y, z);
    }

    void GameObject::yaw(float offset)
    {
        mHeading += offset;

        if (mHeading >= 360.0f)
            mHeading -= 360.0f;
        if (mHeading <= -360.0f)
            mHeading += 360.0f;
    }

} // namespace demos
