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

package com.spidey01.sxe.core.cmds;

import com.spidey01.sxe.core.common.Utils;
import com.spidey01.sxe.core.logging.Log;


/** Generic command execution.
 *
 * This is used by the Console to implement console commands. A Command is a
 * Runnable that includes extra data useful to Commands.
 */
public class Command implements Runnable {
    private static final String TAG = "Command";

    private final String mName;
    private String[] mArgs;


    public Command(String name) {
        mName = name;
        Log.i(TAG, "Created Command for", mName);
    }


    public Command(String name, String[] args) {
        mName = name;
        mArgs = args;
    }


    public Command(String name, String args) {
        mName = name;
        mArgs = tokenize(args);
    }


    public void run() {
        Log.v(TAG, getName()+" executed");
    }


    public String getName() {
        return mName;
    }


    public boolean equals(Command other) {
        return mName.equals(other.getName());
    }


    public String[] getArgs() {
        return mArgs;
    }

    public void setArgs(String[] args) {
        mArgs = args;
    }


    public void setArgs(String args) {
        mArgs = tokenize(args);
    }


    public String[] tokenize(String s) {
        return Utils.tokenize(s);
    }

}

