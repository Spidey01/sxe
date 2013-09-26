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

package com.spidey01.sxe.android.common;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameContext;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.ResourceManager;

import android.content.Context;
import android.os.Build;

/** Utility class to setup a GameContext to the configuration for Android.
 */
public class AndroidConfiguration {

    private static final String TAG = "AndroidConfiguration";

    /** The defaults */
    public static GameEngine setup(Game game, Context context) {
        // TODO for release builds lower this to WARN or INFO.
        Log.add(new AndroidLogSink(Log.VERBOSE));

        AndroidDisplay d = new AndroidDisplay(context);
        GameContext c = new GameContext()
            .setConsole(null)
            .setDisplay(d)
            .setGame(game)
            .setInput(new AndroidInputManager(d))
            .setResources(new ResourceManager())
            .setSettings(new AndroidSettings(context, game.getName()))
            .setPlatform("android")
            .setPlatformVersion("android "+Build.VERSION.RELEASE);

        return new GameEngine(c);
    }
}

