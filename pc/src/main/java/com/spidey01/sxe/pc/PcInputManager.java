package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.InputManager;

import org.lwjgl.input.Keyboard;

public class PcInputManager extends InputManager {

    public PcInputManager() {
        Keyboard.enableRepeatEvents(true);
    }

    public void poll() {
        /* pump the keyboard buffer */
        while (Keyboard.next()) {
            // char k = Keyboard.getEventCharacter();
            String k = Keyboard.getKeyName(Keyboard.getEventKey());
            System.out.println("Key event = "+k+" from thread "+Thread.currentThread().getId());

            Action a = keyBindings.get(k);
            if (a != null) {
                a.execute();
            }
        }
    }

}

