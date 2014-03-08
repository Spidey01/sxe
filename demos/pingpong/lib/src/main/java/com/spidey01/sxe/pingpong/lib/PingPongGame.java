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

package com.spidey01.sxe.pingpong.lib;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.Text;
import com.spidey01.sxe.core.input.InputManager;
import com.spidey01.sxe.core.input.KeyEvent;
import com.spidey01.sxe.core.input.KeyListener;

/** A simple game demo inspired by Pong.
 */
public class PingPongGame
    extends Game
    implements KeyListener
{
    private static final String TAG = "PingPongGame";

    private static final String START_MESSAGE = "Press S key or tap to start game.";
    private static final Text sStartMessage = new Text(START_MESSAGE);


    @Override
    public String getName() {
        return TAG;
    }


    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);
        mState = State.STARTING;

        Log.v(TAG, "Ping Pong Game is starting.");

        InputManager im = mGameEngine.getInputManager();
        im.addKeyListener("Q", this); /* quit key */
        im.addKeyListener("S", this); /* start and movement key */
        im.addKeyListener("W", this); /* movement key */
        im.addKeyListener("A", this); /* movement key */
        im.addKeyListener("D", this); /* movement key */
        mGameEngine.getDisplay().addFrameStartedListener(sStartMessage);

        return true;
    }


    @Override
    public void stop() {
        super.stop();
        if (isStopped()) {
            return;
        }

        Log.v(TAG, "Ping Pong Game is stopping.");
    }


    @Override
    public void tick() {
        Log.xtrace(TAG, "mState =", mState);
        switch (mState) {
            case STARTING: {
            } break;
            case RUNNING: {
            } break;
            case STOPPING: {
            } break;
        }
    }


    @Override
    public boolean onKey(KeyEvent event) {
        /* Quit game. */
        if (event.getKeyName().equals("Q")) {
            requestStop();
            return true;
        }

        /* Start game. */
        if (mState == State.STARTING && event.getKeyName().equals("S")) {
            mGameEngine.getDisplay().removeFrameStartedListener(sStartMessage);
            mState = State.RUNNING;
            return true;
        }

        /* Don't bind keys until started. */
        if (mState != State.RUNNING) {
            return false;
        }

        /* Move UP. */
        if (event.getKeyName().equals("W") || event.getKeyName().equals("A")) {
            Log.xtrace(TAG, "Move up");
            return true;
        }

        /* Move DOWN. */
        if (event.getKeyName().equals("S") || event.getKeyName().equals("D")) {
            Log.xtrace(TAG, "Move down");
            return true;
        }

        return false;
    }

}

