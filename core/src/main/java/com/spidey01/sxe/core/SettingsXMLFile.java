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

package com.spidey01.sxe.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/** java.util.Properties XML implementation of Settings.
 */
public class SettingsXMLFile extends SettingsFile {
    private final static String TAG = "SettingsXMLFile";
    private final String mName;
    private Properties mProps = new Properties();

    public SettingsXMLFile(String name) {
        mName = name;
        try {
            mProps.loadFromXML(new FileInputStream(mName));
        } catch (IOException e) {
            Log.e(TAG, "Couldn't load "+mName+". Using blank Properties.", e);
            mProps.clear();
        }
    }


    public boolean save() {
        try {
            mProps.storeToXML(new FileOutputStream(mName),
                "Comments and whitespace will not be saved.");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Couldn't save "+mName, e);
            return false;
        }
    }
}


