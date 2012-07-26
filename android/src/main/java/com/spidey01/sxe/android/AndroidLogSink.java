package com.spidey01.sxe.android;

import com.spidey01.sxe.core.LogSink;

import android.util.Log;

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

        switch (level) {
            case com.spidey01.sxe.core.Log.ASSERT:
                Log.wtf(tag, message, tr);
            case com.spidey01.sxe.core.Log.DEBUG:
                Log.d(tag, message, tr);
            case com.spidey01.sxe.core.Log.ERROR:
                Log.e(tag, message, tr);
            case com.spidey01.sxe.core.Log.INFO:
                Log.i(tag, message, tr);
            case com.spidey01.sxe.core.Log.VERBOSE:
                Log.v(tag, message, tr);
            case com.spidey01.sxe.core.Log.WARN:
                Log.w(tag, message, tr);
        }
    }
}


