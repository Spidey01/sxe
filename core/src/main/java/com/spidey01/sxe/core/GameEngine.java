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

package com.spidey01.sxe.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/** Class implementing the game engine for PC/Mac hardware. */
public class GameEngine {

    protected GameContext mCtx;
    protected GameThread mGameThread;
    private static final String TAG = "GameEngine";

    /** Initializes itself from configuration stored in com.spidey01.sxe.core.C.
     *
     * The following fields in C are manditory:
     *
     *      - display
     *      - game
     *      - input
     *      - resources
     *
     * @see com.spidey01.sxe.pc.PcConfiguration
     * @see com.spidey01.sxe.android.AndroidConfiguration
     * @depreciated Replaced by {@link GameContext}.
     */
    public GameEngine() {
        this(new GameContext()
            .setDisplay(C.getDisplay())
            .setInput(C.getInput())
            .setResources(C.getResources())
            .setGame(C.getGame()));
    }

    /** Initializes the engine for use.
     *
     * If you have little interest in providing more than "Game" yourself. You
     * can use your platforms configuration class to setup
     * com.spidey01.sxe.core.C, or just fill out the documented fields as
     * necessary.
     */
    public GameEngine(GameContext context) {
        mCtx = context;

        // ternary abuse, yeah.
        final String p;
        p = mCtx.getDisplay() == null ?
            "display" :
                (mCtx.getInput() == null ?
                    "input" : null);
 
        if (p != null) {
            throw new IllegalArgumentException(p+" can't be null!");
        }
    }

    /** Start up the game
     *
     * Takes care of initializing the games display, etc.  It will call the
     * start() method of your Game accordingly.
     */
    public boolean start() {
        Log.v(TAG, "start()");

        if (!mCtx.getDisplay().create()) {
            return false;
        }

        mGameThread = new GameThread(this, mCtx.getGame());
        mGameThread.start();

        return true;
    }

    /** Stop the game
     *
     * Will ensure Game.stop() is called. Shuts down the display, etc.
     */
    public void stop() {
        mCtx.getGame().stop();
        mGameThread.interrupt(); // should this be overriden to do Game.stop()?
		mCtx.getDisplay().destroy();
        Log.v(TAG, "stop() done");
    }

    /** Convenience method that can serve as a simple main loop.
     *
     * The default implementation throws an UnsupportedOperationException.
     */
    public void mainLoop() {
		while (!mCtx.getGame().isStopRequested() && !mCtx.getDisplay().isCloseRequested()) {
            mCtx.getInput().poll();
            mCtx.getDisplay().update();
		}
    }


    public GameContext getGameContext() {
        return mCtx;
    }


    /**
     * @depreciated Use {@link #getGameContext()} instead.
     */
    public Display getDisplay() {
        return mCtx.getDisplay();
    }

    /**
     * @depreciated Use {@link #getGameContext()} instead.
     */
    public InputManager getInput() {
        return mCtx.getInput();
    }

    /**
     * @depreciated Use {@link #getGameContext()} instead.
     */
    public ResourceManager getResources() {
        return mCtx.getResources();
    }
}

