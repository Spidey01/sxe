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

package com.spidey01.sxe.core.entities;

import com.spidey01.sxe.core.input.InputCode;
import com.spidey01.sxe.core.input.InputManager;
import com.spidey01.sxe.core.input.KeyEvent;
import com.spidey01.sxe.core.input.KeyListener;
import com.spidey01.sxe.core.logging.Log;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Entity
    implements KeyListener /* Must be implemented for binding keys. */
{
    private static final String TAG = "Entity";

    protected boolean mIsAlive;

    private InputManager mInputManager;

    private Map<InputCode, Action> mKeyMap = new HashMap<InputCode, Action>();


    public boolean isAlive() {
        return mIsAlive;
    }


    /** Set the InputManager associated with this Entity.
     *
     */
    public final void setInputManager(InputManager manager) {
        // TODO: InputManager lacks this!
        // mInputManager.removeKeyListener(this);

        mKeyMap.clear();
        mInputManager = manager;

        /*
         * ^^^ We need to be sure bound actions get moved. This code is broken!!!! ^^^
         */
    }


    /** Get the InputManager currently associated with this Entity. */
    public final InputManager getInputManager() {
        return mInputManager;
    }



    /** Bind action to keys.
     *
     * @param action the Action to act on.
     * @param keys InputCodes to trigger action.
     */
    public void bindAction(Action action, InputCode[] keys) {
        for (InputCode key : keys) {
            mKeyMap.put(key, action);
            mInputManager.addKeyListener(key, this);
        }
    }


    @Override
    public boolean onKey(KeyEvent event) {
        Action action = mKeyMap.get(event.getKeyCode());

        if (action == null) {
            return false;
        }

        Log.xtrace(TAG, "onKey() firing action", action);
        // action;

        return true;
    }
}

