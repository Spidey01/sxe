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

package com.spidey01.sxe.pc;
import java.io.PrintStream;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameContext;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.LogSink;
import com.spidey01.sxe.core.ResourceManager;

import java.io.FileNotFoundException;

/** Utility class to setup a GameContext to the configuration for PC.
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

        GameContext c = new GameContext()
            .setConsole(null)
            .setDisplay(new PcDisplay(displayMode))
            .setGame(game)
            .setInput(new PcInputManager())
            .setResources(new ResourceManager());

        return new GameEngine(c);
    }
}

