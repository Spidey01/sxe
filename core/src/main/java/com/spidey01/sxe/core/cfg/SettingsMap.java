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

package com.spidey01.sxe.core.cfg;

import com.spidey01.sxe.core.common.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Basic java.util.Map based implementation of Settings.
 */
public class SettingsMap implements Settings {
    private static final String TAG = "SettingsMap";


    private List<Settings.OnChangedListener> mListeners =
        new LinkedList<Settings.OnChangedListener>();


    private Map<String, String> mMap;


    public SettingsMap() {
        mMap = new HashMap<String, String>();
    }


    public SettingsMap(Map<String, String> map) {
        mMap = new HashMap<String, String>(map);
    }


    /** Create SettingsMap from a String[].
     *
     * This is in the format of command line arguments. Namely
     * <samp>"key=value"</samp>.
     */
    public SettingsMap(String[] args) {
        for (String arg : args) {
            int sep = arg.lastIndexOf("=");
            mMap.put(arg.substring(0, sep), arg.substring(sep+1));
        }
    }


    @Override
    public String[] keys() {
        Set<String> length = mMap.keySet();
        return length.toArray(new String[length.size()]);
    }


    @Override
    public boolean contains(String key) {
        return mMap.containsKey(key);
    }


    @Override
    public boolean getBoolean(String key) {
        String value = mMap.get(key);
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    
    @Override
    public float getFloat(String key) {
        String value = mMap.get(key);
        if (value == null) {
            return 0.0f;
        }
        return Float.valueOf(value);
    }

    
    @Override
    public int getInt(String key) {
        String value = mMap.get(key);
        if (value == null) {
            return 0;
        }
        return Integer.valueOf(value);
    }


    @Override
    public long getLong(String key) {
        String value = mMap.get(key);
        if (value == null) {
            return 0L;
        }
        return Long.valueOf(value);
    }


    @Override
    public String getString(String key) {
        String value = mMap.get(key);
        if (value == null) {
            value = "";
        }
        return value;
    }

    @Override
    public Settings setBoolean(String key, boolean value) {
        notifyListeners(key);
        mMap.put(key, Boolean.toString(value));
        return this;
    }


    @Override
    public Settings setFloat(String key, float value) {
        notifyListeners(key);
        mMap.put(key, Float.toString(value));
        return this;
    }


    @Override
    public Settings setInt(String key, int value) {
        notifyListeners(key);
        mMap.put(key, Integer.toString(value));
        return this;
    }


    @Override
    public Settings setLong(String key, long value) {
        notifyListeners(key);
        mMap.put(key, Long.toString(value));
        return this;
    }


    @Override
    public Settings setString(String key, String value) {
        notifyListeners(key);
        mMap.put(key, value);
        return this;
    }


    public void clear() {
        mMap.clear();
    }


    public void save() throws IOException {
        throw new IOException("Can't save to thin air!");
    }


    @Override
    public void merge(Settings s) {
        Utils.merge(this, s);
    }


    @Override
    public void addChangeListener(OnChangedListener listener) {
        mListeners.add(listener);
    }


    @Override
    public void removeChangeListener(OnChangedListener listener) {
        mListeners.remove(listener);
    }

    private void notifyListeners(String key) {
        for (Settings.OnChangedListener l : mListeners) {
            l.onChanged(this, key);
        }
    }


}

