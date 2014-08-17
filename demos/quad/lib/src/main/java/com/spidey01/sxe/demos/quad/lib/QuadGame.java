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

package com.spidey01.sxe.demos.quad.lib;


import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.input.KeyListener;
import com.spidey01.sxe.core.input.KeyEvent;
import com.spidey01.sxe.core.input.InputCode;
import com.spidey01.sxe.core.logging.Log;

import java.io.IOException;


/** Quad demo.
 *
 * Draw a simple coloured quad to the screen until the user exists.
 * Hit the Q key to exit.
 *
 * This serves as a demo of how to implement something like this in SxE.
 */
public class QuadGame
    extends Game
    implements KeyListener
{
    private static final String TAG = "QuadGame";

    private Quad mQuad;

    /** Return the name of this game. */
    @Override
    public String getName() {
        return TAG;
    }


    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);

        mQuad = new  Quad(mGameEngine);

        /* Add our demo Quad to the scene. */
        mGameEngine.getSceneManager().add(mQuad);

        /* Bind ourself to handle the 'Q' key press. */
        mGameEngine.getInputManager().addKeyListener(InputCode.IC_Q, this);
        return true;
    }


    @Override
    public boolean onKey(KeyEvent event) {
        if (event.getKeyCode().equals(InputCode.IC_Q)) {
            requestStop();
            return true;
        }
        return false;
    }

}

