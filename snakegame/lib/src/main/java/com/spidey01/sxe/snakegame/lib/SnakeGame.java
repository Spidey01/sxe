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

package com.spidey01.sxe.snakegame.lib;

import com.spidey01.sxe.core.Console;
import com.spidey01.sxe.core.ConsoleCommand;
import com.spidey01.sxe.core.FrameListener;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.KeyEvent;
import com.spidey01.sxe.core.KeyListener;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.OpenGl;

// for testing stuff
import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.GlslProgram;
import com.spidey01.sxe.core.GlslShader;
import com.spidey01.sxe.core.GpuProgram;
import com.spidey01.sxe.core.Resource;
import com.spidey01.sxe.core.ResourceManager;
import com.spidey01.sxe.core.Shader;
import com.spidey01.sxe.core.ShaderFactory;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

public class SnakeGame
    extends Game
    implements KeyListener, FrameListener
{
    private static final String TAG = "SnakeGame";
    private Console mConsole;


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
        mGameEngine.getDisplay().addFrameStartedListener(this);
        setupConsoleCommands();
        setupControls();

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
            mGameEngine.getInput().addKeyListener(k, this);
        }

        // the console will only steal key events when it is visable.
        mGameEngine.getInput().addKeyListener(mConsole);
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

    public void frameStarted(OpenGl GL20) {
        OpenGl gl = getGameEngine().getDisplay().getOpenGl();
        switch (mState) {
            case STARTING:
                if (!mTestLoaded) {
                    loadTest(gl);
                    mState = State.RUNNING;
                }
                break;
            case RUNNING:
                drawTest(gl);
                break;
            case STOPPING:
                unloadTest(gl);
                break;
        }
    }

    public void frameEnded() {
    }

///////////////////////////////////////////////////////////////////////////////
// Testing stuff
///////////////////////////////////////////////////////////////////////////////

/* triangle.vert:

// shader for the Triangle rendering test code in SnakeGame
attribute vec2 coord2d;
void main(void) {
  gl_Position = vec4(coord2d, 0.0, 1.0);
}
 */
/* triangle.frag:

// shader for the Triangle rendering test code in SnakeGame
void main(void) {
  gl_FragColor[0] = 0.0;
  gl_FragColor[1] = 1.0;
  gl_FragColor[2] = 0.0;
}
 */

    private boolean mTestLoaded = false;
    private int mCoord2d = -1;
    private GpuProgram mProgram;
    private static IntBuffer mVBO;;

    private void loadTest(final OpenGl GL20) {
        mTestLoaded = true;

        // Setup shaders.
        GlslShader vert = null;
        GlslShader frag = null;
        try {
            ShaderFactory<GlslShader> factory = new ShaderFactory<GlslShader>(){
                public Shader make(Shader.Type type, InputStream is, final String path) {
                    return new GlslShader(GL20, type, is, path);
                }
            };
            vert = (GlslShader)C.getResources().load("shaders/triangle.vert", factory).getObject();
            frag = (GlslShader)C.getResources().load("shaders/triangle.frag", factory).getObject();
        } catch(Exception fml) {
            Log.wtf(TAG, "Failed loading shaders", fml);
            fml.printStackTrace();
        }

        // Setup shader program.
        mProgram = new GlslProgram(GL20);
        mProgram.addShader(vert);
        mProgram.addShader(frag);
        if (!mProgram.link() || !mProgram.validate()) {
            Log.wtf(TAG, "Shader failure: "+mProgram.getInfoLog());
        }

        // Setup vertex data to feed into a VBO.
        float[] triangleVertices = {
            0.0f,  0.8f,
            -0.8f, -0.8f,
            0.8f,  -0.8f,
        };
        FloatBuffer buffer = GL20.createFloatBuffer(triangleVertices.length);
        buffer.put(triangleVertices);
        buffer.flip();

        // Generate an ID for our VBO in the video memory and bind it.
        mVBO = GL20.createIntBuffer(1);
        GL20.glGenBuffers(mVBO);
        GL20.glBindBuffer(OpenGl.GL_ARRAY_BUFFER, mVBO.get(0));

        // Buffer it to the GPU.
        GL20.glBufferData(OpenGl.GL_ARRAY_BUFFER, buffer, OpenGl.GL_STATIC_DRAW);

        // Create coord2d attribute for our fragment shader.
        mCoord2d = GL20.glGetAttribLocation(mProgram.getProgram(), "coord2d");
        if (mCoord2d == -1) {
            Log.wtf(TAG, "Couldn't bind coord2d attribute!");
        }
    }
    private void drawTest(OpenGl GL20) {
        // Clear the background.
        GL20.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        GL20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Ready the shader program.
        GL20.glUseProgram(mProgram.getProgram());

        // Lock and load the coord2d attribute for our fragment shader.
        GL20.glEnableVertexAttribArray(mCoord2d);
        GL20.glVertexAttribPointer(mCoord2d, 2, GL20.GL_FLOAT, false, 0, 0);
 
        // feed it to our shader to draw.
        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, mVBO.get(0));
        GL20.glDrawArrays(GL20.GL_TRIANGLES, 0, 3);

        GL20.glDisableVertexAttribArray(mCoord2d);
    }
    private void unloadTest(OpenGl GL20) {
        // TODO
    }
}

