package com.spidey01.sxe.core;

import java.io.PrintStream;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;

/** Logging support for SxE.
 *
 * The goal here is to generally do the right thing.
 *
 * When possible the API resemblies the Android logging system or the Java
 * logging API.
 *
 * Modifying state in this class is not thread safe.
 */
public class Log {
    
    // Android mimicery
    public static final int ASSERT = 0;
    public static final int DEBUG = 1;
    public static final int ERROR = 2;
    public static final int INFO = 3;
    public static final int VERBOSE = 4;
    public static final int WARN = 5;

    private static boolean mUseThreadId = true;
    private static HashMap<String, Integer> mFilters = new HashMap<String, Integer>();
    private static final String TAG = "Log";

    /** Controls whether the thread ID will be included in a log statement
     *
     * Warning this dicks with everything >_<
     */
    public static void useThreadId(boolean flag) {
        mUseThreadId = flag;
    }

    public static boolean isLoggable(String tag, int level) {
        return level == mFilters.get(tag);
    }
    public static int getLevel(String tag) {
        return mFilters.get(tag);
    }
    public static void setLevel(String tag, int level) {
        mFilters.put(tag, level);
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
        // frag it, I'd rather use this on PC and worry about android later, than read java.util.logging
        PrintStream o = level <= ERROR ? System.err : System.out;
        Calendar c = Calendar.getInstance();
        o.println(
            // c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH)+"T"
            // +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)
            // +" "+c.get(Calendar.ZONE_OFFSET)
            translate(level)+"/"+tag
            +"( tid="+Thread.currentThread().getId()
            +" )"
            +": "+(tr == null ? message : message+": "+tr));
    }

    private static String translate(int level) {
        switch (level) {
            case ASSERT:
                return "ASSERT";
            case DEBUG:
                return "d";
            case ERROR:
                return "e";
            case INFO:
                return "i";
            case VERBOSE:
                return "v";
            case WARN:
                return "w";
        }
        return "WTF";
    }
}

