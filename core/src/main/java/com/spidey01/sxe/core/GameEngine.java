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

/** Class implementing the core game engine.
 *
 *
 */
public class GameEngine {
    private static final String TAG = "GameEngine";

    private final Game mGame;
    private final Display mDisplay;
    private final SceneManager mSceneManager;
    private final InputManager mInputManager;
    private final ResourceManager mResourceManager;
    // should this be final?
    private final Settings mSettings;
    private final Platform mPlatform;
    private GameThread mGameThread;


    /** Initializes the engine for use.
    /** Initializes the engine for use.
     *
     * This is the primary chunk-o-code constructor.
     *
     * @param display Display complicata.
     * @param scene  Manager of the scene.
     * @param game Game implementation.
     * @param input All that input related stuff.
     * @param resources Resource management.
     * @param settings Source of configuration infomation.
     * @param platform Platform specific information.
     */
    public GameEngine(Display display, SceneManager scene, Game game,
                      InputManager input, ResourceManager resources,
                      Settings settings, Platform platform)
    {
        mDisplay = display;
        mSceneManager = scene;
        mGame = game;
        mInputManager = input;
        mResourceManager = resources;
        mSettings = settings;
        mPlatform = platform;

        configure();

        // ternary abuse, yeah.
        final String p = 
            (mDisplay == null ? "display"
             : (mSceneManager == null ? "scene"
                 : (mGame == null ? "game"
                     : (mInputManager == null ? "input"
                         : (mResourceManager == null ? "resources"
                             : (mSettings == null ? "settings"
                                 : (mPlatform == null ? "platform" : null)))))));

        if (p != null) {
            throw new IllegalArgumentException(p+" can't be null!");
        }
    }


    /** Start up the game.
     *
     * Takes care of initializing the games engine to run the game in the
     * current context. It will call the start() method of your Game
     * implementation accordingly.
     */
    public boolean start() {
        Log.v(TAG, "start()");

        Log.i(TAG, "platform=\""+System.getProperty("os.name")+"\"",
                   "version=\""+Utils.PLATFORM_VERSION+"\"",
                   "arch=\""+Utils.PLATFORM_ARCHITECTURE+"\"");

        Log.d(TAG, "APP_HOME=\""+System.getenv("APP_HOME")+"\"");
        Log.d(TAG, "XDG_DATA_HOME=\""+System.getenv("XDG_DATA_HOME")+"\"");
        Log.d(TAG, "XDG_CONFIG_HOME=\""+System.getenv("XDG_CONFIG_HOME")+"\"");
        Log.d(TAG, "XDG_CACHE_DIR=\""+System.getenv("XDG_CACHE_DIR")+"\"");
        Log.d(TAG, "XDG_RUNTIME_DIR=\""+System.getenv("XDG_RUNTIME_DIR")+"\"");
        Log.d(TAG, "XDG_DATA_DIRS=\""+System.getenv("XDG_DATA_DIRS")+"\"");
        Log.d(TAG, "XDG_CONFIG_DIRS=\""+System.getenv("XDG_CONFIG_DIRS")+"\"");


        if (!mDisplay.create()) {
            return false;
        }

        mGameThread = new GameThread(this, mGame);
        mGameThread.start();

        return true;
    }


    /** Stop the game.
     *
     * Will ensure Game.stop() is called. Shuts down the display, etc.
     */
    public void stop() {
        mGame.stop();
        mGameThread.interrupt(); // should this be overriden to do Game.stop()?
		mDisplay.destroy();
        Log.v(TAG, "stop() done");
    }


    /** Convenience method that can serve as a simple main loop.
     *
     * <ol>
     *  <li>Calls poll() on the InputManager.</li>
     *  <li>Updates the Display.</li>
     * </ol>
     */
    public void mainLoop() {
		while (!mGame.isStopRequested() && !mDisplay.isCloseRequested()) {
            mInputManager.poll();
            mSceneManager.update();
            mDisplay.update();
		}
    }


    public Display getDisplay() {
        return mDisplay;
    }

    
    public SceneManager getSceneManager() {
        return mSceneManager;
    }


    public Game getGame() {
        return mGame;
    }


    public InputManager getInputManager() {
        return mInputManager;
    }


    public ResourceManager getResourceManager() {
        return mResourceManager;
    }


    public Settings getSettings() {
        return mSettings;
    }


    public void configure() {
        configure(mSettings);
    }


    public void configure(Settings s) {
        /* Key used for general debugging. */
        if (s.getBoolean("debug")) {
            // Make sure that we have a log file.
            if (!s.contains("debug.log_level"))
                s.setInt("debug.log_level", Log.VERBOSE);
            if (!s.contains("debug.log_type"))
                s.setString("debug.log_type", "file");
            if (!s.contains("debug.log_file"))
                s.setString("debug.log_file", "debug.log");
        }

        /* Setup log sinks for every matching config tree. */
        for (String key : s.keys()) {
            int i = key.lastIndexOf(".log_type");
            if (i != -1) {
                makeLogSink(key.substring(0, i));
            }
        }

        final String game = mGame.getName();
        String name;
        String x;

        /* Register resource search path via configuration file. */
        name = game+".resources.path";
        x = mSettings.getString(name);
        if (!x.isEmpty()) {
            for (String dir : x.split(":")) {
                mResourceManager.addResourceLocation(dir);
            }
        }
        

        /* Support setting resolution from configuration file. */
        name = game+".display.resolution";
        x = mSettings.getString(name);
        if (!x.isEmpty()) {
            Log.d(TAG, name, "=", x);
            mDisplay.setMode(x);
        }
    }


    private void makeLogSink(String top) {
        System.err.println("Setting up logging for "+top);
        Settings s = mSettings; // lazy git.
        LogSink sink = null;

        /* this will default to ASSERT(0). */
        int level = s.getInt(top+".log_level");

        String fileName = s.getString(top+".log_file");

        /* Configure the log_type for top.
         */
        String type = s.getString(top+".log_type").toLowerCase();
        if (type.isEmpty()) {
            // not a log spec'
            System.err.println("EMPTY LOG SPEC");
            return;
        }
        else if (type.equals("file")) {
            try {
                sink = new LogSink(new File(fileName), level);
                System.err.println("file sink set.");
            } catch(FileNotFoundException e) {
                System.err.println("Failed creating log file, *sad face*: "+e);
                Log.e(TAG, "Failed creating log file: "+fileName, e);
            }
        }
        else if (type.equals("stdout") || type.equals("stderr")) {
            sink = new LogSink(type.equals("stdout") ? System.out : System.err, level);
            System.err.println("stdout/stderr sink set.");
        }
        else if (type.equals("stdin")) {
            throw new IllegalArgumentException("Can't log to stdin; maybe you has typo?");
        }
        else {
            // null log type gets no LogSink.
            System.err.println("NULL LOG TYPE");
            return;
        }

        /* Configure the log_tags for top.
         */
        String tags = s.getString(top+".log_tags");
        if (!tags.isEmpty()) {
            /*
             * Must be done or it'll have the same default level for !log_tags.
             */
            sink.setDefaultLevel(0);

            for (String t : tags.split(",")) {
                sink.setLevel(t, level);
            }
        }

        String flags = s.getString(top+".log_flags");
        if (!flags.isEmpty()) {
            for (String f : flags.split(",")) {
                boolean value = f.endsWith("=true");
                if (f.startsWith("DisplayThreadId")) {
                    sink.setDisplayThreadId(value);
                } else if (f.startsWith("DisplayDate")) {
                    sink.setDisplayDate(value);
                }
            }
        }

        Log.add(sink);
        Log.i(TAG, "logging for", top, " => ",
              "log_level="+level, ", ",
              "log_type="+type, ", ",
              "log_tags="+tags, ", ",
              "log_file="+fileName);
    }
}

