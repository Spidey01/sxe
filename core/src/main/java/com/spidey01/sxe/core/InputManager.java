package com.spidey01.sxe.core;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

// TODO maybe make key listeners be Map<String, List<KeyListener>>
public abstract class InputManager {
    protected Map<String, List<KeyListener>> mKeyListeners =
        new HashMap<String, List<KeyListener>>();

    protected Map<String, Action> mKeyBindings = new HashMap<String, Action>();

    private static final String TAG = "InputManager";

    public void poll() {
    }

    /**
     * @param keyName The name of the key to bind.
     * @param newAction The action to be executed()'d on keyName.
     * @returns The old Action bound to keyName, or null.
     */
    public Action bindKey(String keyName, Action newAction) {
        Action old = mKeyBindings.get(keyName);
        mKeyBindings.put(keyName, newAction);
        return old;
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

