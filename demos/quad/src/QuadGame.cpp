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

#include <sxe/core/GameEngine.hpp>
#include <sxe/logging.hpp>

namespace demos {

std::string QuadGame::TAG = "QuadGame";

std::string QuadGame::getName() const
{
    return TAG;
}

bool QuadGame::start(sxe::core::GameEngine* engine)
{
    sxe::core::Game::start(engine);

    #if 0 // 1.x
    mQuad = new  Quad(mGameEngine);

    /* Add our demo Quad to the scene. */
    mGameEngine.getSceneManager().add(mQuad.getGraphicsFacet());

    /* Bind ourself to handle the 'Q' key press. */
    mGameEngine.getInputManager().addKeyListener(InputCode.IC_Q, this);
    #endif

    return true;
}


#if 0 // 1.x
    @Override
    public boolean onKey(KeyEvent event) {
        if (event.getKeyCode().equals(InputCode.IC_Q)) {
            requestStop();
            return true;
        }
        return false;
    }
#endif

}

