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

import org.junit.*;

import java.io.IOException;

public class SettingsArgsTest extends AbstractSettingsTest {
    private static final String TAG = "SettingsArgsTest";


    private static final String[] sArgs = new String[]{
        "debug=true",
        TAG+".display.resolution=1280x720",
        TAG+".resources.path=tmp/",
        TAG+".log_level=10",
        TAG+".log_type=file",
        TAG+".log_file=tmp/"+TAG+".log",
        TAG+".log_flags=DisplayThreadId=true,DisplayDate=true,DisplayTime=true"
    };


    @Override
    protected String getTag() {
        return TAG;
    }


    @Override
    protected Settings makeSettings() {
        return new SettingsArgs(sArgs);
    }


    @Test(expected = IOException.class)
    public void save() throws IOException {
        SettingsArgs p = new SettingsArgs(sArgs);
        p.save();
    }


}

