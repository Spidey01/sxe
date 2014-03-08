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

package com.spidey01.sxe.nulldemo.android;

import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.GameEngine;

import com.spidey01.sxe.android.common.AndroidConfiguration;
import com.spidey01.sxe.android.common.AndroidDisplay;
import com.spidey01.sxe.android.common.AssetLoader;
import com.spidey01.sxe.android.common.GameActivity;

import com.spidey01.sxe.nulldemo.lib.NullDemo;

import android.app.Activity;
import android.os.Bundle;

public class NullDemoActivity extends GameActivity {

    protected GameEngine mEngine;
    private static final String TAG = "NullDemoActivity";

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEngine = AndroidConfiguration.setup(new NullDemo(), this);

        // Setup a resource loader so we can use apk:path.
        mEngine.getResourceManager().setLoader("apk", new AssetLoader(getAssets()));

        setContentView((AndroidDisplay)mEngine.getDisplay());

        mEngine.start();
        finish();
    }

    public GameEngine getGameEngine() {
        return mEngine;
    }
}

