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

import com.spidey01.sxe.core.GameEngine;

public abstract class Game {

    public enum State {
        STARTING,
        RUNNING,
        STOPPING,
    }

    protected GameEngine mGameEngine;
    protected volatile boolean mStopRequested;
    protected State mState;

    private static final int mMaxTickRate = 250;
    private RateCounter mTickCounter = new RateCounter("Ticks");
    private static final String TAG = "Game";

    public abstract String getName();

    public boolean start(GameEngine engine) {
        Log.v(TAG, "start() called");

        mGameEngine = engine;
        mState = State.STARTING;

        return true;
    }

    public void stop() {
        requestStop();
        Log.v(TAG, "stop() done");
    }

    public boolean stopRequested() {
        return mStopRequested;
    }

    public void requestStop() {
        Log.v(TAG, "requestStop() called");
        mStopRequested = true;
        mState = State.STOPPING;
    }

    public int getMaxFpsRate() {
        return mMaxTickRate;
    }

    /** Maximum tick rate.  */
    public int getMaxTickRate() {
        return mMaxTickRate;
    }

    public int getTickRate() {
        // it's the client codes job to set this more appropriately.
        return mMaxTickRate;
    }

    public void tick() {
        mTickCounter.update();
    }

    public GameEngine getGameEngine() {
        return mGameEngine;
    }
}

