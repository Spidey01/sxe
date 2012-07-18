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

            Action a = mKeyBindings.get(k);
            if (a != null) {
                a.execute();
            }

            KeyEvent e = new KeyEvent(this, null, code, k, down);
            notifyKeyListeners(e);
        }
    }

}

