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

package com.spidey01.sxe.snake.lib;

import com.spidey01.sxe.core.Console;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.cmds.Command;
import com.spidey01.sxe.core.cmds.QuitCommand;
import com.spidey01.sxe.core.gl.OpenGL;
import com.spidey01.sxe.core.graphics.FrameListener;
import com.spidey01.sxe.core.graphics.Mesh;
import com.spidey01.sxe.core.graphics.Sprite;
import com.spidey01.sxe.core.graphics.VertexBufferTechnique;
import com.spidey01.sxe.core.input.InputCode;
import com.spidey01.sxe.core.input.KeyEvent;
import com.spidey01.sxe.core.input.KeyListener;
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.resource.ResourceManager;

import java.util.Random;


public class SnakeGame
    extends Game
    implements KeyListener, FrameListener
{
    private static final String TAG = "SnakeGame";
    private Console mConsole;
    // for testing
    private Sprite mTriangle;


    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);

        Log.v(TAG, "Snake Game is starting.");

        mConsole = new Console(/* args we may need from mEngine */);
        mGameEngine.getDisplay().addFrameStartedListener(mConsole);

        setupConsoleCommands();
        setupControls();

        mTriangle = new Sprite(
                new Mesh(new float[]{
                    0.0f,  0.8f,
                    -0.8f, -0.8f,
                    0.8f,  -0.8f,
                }),
                null /* unused for now */
        );


        mTriangle.setTechnique(new VertexBufferTechnique(engine.getDisplay().getOpenGL()));
        mGameEngine.getSceneManager().add(mTriangle);


        return true;
    }

    @Override
    public void stop() {
        super.stop();
        if (isStopped()) {
            return;
        }

        Log.v(TAG, "Snake Game is stopping.");
        mGameEngine.getDisplay().addFrameStartedListener(this);
    }

    @Override
    public int getTickRate() {
        return 40; // ticks per second
    }

    private static Random rand = new Random();
    @Override
    public void tick() {
        super.tick();

        try {
            // simulate taking a while to complete a tick.
            Thread.currentThread().sleep(rand.nextInt(25));
        } catch (InterruptedException iex) {
            Log.d(TAG, "tick() interrupted");
            stop();
        }
    }


    public void setupConsoleCommands() {
        mConsole.addCommand(new QuitCommand(mGameEngine));
    }

    public void setupControls() {
        /* setup controls, these could just as easily be from a file */

        InputCode[] keys = new InputCode[]{
            InputCode.IC_ESCAPE, InputCode.IC_BACK,
            InputCode.IC_W, InputCode.IC_A, InputCode.IC_S, InputCode.IC_D,
            InputCode.IC_P, // used to toggle repeating mode on the console for testing.
        };
        for (InputCode c : keys) {
            mGameEngine.getInputManager().addKeyListener(c, this);
        }

        // the console will only steal key events when it is visable.
        mGameEngine.getInputManager().addKeyListener(mConsole);
    }

    // Very simple way of doing some key binds
    public boolean onKey(KeyEvent event) {
        // if (Log.isLoggable(TAG, Log.VERBOSE)) {
            // Log.v(TAG, "onKey() called for event "+event);
        // }

        if (event.isKeyUp()) {

            // quit game
            if ((event.getKeyCode().equals(InputCode.IC_ESCAPE)
                || event.getKeyCode().equals(InputCode.IC_Q)
                || event.getKeyCode().equals(InputCode.IC_BACK)))
            {
                Log.d(TAG, "Quit");
                requestStop();
                return true;
            }

            if (event.getKeyCode().equals(InputCode.IC_P)) {
                Log.v(TAG, "P command fired by "+TAG);
                mConsole.allowRepeating(!mConsole.repeatingAllowed());
                return true;
            }
        } else {

            // movement keys
            if (event.getKeyCode().equals(InputCode.IC_W)) {
                Log.d(TAG, "Move Up");
                return true;
            }

            if (event.getKeyCode().equals(InputCode.IC_S)) {
                Log.d(TAG, "Move Down");
                return true;
            }
            
            if (event.getKeyCode().equals(InputCode.IC_A)) {
                Log.d(TAG, "Move Left");
                return true;
            }
            
            if (event.getKeyCode().equals(InputCode.IC_D)) {
                Log.d(TAG, "Move Right");
                return true;
            }
        }

        return false;
    }

    public void frameStarted(OpenGL GL20) {
    }

    public void frameEnded() {
    }
}

