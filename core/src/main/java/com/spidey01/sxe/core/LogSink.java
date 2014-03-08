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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/** Sink for consuming statements from Log.
 *
 * The default implementation simply works with a PrintStream. Like System.out or System.err.
 */
public class LogSink {
    private static final String TAG = "LogSink";

    /** Value used to log a null. */
    private static final String sNullFormat = "(null)";

    /** Default log level for previously unseen tags. */
    public static final int DEFAULT_LOG_LEVEL = Log.INFO;

    public static final String DEFAULT_LOG_NAME = "debug";

    public static final PrintStream DEFAULT_LOG_STREAM = System.err;


    private final PrintStream mOutput;

    protected int mDefaultLevel = DEFAULT_LOG_LEVEL;
    protected HashMap<String, Integer> mFilters = new HashMap<String, Integer>();
    protected boolean mDisplayThreadId = true;
    protected boolean mDisplayDate = true;
    protected boolean mDisplayTime = true;

    private final String mName;


    public LogSink(String name, int level, PrintStream output) {
        mName = name;
        mDefaultLevel = level;
        mOutput = output;
    }


    public LogSink(String name, int level, File file) throws FileNotFoundException {
        this(name, level, (new PrintStream(file)));
    }


    /** Creates a LogSink with the default settings. */
    public LogSink() {
        this(DEFAULT_LOG_NAME, DEFAULT_LOG_LEVEL, DEFAULT_LOG_STREAM);
    }


    public LogSink(int level) {
        this(DEFAULT_LOG_NAME, level, DEFAULT_LOG_STREAM);
    }


    public LogSink(PrintStream s) {
        this(DEFAULT_LOG_NAME, DEFAULT_LOG_LEVEL, s);
    }


    /** Creates a LogSink for PrintStream.
     *
     * @param s A print stream like System.err, etc.
     * @param level default log level.
     */
    public LogSink(PrintStream s, int level) {
        this(DEFAULT_LOG_NAME, level, s);
    }


    public LogSink(File file) throws FileNotFoundException {
        this(new PrintStream(file));
    }


    /** Creates a LogSink for a File.
     *
     * @param file to sink to.
     * @param level default log level.
     */
    public LogSink(File file, int level) throws FileNotFoundException {
        this(new PrintStream(file), level);
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


    /** Sets the default log level.
     *
     * This is comparable to setting the level in the constructor but does not
     * modify any levels already set.
     */
    public void setDefaultLevel(int level) {
        mDefaultLevel = level;
    }


    public String getName() {
        return mName;
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
            case Log.TRACE:
                return "x";
        }
        return "WTF";
    }


    public void log(int level, String tag, Object... messages) {
        if (messages.length == 0) {
            throw new IllegalArgumentException("No messages to log. Did you forget the TAG?");
        }

        if (level > getLevel(tag)) {
            return;
        }

        header(level, tag);

        //
        // We need to handle null specially because null isn't an Object, hence
        // we can't call toString() on it. Joy, right?
        //

        Object m;
        for (int i=0; i < messages.length - 1; ++i) {
            m = messages[i];
            mOutput.print( m == null ? sNullFormat : m.toString());
            mOutput.print(" ");
        }
        m = messages[messages.length-1];
        mOutput.println( m == null ? sNullFormat : m.toString());
    }


    public void logf(int level, String tag, String format, Object... args) {
        if (level > getLevel(tag)) {
            return;
        }

        header(level, tag);

        mOutput.format(format, args);
        mOutput.format("%n");
    }


    private void header(int level, String tag) {
        mOutput.print(LogSink.translate(level));
        mOutput.print("/");
        mOutput.print(tag);
        mOutput.print("(");
        if (mDisplayThreadId) {
            mOutput.print(" tid="+Thread.currentThread().getId());
        }
        if (mDisplayDate) {
            if (mDisplayThreadId) { mOutput.print(","); }
            Calendar c = Calendar.getInstance();
            mOutput.print(" date=");
            mOutput.print(c.get(Calendar.YEAR));
            mOutput.print("-");
            mOutput.print(c.get(Calendar.MONTH));
            mOutput.print("-");
            mOutput.print(c.get(Calendar.DAY_OF_WEEK));
        }
        if (mDisplayTime) {
            if (mDisplayThreadId || mDisplayDate) { mOutput.print(","); }
            Calendar c = Calendar.getInstance();
            mOutput.print(" time=");
            mOutput.print(c.get(Calendar.HOUR_OF_DAY));
            mOutput.print(":");
            mOutput.print(c.get(Calendar.MINUTE));
            mOutput.print(":");
            mOutput.print(c.get(Calendar.SECOND));
        }
        mOutput.print(" ): ");
    }

    public boolean getDisplayThreadId(boolean x) {
        return mDisplayThreadId;
    }


    public void setDisplayThreadId(boolean x) {
        mDisplayThreadId = x;
    }


    public boolean getDisplayDate(boolean x) {
        return mDisplayDate;
    }


    public void setDisplayDate(boolean x) {
        mDisplayDate = x;
    }


    public boolean getDisplayTime(boolean x) {
        return mDisplayTime;
    }


    public void setDisplayTime(boolean x) {
        mDisplayTime = x;
    }
}

