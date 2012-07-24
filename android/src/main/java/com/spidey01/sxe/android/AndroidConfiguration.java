package com.spidey01.sxe.android;

import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.ResourceManager;

import com.spidey01.sxe.android.AndroidDisplay;
import com.spidey01.sxe.android.AndroidGameEngine;
import com.spidey01.sxe.android.AndroidInputManager;

import android.content.Context;

/** Utility class to set C.java to the configuration for PC.
 *
 * Simply call any method and vola. C should be ready for use on PC.
 */
public class AndroidConfiguration {

    private static final String TAG = "AndroidConfiguration";

    /** The defaults */
    public static GameEngine setup(Game game, Context context) {
        C.put("console", null); // no default
        C.put("display", new AndroidDisplay(context));
        C.put("game", game);
        C.put("input", new AndroidInputManager((AndroidDisplay)C.getDisplay()));
        C.put("resources", new ResourceManager());
        C.put("engine", new AndroidGameEngine()); // last so it uses the above.
        return C.getEngine();
    }
}


