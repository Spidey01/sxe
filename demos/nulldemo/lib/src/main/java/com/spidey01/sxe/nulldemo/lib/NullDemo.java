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

package com.spidey01.sxe.nulldemo.lib;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;

import com.spidey01.sxe.core.*;
import com.spidey01.sxe.core.gl.*;
import com.spidey01.sxe.core.graphics.*;

import java.io.IOException;

/** Null demo.
 *
 * TODO: Hook this to a textual console for I/O
 */
public class NullDemo
    extends Game
    implements KeyListener
{
    private static final String TAG = "NullDemo";


    @Override
    public String getName() {
        return TAG;
    }


    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);

        Log.v(TAG, "Null demo is starting.");

        mGameEngine.getInputManager().addKeyListener("Q", this);

        return true;
    }


    @Override
    public void stop() {
        super.stop();
        if (isStopped()) {
            return;
        }

        Log.v(TAG, "Null demo is stopping.");
    }


    @Override
    public void tick() {
        Log.xtrace(TAG, "tick()");
    }


    @Override
    public boolean onKey(KeyEvent event) {
        if (event.isKeyUp()) {
            if (event.getKeyName().equals("Q")) {
                Log.d(TAG, "Q key released");
                requestStop();
                return true;
            }
        }
        return false;
    }
}

