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
import com.spidey01.sxe.core.ConsoleCommand;
import com.spidey01.sxe.core.FrameListener;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.KeyEvent;
import com.spidey01.sxe.core.KeyListener;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.OpenGL;

// for testing stuff
import com.spidey01.sxe.core.GpuProgram;
import com.spidey01.sxe.core.Mesh;
import com.spidey01.sxe.core.ResourceManager;
import com.spidey01.sxe.core.Shader;
import com.spidey01.sxe.core.VirtualFileSystemLoader;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;
import com.spidey01.sxe.core.Settings;
import com.spidey01.sxe.core.SettingsFile;

public class SnakeGame
    extends Game
    implements KeyListener, FrameListener
{
    private static final String TAG = "SnakeGame";
    private Console mConsole;
    // for testing
    private Mesh mTriangle;


    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);

        Log.v(TAG, "Snake Game is starting.");

        mConsole = new Console(/* args we may need from mEngine */);
        mGameEngine.getGameContext().getDisplay().addFrameStartedListener(mConsole);
        // mGameEngine.getDisplay().addFrameStartedListener(this);
        setupConsoleCommands();
        setupControls();

        mTriangle = new Mesh(mGameEngine.getGameContext(), new float[]{
              0.0f,  0.8f,
              -0.8f, -0.8f,
              0.8f,  -0.8f,
        });
        mGameEngine.getGameContext().getDisplay().addFrameStartedListener(mTriangle);

        /* old code that's becoming extinct as ResourceManager gets rewritten.
        {
            Log.v(TAG, "testing vfs: ");

            VirtualFileSystemLoader vfs = new VirtualFileSystemLoader(
                mGameEngine.getGameContext().getResources(),
                new String[]{ 
                    "shaders", "debug.zip:", "default:"
                });

            mGameEngine.getGameContext().getResources().setLoader("vfs", vfs);



            Resource foo = mGameEngine.getGameContext().getResources().load("vfs:test/foo.txt");
            Log.v(TAG, "result of load via vfs container:\n"
                +"\n ref "+foo.toString()
                +"\n file name "+foo.getFileName()
                +"\n input stream "+foo.getInputStream()
                +"\n object "+foo.getObject()
                +"\n loader "+foo.getLoader()
            );
            Log.v(TAG, "Done testing vfs; requesting stop.");
        }
        */

        return true;
    }

    @Override
    public void stop() {
        super.stop();
        if (isStopped()) {
            return;
        }

        Log.v(TAG, "Snake Game is stopping.");
        mGameEngine.getGameContext().getDisplay().addFrameStartedListener(this);
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
        mConsole.addCommand(new ConsoleCommand(){
            public void execute() {
                super.execute();
                requestStop();
            }
            public String getName() {
                return "EXIT";
            }
        });
    }

    public void setupControls() {
        /* setup controls, these could just as easily be from a file */

        String[] keys = new String[]{
            "ESCAPE", "BACK",
            "W", "A", "S", "D",
            "P", // used to toggle repeating mode on the console for testing.
        };
        for (String k : keys) {
            mGameEngine.getGameContext().getInput().addKeyListener(k, this);
        }

        // the console will only steal key events when it is visable.
        mGameEngine.getGameContext().getInput().addKeyListener(mConsole);
    }

    // Very simple way of doing some key binds
    public boolean onKey(KeyEvent event) {
        // if (Log.isLoggable(TAG, Log.VERBOSE)) {
            // Log.v(TAG, "onKey() called for event "+event);
        // }

        if (event.isKeyUp()) {

            // quit game
            if ((event.getKeyName().equals("ESCAPE")
                || event.getKeyName().equals("BACK")))
            {
                Log.d(TAG, "Quit");
                requestStop();
                return true;
            }

            if (event.getKeyName().equals("P")) {
                Log.v(TAG, "P command fired by "+TAG);
                mConsole.allowRepeating(!mConsole.repeatingAllowed());
                return true;
            }
        } else {

            // movement keys
            if (event.getKeyName().equals("W")) {
                Log.d(TAG, "Move Up");
                return true;
            }

            if (event.getKeyName().equals("S")) {
                Log.d(TAG, "Move Down");
                return true;
            }
            
            if (event.getKeyName().equals("A")) {
                Log.d(TAG, "Move Left");
                return true;
            }
            
            if (event.getKeyName().equals("D")) {
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

