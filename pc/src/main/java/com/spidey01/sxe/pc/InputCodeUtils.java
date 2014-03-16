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

package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.input.InputCode;

import org.lwjgl.input.Keyboard;


public class InputCodeUtils {
    /** Translates from LWJGL to SxE key events. */
    public static InputCode toSxE(int lwgjlEventKey) {
        switch (lwgjlEventKey) {
            case Keyboard.KEY_0: return InputCode.IC_0;
            case Keyboard.KEY_1: return InputCode.IC_1;
            case Keyboard.KEY_2: return InputCode.IC_2;
            case Keyboard.KEY_3: return InputCode.IC_3;
            case Keyboard.KEY_4: return InputCode.IC_4;
            case Keyboard.KEY_5: return InputCode.IC_5;
            case Keyboard.KEY_6: return InputCode.IC_6;
            case Keyboard.KEY_7: return InputCode.IC_7;
            case Keyboard.KEY_8: return InputCode.IC_8;
            case Keyboard.KEY_9: return InputCode.IC_9;
            case Keyboard.KEY_A: return InputCode.IC_A;
            case Keyboard.KEY_ADD: return InputCode.IC_PLUS_SIGN;
            case Keyboard.KEY_APOSTROPHE: return InputCode.IC_APOSTROPHE;
            case Keyboard.KEY_AT: return InputCode.IC_AT_SIGN;
            case Keyboard.KEY_B: return InputCode.IC_B;
            case Keyboard.KEY_BACK: return InputCode.IC_BACK;
            case Keyboard.KEY_BACKSLASH: return InputCode.IC_BACKSLASH;
            case Keyboard.KEY_C: return InputCode.IC_C;
            case Keyboard.KEY_CIRCUMFLEX: return InputCode.IC_CARET;
            case Keyboard.KEY_CLEAR: return InputCode.IC_CLEAR;
            case Keyboard.KEY_COLON: return InputCode.IC_COLON;
            case Keyboard.KEY_COMMA: return InputCode.IC_COMMA;
            case Keyboard.KEY_D: return InputCode.IC_D;
            case Keyboard.KEY_DELETE: return InputCode.IC_DELETE;
            case Keyboard.KEY_DOWN: return InputCode.IC_DOWN_ARROW;
            case Keyboard.KEY_E: return InputCode.IC_E;
            case Keyboard.KEY_END: return InputCode.IC_END;
            case Keyboard.KEY_EQUALS: return InputCode.IC_EQUAL_SIGN;
            case Keyboard.KEY_ESCAPE: return InputCode.IC_ESCAPE;
            case Keyboard.KEY_F10: return InputCode.IC_F10;
            case Keyboard.KEY_F11: return InputCode.IC_F11;
            case Keyboard.KEY_F12: return InputCode.IC_F12;
            case Keyboard.KEY_F1: return InputCode.IC_F1;
            case Keyboard.KEY_F2: return InputCode.IC_F2;
            case Keyboard.KEY_F3: return InputCode.IC_F3;
            case Keyboard.KEY_F4: return InputCode.IC_F4;
            case Keyboard.KEY_F5: return InputCode.IC_F5;
            case Keyboard.KEY_F6: return InputCode.IC_F6;
            case Keyboard.KEY_F7: return InputCode.IC_F7;
            case Keyboard.KEY_F8: return InputCode.IC_F8;
            case Keyboard.KEY_F9: return InputCode.IC_F9;
            case Keyboard.KEY_F: return InputCode.IC_F;
            case Keyboard.KEY_G: return InputCode.IC_G;
            case Keyboard.KEY_GRAVE: return InputCode.IC_GRAVE;
            case Keyboard.KEY_H: return InputCode.IC_H;
            case Keyboard.KEY_HOME: return InputCode.IC_HOME;
            case Keyboard.KEY_I: return InputCode.IC_I;
            case Keyboard.KEY_INSERT: return InputCode.IC_INSERT;
            case Keyboard.KEY_J: return InputCode.IC_J;
            case Keyboard.KEY_K: return InputCode.IC_K;
            case Keyboard.KEY_L: return InputCode.IC_L;
            case Keyboard.KEY_LBRACKET: return InputCode.IC_LEFT_SQUARE_BRACKET;
            case Keyboard.KEY_LCONTROL: return InputCode.IC_CTRL_LEFT;
            case Keyboard.KEY_LEFT: return InputCode.IC_LEFT_ARROW;
            case Keyboard.KEY_LMENU: return InputCode.IC_MENU;
            case Keyboard.KEY_LMETA: return InputCode.IC_META_LEFT;
            case Keyboard.KEY_LSHIFT: return InputCode.IC_SHIFT_LEFT;
            case Keyboard.KEY_M: return InputCode.IC_M;
            case Keyboard.KEY_MINUS: return InputCode.IC_HYPHEN_MINUS;
            case Keyboard.KEY_MULTIPLY: return InputCode.IC_ASTERISK;
            case Keyboard.KEY_N: return InputCode.IC_N;
            case Keyboard.KEY_NUMLOCK: return InputCode.IC_NUM_LOCK;
            case Keyboard.KEY_NUMPAD0: return InputCode.IC_NUMPAD_0;
            case Keyboard.KEY_NUMPAD1: return InputCode.IC_NUMPAD_1;
            case Keyboard.KEY_NUMPAD2: return InputCode.IC_NUMPAD_2;
            case Keyboard.KEY_NUMPAD3: return InputCode.IC_NUMPAD_3;
            case Keyboard.KEY_NUMPAD4: return InputCode.IC_NUMPAD_4;
            case Keyboard.KEY_NUMPAD5: return InputCode.IC_NUMPAD_5;
            case Keyboard.KEY_NUMPAD6: return InputCode.IC_NUMPAD_6;
            case Keyboard.KEY_NUMPAD7: return InputCode.IC_NUMPAD_7;
            case Keyboard.KEY_NUMPAD8: return InputCode.IC_NUMPAD_8;
            case Keyboard.KEY_NUMPAD9: return InputCode.IC_NUMPAD_9;
            case Keyboard.KEY_NUMPADCOMMA: return InputCode.IC_NUMPAD_COMMA;
            case Keyboard.KEY_NUMPADENTER: return InputCode.IC_NUMPAD_ENTER;
            case Keyboard.KEY_NUMPADEQUALS: return InputCode.IC_NUMPAD_EQUAL_SIGN;
            case Keyboard.KEY_O: return InputCode.IC_O;
            case Keyboard.KEY_P: return InputCode.IC_P;
            case Keyboard.KEY_PAUSE: return InputCode.IC_PAUSE;
            case Keyboard.KEY_PERIOD: return InputCode.IC_PERIOD;
            case Keyboard.KEY_POWER: return InputCode.IC_POWER;
            case Keyboard.KEY_Q: return InputCode.IC_Q;
            case Keyboard.KEY_R: return InputCode.IC_R;
            case Keyboard.KEY_RBRACKET: return InputCode.IC_RIGHT_SQUARE_BRACKET;
            case Keyboard.KEY_RCONTROL: return InputCode.IC_CTRL_RIGHT;
            case Keyboard.KEY_RETURN: return InputCode.IC_ENTER;
            case Keyboard.KEY_RIGHT: return InputCode.IC_RIGHT_ARROW;
            case Keyboard.KEY_RMENU: return InputCode.IC_MENU;
            case Keyboard.KEY_RMETA: return InputCode.IC_META_RIGHT;
            case Keyboard.KEY_RSHIFT: return InputCode.IC_SHIFT_RIGHT;
            case Keyboard.KEY_S: return InputCode.IC_S;
            case Keyboard.KEY_SCROLL: return InputCode.IC_SCROLL_LOCK;
            case Keyboard.KEY_SEMICOLON: return InputCode.IC_SEMICOLON;
            case Keyboard.KEY_SLASH: return InputCode.IC_SLASH;
            case Keyboard.KEY_SPACE: return InputCode.IC_SPACE;
            case Keyboard.KEY_SUBTRACT: return InputCode.IC_HYPHEN_MINUS;
            case Keyboard.KEY_SYSRQ: return InputCode.IC_SYSRQ;
            case Keyboard.KEY_T: return InputCode.IC_T;
            case Keyboard.KEY_TAB: return InputCode.IC_TAB;
            case Keyboard.KEY_U: return InputCode.IC_U;
            case Keyboard.KEY_UNDERLINE: return InputCode.IC_UNDERSCORE;
            case Keyboard.KEY_UP: return InputCode.IC_UP_ARROW;
            case Keyboard.KEY_V: return InputCode.IC_V;
            case Keyboard.KEY_W: return InputCode.IC_W;
            case Keyboard.KEY_X: return InputCode.IC_X;
            case Keyboard.KEY_Y: return InputCode.IC_Y;
            case Keyboard.KEY_YEN: return InputCode.IC_YEN;
            case Keyboard.KEY_Z: return InputCode.IC_Z;

            /*
             * Codes I'm not sure of or have no translation at this time.
             */

            case Keyboard.KEY_APPS: break;
            case Keyboard.KEY_AX: break;
            case Keyboard.KEY_CAPITAL: break;
            case Keyboard.KEY_CONVERT: break;
            case Keyboard.KEY_DECIMAL: break;
            case Keyboard.KEY_DIVIDE: break;
            case Keyboard.KEY_F13: break;
            case Keyboard.KEY_F14: break;
            case Keyboard.KEY_F15: break;
            case Keyboard.KEY_F16: break;
            case Keyboard.KEY_F17: break;
            case Keyboard.KEY_F18: break;
            case Keyboard.KEY_F19: break;
            case Keyboard.KEY_KANA: break;
            case Keyboard.KEY_KANJI: break;
            case Keyboard.KEY_NEXT: break;
            case Keyboard.KEY_NOCONVERT: break;
            case Keyboard.KEY_PRIOR: break;
            case Keyboard.KEY_SECTION: break;
            case Keyboard.KEY_SLEEP: break;
            case Keyboard.KEY_STOP: break;
            case Keyboard.KEY_NONE:
            default:
                break;
                // Silly Java bellyaches if return here instead of below.
        }
        return InputCode.IC_UNKNOWN;
    }
}

