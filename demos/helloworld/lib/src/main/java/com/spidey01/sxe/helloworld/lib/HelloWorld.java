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

package com.spidey01.sxe.helloworld.lib;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.Text;
import com.spidey01.sxe.core.input.InputCode;
import com.spidey01.sxe.core.input.KeyEvent;
import com.spidey01.sxe.core.input.KeyListener;

/** Hello World demo.
 */
public class HelloWorld
    extends Game
    implements KeyListener
{
    private static final String TAG = "HelloWorld";


    /** Return the name of this game. */
    @Override
    public String getName() {
        return TAG;
    }


    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);

        /* Display "HelloWorld" on screen. */
        Text message = new Text(TAG);
        mGameEngine.getDisplay().addFrameStartedListener(message);

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

