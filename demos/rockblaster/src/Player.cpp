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

#include "Player.h"

#include <sxe/input/InputManager.hpp>
#include <sxe/logging.hpp>

using std::bind;
using std::make_unique;

namespace demos {

const Player::string_type Player::TAG = "Player";

Player::Player()
{
    Log::xtrace(TAG, "Player()");
}

Player::~Player()
{
    Log::xtrace(TAG, "~Player()");

    Log::v(TAG, "mInputFacet.reset()");
    mInputFacet.reset();
}

bool Player::setupInput(sxe::input::InputManager& controller)
{
    Log::xtrace(TAG, "setupInput()");

    mInputFacet = make_unique<sxe::input::InputFacet>(controller);
    mInputFacet->addKeyListener(InputCode::IC_UP_ARROW, bind(&Player::onUpArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_LEFT_ARROW, bind(&Player::onLeftArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_RIGHT_ARROW, bind(&Player::onRightArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_SPACE, bind(&Player::onSpaceBar, this, std::placeholders::_1));

    return true;
}

bool Player::onUpArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onUpArrow(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "AHEAD, FULL!" << std::endl;

    return true;
}

bool Player::onLeftArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onLeftArrow(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "HARD TO PORT!" << std::endl;

    return true;
}

bool Player::onRightArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onRightArrow(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "HARD TO STARBOARD!" << std::endl;

    return true;
}

bool Player::onSpaceBar(KeyEvent event)
{
    Log::xtrace(TAG, "onSpaceBar(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "PEW, PEW, PEW" << std::endl;

    return true;
}

}
