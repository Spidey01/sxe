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

package com.spidey01.sxe.android;

import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.Display;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameContext;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.InputManager;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.ResourceManager;

/** Class implementing the game engine for PC/Mac hardware. */
public class AndroidGameEngine extends GameEngine {
    private static final String TAG = "AndroidGameEngine";

    /**
     * @depreciated Replaced by {@link GameContext}.
     */
    public AndroidGameEngine() {
        this(new GameContext()
            .setDisplay(C.getDisplay())
            .setInput(C.getInput())
            .setResources(C.getResources())
            .setGame(C.getGame()));
    }

    public AndroidGameEngine(GameContext context) {
        super(context);
    }

    public boolean start() {
        Log.v(TAG, "AndroidGameEngine.start()");

        super.start();
        return true;
    }
}

