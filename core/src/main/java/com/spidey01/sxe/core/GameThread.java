package com.spidey01.sxe.core;

/** Class implementing the game engine for PC/Mac hardware. */
public class GameThread extends Thread {
    protected GameEngine mEngine;
    protected Game mGame;
    private static final String TAG = "GameThread";

    public GameThread(GameEngine e, Game g) {
        mEngine = e;
        mGame = g;
    }

    public void run() {

        mGame.start(mEngine);

        while (!mGame.stopRequested()) {
            long startTime = System.currentTimeMillis();
            // Trap the exception so that we can shutdown. Maybe do same at
            // game start, or move this into interrupt()?
            try {
                mGame.tick();
            } catch(Exception e) {
                Log.wtf(TAG, "Game thread has died!", e);
                mEngine.stop();
            }
            long endTime = System.currentTimeMillis() - startTime;
            long sleepTime = (1000L / mGame.getTickRate()) - endTime;
            if (sleepTime <= 0) {
                // clip to the max tick rate
                sleepTime = 1000L / mGame.getMaxTickRate();
            }

            try {
                // Log.d("one tick took "+endTime+" ms"+" and sleep for "+sleepTime+" ms");
                Thread.currentThread().sleep(sleepTime);
            } catch (InterruptedException iex) {
                Log.d(TAG, "run() interrupted");
                stop();
            }
        }
    }
}

