#ifndef SXE_DEMOS_ROCKBLASTERGAME__H
#define SXE_DEMOS_ROCKBLASTERGAME__H
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

#include <sxe/Game.hpp>
#include <sxe/input/InputFacet.hpp>

#include "Player.h"

namespace demos {

    /** Rock Blaster's sxe::Game implementation.
     */
    class RockBlasterGame
    : public sxe::Game
    {
      public:
        RockBlasterGame();
        virtual ~RockBlasterGame();
        string_type getName() const override;
        bool start() override;
        void stop() override;

      protected:
        void updateGameThread() override;

      private:
        static const string_type TAG;
        std::unique_ptr<demos::Player> mPlayer;
        bool mShownIntro;
        bool mReady;

        /** Setup the player instance.
         * @returns true on success.
         */
        bool setupPlayer(demos::Player& player);

        /** Handle global key bindings.
         * @see sxe::input::KeyListener.
         */
        bool onKeyEvent(sxe::input::KeyEvent event);
    };
}

#endif // SXE_DEMOS_ROCKBLASTERGAME__H