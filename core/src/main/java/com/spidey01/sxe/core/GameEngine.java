package com.spidey01.sxe.core;

/** Class implementing the game engine for PC/Mac hardware. */
public class GameEngine {
    protected Game mGame;
    protected GameThread mGameThread;
    protected Display mDisplay;
    protected InputManager mInput;

    public GameEngine(Display display, Game app) {

        mGame = app;
        if (mGame == null) {
            throw new IllegalArgumentException("app can't be null!");
        }

        mDisplay = display;
        if (mDisplay == null) {
            throw new IllegalArgumentException("display can't be null!");
        }

    }

    /** Start up the game
     *
     * Takes care of initializing the games display, etc.  It will call the
     * start() method of your Game accordingly.
     */
    public boolean start() {
        System.out.println("GameEngine.start() in thread "+Thread.currentThread().getId());

        if (!mDisplay.create()) {
            return false;
        }

        mGameThread = new GameThread(this, mGame);
        mGameThread.start();

        return true;
    }

    /** Stop the game
     *
     * Will ensure Game.stop() is called. Shuts down the display, etc.
     */
    public void stop() {
        // mGame.stop();
        mGameThread.interrupt();
		mDisplay.destroy();
        System.out.println("GameEngine.stop() done in thread "+Thread.currentThread().getId());
    }

    /** Convenience method that can serve as a simple main loop.
     *
     * The default implementation throws an UnsupportedOperationException.
     */
    public void mainLoop() {
        throw new UnsupportedOperationException("GameEngine.mainLoop() doesn't  have a default implementation. Use a platform specific one.");
    }

    public InputManager getInput() {
        return mInput;
    }

    public void debug(String message) {
        System.out.println(message+" from thread "+Thread.currentThread().getId());
    }
}

