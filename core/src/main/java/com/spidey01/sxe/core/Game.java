package com.spidey01.sxe.core;

import com.spidey01.sxe.core.GameEngine;

import java.util.Random;

public class Game {

    protected GameEngine ge;
    protected volatile boolean mStopRequested;

    private static final int mMaxTickRate = 250;
    private RateCounter mTickCounter = new RateCounter("Ticks");
    private static final String TAG = "Game";

    public boolean start(GameEngine ge) {
        Log.v(TAG, "start() called");

        this.ge = ge;

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

    private static Random rand = new Random();
    public void tick() {
        mTickCounter.update();

        try {
            // simulate taking a while to complete a tick.
            Thread.currentThread().sleep(rand.nextInt(25));
        } catch (InterruptedException iex) {
            Log.d(TAG, "tick() interrupted");
            stop();
        }

    }
}

