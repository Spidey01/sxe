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
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.logging.LogSink;
import com.spidey01.sxe.core.logging.Logging;
import com.spidey01.sxe.core.ResourceManager;
import com.spidey01.sxe.core.SceneManager;
import com.spidey01.sxe.core.cfg.Settings;
import com.spidey01.sxe.core.cfg.SettingsMap;
import com.spidey01.sxe.core.io.SettingsFile;
import com.spidey01.sxe.core.io.SettingsXMLFile;
import com.spidey01.sxe.core.sys.Platform;
import com.spidey01.sxe.core.sys.Xdg;

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
        Platform platform = new Platform(Platform.guess());
        return new GameEngine(
            new SettingsMap(args)
            , new PcDisplay()
            , new SceneManager()
            , game
            , new PcInputManager()
            , new ResourceManager()
            , new Logging()
            , PcConfiguration.settings(game, platform)
            , platform
        );
    }


    /** PC specific settings.
     *
     * On Windows, we look at %LOCALAPPDATA%\Publisher\Gamename.{cfg,xml}.
     *
     *
     * Note:
     * 
     *      On some systems we may substutie other values for version
     *      compatability. E.g. on XP, there is no %LocalAppData% there is only
     *      <strike>Zuul</strike> %AppData%.
     */
    public static Settings settings(Game game, Platform platform) {
        if (platform.isWindows()) {
            String localAppData = System.getenv("LOCALAPPDATA");
            String publisher = game.getPublisher();


            // Compat for Windows XP / 2K. Love and hate y'all!
            if (platform.version.startsWith("Windows XP 5.1")
                || platform.version.startsWith("Windows 2000 5.0"))
            {
                localAppData = System.getenv("APPDATA");
            }

            if (localAppData == null || publisher == null) {
                return null;
            }

            File dir = new File(localAppData, publisher);
            File path;

            path = new File(dir, game.getName()+".cfg");
            System.err.println(TAG+" Looking for Windows config in "+path.getPath());
            if (path.exists()) {
                return new SettingsFile(path);
            }

            path = new File(dir, game.getName()+".xml");
            if (path.exists()) {
                return new SettingsXMLFile(path);
            }
        }

        return null;
    }
}

