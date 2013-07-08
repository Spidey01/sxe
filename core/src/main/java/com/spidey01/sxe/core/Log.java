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
 * Logging is a simple Android inspired API based on log levels. Each log level
 * has a set of related methods.  This is backwards compatable with the Android
 * API, while allowing good freedom for a more specialized logging API.
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
    public static final int TRACE   = 10;

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


    /** Send an ASSERT message.
     *
     * Report a condition that should never happen. A false assertion will also
     * be triggered if assertions are enabled.  Android and the assertion
     * method calls this "What a Terrible Failure" but you may call it by other
     * names if you wish ;).
     */
    public static void wtf(String tag, Object... messages) {
        logit(ASSERT, tag, messages);
        assert false : "What a Terrible Failure Report we has here";
    }


    /** Send a DEBUG message. */
    public static void d(String tag, Object... messages) {
        logit(DEBUG, tag, messages);
    }


    /** Send a ERROR message. */
    public static void e(String tag, Object... messages) {
        logit(ERROR, tag, messages);
    }


    /** Send a INFO message. */
    public static void i(String tag, Object... messages) {
        logit(INFO, tag, messages);
    }


    /** Send a VERBOSE message. */
    public static void v(String tag, Object... messages) {
        logit(VERBOSE, tag, messages);
    }


    /** Send a WARN message. */
    public static void w(String tag, Object... messages) {
        logit(WARN, tag, messages);
    }

    /** Send a TRACE message.
     *
     * This is intended as a log level more verbose than a DEBUG message, yet
     * more specific than a VERBOSE message. It's purpose is for tracing
     * control flow and conditions through the log. It is best combined with a
     * LogSink set to the specific tag(s) being traced.
     *
     * The name is taken from the Bourne shell option called 'xtrace'.
     */
    public static void xtrace(String tag, Object... messages) {
        logit(TRACE, tag, messages);
    }
 

    /** Send a message to all sinks.
     */
    public static void log(int level, String tag, Object... messages) {
        for (LogSink sink : mSinks) {
            sink.log(level, tag, messages);
        }
    }


    /** Send a formatted message to all sinks.
     */
    public static void logf(int level, String tag, String format, Object... args) {
        for (LogSink sink : mSinks) {
            sink.logf(level, tag, format, args);
        }
    }

}

