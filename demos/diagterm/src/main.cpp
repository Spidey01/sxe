/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "GameStub.hpp"

#include <sxe/Game.hpp>
#include <sxe/GameEngine.hpp>
#include <sxe/config/SettingsMap.hpp>
#include <sxe/input/ConsoleInputManager.hpp>
#include <sxe/logging/LoggingManager.hpp>
#include <sxe/resource/ResourceManager.hpp>
#include <sxe/sys/Platform.hpp>
#include <sxe/testing/NullDisplay.hpp>

using std::make_unique;
using sxe::Game;
using sxe::GameEngine;
using sxe::config::SettingsMap;
using sxe::input::ConsoleInputManager;
using sxe::logging::LoggingManager;
using sxe::resource::ResourceManager;
using sxe::sys::Platform;
using sxe::testing::NullDisplay;

static GameEngine::unique_ptr setup(int argc, char* argv[], Game::shared_ptr game)
{
    argc -= 1;
    argv += 1;

    return make_unique<sxe::GameEngine>
    (
        game
        , make_unique<SettingsMap>(argc, argv)
        , make_unique<NullDisplay>(true)
        , nullptr // scene manager
        , make_unique<ConsoleInputManager>()
        , make_unique<ResourceManager>()
        , make_unique<LoggingManager>()
        , nullptr // platform specific settings
        , sxe::sys::Platform()
    );
}


int main(int argc, char* argv[])
{
    auto diagterm = std::make_shared<GameStub>();

    sxe::GameEngine::unique_ptr g = setup(argc, argv, diagterm);

    if (!g) {
        std::cout << argv[0] << ": setup() failed!" << std::endl;
        return 1;
    }

    g->start();
    g->mainLoop();
    g->stop();

    return 0;
}

