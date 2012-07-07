package com.spidey01.sxe.android;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;

/** Class implementing the game engine for PC/Mac hardware. */
public class AndroidGameEngine extends GameEngine {


    public AndroidGameEngine(Game app) {
        super();

        mGame = app;
        if (mGame == null) {
            throw new IllegalArgumentException("app can't be null!");
        }
    }

    @Override
    public boolean start() {
        // System.out.println("AndroidGameEngine.start() in thread "+Thread.currentThread().getId());

        // mInput = new AndroidInputManager();

        super.start();

        return true;
    }

    @Override
    public void stop() {
        super.stop();

        // System.out.println("AndroidGameEngine.stop() done in thread "+Thread.currentThread().getId());
    }

    // public void mainLoop() {
    // }

}

