/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

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

