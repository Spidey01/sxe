#ifndef SXE_DEMOS_ROCKBLASTER_PLAYER__H
#define SXE_DEMOS_ROCKBLASTER_PLAYER__H
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

#include <sxe/input/InputManager.hpp>
#include <sxe/input/KeyEvent.hpp>
#include <sxe/resource/ResourceManager.hpp>
#include <sxe/scene/SceneManager.hpp>
#include <sxe/graphics/Sprite.hpp>

namespace demos {

    /** The player.
     */
    class Player
        : public sxe::common::stdtypedefs<Player>
        , public sxe::graphics::stdmathtypes
    {
      public:
        using KeyEvent = sxe::input::KeyEvent;
        using InputCode = sxe::input::InputCode;
        using Entity = sxe::scene::Entity;

        Player(sxe::GameEngine& engine);
        ~Player();

        /** Return the entity used for scene management.
         */
        sxe::scene::Entity::shared_ptr getEntity() const;

        /** Setup input for controlling the player.
         * 
         * @param controller the input manager.
         */
        bool setupInput(sxe::input::InputManager& controller);

        bool onUpArrow(KeyEvent event);
        bool onLeftArrow(KeyEvent event);
        bool onRightArrow(KeyEvent event);
        bool onSpaceBar(KeyEvent event);

        /** Called everyime the scene updates.
         */
        void onDraw();

        /** Called everyime the game world updates.
         */
        void think();

        /** Returns the current speed in units.
         */
        int speed() const;

        /** Sets the current speed.
         * 
         * @param vel the absolute speed in units.
         */
        void speed(int vel);

        /** Ship's position. Modify it to move.
         */
        vec3& position() const;

        /** Ship's direction of movement.
         * 
         * @param heading in degrees.
         * 
         * @returns vector that can be added to ships position to move in
         * direction.
         */
        vec3 direction(float heading) const;

        /** Yaw the ship.
         * 
         * @param offset degree of yaw: positive offsets point the nose to the
         * right; negative to the left.
         */
        void yaw(float offset);

      private:
        static const string_type TAG;
        mutable sxe::graphics::Sprite mSprite;

        using clock_type = std::chrono::steady_clock;
        using time_point = clock_type::time_point;

        time_point mLastOnDraw;
        time_point mLastThink;

        /** How fast the ship is moving in units.
         */
        std::atomic_int mSpeed;

        /** True if the ship's engines are on.
         */
        std::atomic_bool mBoosting;

        /** Ship's heading in degrees.
         */
        float mHeading;

        /** How much the heading changes (yaws) on input.
         */
        float mYawRate;
    };
}

#endif // SXE_DEMOS_ROCKBLASTER_PLAYER__H
