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

package com.spidey01.sxe.android;

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

        setupXdg(game, context);

        AndroidDisplay d = new AndroidDisplay(context);
        GameContext c = new GameContext()
            .setConsole(null)
            .setDisplay(d)
            .setGame(game)
            .setInput(new AndroidInputManager(d))
            .setResources(new ResourceManager())
            .setPlatform("android")
            .setPlatformVersion("android "+Build.VERSION.RELEASE);

        return new GameEngine(c);
    }

    public static void setupXdg(Game game, Context context) {
        boolean mExternalReadable = false;
        boolean mExternalWritable = false;

        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalWritable = mExternalReadable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalReadable = true;
        }

        System.setenv("XDG_CACHE_HOME", context.getCacheDir());

        // config and data home?

        System.setenv("XDG_DATA_DIRS",
            context.getFilesDir().toString()
            + ":" + context.getExternalFilesDir(null).toString()
            + ":" + context.getObbDir().toString());

        // config dirs?
    }
}


