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

#include "./GameObject.h"

namespace demos
{

    /** The player.
     */
    class Player
        : public demos::GameObject
    {
      public:
        using KeyEvent = sxe::input::KeyEvent;
        using InputCode = sxe::input::InputCode;

        Player(sxe::GameEngine& engine);
        ~Player();

        /** Setup input for controlling the player.
         * 
         * @param controller the input manager.
         */
        bool setupInput(sxe::input::InputManager& controller);

        bool onUpArrow(KeyEvent event);
        bool onLeftArrow(KeyEvent event);
        bool onRightArrow(KeyEvent event);
        bool onSpaceBar(KeyEvent event);

        /** Called everyime the game world updates.
         */
        void think() override;

      private:
        static const string_type TAG;

        /** True if the ship's engines are on.
         */
        std::atomic_bool mBoosting;

        /** Speed gain in units per tick.
         */
        int mBoostAccelerationRate;

        /** Natural decay rate of in units per tick.
         */
        int mBoostDecelerationRate;

        /** Max speed of the booster in units per tick.
         */
        int mBoostSpeedLimit;

        /** How much the heading changes (yaws) on input.
         */
        float mYawRate;
    };
} // namespace demos

#endif // SXE_DEMOS_ROCKBLASTER_PLAYER__H