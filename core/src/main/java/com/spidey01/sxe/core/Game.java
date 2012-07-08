package com.spidey01.sxe.core;

import com.spidey01.sxe.core.GameEngine;

public class Game {

    protected GameEngine ge;
    protected volatile boolean mStopRequested;

    public boolean start(GameEngine ge) {
        ge.debug("Game.start() called in thread "+Thread.currentThread().getId());

        this.ge = ge;

        return true;
    }

    public void stop() {
        requestStop();
        ge.debug("Game.stop() called in thread "+Thread.currentThread().getId());
    }

    public boolean stopRequested() {
        return mStopRequested;
    }

    public void requestStop() {
        ge.debug("Game.requestStop() called in thread "+Thread.currentThread().getId());
        mStopRequested = true;
    }

    public void tick() {
        try {
            // disable this for now on Android
            // ge.debug("Game.tick() called in thread "+Thread.currentThread().getId());
            Thread.currentThread().sleep(250);
        } catch (InterruptedException iex) {
            ge.debug("Game.tick() interrupted in thread "+Thread.currentThread().getId());
            stop();
        }
    }
}

