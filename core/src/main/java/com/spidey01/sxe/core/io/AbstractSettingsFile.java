/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

package com.spidey01.sxe.core.io;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.cfg.Settings;
import com.spidey01.sxe.core.cfg.SettingsManager;
import com.spidey01.sxe.core.common.NotificationManager;
import com.spidey01.sxe.core.common.Utils;
import com.spidey01.sxe.core.sys.FileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/** Abstract version of Settings based on java.util.Properties.
 */
public abstract class AbstractSettingsFile implements Settings {
    private final static String TAG = "AbstractSettingsFile";

    private SettingsManager mSettingsManager;


    /* This is provided to concrete implementations. You want to use it for
     * loading/saving the data. */
    protected Properties mProps = new Properties();


    @Override
    public void addChangeListener(OnChangedListener listener) {
        mSettingsManager.subscribe(listener);
    }


    @Override
    public void addChangeListener(String key, OnChangedListener listener) {
        mSettingsManager.subscribe(key, listener);
    }


    @Override
    public void removeChangeListener(OnChangedListener listener) {
        mSettingsManager.unsubscribe(listener);
    }


    // @Override
    // public void removeChangeListener(String key, OnChangedListener listener) {
        // mSettingsManager.unsubscribe(key, listener);
    // }


    @Override
    public String[] keys() {
        Set<String> length = mProps.stringPropertyNames();
        return length.toArray(new String[length.size()]);
    }


    @Override
    public boolean contains(String key) {
        return mProps.containsKey(key);
    }


    @Override
    public boolean getBoolean(String key) {
        return Boolean.valueOf(mProps.getProperty(key, "false"));
    }

    
    @Override
    public float getFloat(String key) {
        return Float.valueOf(mProps.getProperty(key, "0.0"));
    }

    
    @Override
    public int getInt(String key) {
        return Integer.valueOf(mProps.getProperty(key, "0"));
    }


    @Override
    public long getLong(String key) {
        return Long.valueOf(mProps.getProperty(key, "0"));
    }


    @Override
    public String getString(String key) {
        return mProps.getProperty(key, "");
    }


    @Override
    public Settings setBoolean(String key, boolean value) {
        mProps.setProperty(key, Boolean.toString(value));
        mSettingsManager.notifyListeners(key);
        return this;
    }


    @Override
    public Settings setFloat(String key, float value) {
        mProps.setProperty(key, Float.toString(value));
        mSettingsManager.notifyListeners(key);
        return this;
    }


    @Override
    public Settings setInt(String key, int value) {
        mProps.setProperty(key, Integer.toString(value));
        mSettingsManager.notifyListeners(key);
        return this;
    }


    @Override
    public Settings setLong(String key, long value) {
        mProps.setProperty(key, Long.toString(value));
        mSettingsManager.notifyListeners(key);
        return this;
    }


    @Override
    public Settings setString(String key, String value) {
        mProps.setProperty(key, value);
        mSettingsManager.notifyListeners(key);
        return this;
    }


    @Override
    public void clear() {
        mProps.clear();
    }


    public abstract void save() throws IOException;


    /** Save settings to Stream. */
    public abstract void save(OutputStream stream) throws IOException;


    public void save(String name) throws IOException {
        try {
            save(new FileOutputStream(name));
        } catch (IOException e) {
            Log.e(TAG, "Couldn't save(\"", name, "\")");
            throw e;
        }
    }


    /** Save settings to file path. */
    public void save(File path) throws IOException {
        save(path.getPath());
    }


    /** Load settings from Stream. */
    public abstract void load(InputStream stream) throws IOException;


    public void load(String name) throws IOException {
        try {
            load(new FileInputStream(name));
        } catch (IOException e) {
            Log.e(TAG, "Couldn't load(\"", name, "\")");
            throw e;
        }
    }


    /** Load settings from file path. */
    public void load(File path) throws IOException {
        load(path.getPath());
    }


    @Override
    public void merge(Settings s) {
        Utils.merge(this, s);
    }


}

