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
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.Platform;
import com.spidey01.sxe.core.ResourceManager;
import com.spidey01.sxe.core.SceneManager;
import com.spidey01.sxe.core.Settings;
import com.spidey01.sxe.core.SettingsArgs;
import com.spidey01.sxe.core.SettingsFile;
import com.spidey01.sxe.core.Utils;
import com.spidey01.sxe.core.Xdg;

import java.io.File;

/** Utility class to setup a GameEngine configured for PC.
 */
public class PcConfiguration {
    private static final String TAG = "PcConfiguration";

    /** The defaults */
    public static GameEngine setup(Game game) {
        return setup(
                new String[]{ game.getName()+".display.resolution=640x480" },
                game);
    }


    public static GameEngine setup(String[] args, Game game) {
        SettingsArgs cliSettings = new SettingsArgs(args);
        // Settings userSettings = 

        return new GameEngine(
            cliSettings
            , new PcDisplay()
            , new SceneManager()
            , game
            , new PcInputManager()
            , new ResourceManager()
            , PcConfiguration.settings(game)
            , new Platform(Platform.guess())
        );
    }


    public static Settings settings(Game game) {
        for (String extension : new String[]{ ".cfg", ".xml" }) {
        }

        File local = new File(Xdg.XDG_CONFIG_HOME, game.getName()+".cfg");
        if (local.exists()) {
            return new SettingsFile(local);
        }

        File system = new File(Utils.find(Xdg.XDG_CONFIG_DIRS, game.getName()+".cfg"));
        if (system.exists()) {
            return new SettingsFile(system);
        }

        /** A checked FileNotFoundException would be better, IMHO. */
        return null;
    }
}

