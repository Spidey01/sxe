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

