#ifndef SXE_CORE_GAMEENGINE_H
#define SXE_CORE_GAMEENGINE_H
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

#include <sxe/api.hpp>
#include "sxe/core/sys/Xdg.hpp"

namespace sxe { namespace core {

    class SXE_PUBLIC GameEngine
    {
      public:
        using unique_ptr = std::unique_ptr<GameEngine>;
        using shared_ptr = std::shared_ptr<GameEngine>;
        using weak_ptr = std::weak_ptr<GameEngine>;

        GameEngine();
        virtual ~GameEngine();

        /** Start up the game.
         *
         * Takes care of initializing the games engine to run the game in the
         * current context. It will call the start() method of your Game
         * implementation accordingly.
         */
        bool start();

        /** Stop the game.
         *
         * Will ensure Game::stop() is called. Shuts down the display, etc.
         */
        void stop();

        /** Convenience method that can serve as a simple main loop. */
        void mainLoop();

        /** Determine if the Game environment is running. */
        bool isRunning() const;

        /** Update Game engine state.
         *
         * <ol>
         *  <li>Polls the InputManager.</li>
         *  <li>Updates the SceneManager.</li>
         *  <li>Updates the Display.</li>
         * </ol>
         *
         * Game implementations update independantly of GameEngine, as it runs on a
         * seperate thread.
         */
        void update();

      private:
        static const std::string TAG;

        sys::Xdg mXdg;

    };

} }

#endif
