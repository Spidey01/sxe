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
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.InputManager;
import com.spidey01.sxe.core.ResourceManager;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/** Class implementing the game engine for PC/Mac hardware. */
public class AndroidGameEngine extends GameEngine {
    private static final String TAG = "AndroidGameEngine";

    public AndroidGameEngine() {
        this(C.getDisplay(), C.getInput(), C.getResources(), C.getGame());
    }

    public AndroidGameEngine(Display display, InputManager input,
            ResourceManager res, Game game)
    {
        super(display, input, res, game);
    }

    public boolean start() {
        debug("AndroidGameEngine.start()");

        super.start();
        return true;
    }

    /*
     * Currently a simple toast is used as a substitute for println().
     *
     * We also need to monkey with mDisplay in order to run this handler on the
     * UI thread because Android doesn't like worker threads accessing the UI
     * (thread safty ftw?). Otherwise we've the choice: crash when clients call
     * this from e.g. the Game Thread or slow down testing for logcat's.
     */
    public void debug(final String message) {
        final AndroidDisplay d = (AndroidDisplay)getDisplay();

        d.post(new Runnable() {
            public void run() {
                Context context = d.getContext();
                CharSequence m = message+" from thread "+Thread.currentThread().getId();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, m, duration);
                toast.show();
            }
        });
    }
}

