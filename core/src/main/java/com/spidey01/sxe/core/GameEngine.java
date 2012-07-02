package com.spidey01.sxe.core;

/** Class implementing the game engine for PC/Mac hardware. */
public abstract class GameEngine {
    protected Game mApp;
    protected InputManager mInput;

    /** Start up the game
     *
     * Takes care of initializing the game viewport, etc.  It will call the
     * start() method of your Game accordingly.
     */
    public boolean start() {
        System.out.println("GameEngine.start() called");
        mApp.start(this);
        return true;
    }

    public void stop() {
        System.out.println("GameEngine.stop() called");

        mApp.stop();
    }

    /** Convenience method that can serve as a simple main loop.
     */
    public void mainLoop() {
    }

    public InputManager getInput() {
        return mInput;
    }
}

