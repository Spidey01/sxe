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

import java.util.List;
import java.util.LinkedList;

/** Logging support for SxE.
 *
 * The goal here is to generally do the right thing.
 *
 * When possible the API resemblies the Android logging system. It is however
 * paired up with a notion of "Sinks" that process the log data. Log sinks have
 * a per 'tag' log level that controls whether or not the will statement will
 * be logged. So it is entirely possible to send log data to multiple sources
 * and to configure each source based on tag/level.
 *
 * By default there are no log sinks, which causes all log data to be silenced.
 */
public class Log {
    
    // Android mimicery: least to most: ERROR, WARN, INFO, DEBUG, VERBOSE.
    public static final int ASSERT  = 0;
    public static final int ERROR   = 1;
    public static final int WARN    = 2;
    public static final int INFO    = 3;
    public static final int DEBUG   = 4;
    public static final int VERBOSE = 5;

    private static List<LogSink> mSinks = new LinkedList<LogSink>();
    private static final String TAG = "Log";

    public static void add(LogSink sink) {
        mSinks.add(sink);
    }
    public static void remove(LogSink sink) {
        mSinks.remove(sink);
    }

    public static boolean isLoggable(String tag, int level) {
        for (LogSink sink : mSinks) {
            if (sink.isLoggable(tag, level)) {
                return true;
            }
        }

        return false;
    }

    /** Convenience method that sets the level of tag for every sink. */
    public static void setLevel(String tag, int level) {
        for (LogSink sink : mSinks) {
            sink.setLevel(tag, level);
        }
    }

    public static void wtf(String tag, String message) {
        wtf(tag, message, null);
    }
    public static void wtf(String tag, String message, Throwable tr) {
        logit(ASSERT, tag, message, tr);
        assert false : "What a Terrible Failure Report we has here";
    }

    public static void d(String tag, String message) {
        d(tag, message, null);
    }
    public static void d(String tag, String message, Throwable tr) {
        logit(DEBUG, tag, message, tr);
    }

    public static void e(String tag, String message) {
        e(tag, message, null);
    }
    public static void e(String tag, String message, Throwable tr) {
        logit(ERROR, tag, message, tr);
    }

    public static void i(String tag, String message) {
        i(tag, message, null);
    }
    public static void i(String tag, String message, Throwable tr) {
        logit(INFO, tag, message, tr);
    }

    public static void v(String tag, String message) {
        v(tag, message, null);
    }
    public static void v(String tag, String message, Throwable tr) {
        logit(VERBOSE, tag, message, tr);
    }

    public static void w(String tag, String message) {
        w(tag, message, null);
    }
    public static void w(String tag, String message, Throwable tr) {
        logit(WARN, tag, message, tr);
    }
 

    private static void logit(int level, String tag, String message, Throwable tr) {
        for (LogSink sink : mSinks) {
            sink.log(level, tag, message, tr);
        }
    }

}

