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

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.KeyListener;
import com.spidey01.sxe.core.KeyEvent;
import com.spidey01.sxe.core.InputManager;
import com.spidey01.sxe.core.Log;

import org.lwjgl.input.Keyboard;

public class PcInputManager extends InputManager {

    private static final String TAG = "PcInputManager";

    public PcInputManager() {
        Keyboard.enableRepeatEvents(true);
    }

    public void poll() {
        /* pump the keyboard buffer */
        while (Keyboard.next()) {
            // char k = Keyboard.getEventCharacter();
            int code = Keyboard.getEventKey();
            String k = Keyboard.getKeyName(code);
            boolean down = Keyboard.isKeyDown(code);

            KeyEvent e = new KeyEvent(this, null, code, k, down);
            notifyKeyListeners(e);
        }
    }

}

