package com.spidey01.sxe.pc;
import java.io.PrintStream;

import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.LogSink;
import com.spidey01.sxe.core.ResourceManager;

import java.io.FileNotFoundException;

/** Utility class to set C.java to the configuration for PC.
 *
 * Simply call any method and vola. C should be ready for use on PC.
 */
public class PcConfiguration {
    private static final String TAG = "PcConfiguration";

    /** The defaults */
    public static GameEngine setup(Game game) {
        return setup(game, "640 x 480");
    }

    public static GameEngine setup(Game game, String displayMode) {

        /*
         * default log setup
         *
         * This is bad default b/c the directory might not be writable, but
         * this so happens to be for debug builds running out of ./dist, not a
         * release build. Change this for a release, D'uh!
         *
         * like wise it should be for Log.INFO or higher, not for Log.VERBOSE.
         */
        try {
            Log.add(new LogSink(new PrintStream(game.getName()+".log"), Log.VERBOSE));
        } catch(FileNotFoundException e) {
            System.err.println("Failed creating log file, *sad face*: "+e);
        }

        C.put("console", null); // no default
        if (C.get("display") == null) {
            C.put("display", new PcDisplay(displayMode));
        }
        C.put("game", game);
        C.put("input", new PcInputManager());
        C.put("resources", new ResourceManager());
        C.put("engine", new GameEngine()); // last so it uses the above.
        return C.getEngine();
    }
}

