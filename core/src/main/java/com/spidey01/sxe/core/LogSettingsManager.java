/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

import com.spidey01.sxe.core.cfg.Settings;
import com.spidey01.sxe.core.cfg.SettingsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;


/** Settings.OnChangedListener implementation for Log.
 */
class LogSettingsManager extends SettingsListener {
    private static final String TAG = "LogSettingsManager";

    public LogSettingsManager(GameEngine engine) {
        super(engine.getSettings());
        mSettings.addChangeListener(this);
    }


    @Override
    public void onChanged(String key) {
        super.onChanged(key);
        Log.xtrace(TAG, "onChanged(String key =>", key);

        int i = key.lastIndexOf(".log_");
        if (i != -1) {
            String name = key.substring(0, i);
            LogSink sink = getLogSink(name);

            if (sink == null && key.lastIndexOf(".log_type") != -1) {
                Log.add(makeLogSink(name, mSettings.getString(key).toLowerCase()));
            } else if (sink !=  null) {
                // Apply whatever settings are currently available.
                editLogSink(sink);
            } else {
                // this log sink doesn't exist yet. Skip for now and edit later.
                return;
            }
        }
    }


    /** Creates a LogSink of specified type.
     */
    private LogSink makeLogSink(String name, String type) {
        int level = LogSink.DEFAULT_LOG_LEVEL;

        if (type.isEmpty()) {
            // not a log spec'
            throw new IllegalArgumentException("EMPTY LOG SPEC");
        }
        else if (type.equals("stdin")) {
            throw new IllegalArgumentException("Can't log to stdin; maybe you has typo?");
        }
        else if (type.equals("stdout") || type.equals("stderr")) {
            return new LogSink(name, level, type.equals("stdout") ? System.out : System.err);
        }
        else {
            /* .log_type's value is a file name. */
            try {
                return new LogSink(name, level, new File(type));
            } catch(FileNotFoundException e) {
                System.err.println("Failed creating log file, *sad face*: "+e);
                Log.e(TAG, "Failed creating log file: "+type, e);
            }
        }
        throw new IllegalArgumentException("Internal typo!");
    }

    
    private LogSink getLogSink(String name) {
        for (LogSink sink : Log.getSinks()) {
            if (sink.getName().equals(name)) {
                return sink;
            }
        }
        return null;
    }


    private void editLogSink(LogSink sink) {
        final String name = sink.getName();
        final int level = mSettings.getInt(name+".log_level");

        sink.setDefaultLevel(level);

        /* Configure the log_tags for name.
        */
        String tags = mSettings.getString(name+".log_tags");
        if (!tags.isEmpty()) {
            /*
             * Must be done or it'll have the same default level for !log_tags.
             */
            sink.setDefaultLevel(0);

            for (String t : tags.split(",")) {
                sink.setLevel(t, level);
            }
        }

        String flags = mSettings.getString(name+".log_flags");
        if (!flags.isEmpty()) {
            for (String f : flags.split(",")) {
                boolean value = f.endsWith("=true");
                if (f.startsWith("DisplayThreadId")) {
                    sink.setDisplayThreadId(value);
                } else if (f.startsWith("DisplayDate")) {
                    sink.setDisplayDate(value);
                }
            }
        }
    }


    private void xmakeLogSink(String top) {
        System.err.println("Setting up logging for "+top);
        Log.e("XXX", "Setting up logging for",top);
        Settings s = mSettings; // lazy git.
        LogSink sink = null;

        /* this will default to ASSERT(0). */
        int level = s.getInt(top+".log_level");
        Log.e("XXX", "level =", level);

        String fileName = s.getString(top+".log_file");
        Log.e("XXX", "log_file =", fileName);

        /* Configure the log_type for top.
        */
        String type = s.getString(top+".log_type").toLowerCase();
        Log.e("XXX", "log_type =", type);
        if (type.isEmpty()) {
            // not a log spec'
            System.err.println("EMPTY LOG SPEC");
            return;
        }
        else if (type.equals("file")) {
            try {
                sink = new LogSink(new File(fileName), level);
                System.err.println("file sink set.");
            } catch(FileNotFoundException e) {
                System.err.println("Failed creating log file, *sad face*: "+e);
                Log.e(TAG, "Failed creating log file: "+fileName, e);
            }
        }
        else if (type.equals("stdout") || type.equals("stderr")) {
            sink = new LogSink(type.equals("stdout") ? System.out : System.err, level);
            System.err.println("stdout/stderr sink set.");
        }
        else if (type.equals("stdin")) {
            throw new IllegalArgumentException("Can't log to stdin; maybe you has typo?");
        }
        else {
            // null log type gets no LogSink.
            System.err.println("NULL LOG TYPE");
            return;
        }

        /* Configure the log_tags for top.
        */
        String tags = s.getString(top+".log_tags");
        Log.e("XXX", "log_tags =", tags);
        if (!tags.isEmpty()) {
            /*
             * Must be done or it'll have the same default level for !log_tags.
             */
            sink.setDefaultLevel(0);

            for (String t : tags.split(",")) {
                sink.setLevel(t, level);
            }
        }

        String flags = s.getString(top+".log_flags");
        Log.e("XXX", "log_flags =", flags);
        if (!flags.isEmpty()) {
            for (String f : flags.split(",")) {
                boolean value = f.endsWith("=true");
                if (f.startsWith("DisplayThreadId")) {
                    sink.setDisplayThreadId(value);
                } else if (f.startsWith("DisplayDate")) {
                    sink.setDisplayDate(value);
                }
            }
        }

        Log.add(sink);
        Log.i(TAG, "logging for", top, " => ",
                "log_level="+level, ", ",
                "log_type="+type, ", ",
                "log_tags="+tags, ", ",
                "log_file="+fileName);
    }
}

