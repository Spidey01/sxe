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

import com.spidey01.sxe.core.cmds.EchoCommand;
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.testing.NullDisplay;
import com.spidey01.sxe.core.testing.NullInputManager;
import com.spidey01.sxe.core.testing.NullOpenGL;
import com.spidey01.sxe.core.testing.UnitTest;

import org.junit.*;


public class ConsoleTest extends UnitTest {
    private static final String TAG = "ConsoleTest";

    /** Helper class for test.
     *
     * This just extends Console so that we can inspect the command.
     */
    private class MyConsole extends Console {
        private static final String TAG = "MyConsole";
        public String command;


        @Override
        public void execute(String line) {
            command = line;
            Log.xtrace(TAG, "proof: ", "|"+command+"|");
            super.execute(line);
        }


    }

    private MyConsole mConsole;
    private NullInputManager mInputManager;
    private NullDisplay mDisplay;


    @BeforeClass
    public static void beforeClass() {
        UnitTest.setup();
    }


    @Before
    public void before() {
        Log.i(TAG, "before()");
        mInputManager = new NullInputManager();
        mDisplay = new NullDisplay(new NullOpenGL(), true);

        mConsole = new MyConsole();

        mDisplay.addFrameListener(mConsole);
        mInputManager.addKeyListener(mConsole);
    }


    protected void sendToggle() {
        mInputManager.inject(Console.DEFAULT_TOGGLE_KEY, false);
    }


    protected void sendCommandLine(String line) {
        mInputManager.inject(line);
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


    private static final String sSimpleCommand_command = "echo \"This is a simple command\"";

    @Test
    public void simpleCommand() {
        Log.i(TAG, "simpleCommand()");
        mConsole.setVisible(true);
        sendCommandLine(sSimpleCommand_command);
        Assert.assertEquals(sSimpleCommand_command, mConsole.command);
    }


    @Test
    public void linear() {
        Log.i(TAG, "linear()");
        mConsole.setVisible(true);
        for (int i=0; i < Console.VALID_SYMBOLS.length(); ++i) {
            mInputManager.inject(String.valueOf(Console.VALID_SYMBOLS.charAt(i)), true);
        }
    }


    private static final String sUnaryCommand_command = "quit";
    @Test
    public void unaryCommand() {
        Log.i(TAG, "unaryCommand()");
        mConsole.setVisible(true);
        sendCommandLine(sUnaryCommand_command);
        Assert.assertEquals(sUnaryCommand_command, mConsole.command);
    }



    @Test
    public void echoCommand() {
        Log.i(TAG, "echoCommand()");
        mConsole.setVisible(true);
        EchoCommand echoCommand = new EchoCommand();
        mConsole.addCommand(echoCommand);


        String echo;
        String[] args;


        echo = "echo foo bar";
        args = new String[] { "foo", "bar" };
        sendCommandLine(echo);
        Assert.assertEquals(args, echoCommand.getArgs());

        echo = "echo 'foo bar'";
        args = new String[] { "foo bar" };
        sendCommandLine(echo);
        Assert.assertEquals(args, echoCommand.getArgs());

        echo = "echo \"foo bar\"";
        sendCommandLine(echo);
        Assert.assertEquals(args, echoCommand.getArgs());

        echo = "echo `foo bar`";
        sendCommandLine(echo);
        Assert.assertEquals(args, echoCommand.getArgs());

        echo = "echo \\\"foo bar\\\"";
        args = new String[] { "\\\"foo", "bar\\\"" };
        sendCommandLine(echo);
        Assert.assertEquals(args, echoCommand.getArgs());
    }
}

