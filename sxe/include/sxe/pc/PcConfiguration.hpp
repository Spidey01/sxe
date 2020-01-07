#ifndef SXE_PC_PCCONFIGURATION__HPP
#define SXE_PC_PCCONFIGURATION__HPP
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
#include <sxe/Game.hpp>
#include <sxe/GameEngine.hpp>

namespace sxe { namespace pc {

    class SXE_PUBLIC PcConfiguration {
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

        /** PC specific settings.
         *
         * On Windows, we look at %LOCALAPPDATA%/Publisher/Gamename.{cfg,xml}.
         *
         *
         * Note:
         *
         *      On some systems we may substitute other values for version
         *      compatibility. E.g. on XP, there is no %LocalAppData% there is only
         *      ~~Zuul~~ %AppData%.
         */
        static GameEngine::Settings_ptr settings(Game::shared_ptr game, sys::Platform platform);

      private:
        static const std::string TAG;
    };

} }

#endif // SXE_PC_PCCONFIGURATION__HPP
