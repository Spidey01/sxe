package com.spidey01.sxe.core;

/** Class implementing the game engine for PC/Mac hardware. */
public class GameEngine {
    protected Game mGame;
    protected GameThread mGameThread;
    protected Display mDisplay;
    protected InputManager mInput;
    private static final String TAG = "GameEngine";

    public GameEngine(Display display, InputManager input, Game game) {
        final String p;

        mDisplay = display;
        mInput = input;
        mGame = game;

        // ternary abuse, yeah.
        p = mDisplay == null ?
            "display" :
                (mInput == null ?
                    "input" :
                        (mGame == null ?
                            "game" : null));
 
        if (p != null) {
            throw new IllegalArgumentException(p+" can't be null!");
        }

    }

    /** Start up the game
     *
     * Takes care of initializing the games display, etc.  It will call the
     * start() method of your Game accordingly.
     */
    public boolean start() {
        Log.v(TAG, "start()");

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
        Log.v(TAG, "stop() done");
    }

    /** Convenience method that can serve as a simple main loop.
     *
     * The default implementation throws an UnsupportedOperationException.
     */
    public void mainLoop() {
		while (!mGame.stopRequested() && !mDisplay.isCloseRequested()) {
            mInput.poll();
            mDisplay.update();
		}
    }

    public Display getDisplay() {
        return mDisplay;
    }

    public InputManager getInput() {
        return mInput;
    }

    // Delete me when android stuff updated
    public void debug(final String message) {
        // System.out.println(message+" from thread "+Thread.currentThread().getId());
    }
}

