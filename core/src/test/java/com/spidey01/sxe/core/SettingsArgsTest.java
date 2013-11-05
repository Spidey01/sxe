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

public class SettingsArgsTest extends UnitTest {
    private static final String TAG = "SettingsArgsTest";


    private static final String[] sArgs = new String[]{
        "debug=true",
        "HelloWorld.display.resolution=1280x720",
        "HelloWorld.resources.path=tmp/",
        "HelloWorld.log_level=10",
        "HelloWorld.log_type=file",
        "HelloWorld.log_file=tmp/HelloWorld.log",
        "HelloWorld.log_flags=DisplayThreadId=true,DisplayDate=true,DisplayTime=true"
    };


    @BeforeClass
    public static void setup() {
        UnitTest.setup();
    }


    @Test
    public void ctor() {
        SettingsArgs test = new SettingsArgs(sArgs);

        Assert.assertTrue(test.contains("debug"));
        Assert.assertTrue(test.getBoolean("debug"));

        Assert.assertTrue(test.contains("HelloWorld.display.resolution"));
        Assert.assertEquals("1280x720", test.getString("HelloWorld.display.resolution"));

        Assert.assertTrue(test.contains("HelloWorld.log_level"));
        Assert.assertEquals(10, test.getInt("HelloWorld.log_level"));
    }



    @Test
    public void clear() {
        SettingsArgs test = new SettingsArgs(sArgs);

        test.clear();

        Assert.assertFalse(test.contains("debug"));
        Assert.assertFalse(test.getBoolean("debug"));

        Assert.assertFalse(test.contains("HelloWorld.display.resolution"));
        Assert.assertEquals("", test.getString("HelloWorld.display.resolution"));

        Assert.assertFalse(test.contains("HelloWorld.log_level"));
        Assert.assertEquals(0, test.getInt("HelloWorld.log_level"));
    }


    @Test(expected = IOException.class)
    public void noSave() throws IOException {
        SettingsArgs p = new SettingsArgs(sArgs);
        p.save();
    }


}

