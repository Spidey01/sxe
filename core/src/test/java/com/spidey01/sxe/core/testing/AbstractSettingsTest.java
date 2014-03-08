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

package com.spidey01.sxe.core.testing;

import com.spidey01.sxe.core.cfg.Settings;
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.testing.UnitTest;

import org.junit.*;

import java.io.File;
import java.io.IOException;


public abstract class AbstractSettingsTest
    extends UnitTest
    implements Settings.OnChangedListener
{
    private static final String TAG = "AbstractSettingsTest";

    @BeforeClass
    public static void setup() {
        UnitTest.setup();
    }


    protected abstract String getTag();
    protected abstract Settings makeSettings();


    @Test
    public void load() {
        Settings test = makeSettings();

        Assert.assertTrue(test.contains("debug"));
        Assert.assertTrue(test.getBoolean("debug"));

        Assert.assertTrue(test.contains(getTag()+".display.resolution"));
        Assert.assertEquals("1280x720", test.getString(getTag()+".display.resolution"));

        Assert.assertTrue(test.contains(getTag()+".log_level"));
        Assert.assertEquals(10, test.getInt(getTag()+".log_level"));
    }


    @Test
    public void clear() {
        Settings test = makeSettings();
        test.clear();

        Assert.assertFalse(test.contains("debug"));
        Assert.assertFalse(test.getBoolean("debug"));

        Assert.assertFalse(test.contains(getTag()+".display.resolution"));
        Assert.assertEquals("", test.getString(getTag()+".display.resolution"));

        Assert.assertFalse(test.contains(getTag()+".log_level"));
        Assert.assertEquals(0, test.getInt(getTag()+".log_level"));
    }


    @Test
    public void merge() {
        Settings p = makeSettings();
        Settings c = makeSettings();

        Assert.assertFalse(p.contains("merge.c"));
        c.setBoolean("merge.c", true);
        p.merge(c);
        Assert.assertTrue(p.contains("merge.c"));
        Assert.assertTrue(p.getBoolean("merge.c"));
    }


    private boolean mOnChanged_called;


    @Test
    public void onChangedListener_onChanged() {
        Settings p = makeSettings();
        mOnChanged_called = false;

        p.addChangeListener(this);
        p.setInt("foo", 1);
        Assert.assertTrue(mOnChanged_called);

        mOnChanged_called = false;
        p.removeChangeListener(this);
        p.setInt("foo", 0);
        Assert.assertFalse(mOnChanged_called);
    }


    public void onChanged(String key) {
        Assert.assertNotNull(key);
        Assert.assertFalse(key.isEmpty());
        mOnChanged_called = true;
    }

}

