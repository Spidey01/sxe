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

package com.spidey01.sxe.android.api15;

import com.spidey01.sxe.core.input.InputCode;

import android.view.KeyEvent;


/* Extended support for API 15. */
public class InputCodeUtils
    extends com.spidey01.sxe.android.common.InputCodeUtils
{
    public static InputCode toSxE(int androidKeyCode) {
        switch (androidKeyCode) {
            /// Requires API 11
            case KeyEvent.KEYCODE_APP_SWITCH: return InputCode.IC_APP_SWITCH;
            case KeyEvent.KEYCODE_AVR_INPUT: return InputCode.IC_AVR_INPUT;
            case KeyEvent.KEYCODE_AVR_POWER: return InputCode.IC_AVR_POWER;
            case KeyEvent.KEYCODE_BOOKMARK: return InputCode.IC_BOOKMARK;
            case KeyEvent.KEYCODE_BREAK: return InputCode.IC_BREAK;
            case KeyEvent.KEYCODE_CAPS_LOCK: return InputCode.IC_CAPS_LOCK;
            case KeyEvent.KEYCODE_CAPTIONS: return InputCode.IC_CAPTIONS;
            case KeyEvent.KEYCODE_CHANNEL_DOWN: return InputCode.IC_CHANNEL_DOWN;
            case KeyEvent.KEYCODE_CHANNEL_UP: return InputCode.IC_CHANNEL_UP;
            case KeyEvent.KEYCODE_CTRL_LEFT: return InputCode.IC_CTRL_LEFT;
            case KeyEvent.KEYCODE_CTRL_RIGHT: return InputCode.IC_CTRL_RIGHT;
            case KeyEvent.KEYCODE_DVR: return InputCode.IC_DVR;
            case KeyEvent.KEYCODE_ESCAPE: return InputCode.IC_ESCAPE;
            case KeyEvent.KEYCODE_F10: return InputCode.IC_F10;
            case KeyEvent.KEYCODE_F11: return InputCode.IC_F11;
            case KeyEvent.KEYCODE_F12: return InputCode.IC_F12;
            case KeyEvent.KEYCODE_F1: return InputCode.IC_F1;
            case KeyEvent.KEYCODE_F2: return InputCode.IC_F2;
            case KeyEvent.KEYCODE_F3: return InputCode.IC_F3;
            case KeyEvent.KEYCODE_F4: return InputCode.IC_F4;
            case KeyEvent.KEYCODE_F5: return InputCode.IC_F5;
            case KeyEvent.KEYCODE_F6: return InputCode.IC_F6;
            case KeyEvent.KEYCODE_F7: return InputCode.IC_F7;
            case KeyEvent.KEYCODE_F8: return InputCode.IC_F8;
            case KeyEvent.KEYCODE_F9: return InputCode.IC_F9;
            case KeyEvent.KEYCODE_FORWARD: return InputCode.IC_FORWARD;
            case KeyEvent.KEYCODE_FORWARD_DEL: return InputCode.IC_FORWARD_DEL;
            case KeyEvent.KEYCODE_FUNCTION: return InputCode.IC_FUNCTION;
            case KeyEvent.KEYCODE_GUIDE: return InputCode.IC_GUIDE;
            case KeyEvent.KEYCODE_INFO: return InputCode.IC_INFO;
            case KeyEvent.KEYCODE_INSERT: return InputCode.IC_INSERT;
            case KeyEvent.KEYCODE_MEDIA_CLOSE: return InputCode.IC_MEDIA_CLOSE;
            case KeyEvent.KEYCODE_MEDIA_EJECT: return InputCode.IC_MEDIA_EJECT;
            case KeyEvent.KEYCODE_MEDIA_PAUSE: return InputCode.IC_MEDIA_PAUSE;
            case KeyEvent.KEYCODE_MEDIA_PLAY: return InputCode.IC_MEDIA_PLAY;
            case KeyEvent.KEYCODE_MEDIA_RECORD: return InputCode.IC_MEDIA_RECORD;
            case KeyEvent.KEYCODE_META_LEFT: return InputCode.IC_META_LEFT;
            case KeyEvent.KEYCODE_META_RIGHT: return InputCode.IC_META_RIGHT;
            case KeyEvent.KEYCODE_MOVE_END: return InputCode.IC_MOVE_END;
            case KeyEvent.KEYCODE_MOVE_HOME: return InputCode.IC_MOVE_HOME;
            case KeyEvent.KEYCODE_NUMPAD_0: return InputCode.IC_NUMPAD_0;
            case KeyEvent.KEYCODE_NUMPAD_1: return InputCode.IC_NUMPAD_1;
            case KeyEvent.KEYCODE_NUMPAD_2: return InputCode.IC_NUMPAD_2;
            case KeyEvent.KEYCODE_NUMPAD_3: return InputCode.IC_NUMPAD_3;
            case KeyEvent.KEYCODE_NUMPAD_4: return InputCode.IC_NUMPAD_4;
            case KeyEvent.KEYCODE_NUMPAD_5: return InputCode.IC_NUMPAD_5;
            case KeyEvent.KEYCODE_NUMPAD_6: return InputCode.IC_NUMPAD_6;
            case KeyEvent.KEYCODE_NUMPAD_7: return InputCode.IC_NUMPAD_7;
            case KeyEvent.KEYCODE_NUMPAD_8: return InputCode.IC_NUMPAD_8;
            case KeyEvent.KEYCODE_NUMPAD_9: return InputCode.IC_NUMPAD_9;
            case KeyEvent.KEYCODE_NUMPAD_ADD: return InputCode.IC_NUMPAD_ADD;
            case KeyEvent.KEYCODE_NUMPAD_COMMA: return InputCode.IC_NUMPAD_COMMA;
            case KeyEvent.KEYCODE_NUMPAD_DIVIDE: return InputCode.IC_NUMPAD_DIVIDE;
            case KeyEvent.KEYCODE_NUMPAD_DOT: return InputCode.IC_NUMPAD_DOT;
            case KeyEvent.KEYCODE_NUMPAD_ENTER: return InputCode.IC_NUMPAD_ENTER;
            case KeyEvent.KEYCODE_NUMPAD_EQUALS: return InputCode.IC_NUMPAD_EQUALS;
            case KeyEvent.KEYCODE_NUMPAD_LEFT_PAREN: return InputCode.IC_NUMPAD_LEFT_PAREN;
            case KeyEvent.KEYCODE_NUMPAD_MULTIPLY: return InputCode.IC_NUMPAD_MULTIPLY;
            case KeyEvent.KEYCODE_NUMPAD_RIGHT_PAREN: return InputCode.IC_NUMPAD_RIGHT_PAREN;
            case KeyEvent.KEYCODE_NUMPAD_SUBTRACT: return InputCode.IC_NUMPAD_SUBTRACT;
            case KeyEvent.KEYCODE_NUM_LOCK: return InputCode.IC_NUM_LOCK;
            case KeyEvent.KEYCODE_PROG_BLUE: return InputCode.IC_PROG_BLUE;
            case KeyEvent.KEYCODE_PROG_GREEN: return InputCode.IC_PROG_GREEN;
            case KeyEvent.KEYCODE_PROG_RED: return InputCode.IC_PROG_RED;
            case KeyEvent.KEYCODE_PROG_YELLOW: return InputCode.IC_PROG_YELLOW;
            case KeyEvent.KEYCODE_SCROLL_LOCK: return InputCode.IC_SCROLL_LOCK;
            case KeyEvent.KEYCODE_SETTINGS: return InputCode.IC_SETTINGS;
            case KeyEvent.KEYCODE_STB_INPUT: return InputCode.IC_STB_INPUT;
            case KeyEvent.KEYCODE_STB_POWER: return InputCode.IC_STB_POWER;
            case KeyEvent.KEYCODE_SYSRQ: return InputCode.IC_SYSRQ;
            case KeyEvent.KEYCODE_TV: return InputCode.IC_TV;
            case KeyEvent.KEYCODE_TV_INPUT: return InputCode.IC_TV_INPUT;
            case KeyEvent.KEYCODE_TV_POWER: return InputCode.IC_TV_POWER;
            case KeyEvent.KEYCODE_VOLUME_MUTE: return InputCode.IC_VOLUME_MUTE;
            case KeyEvent.KEYCODE_WINDOW: return InputCode.IC_WINDOW;
            case KeyEvent.KEYCODE_ZOOM_IN: return InputCode.IC_ZOOM_IN;
            case KeyEvent.KEYCODE_ZOOM_OUT: return InputCode.IC_ZOOM_OUT;

            /// Requires API 12
            case KeyEvent.KEYCODE_BUTTON_10: return InputCode.IC_BUTTON_10;
            case KeyEvent.KEYCODE_BUTTON_11: return InputCode.IC_BUTTON_11;
            case KeyEvent.KEYCODE_BUTTON_12: return InputCode.IC_BUTTON_12;
            case KeyEvent.KEYCODE_BUTTON_13: return InputCode.IC_BUTTON_13;
            case KeyEvent.KEYCODE_BUTTON_14: return InputCode.IC_BUTTON_14;
            case KeyEvent.KEYCODE_BUTTON_15: return InputCode.IC_BUTTON_15;
            case KeyEvent.KEYCODE_BUTTON_16: return InputCode.IC_BUTTON_16;
            case KeyEvent.KEYCODE_BUTTON_1: return InputCode.IC_BUTTON_1;
            case KeyEvent.KEYCODE_BUTTON_2: return InputCode.IC_BUTTON_2;
            case KeyEvent.KEYCODE_BUTTON_3: return InputCode.IC_BUTTON_3;
            case KeyEvent.KEYCODE_BUTTON_4: return InputCode.IC_BUTTON_4;
            case KeyEvent.KEYCODE_BUTTON_5: return InputCode.IC_BUTTON_5;
            case KeyEvent.KEYCODE_BUTTON_6: return InputCode.IC_BUTTON_6;
            case KeyEvent.KEYCODE_BUTTON_7: return InputCode.IC_BUTTON_7;
            case KeyEvent.KEYCODE_BUTTON_8: return InputCode.IC_BUTTON_8;
            case KeyEvent.KEYCODE_BUTTON_9: return InputCode.IC_BUTTON_9;

            /// Requires API 14
            case KeyEvent.KEYCODE_3D_MODE: return InputCode.IC_3D_MODE;
            case KeyEvent.KEYCODE_LANGUAGE_SWITCH: return InputCode.IC_LANGUAGE_SWITCH;
            case KeyEvent.KEYCODE_MANNER_MODE: return InputCode.IC_MANNER_MODE;

            /// Requires API 15
            case KeyEvent.KEYCODE_CALCULATOR: return InputCode.IC_CALCULATOR;
            case KeyEvent.KEYCODE_CALENDAR: return InputCode.IC_CALENDAR;
            case KeyEvent.KEYCODE_CONTACTS: return InputCode.IC_CONTACTS;
            case KeyEvent.KEYCODE_MUSIC: return InputCode.IC_MUSIC;
        }
        return com.spidey01.sxe.android.common.InputCodeUtils.toSxE(androidKeyCode);
    }
}

