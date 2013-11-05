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

import java.io.File;
import java.io.IOException;

public class SettingsFileTest extends UnitTest {
    private static final String TAG = "SettingsFileTest";


    @BeforeClass
    public static void setup() {
        UnitTest.setup();
    }


    private SettingsFile makeSettings() {
        return new SettingsFile(new File(TestResources.directory, TestResources.settingsFileName));
    }


    @Test
    public void ctor() {
        SettingsFile test = makeSettings();

        Assert.assertTrue(test.contains("debug"));
        Assert.assertTrue(test.getBoolean("debug"));

        Assert.assertTrue(test.contains("SettingsFile.display.resolution"));
        Assert.assertEquals("1280x720", test.getString("SettingsFile.display.resolution"));

        Assert.assertTrue(test.contains("SettingsFile.log_level"));
        Assert.assertEquals(10, test.getInt("SettingsFile.log_level"));
    }



    @Test
    public void clear() {
        SettingsFile test = makeSettings();

        test.clear();

        Assert.assertFalse(test.contains("debug"));
        Assert.assertFalse(test.getBoolean("debug"));

        Assert.assertFalse(test.contains("SettingsFile.display.resolution"));
        Assert.assertEquals("", test.getString("SettingsFile.display.resolution"));

        Assert.assertFalse(test.contains("SettingsFile.log_level"));
        Assert.assertEquals(0, test.getInt("SettingsFile.log_level"));
    }

}

