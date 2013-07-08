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
 * Logging is a simple Android inspired API based on log levels. Each log
 * level has a set of overloads based on LogSink:
 * <ol>
 *   <li>X(tag, messages...);</li>
 *   <li>X(tag, format, objects...);</li>
 * </ol>
 * Where X will be the terse form of the level, e.g. d for debug, e for error,
 * etc. This is backwards compatable with the Android API, while allowing a
 * much nicer syntax from my point of view: less +'s and more let the language
 * do it.
 *
 * In SxE the destination of log messages can be customized. Everything is
 * logged to a collection LogSink's according to the log levels and tags
 * supplied with the message.  Logging is controlled by a per 'tag' log level.
 * So it is entirely possible to send log data to multiple sources and to
 * configure each source based on tag/level.
 *
 * By default there are no log sinks, which causes all log data to be silenced.
 *
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


    /** Add log sink. */
    public static void add(LogSink sink) {
        mSinks.add(sink);
    }


    /** Remove log sink. */
    public static void remove(LogSink sink) {
        mSinks.remove(sink);
    }


    /** Return if tag is loggable at level. */
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


    public static void wtf(String tag, Object... messages) {
        logit(ASSERT, tag, messages);
        death();
    }
    public static void wtf(String tag, String format, Object... args) {
        logit(ASSERT, tag, format, args);
        death();
    }


    public static void d(String tag, Object... messages) {
        logit(DEBUG, tag, messages);
    }
    public static void d(String tag, String format, Object... args) {
        logit(DEBUG, tag, format, args);
    }


    public static void e(String tag, Object... messages) {
        logit(ERROR, tag, messages);
    }
    public static void e(String tag, String format, Object... args) {
        logit(ERROR, tag, format, args);
    }


    public static void i(String tag, Object... messages) {
        logit(INFO, tag, messages);
    }
    public static void i(String tag, String format, Object... args) {
        logit(INFO, tag, format, args);
    }


    public static void v(String tag, Object... messages) {
        logit(VERBOSE, tag, messages);
    }
    public static void v(String tag, String format, Object... args) {
        logit(VERBOSE, tag, format, args);
    }


    public static void w(String tag, Object... messages) {
        logit(WARN, tag, messages);
    }
    public static void w(String tag, String format, Object... args) {
        logit(WARN, tag, format, args);
    }
 

    private static void logit(int level, String tag, Object... messages) {
        for (LogSink sink : mSinks) {
            sink.log(level, tag, messages);
        }
    }


    private static void logit(int level, String tag, String format, Object... args) {
        for (LogSink sink : mSinks) {
            sink.log(level, tag, format, args);
        }
    }


    /** Used by the wtf() methods to trigger an assertion. */
    private static void death() {
        assert false : "What a Terrible Failure Report we has here";
    }

}

