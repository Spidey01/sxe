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

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.common.Subsystem;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.input.AbstractInputManager;
import com.spidey01.sxe.core.common.Subsystem;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

/** Null input system.
 *
 * No input events are generated unless injected.
 */
public class NullInputManager
    extends AbstractInputManager
    implements Subsystem
{
    private static final String TAG = "NullInputManager";
    
    public NullInputManager() {
        Log.d(TAG, "Default ctor.");
    }


    public void poll() {
        Log.xtrace(TAG,  "poll()");
    }


    @Override
    public String name() {
        return TAG;
    }


    @Override
    public void initialize(GameEngine engine) {
        Log.d(TAG, "initialize(", engine, ")");
    }


    @Override
    public void reinitialize(GameEngine engine) {
        uninitialize();
        initialize(engine);
    }


    @Override
    public void uninitialize() {
        Log.d(TAG, "uninitialize()");
    }
}


