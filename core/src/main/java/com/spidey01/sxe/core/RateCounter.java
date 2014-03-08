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

package com.spidey01.sxe.core;

import com.spidey01.sxe.core.logging.Log;

// TODO: use Log.
public class RateCounter {
    private long mPerSecond;
    private long ms;

    private String mName;
    private boolean mDebuggingEnabled;
    private static final String TAG = "RateCounter";

    public RateCounter(String name, boolean debug) {
        mName = name;
        mDebuggingEnabled = debug;
        ms = System.currentTimeMillis();
    }

    public RateCounter(String name) {
        this(name, true);
    }

    public String getName() {
        return mName;
    }

    public void disableDebugging() {
        mDebuggingEnabled = false;
    }
    public void enableDebugging() {
        mDebuggingEnabled = true;
    }
    public boolean isDebuggingEnabled() {
        return mDebuggingEnabled;
    }

    public void update() {
        long now = System.currentTimeMillis();

        if (now > (ms+1000)) {
            if (isDebuggingEnabled()) {
                Log.v(mName+" "+TAG, getName()+": "+(mPerSecond-1)+" per second");
            }
            ms = now;
            mPerSecond = 0;
        }
        mPerSecond++;
    }

}
