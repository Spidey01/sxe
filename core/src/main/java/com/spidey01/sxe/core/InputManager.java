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

package com.spidey01.sxe.core;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

/** Class for managing game input.
 *
 * Allows registering listeners for key events, etc. The input manager will
 * only pass on input to the listeners when the appropriate notify method is
 * executed.
 *
 * Platform specific implementations of this class will provide the backend for
 * obtaining input that can then be passed on to an appropriate notify method.
 * The reccomended way to do this is overriding poll(), the default mainLoop for
 * GameEngine expects it.
 */
public abstract class InputManager {
// TODO maybe make key listeners be Map<String, List<KeyListener>>
    
    /** List of listeners who wish to receive a broad cast of any key event */
    protected List<KeyListener> mKeyBroadcastReceivers =
        new LinkedList<KeyListener>();

    /** Map of keys to listeners for bound keys */
    protected Map<String, List<KeyListener>> mKeyListeners =
        new HashMap<String, List<KeyListener>>();

    private static final String TAG = "InputManager";

    /** Poll for new input and notify listeners.
     *
     * The default implementation does nothing.
     */
    public void poll() {
    }

    /** Add a KeyListener to recieve all KeyEvent.
     *
     * This will register a KeyListener that will be called whenever a key
     * event occurs. Note that in the default implementation of
     * NotifyKeyListeners, the first KeyListener to return true from onKey
     * consumes the event and it will <strong>not</strong> be passed on to
     * subsequent KeyListeners.
     *
     * @see #notifyKeyListeners
     * @see KeyEvent
     */
    public void addKeyListener(KeyListener listener) {
        mKeyBroadcastReceivers.add(listener);
    }

    /** Add a KeyListener for a specified key.
     *
     * This provides a way to register a key listener for a specific key. A key
     * may have many listeners. They will be called in the order the were
     * added, until a KeyListener returns true.
     *
     * You can use this to implement keybinds for game play.
     *
     * @param keyName what key to listen for.
     * @param listener the KeyListener for keyName.
     * @see KeyEvent
     */
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

    /** Dispatch KeyEvent to registered listeners.
     *
     * Notification is to delivered in the following order to each
     * KeyListener until their onKey method returns true.
     *
     * <ol>
     *  <li>In order of registeration, each KeyListener registered for broadcast until event is consumed.
     *  <li>In order of registration, each KeyListener registered to this specific key.
     * </ol>
     *
     * The KeyEvent is considered consomed when the KeyListener returns true
     * from onKey(); which is the method invokved.
     *
     * @see KeyListener
     * @see KeyEvent
     */
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



    /** Inject key events.
     *
     * Note that this injects the event to key listeners, not the host
     * operating system or hardware environment.
     */
    public void inject(KeyEvent event) {
        Log.xtrace(TAG, "inject(", event, ")");
        notifyKeyListeners(event);
    }


    /** Inject key event by name.
     *
     * @param keyName key name of the event.
     * @param isDown whether this is a key down or up event.
     */
    public void inject(String keyName, boolean isDown) {
        // TODO: figure out a key code for the event. -1 shouldn't be on the kb.
        inject(new KeyEvent(this, -1, keyName, isDown));
    }


}

