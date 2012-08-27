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

package com.spidey01.sxe.android;

import core.spidey01.sxe.core.Settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.LinkedList;

/** Implementation of Settings using SharedPreferences.
 *
 * The default mode is Context.MODE_PRIVATE.
 */
public class AndroidSettings implements Settings {
    private final static String TAG = "AndroidSettings";
    private final int DEFAULT_MODE = Context.MODE_PRIVATE;
    private List<Settings.OnChangedListener> mListeners =
        new LinkedList<Settings.OnChangedListener>();
    private SharedPreferences mPrefs = null;

    public AndroidSettings(Context context, String name) {
        mPrefs = context.getSharedPreferences(name, DEFAULT_MODE);

        final Settings s = this;

        mPrefs.registerOnSharedPreferenceChangeListener(
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
                    s.notifyListeners(key);
                }
            }
        );
    }

    public void addChangeListener(OnChangedListener listener) {
        mListeners.add(listener);
    }

registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
Registers a callback to be invoked when a change happens to a preference.
abstract void	 unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    public void removeChangeListener(OnChangedListener listener) {
        mListeners.remove(listener);
    }

    private void notifyListeners(String key) {
        for (Settings.OnChangedListener l : mListeners) {
            l.onChanged(this, key);
        }
    }

    public boolean contains(String key) {
        return mProps.containsKey(key);
    }

    public Map<String, Object> getAll() {
        return mPrefs.getAll();
    }

    public boolean getBoolean(String key) {
        return mPrefs.getBoolean(key, false);
    }
    
    public float getFloat(String key) {
        return mPrefs.getFloat(key, 0.0f);
    }
    
    public int getInt(String key) {
        return mPrefs.getInt(key, 0);
    }

    public long getLong(String key) {
        return mPrefs.getLong(key, 0);
    }

    public String getString(String key) {
        return mPrefs.getString(key, "");
    }


    public Settings setBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value);
        return this;
    }

    public Settings setFloat(String key, float value) {
        mPrefs.edit().putFloat(key, value);
        return this;
    }

    public Settings setInt(String key, int value) {
        mPrefs.edit().putInt(key, value);
        return this;
    }

    public Settings setLong(String key, long value) {
        mPrefs.edit().putLong(key, value);
        return this;
    }

    public Settings setString(String key, String value) {
        mPrefs.edit().putString(key, value);
        return this;
    }

    boolean save() {
        return mPrefs.edit().commit();
    }
}

