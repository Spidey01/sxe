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
package com.spidey01.sxe.core.cfg;

import com.spidey01.sxe.core.Log;

/** Helper class for creating a Settings.OnChangedListener.
 *
 * Simply pass the correct data through the constructor with super() and
 * override the onChanged() method.
 */
public class SettingsListener implements Settings.OnChangedListener {
    private static final String TAG = "SettingsListener";

    protected final String mPrefix;
    protected final Settings mSettings;


    /** Creates a listener for changes to settings.
     *
     * Only settings marked with prefix are of interest.
     * You can then make use of mPrefix in onChanged().
     */
    public SettingsListener(Settings settings, String prefix) {
        mPrefix = prefix;
        mSettings = settings;
        Log.v(TAG, "Listening for settings prefixed:", prefix, "on Settings =>", mSettings);
        mSettings.addChangeListener(this);
    }


    public void clear() {
        mSettings.removeChangeListener(this);
    }


    public void onChanged(Settings settings, String key) {
        Log.xtrace(TAG, "onChanged(Settings =>", settings, ", String =>", key);
        assert mSettings.equals(settings);
    }
}
