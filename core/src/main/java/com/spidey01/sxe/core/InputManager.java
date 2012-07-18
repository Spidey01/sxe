package com.spidey01.sxe.core;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

// TODO maybe make key listeners be Map<String, List<KeyListener>>
public abstract class InputManager {
    
    /** List of listeners who wish to receive a broad cast of any key event */
    protected List<KeyListener> mKeyBroadcastReceivers =
        new LinkedList<KeyListener>();

    /** Map of keys to listeners for bound keys */
    protected Map<String, List<KeyListener>> mKeyListeners =
        new HashMap<String, List<KeyListener>>();

    private static final String TAG = "InputManager";

    public void poll() {
    }

    /** Add a KeyListener for key events.
     *
     * This will register a KeyListener that will be called whenever a key event occurs.
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
     * @param listener the KeyListener.
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
}

