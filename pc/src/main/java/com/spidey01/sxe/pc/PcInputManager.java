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

package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.common.Subsystem;
import com.spidey01.sxe.core.input.AbstractInputManager;
import com.spidey01.sxe.core.input.InputCode;
import com.spidey01.sxe.core.input.KeyEvent;
import com.spidey01.sxe.core.input.KeyListener;

import org.lwjgl.input.Keyboard;

public class PcInputManager extends AbstractInputManager {

    private static final String TAG = "PcInputManager";


    public PcInputManager() {
        Keyboard.enableRepeatEvents(true);
    }


    @Override
    public void poll() {
        /* pump the keyboard buffer */
        while (Keyboard.next()) {
            // char k = Keyboard.getEventCharacter();
            int code = Keyboard.getEventKey();
            String k = Keyboard.getKeyName(code);
            boolean down = Keyboard.isKeyDown(code);

            KeyEvent e = new KeyEvent(this, null, InputCodeUtils.toSxE(code), k, down);
            notifyKeyListeners(e);
        }
    }


    @Override
    public String name() {
        return TAG;
    }


    @Override
    public void initialize(GameEngine engine) {
        Log.d(TAG, "initialize(", engine, ")");
        super.initialize(engine);
    }


    @Override
    public void uninitialize() {
        Log.d(TAG, "uninitialize()");
        super.uninitialize();
    }
}

