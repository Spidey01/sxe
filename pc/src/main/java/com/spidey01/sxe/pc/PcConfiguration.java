package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.ResourceManager;

/** Utility class to set C.java to the configuration for PC.
 *
 * Simply call any method and vola. C should be ready for use on PC.
 */
public class PcConfiguration {

    /** The defaults */
    public static GameEngine setup(Game game) {
        return setup(game, "640 x 480");
    }

    public static GameEngine setup(Game game, String displayMode) {
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

