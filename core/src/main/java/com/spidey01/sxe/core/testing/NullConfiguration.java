/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

package com.spidey01.sxe.core.testing;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.ResourceManager;
import com.spidey01.sxe.core.SceneManager;
import com.spidey01.sxe.core.cfg.Settings;
import com.spidey01.sxe.core.cfg.SettingsMap;
import com.spidey01.sxe.core.common.Subsystem;
import com.spidey01.sxe.core.io.SettingsFile;
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.logging.Logging;
import com.spidey01.sxe.core.sys.Platform;

import java.io.File;

/** Utility class to setup a GameEngine configured with null input and display.
 */
public class NullConfiguration {
    private static final String TAG = "NullConfiguration";

    /** The defaults */
    public static GameEngine setup(Game game) {
        return setup(null, game);
    }


    public static GameEngine setup(String[] args, Game game) {
        return new GameEngine(
            new SettingsMap(args)
            , new NullDisplay(new NullOpenGL(), true)
            , new SceneManager()
            , game
            , new NullInputManager()
            , new ResourceManager()
            , new Logging()
            , null
            , new Platform(Platform.guess())
        );
    }
}

