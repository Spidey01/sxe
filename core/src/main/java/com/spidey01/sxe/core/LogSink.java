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

import java.io.PrintStream;
import java.util.HashMap;

/** Sink for consuming statements from Log.
 *
 * The default implementation simply works with a PrintStream. Like System.out or System.err.
 */
public class LogSink {

    /** Default log level for previously unseen tags. */
    public static final int DEFAULT_LOG_LEVEL = Log.INFO;

    protected int mDefaultLevel = DEFAULT_LOG_LEVEL;
    protected HashMap<String, Integer> mFilters = new HashMap<String, Integer>();

    private PrintStream mOutput;
    private static final String TAG = "LogSink";

    /** Creates a LogSink that writes to System.out. */
    public LogSink() {
        mOutput = System.out;
    }

    public LogSink(int level) {
        this();
        mDefaultLevel = level;
    }

    public LogSink(PrintStream s) {
        mOutput = s;
    }

    /** Creates a LogSink for PrintStream.
     *
     * @param s A print stream like System.err, etc.
     * @param level default log level.
     */
    public LogSink(PrintStream s, int level) {
        this(s);
        mDefaultLevel = level;
    }

    public boolean isLoggable(String tag, int level) {
        return level == getLevel(tag);
    }

    public int getLevel(String tag) {
        Integer level = mFilters.get(tag);
        if (level == null) {
            level = mDefaultLevel;
            setLevel(tag, level);
        }
        return level;
    }

    public void setLevel(String tag, int level) {
        mFilters.put(tag, level);
    }

    public void log(int level, String tag, String message, Throwable tr) {
        if (level > getLevel(tag)) {
            return;
        }

        mOutput.println(
            LogSink.translate(level)+"/"+tag
            +"( tid="+Thread.currentThread().getId()
            +" )"
            +": "+(tr == null ? message : message+": "+tr)
        );
    }

    private static String translate(int level) {
        switch (level) {
            case Log.ASSERT:
                return "ASSERT";
            case Log.DEBUG:
                return "d";
            case Log.ERROR:
                return "e";
            case Log.INFO:
                return "i";
            case Log.VERBOSE:
                return "v";
            case Log.WARN:
                return "w";
        }
        return "WTF";
    }
}

