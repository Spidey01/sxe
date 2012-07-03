package com.spidey01.sxe.core;

import com.spidey01.sxe.core.GameEngine;

public class Game {

    protected GameEngine ge;
    protected volatile boolean mStopRequested;

    public boolean start(GameEngine ge) {
        System.out.println("Game.start() called in thread "+Thread.currentThread().getId());

        this.ge = ge;

        return true;
    }

    public void stop() {
        requestStop();
        System.out.println("Game.stop() called in thread "+Thread.currentThread().getId());
    }

    public boolean stopRequested() {
        return mStopRequested;
    }

    public void requestStop() {
        System.out.println("Game.requestStop() called in thread "+Thread.currentThread().getId());
        mStopRequested = true;
    }

    public void tick() {
        try {
            System.out.println("Game.tick() called in thread "+Thread.currentThread().getId());
            Thread.currentThread().sleep(250);
        } catch (InterruptedException iex) {
            System.out.println("Game.tick() interrupted in thread "+Thread.currentThread().getId());
            stop();
        }
    }
}

