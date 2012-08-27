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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/** Class implementing the game engine for PC/Mac hardware. */
public class GameEngine {

    protected GameContext mCtx;
    protected GameThread mGameThread;
    private static final String TAG = "GameEngine";

    /** Initializes itself from configuration stored in com.spidey01.sxe.core.C.
     *
     * The following fields in C are manditory:
     *
     *      - display
     *      - game
     *      - input
     *      - resources
     *
     * @see com.spidey01.sxe.pc.PcConfiguration
     * @see com.spidey01.sxe.android.AndroidConfiguration
     * @depreciated Replaced by {@link GameContext}.
     */
    public GameEngine() {
        this(new GameContext()
            .setDisplay(C.getDisplay())
            .setInput(C.getInput())
            .setResources(C.getResources())
            .setGame(C.getGame()));
    }

    /** Initializes the engine for use.
     *
     * If you have little interest in providing more than "Game" yourself. You
     * can use your platforms configuration class to setup
     * com.spidey01.sxe.core.C, or just fill out the documented fields as
     * necessary.
     */
    public GameEngine(GameContext context) {
        mCtx = context;

        setupLogging();

        Log.i(TAG,
            "platform=\""+mCtx.getPlatform()+"\""
            + " version=\""+mCtx.getPlatformVersion()+"\""
            + " arch=\""+mCtx.getPlatformArch()+"\"");

        // ternary abuse, yeah.
        final String p;
        p = mCtx.getDisplay() == null ?
            "display" :
                (mCtx.getInput() == null ?
                    "input" : null);
 
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

        Log.i(TAG, "XDG_DATA_HOME=\""+System.getenv("XDG_DATA_HOME")+"\"");
        Log.i(TAG, "XDG_CONFIG_HOME=\""+System.getenv("XDG_CONFIG_HOME")+"\"");
        Log.i(TAG, "XDG_CACHE_DIR=\""+System.getenv("XDG_CACHE_DIR")+"\"");
        Log.i(TAG, "XDG_RUNTIME_DIR=\""+System.getenv("XDG_RUNTIME_DIR")+"\"");
        Log.i(TAG, "XDG_DATA_DIRS=\""+System.getenv("XDG_DATA_DIRS")+"\"");
        Log.i(TAG, "XDG_CONFIG_DIRS=\""+System.getenv("XDG_CONFIG_DIRS")+"\"");

        if (!mCtx.getDisplay().create()) {
            return false;
        }

        mGameThread = new GameThread(this, mCtx.getGame());
        mGameThread.start();

        return true;
    }

    /** Stop the game
     *
     * Will ensure Game.stop() is called. Shuts down the display, etc.
     */
    public void stop() {
        mCtx.getGame().stop();
        mGameThread.interrupt(); // should this be overriden to do Game.stop()?
		mCtx.getDisplay().destroy();
        Log.v(TAG, "stop() done");
    }

    /** Convenience method that can serve as a simple main loop.
     */
    public void mainLoop() {
		while (!mCtx.getGame().isStopRequested() && !mCtx.getDisplay().isCloseRequested()) {
            mCtx.getInput().poll();
            mCtx.getDisplay().update();
		}
    }


    public GameContext getGameContext() {
        return mCtx;
    }


    /**
     * @depreciated Use {@link #getGameContext()} instead.
     */
    public Display getDisplay() {
        return mCtx.getDisplay();
    }

    /**
     * @depreciated Use {@link #getGameContext()} instead.
     */
    public InputManager getInput() {
        return mCtx.getInput();
    }

    /**
     * @depreciated Use {@link #getGameContext()} instead.
     */
    public ResourceManager getResources() {
        return mCtx.getResources();
    }

    private void setupLogging() {
        Settings s = mCtx.getSettings();

        if (s.getBoolean("debug")) {
            // Make sure that we have a log file.
            if (!s.contains("debug.log_level"))
                s.setInt("debug.log_level", Log.VERBOSE);
            if (!s.contains("debug.log_type"))
                s.setString("debug.log_type", "file");
            if (!s.contains("debug.log_file"))
                s.setString("debug.log_file", "debug.log");
        }

        // Setup log sinks for every matching config tree.
        for (String key : s.keys()) {
            int i = key.lastIndexOf(".log_type");
            if (i != -1) {
                makeLogSink(key.substring(0, i));
            }
        }
    }

    private void makeLogSink(String top) {
        Settings s = mCtx.getSettings();
        String type = s.getString(top+".log_type").toLowerCase();

        if (type.isEmpty()) {
            // not a log spec'
            return;
        }
        else if (type.equals("file")) {
            String fileName = s.getString(top+".log_file");

            try {
                // this will default to ASSERT(0)
                int l = s.getInt(top+".log_level");
                Log.add(new LogSink(new File(fileName), l));
                Log.i(TAG, "logging for "+top+" is level="+l+" file="+fileName);
            } catch(FileNotFoundException e) {
                System.err.println("Failed creating default log file, *sad face*: "+e);
                Log.e(TAG, "Failed creating log file: "+fileName, e);
            }
        }
        else if (type.equals("stdout") || type.equals("stderr")) {
            int l = s.getInt(top+".log_level");
            Log.add(new LogSink(
                type.equals("stdout") ? System.out : System.err, l));
            Log.i(TAG, "logging for "+top+" is level="+l+" type="+type);
        }
        // null log type gets no LogSink.
    }
}

