package com.spidey01.sxe.core;

import com.spidey01.sxe.core.GameEngine;

public abstract class Game {

    protected GameEngine mGameEngine;
    protected volatile boolean mStopRequested;

    private static final int mMaxTickRate = 250;
    private RateCounter mTickCounter = new RateCounter("Ticks");
    private static final String TAG = "Game";

    public abstract String getName();

    public boolean start(GameEngine engine) {
        Log.v(TAG, "start() called");

        mGameEngine = engine;

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
}

