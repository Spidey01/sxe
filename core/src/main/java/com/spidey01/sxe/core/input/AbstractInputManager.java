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

package com.spidey01.sxe.core.input;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.common.NotificationManager;
import com.spidey01.sxe.core.common.Subsystem;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;


/** Abstract base for managing game input.
 *
 * Provides most of the house keeping for event notifications and injections.
 * You should just subclass this on most platforms and implement poll().
 */
public abstract class AbstractInputManager implements InputManager {
    private static final String TAG = "AbstractInputManager";


    protected class KeyEventManager extends NotificationManager<KeyListener, KeyEvent> {
        @Override
        protected void invoke(KeyListener listener, KeyEvent event) {
            super.invoke(listener, event);
            listener.onKey(event);
        }


        @Override
        protected String asString(KeyEvent event) {
            return event.getKeyName();
        }
    }
    

    protected KeyEventManager mKeyEventManager = new KeyEventManager();


    public abstract void poll();


    public void addKeyListener(KeyListener listener) {
        mKeyEventManager.subscribe(listener);
    }


    public void addKeyListener(String keyName, KeyListener listener) {
        mKeyEventManager.subscribe(listener);
        mKeyEventManager.subscribe(keyName, listener);
    }


    public void notifyKeyListeners(KeyEvent event) {
        mKeyEventManager.notifyListeners(event);
    }


    public void inject(KeyEvent event) {
        Log.xtrace(TAG, "inject(", event, ")");
        notifyKeyListeners(event);
    }


    public void inject(String keyName, boolean isDown) {
        // TODO: figure out a key code for the event. -1 shouldn't be on the kb.
        inject(new KeyEvent(this, -1, keyName, isDown));
    }


    public void inject(String line) {
        String[] words = line.split("\\s");
        String word;

        for (int w=0; w < words.length; ++w) {
            word = words[w];
            for (int ch=0; ch < word.length(); ++ch) {
                String letter = String.valueOf(word.charAt(ch));
                inject(letter, true);
                inject(letter, false);
            }
            if (w < words.length - 1) { // only if not the last letter.
                inject("SPACE", true);
                inject("SPACE", false);
            }
        }
        inject("RETURN", true);
        inject("RETURN", false);
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


