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
package com.spidey01.sxe.core.logging;

import com.spidey01.sxe.core.GameEngine;
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

            if (sink == null && key.lastIndexOf(".log_to") != -1) {
                Log.add(makeLogSink(name, mSettings.getString(key)));
            } else if (sink !=  null) {
                // Apply whatever settings are currently available.
                editLogSink(sink);
            } else {
                // this log sink doesn't exist yet. Skip for now and edit later.
                return;
            }
        }
    }


    /** Creates a LogSink of specified to.
     */
    private LogSink makeLogSink(String name, String to) {
        int level = LogSink.DEFAULT_LOG_LEVEL;
                Log.w("XXX", "making log sink named", name, "with to", to);

        if (to.isEmpty()) {
            // not a log spec'
            throw new IllegalArgumentException("EMPTY LOG SPEC");
        }
        else if (to.equals("stdin")) {
            throw new IllegalArgumentException("Can't log to stdin; maybe you has typo?");
        }
        else if (to.equals("stdout") || to.equals("stderr")) {
            return new LogSink(name, level, to.equals("stdout") ? System.out : System.err);
        }
        else {
            /* .log_to's value is a file name. */
            try {
                return new LogSink(name, level, new File(to));
            } catch(FileNotFoundException e) {
                System.err.println("Failed creating log file, *sad face*: "+e);
                Log.e(TAG, "Failed creating log file: "+to, e);
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
}

