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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class SettingsFile implements Settings {
    private final static String TAG = "SettingsFile";
    private final String mName;
    private Properties mProps = new Properties();
    private List<Settings.OnChangedListener> mListeners =
        new LinkedList<Settings.OnChangedListener>();

    public SettingsFile(String name) {
        mName = name;
        try {
            mProps.load(new FileInputStream(mName));
        } catch (IOException e) {
            Log.e(TAG, "Couldn't load "+mName+". Using blank Properties.", e);
            mProps.clear();
        }
    }

    public void addChangeListener(OnChangedListener listener) {
        mListeners.add(listener);
    }

    public void removeChangeListener(OnChangedListener listener) {
        mListeners.remove(listener);
    }

    private void notifyListeners(String key) {
        for (Settings.OnChangedListener l : mListeners) {
            l.onChanged(this, key);
        }
    }

    public String[] keys() {
        return mProps.stringPropertyNames().toArray(new String[0]);
    }

    public boolean contains(String key) {
        return mProps.containsKey(key);
    }

    public boolean getBoolean(String key) {
        return Boolean.valueOf(mProps.getProperty(key, "false"));
    }
    
    public float getFloat(String key) {
        return Float.valueOf(mProps.getProperty(key, "0.0"));
    }
    
    public int getInt(String key) {
        return Integer.valueOf(mProps.getProperty(key, "0"));
    }

    public long getLong(String key) {
        return Long.valueOf(mProps.getProperty(key, "0"));
    }

    public String getString(String key) {
        return mProps.getProperty(key, "");
    }


    public Settings setBoolean(String key, boolean value) {
        notifyListeners(key);
        mProps.setProperty(key, Boolean.toString(value));
        return this;
    }

    public Settings setFloat(String key, float value) {
        notifyListeners(key);
        mProps.setProperty(key, Float.toString(value));
        return this;
    }

    public Settings setInt(String key, int value) {
        notifyListeners(key);
        mProps.setProperty(key, Integer.toString(value));
        return this;
    }

    public Settings setLong(String key, long value) {
        notifyListeners(key);
        mProps.setProperty(key, Long.toString(value));
        return this;
    }

    public Settings setString(String key, String value) {
        notifyListeners(key);
        mProps.setProperty(key, value);
        return this;
    }

    public boolean save() {
        try {
            mProps.store(new FileOutputStream(mName),
                "Comments and whitespace will not be saved.");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Couldn't save "+mName, e);
            return false;
        }
    }
}

