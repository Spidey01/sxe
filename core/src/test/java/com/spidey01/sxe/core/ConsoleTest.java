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

import com.spidey01.sxe.core.testing.UnitTest;
import com.spidey01.sxe.core.testing.NullDisplay;
import com.spidey01.sxe.core.testing.NullInputManager;
import com.spidey01.sxe.core.testing.NullOpenGL;

import org.junit.*;


public class ConsoleTest extends UnitTest {
    private static final String TAG = "SettingsMapTest";


    // private TextConsole mConsole = new TextConsole();
    private Console mConsole; // = new Console();
    private NullInputManager mInputManager; // = new NullInputManager();
    private NullDisplay mDisplay; // = new NullDisplay(new NullOpenGL(), true);


    @BeforeClass
    public static void beforeClass() {
        UnitTest.setup();
    }


    @Before
    public void before() {
        Log.i(TAG, "before()");
        mInputManager = new NullInputManager();
        mDisplay = new NullDisplay(new NullOpenGL(), true);

        mConsole = new Console();

        mDisplay.addFrameListener(mConsole);
        mInputManager.addKeyListener(mConsole);
    }


    protected void sendToggle() {
        mInputManager.inject("GRAVE", false);
    }


    protected void sendKeys(String[] words) {
        for (String word : words) {
            for (int i=0; i < word.length(); ++i) {
                String l = String.valueOf(word.charAt(i));
                mInputManager.inject(l, true);
                mInputManager.inject(l, false);
            }
        }
        mInputManager.inject("RETURN", true);
        mInputManager.inject("RETURN", false);
    }

    protected void sendKeys(String line) {
        sendKeys(line.split("\\s"));
    }


    @Test
    public void toggleByCode() {
        Log.i(TAG, "toggleByCode()");
        Assert.assertFalse("Console should begin hidden.", mConsole.isVisible());
        mConsole.setVisible(true);
        Assert.assertTrue("Console should now be shown.", mConsole.isVisible());
        mConsole.setVisible(false);
        Assert.assertFalse("Console should now be hidden.", mConsole.isVisible());
    }


    @Test
    public void toggleByKey() {
        Log.i(TAG, "toggleByKey()");
        Assert.assertFalse("Console should begin hidden.", mConsole.isVisible());
        sendToggle();
        Assert.assertTrue("Console should now be shown.", mConsole.isVisible());
        sendToggle();
        Assert.assertFalse("Console should now be hidden.", mConsole.isVisible());
    }


    @Test
    public void simpleCommand() {
        Log.i(TAG, "simpleCommand()");
        mConsole.setVisible(true);
        sendKeys("echo \"This is a simple command\"");
    }

}

