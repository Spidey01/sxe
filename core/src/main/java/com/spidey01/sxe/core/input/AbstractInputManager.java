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
    

    /** List of listeners who wish to receive a broad cast of any key event */
    protected List<KeyListener> mKeyBroadcastReceivers =
        new LinkedList<KeyListener>();


    /** Map of keys to listeners for bound keys */
    protected Map<String, List<KeyListener>> mKeyListeners =
        new HashMap<String, List<KeyListener>>();


    public abstract void poll();


    public void addKeyListener(KeyListener listener) {
        mKeyBroadcastReceivers.add(listener);
    }


    public void addKeyListener(String keyName, KeyListener listener) {
        List<KeyListener> bindings = mKeyListeners.get(keyName);
        if (bindings == null) {
            bindings = new LinkedList<KeyListener>();
            mKeyListeners.put(keyName, bindings);
            Log.v(TAG, "created list of KeyListener for '"+keyName+"'");
        }

        bindings.add(listener);
        Log.v(TAG, listener+" is now listening for '"+keyName+"'");
    }


    public void notifyKeyListeners(KeyEvent event) {
        // broadcast receivers get first dibs on any event.
        for (KeyListener listener : mKeyBroadcastReceivers) {
            if (listener.onKey(event)) {
                return;
            }
        }

        List<KeyListener> bindings = mKeyListeners.get(event.getKeyName());
        if (bindings == null) {
            return;
        }

        for (KeyListener listener : bindings) {
            if (listener.onKey(event)) {
                break;
            }
        }
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

}


