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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** Basic java.util.Properties implementation of Settings.
 */
public class SettingsFile extends AbstractSettingsFile {
    private static final String TAG = "SettingsFile";
    private static final String sComment = "Comments and whitespace will not be saved.";
    private final String mName;


    public SettingsFile(String name) {
        mName = name;
        try {
            load(mName);
        } catch (IOException e) {
            Log.e(TAG, "Couldn't load "+mName+". Using blank Properties.", e);
            mProps.clear();
        }
    }


    @Override
    public void save() throws IOException {
        save(new FileOutputStream(mName));
    }


    @Override
    public void save(OutputStream stream) throws IOException {
        mProps.store(stream, sComment);
    }


    @Override
    public void load(InputStream stream) throws IOException {
        mProps.load(stream);
    }

}

