package com.spidey01.sxe.core;

/** Class implementing the game engine for PC/Mac hardware. */
public abstract class GameEngine {
    protected Game mGame;
    protected InputManager mInput;

    /** Start up the game
     *
     * Takes care of initializing the game viewport, etc.  It will call the
     * start() method of your Game accordingly.
     */
    public boolean start() {
        System.out.println("GameEngine.start() called");
        mGame.start(this);
        return true;
    }

    public void stop() {
        mGame.stop();
        System.out.println("GameEngine.stop() done");
    }

    /** Convenience method that can serve as a simple main loop.
     */
    public void mainLoop() {
    }

    public InputManager getInput() {
        return mInput;
    }
}

