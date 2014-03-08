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

import com.spidey01.sxe.core.cfg.Settings;
import com.spidey01.sxe.core.cfg.SettingsMap;
import com.spidey01.sxe.core.common.Subsystem;
import com.spidey01.sxe.core.common.Utils;
import com.spidey01.sxe.core.graphics.Display;
import com.spidey01.sxe.core.input.InputManager;
import com.spidey01.sxe.core.io.SettingsFile;
import com.spidey01.sxe.core.io.SettingsXMLFile;
import com.spidey01.sxe.core.sys.FileSystem;
import com.spidey01.sxe.core.sys.Platform;
import com.spidey01.sxe.core.sys.Xdg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


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
    private final Logging mLogging;

    /** Master source of Settings.
     *
     * From lowest to highest priority, this will contain the following sources
     * of Settings data.
     *
     * <ol>
     *      <li>Platform settings.</li>
     *      <li>System settings file.</li>
     *      <li>User settings file.</li>
     *      <li>Command line arguments</li>
     * </ol>
     */
    private Settings mRuntimeSettings = new SettingsMap();

    /** Platform settings loaded from the ctor. */
    private Settings mPlatformSettings;

    /** System settings loaded from $XDG_CONFIG_DIRS. */
    private Settings mSystemSettings;

    /** User settings loaded from $XDG_CONFIG_HOME. */
    private Settings mUserSettings;

    /** Settings provided at the command line. */
    private final SettingsMap mCommandLineSettings;

    private final Platform mPlatform;
    private GameThread mGameThread;

    private Map<String, Subsystem> mSubsystems = new HashMap<String, Subsystem>();


    /** Initializes the engine for use.
     *
     * This is the primary chunk-o-code constructor.
     *
     * @param args Command line arguments.
     * @param display Display complicata.
     * @param scene  Manager of the scene.
     * @param game Game implementation.
     * @param input All that input related stuff.
     * @param resources Resource management.
     * @param settings Platform specific settings.
     * @param platform Platform specific information.
     */
    public GameEngine(SettingsMap args, Display display, SceneManager scene,
                      Game game, InputManager input, ResourceManager resources,
                      Logging logging, Settings settings, Platform platform)
    {
        mCommandLineSettings = args;
        mDisplay = display;
        mSceneManager = scene;
        mGame = game;
        mInputManager = input;
        mResourceManager = resources;
        mLogging = logging;
        mPlatformSettings = settings;
        mPlatform = platform;


        {
            assert mRuntimeSettings != null : "Programmer failure.";

            String cfgName = game.getName()+".cfg";
            String xmlName = game.getName()+".xml";
            String[] names = new String[]{ cfgName, xmlName };
            String p;
            File path;

            /*
             * System settings:
             *
             *      Look for the first GameName.cfg file in $XDG_CONFIG_DIRS.
             *      If not found, try GameName.xml by same method.
             */
            for (String n : names) {
                p = FileSystem.find(Xdg.XDG_CONFIG_DIRS, n);
                if (p == null) continue;
                path = new File(p);
                if (path.exists()) {
                    mSystemSettings = new SettingsFile(path);
                    break;
                }
            }

            /*
             * User settings:
             *
             *      Look for $XDG_CONFIG_HOME/|game name|cfg.
             *      If not found, try $XDG_CONFIG_HOME/|game name|.xml
             */
            for (String n : names) {
                path = new File(Xdg.XDG_CONFIG_HOME, n);
                if (path.exists()) {
                    mUserSettings = new SettingsFile(path);
                    break;
                }
            }

        }


        /*
         * These initialize functions will subscribe to whatever runtime
         * settings they want. As well as perform any pre start() setup.
         */
        mLogging.initialize(this);
        mDisplay.initialize(this);
        mSceneManager.initialize(this);
        mInputManager.initialize(this);
        mResourceManager.initialize(this);


        /*
         * Process the various sources of Settings.
         */
        if (mPlatformSettings != null)      mRuntimeSettings.merge(mPlatformSettings);
        if (mSystemSettings != null)        mRuntimeSettings.merge(mSystemSettings);
        if (mUserSettings != null)          mRuntimeSettings.merge(mUserSettings);
        if (mCommandLineSettings != null)   mRuntimeSettings.merge(mCommandLineSettings);

        configure();

        // ternary abuse, yeah.
        final String p = 
            (mGame == null ? "game" :
                (mPlatform == null ? "platform"
                    : null));

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

        Log.i(TAG, mPlatform);

        Log.d(TAG, "APP_HOME=\""+Xdg.APP_HOME+"\"");
        Log.d(TAG, "XDG_DATA_HOME=\""+Xdg.XDG_DATA_HOME+"\"");
        Log.d(TAG, "XDG_CONFIG_HOME=\""+Xdg.XDG_CONFIG_HOME+"\"");
        Log.d(TAG, "XDG_CACHE_HOME=\""+Xdg.XDG_CACHE_HOME+"\"");
        Log.d(TAG, "XDG_RUNTIME_DIR=\""+Xdg.XDG_RUNTIME_DIR+"\"");
        Log.d(TAG, "XDG_DATA_DIRS=\""+Utils.join(Xdg.XDG_DATA_DIRS, ':')+"\"");
        Log.d(TAG, "XDG_CONFIG_DIRS=\""+Utils.join(Xdg.XDG_CONFIG_DIRS, ':')+"\"");


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


    /** Convenience method that can serve as a simple main loop. */
    public void mainLoop() {
        try {
            while (isRunning()) {
                update();
            }
        } catch(Exception e) {
            Log.e(TAG, "Unhandled exception in mainLoop().", e);
            // Don't call stop. We don't know if it's that recoverable.
            // Dump a stack trace and force an exit NOW.
            e.printStackTrace();
            System.exit(1);
        }
    }


    /** Determine if the Game environment is running. */
    public boolean isRunning() {
        return (!mGame.isStopRequested()
                && !mDisplay.isCloseRequested()
                && mGameThread.isAlive());
    }


    /** Update Game engine state.
     *
     * <ol>
     *  <li>Polls the InputManager.</li>
     *  <li>Updates the SceneManager.</li>
     *  <li>Updates the Display.</li>
     * </ol>
     *
     * Game implementations update independantly of GameEngine, as it runs on a
     * seperate thread.
     */
    public void update() {
            mInputManager.poll();
            mSceneManager.update();
            mDisplay.update();
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
        return mRuntimeSettings;
    }


    public void configure() {
        configure(mRuntimeSettings);
    }


    public void configure(Settings s) {
        /* Shortcut key used for general debugging. */
        if (s.getBoolean("debug")) {
            // Make sure that we have a log file.
            if (!s.contains("debug.log_to"))
                s.setString("debug.log_to", "debug.log");
            if (!s.contains("debug.log_level"))
                s.setInt("debug.log_level", Log.DEBUG);
        }
    }


}

