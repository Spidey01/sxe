package com.spidey01.sxe.core;

import com.spidey01.sxe.core.GameEngine;

public class Game {

    protected GameEngine ge;
    protected boolean mStopRequested;

    public boolean start(GameEngine ge) {
        System.out.println("Game.start() called in thread "+Thread.currentThread().getId());

        this.ge = ge;

        return true;
    }

    public void stop() {
        System.out.println("Game.stop() called in thread "+Thread.currentThread().getId());
    }

    public boolean stopRequested() {
        return mStopRequested;
    }

    public void requestStop() {
        mStopRequested = true;
    }
}

