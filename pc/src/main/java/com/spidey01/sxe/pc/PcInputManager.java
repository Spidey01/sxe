package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.Action;
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
            String k = Keyboard.getKeyName(Keyboard.getEventKey());
            Log.v(TAG, "Key event = "+k);

            Action a = mKeyBindings.get(k);
            if (a != null) {
                a.execute();
            }
        }
    }

}

