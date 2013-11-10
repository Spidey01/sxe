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

package com.spidey01.sxe.core.input;

import com.spidey01.sxe.core.Log;

import java.io.Console;

/** InputManager for a Console / Terminal environment.
 *
 * You would use this implementation of InputManager when you want to create a
 * Read, Eval, Print, Loop (REPL) type of program. Such as a server process
 * with it's own console interface.
 */
public class ConsoleInputManager extends AbstractInputManager {
    private static final String TAG = "ConsoleInputManager";
    private final Console mTerminal;
    private String mPrompt;


    public ConsoleInputManager() {
        this(System.console(), "SxE->");
    }


    /** Initialize console input management.
     *
     * @param terminal A Java Console to poll from.
     * @param prompt Initial command prompt.
     */
    public ConsoleInputManager(Console terminal, String prompt) {
        mPrompt = prompt;
        mTerminal = terminal;

        if (mTerminal == null) {
            Log.e(TAG, "Term");
            throw new RuntimeException("Process was spawned without a system console.");
        }
    }


    public String getPrompt() {
        return mPrompt;
    }


    public void setPrompt(String newPrompt) {
        mPrompt = newPrompt;
    }


    /** Poll console for new input and notify listeners.
     *
     * Given the nature of REPLs and how little desire I have to get epically
     * fancy with console I/O in Java, this is a line based poll using
     * java.io.Console.readLine(). It will inject() key events correctly but it
     * will batch events by line. D'uh.
     */
    public void poll() {
        synchronized(mTerminal.reader()) {
            inject(mTerminal.readLine(mPrompt));
        }
    }

}


