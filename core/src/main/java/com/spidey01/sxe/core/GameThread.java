package com.spidey01.sxe.core;

/** Class implementing the game engine for PC/Mac hardware. */
public class GameThread extends Thread {
    protected GameEngine mEngine;
    protected Game mGame;

    public GameThread(GameEngine e, Game g) {
        mEngine = e;
        mGame = g;
    }

    public void run() {
        mGame.start(mEngine);
        while (!mGame.stopRequested()) {
            mGame.tick();
        }
    }
}

