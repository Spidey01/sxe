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
package com.spidey01.sxe.core.config;

import java.io.IOException;
import java.util.EventListener;


/** Access to persistent settings.
 *
 * Settings are presented as a simple key/value store. The actual storage
 * method is platform specific: it could be anything from a property file, a
 * database, or even a hashtable in ram provided by a game console.
 *
 * Values are bound to a key, where the key is a String. To create an artifical
 * relationship between values, use a form of hierarchal naming. SxE uses the
 * period for this purpose, e.g. quux.foo, quux.bar. This mapping method makes
 * things simple to process in code and minimizes requirements on the storage
 * engine.
 *
 * @see SettingsFile
 * @see AndroidSettings
 */
public interface Settings {

    /** Interface for subscribing to settings changes.
     *
     * Use this if you must be kept up to date with the current value of a key.
     */
    public interface OnChangedListener extends EventListener {
        /** Handle change notification.
         *
         * You are provided an instance of the Settings so that you can query
         * its value using the provided methods.  It is not recommended to
         * modify the Settings instance unless you must update related settings
         * for e.g. consistency.
         *
         * No guarantee of thread safety is required for implementations of
         * this interface.
         *
         * @param settings - used to obtain the new value of key.
         * @param key - the key associated with the change.
         */
        void onChanged(String key);
    }
    
    void addChangeListener(OnChangedListener listener);
    void addChangeListener(String key, OnChangedListener listener);
    void removeChangeListener(OnChangedListener listener);
    // void removeChangeListener(String key, OnChangedListener listener);

    /** Sequence of all keys that are set. */
    String[] keys();

    /** Test if preference is set. */
    boolean contains(String key);

    /** Get key as a boolean value.
     *
     * @return the value of key or false if not set.
     */
    boolean getBoolean(String key);

    /** Get key as a float value.
     *
     * @return the value of key or 0 if not set.
     */
    float getFloat(String key);

    /** Get key as a int value.
     *
     * @return the value of key or 0 if not set.
     */
    int getInt(String key);

    /** Get key as a long value.
     *
     * @return the value of key or 0 if not set.
     */
    long getLong(String key);

    /** Get key as a String value.
     *
     * @return the value of key or "" if not set.
     */
    String getString(String key);

    /** Set key as a boolean value.  */
    Settings setBoolean(String key, boolean value);

    /** Set key as a float value.  */
    Settings setFloat(String key, float value);

    /** Set key as a int value.  */
    Settings setInt(String key, int value);

    /** Set key as a long value.  */
    Settings setLong(String key, long value);

    /** Set key as a String value.  */
    Settings setString(String key, String value);

    /** Clear loaded settings.
     */
    void clear();

    /** Commit changes back to storage. */
    void save() throws IOException;

    /** Merge contents of other Settings. */
    void merge(Settings s);
}

