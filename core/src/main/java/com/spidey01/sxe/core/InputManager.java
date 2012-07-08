package com.spidey01.sxe.core;

import java.util.Map;
import java.util.HashMap;

public abstract class InputManager {
    protected Map<String, Action> mKeyBindings = new HashMap<String, Action>();

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

}

