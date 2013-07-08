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

import com.spidey01.sxe.core.LogSink;

import android.util.Log;

import java.util.Formatter;
import java.util.HashMap;

/** Sink that uses android.util.Log.
 */
public class AndroidLogSink extends LogSink {

    private static final String TAG = "AndroidLogSink";

    public AndroidLogSink() {
    }

    public AndroidLogSink(int level) {
        this();
        mDefaultLevel = level;
    }


    public void log(int level, String tag, Object... messages) {
        if (level > getLevel(tag)) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Object o : messages) {
            sb.append(o);
        }
        alog(level, tag, sb.toString());
    }


    public void log(int level, String tag, String format, Object... args) {
        if (level > getLevel(tag)) {
            return;
        }

        Formatter f = new Formatter();
        alog(level, tag, f.format(format, args).out().toString());
    }


    private void alog(int level, String tag, String message) {
        switch (level) {
            case com.spidey01.sxe.core.Log.ASSERT:
                Log.wtf(tag, message);
                break;
            case com.spidey01.sxe.core.Log.DEBUG:
                Log.d(tag, message);
                break;
            case com.spidey01.sxe.core.Log.ERROR:
                Log.e(tag, message);
                break;
            case com.spidey01.sxe.core.Log.INFO:
                Log.i(tag, message);
                break;
            case com.spidey01.sxe.core.Log.VERBOSE:
                Log.v(tag, message);
                break;
            case com.spidey01.sxe.core.Log.WARN:
                Log.w(tag, message);
                break;
        }
    }
}


