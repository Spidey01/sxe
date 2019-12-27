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

#include "sxe/core/testing/NullConfiguration.hpp"

#include <sxe/core/config/SettingsMap.hpp>
#include <sxe/core/sys/Platform.hpp>
#include <sxe/core/testing/NullDisplay.hpp>

using std::make_shared;
using std::make_unique;
using sxe::core::config::Settings;
using sxe::core::config::SettingsMap;

namespace sxe { namespace core { namespace testing {

GameEngine::unique_ptr NullConfiguration::setup(Game::shared_ptr game)
{
    return setup(0, nullptr, game);
}


GameEngine::unique_ptr NullConfiguration::setup(int argc, char* argv[], Game::shared_ptr game)
{
    argc -= 1;
    argv += 1;

    return std::make_unique<sxe::core::GameEngine>
    (
        game
        , make_unique<SettingsMap>(argc, argv)
        , make_unique<NullDisplay>(true)
        , nullptr // scene manager
        , nullptr // input maanger -- TODO NullInputManager
        , nullptr // resource manager
        , nullptr // logging manager
        , nullptr // platform specific settings
        , sxe::core::sys::Platform()
    );
}

} } }

