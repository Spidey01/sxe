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

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameContext;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.ResourceManager;
import com.spidey01.sxe.core.Settings;
import com.spidey01.sxe.core.SettingsFile;

/** Utility class to setup a GameContext to the configuration for PC.
 */
public class PcConfiguration {
    private static final String TAG = "PcConfiguration";

    /** The defaults */
    public static GameEngine setup(Game game) {
        return setup(game, "640 x 480");
    }

    public static GameEngine setup(Game game, String displayMode) {

        GameContext c = new GameContext()
            .setConsole(null)
            .setDisplay(new PcDisplay(displayMode))
            .setGame(game)
            .setInput(new PcInputManager())
            .setResources(new ResourceManager())
            .setSettings(PcConfiguration.settings(game))
            .setPlatform("pc");

        return new GameEngine(c);
    }

    public static Settings settings(Game game) {
        final String os = System.getProperty("os.name");

        if (os.startsWith("Windows")) {
            final String ver = System.getProperty("os.version");
            final float fuzzyVer = Float.valueOf(ver);

            if (fuzzyVer < 5.0) {
                Log.w(TAG, "os.version reports a Windows version older than Windows 2000. The name/value was "+os+"/"+ver);
                Log.w(TAG, "Unsupported Windows version: os.name="+os+" os.version="+ver);
            }

            final String localAppData = System.getenv("LocalAppData");
            if (localAppData == null) {
                Log.e(TAG, "This appears to be a Microsoft Windows OS but %LocalAppData% is not set!");
                throw new
                    RuntimeException("%LocalAppData% or %XDG_*% must be set on Windows.");
            }

            return new SettingsFile(
                localAppData+"/"
                /*+game.getPublisher()+"/"*/
                +game.getName()+".cfg");
        }

        // go with unix as default.
        String dir = System.getenv("XDG_CONFIG_HOME");
        if (dir == null) {
            dir = System.getProperty("user.home")+"/.config";
        }
        return new SettingsFile(dir+"/"+game.getName()+".cfg");
    }
}

