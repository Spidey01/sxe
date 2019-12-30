#ifndef SXE_TESTING_NULLCONFIGURATION__HPP
#define SXE_TESTING_NULLCONFIGURATION__HPP
/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
#include <sxe/Game.hpp>
#include <sxe/GameEngine.hpp>

namespace sxe { namespace testing {

    /** Utility class to setup a GameEngine configured with null input and display.
    */
    class SXE_PUBLIC NullConfiguration
    {
      public:

        /** The defaults */
        static GameEngine::unique_ptr setup(Game::shared_ptr game);

        /** Setup.
         *
         * @param argc same as main().
         * @param argv same as main().
         * @param game a shared_ptr to your Game implementation.
         *
         * @returns unique_ptr to GameEngine ready to be started.
         */
        static GameEngine::unique_ptr setup(int argc, char* argv[], Game::shared_ptr game);
    };
} }

#endif // SXE_TESTING_NULLCONFIGURATION__HPP
