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
import com.spidey01.sxe.core.input.InputCode;
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

    private static final Player mPlayer = new Player();

    @Override
    public String getName() {
        return TAG;
    }


    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);
        mState = State.STARTING;

        Log.v(TAG, "Ping Pong Game is starting.");

        /*
         * Build the key we want to use for quitting the game.
         */
        mGameEngine.getInputManager().addKeyListener(InputCode.IC_Q, this);

        /*
         * Display a helpful start message.
         */
        mGameEngine.getDisplay().addFrameStartedListener(sStartMessage);

        /*
         * We want Player to listen for these keys and bind them to the desired actions.
         * E.g.:
         *  - W, A, H, K, LEFT, and UP = move up the screen.
         *  - S, D, L, J, RIGHT, and DOWN = move down the screen.
         */
        InputCode[] upKeys = new InputCode[]{
            InputCode.IC_W,
            InputCode.IC_A,
            InputCode.IC_H,
            InputCode.IC_K,
            InputCode.IC_LEFT_ARROW,
            InputCode.IC_UP_ARROW,
        };
        InputCode[] downKeys = new InputCode[]{
            InputCode.IC_S,
            InputCode.IC_D,
            InputCode.IC_L,
            InputCode.IC_J,
            InputCode.IC_RIGHT_ARROW,
            InputCode.IC_DOWN_ARROW,
        };

        mPlayer.setInputManager(mGameEngine.getInputManager());
        mPlayer.bindAction(new MoveUpAction(), upKeys);
        mPlayer.bindAction(new MoveDownAction(), downKeys);

        return true;
    }


    @Override
    public boolean onKey(KeyEvent event) {
        Log.xtrace(TAG, "onKey(", event, ")");

        /* Quit game. */
        if (event.getKeyCode().equals(InputCode.IC_Q)) {
            requestStop();
            return true;
        }

        /* Start game. */
        if (mState == State.STARTING && event.getKeyCode().equals(InputCode.IC_S)) {
            /*
             * Remove start message so it's out of the game screen.
             */
            mGameEngine.getDisplay().removeFrameStartedListener(sStartMessage);
            mState = State.RUNNING;
            return true;
        }

        return false;
    }

}

