#ifndef SXE_DEMOS_ROCKBLASTER_GAMEOBJECT__H
#define SXE_DEMOS_ROCKBLASTER_GAMEOBJECT__H
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

#include <sxe/graphics/Sprite.hpp>
#include <sxe/resource/ResourceManager.hpp>
#include <sxe/scene/SceneManager.hpp>

namespace demos
{

    /** Common code for game objects.
     */
    class GameObject
        : public sxe::common::stdtypedefs<GameObject>
        , public sxe::graphics::stdmathtypes
    {
      public:
        using Entity = sxe::scene::Entity;

        /** Create the game object.
         * 
         * @param engine the GameEngine to utilize.
         * @param name name of this GameObject used for settings prefix, debug, etc.
         */
        GameObject(sxe::GameEngine& engine, const string_type& name);

        virtual ~GameObject();

        /** Return the entity used for scene management.
         */
        sxe::scene::Entity::shared_ptr getEntity() const;

        /** Called everyime the scene updates.
         */
        virtual void onDraw();

        /** Called everyime the game world updates.
         */
        virtual void think();

        /** Returns the current speed in units.
         */
        int speed() const;

        /** Sets the current speed.
         * 
         * @param vel the absolute speed in units.
         */
        void speed(int vel);

        /** Object's position. Modify it to move.
         */
        vec3& position() const;

        /** Compute object's direction of movement.
         * 
         * @param heading in degrees of yaw.
         * 
         * @returns vector that can be added to position() to move in
         * direction.
         */
        vec3 direction(float heading) const;

        /** Adjust the object's yaw.
         * 
         * @param offset degree of yaw: positive offsets yaw to the
         * right; negative to the left.
         */
        void yaw(float offset);

      protected:

        /** Object's name from the ctor.
         */
        string_type mName;

        /** Sprite used for rendering the object.
         */
        mutable sxe::graphics::Sprite mSprite;

        using clock_type = std::chrono::steady_clock;
        using time_point = clock_type::time_point;

        /** Last time_point of onDraw() called.
         */
        time_point mLastOnDraw;

        /** Last time_point of think() called.
         */
        time_point mLastThink;

        /** Scale factor for our Sprite's model matrix.
         *
         * Initialized from {WHAT}.scale setting.
         */
        float mScaleFactor;

        /** How fast the object is moving in units.
         */
        std::atomic_int mSpeed;

        /** Object's heading in degrees.
         */
        float mHeading;

        /** Rock's velocity multiplier.
         * 
         * Used by onDraw() when computing the rock's velocity based on boost
         * speed and time since mLastOnDraw.
         */
        float mVelocityMultiplier;

      private:
        static const string_type TAG;
    };
} // namespace demos

#endif // SXE_DEMOS_ROCKBLASTER_GAMEOBJECT__H