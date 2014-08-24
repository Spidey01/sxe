/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

import com.spidey01.sxe.core.logging.Log;

import java.util.HashMap;
import java.util.Map;

/** Facet enabling binding keys to.
 *
 * Rather than writing your own input handling code with {@link InputManager},
 * you can utilize this facet to manage input. Composition ftw?
 *
 * You provide {@link Runnable} that will be executed when the bound input
 * event is passed through the associated {@link InputManager}.
 */

public class InputFacet
    implements KeyListener /* Must be implemented for binding keys. */
{
    private static final String TAG = "InputFacet";

    private InputManager mInputManager;

    private Map<InputCode, Runnable> mKeyMap = new HashMap<InputCode, Runnable>();


    public InputFacet(InputManager provider) {
        mInputManager = provider;
    }


    /** Bind action to keys.
     *
     * @param action the {@link Runnable} to act on.
     * @param keys InputCodes to trigger action.
     */
    public void bindAction(Runnable action, InputCode[] keys) {
        for (InputCode key : keys) {
            mKeyMap.put(key, action);
            mInputManager.addKeyListener(key, this);
        }
    }


    @Override
    public boolean onKey(KeyEvent event) {
        Runnable action = mKeyMap.get(event.getKeyCode());

        if (action == null) {
            return false;
        }

        Log.xtrace(TAG, "onKey() firing action", action);
        action.run();

        return true;
    }
}


