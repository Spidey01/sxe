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

#include <sxe/GameEngine.hpp>
#include <sxe/cmds/EchoCommand.hpp>
#include <sxe/cmds/QuitCommand.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/logging.hpp>

using std::string;
using sxe::Game;
using sxe::GameEngine;

const std::string GameStub::TAG = "GameStub";


string GameStub::getName() const
{
    return "diagterm";
}


bool GameStub::start()
{
    if (!Game::start())
        return false;

    mConsole.setInputManager(&(getGameEngine().getInputManager()));

    // Setup our commands.
    {
        using namespace sxe::cmds;
        using std::make_shared;

        mConsole.addCommand(make_shared<QuitCommand>(getGameEngine().getGame()));

        mConsole.addCommand(make_shared<EchoCommand>());
    }

    mConsole.setVisible(true);

    return mConsole.isVisible();
}


void GameStub::stop()
{
    mConsole.setVisible(false);
    Game::stop();
}

