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


#include "QuadGame.h"

#include <sxe/GameEngine.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/input/InputFacet.hpp>
#include <sxe/logging.hpp>
#include <sxe/scene/SceneManager.hpp>

#include "Quad.h"

using sxe::input::InputCode;
using sxe::input::KeyListener;
using sxe::input::KeyEvent;

namespace demos {

QuadGame::string_type QuadGame::TAG = "QuadGame";

QuadGame::string_type QuadGame::getName() const
{
    return TAG;
}

bool QuadGame::start()
{
    if (!sxe::Game::start())
        return false;

    mQuad = std::make_shared<Quad>(getGameEngine());

    /* Add our demo Quad to the scene. */
    getGameEngine().getSceneManager().addEntity(mQuad);

    /* Bind ourself to handle the 'Q' key press. */
    KeyListener listener = std::bind(&QuadGame::onKey, this, std::placeholders::_1);
    getInputFacet().addKeyListener(InputCode::IC_Q, listener);

    return true;
}


void QuadGame::stop()
{
    mQuad.reset();

    sxe::Game::stop();
}


bool QuadGame::onKey(KeyEvent event)
{
    if (event.isKeyUp())
        Log::d(TAG, event.toString());

    if (event.getKeyCode() == InputCode::IC_Q) {
        requestStop();
        return true;
    }
    return false;
}

}

