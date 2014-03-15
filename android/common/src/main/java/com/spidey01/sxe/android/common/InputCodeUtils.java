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

package com.spidey01.sxe.android.common;

import com.spidey01.sxe.core.input.InputCode;

import android.view.KeyEvent;


public class InputCodeUtils {

    /** Translates from Android KeyEvent.KEYCODE_* to SxE InputCode.IC_*. */
    public static InputCode toSxE(int androidKeyCode) {
        switch (androidKeyCode) {
            case KeyEvent.KEYCODE_0: return InputCode.IC_0;
            case KeyEvent.KEYCODE_1: return InputCode.IC_1;
            case KeyEvent.KEYCODE_2: return InputCode.IC_2;
            case KeyEvent.KEYCODE_3: return InputCode.IC_3;
            case KeyEvent.KEYCODE_4: return InputCode.IC_4;
            case KeyEvent.KEYCODE_5: return InputCode.IC_5;
            case KeyEvent.KEYCODE_6: return InputCode.IC_6;
            case KeyEvent.KEYCODE_7: return InputCode.IC_7;
            case KeyEvent.KEYCODE_8: return InputCode.IC_8;
            case KeyEvent.KEYCODE_9: return InputCode.IC_9;
            case KeyEvent.KEYCODE_STAR: return InputCode.IC_STAR;
            case KeyEvent.KEYCODE_POUND: return InputCode.IC_POUND;
            case KeyEvent.KEYCODE_A: return InputCode.IC_A;
            case KeyEvent.KEYCODE_B: return InputCode.IC_B;
            case KeyEvent.KEYCODE_C: return InputCode.IC_C;
            case KeyEvent.KEYCODE_D: return InputCode.IC_D;
            case KeyEvent.KEYCODE_E: return InputCode.IC_E;
            case KeyEvent.KEYCODE_F: return InputCode.IC_F;
            case KeyEvent.KEYCODE_G: return InputCode.IC_G;
            case KeyEvent.KEYCODE_H: return InputCode.IC_H;
            case KeyEvent.KEYCODE_I: return InputCode.IC_I;
            case KeyEvent.KEYCODE_J: return InputCode.IC_J;
            case KeyEvent.KEYCODE_K: return InputCode.IC_K;
            case KeyEvent.KEYCODE_L: return InputCode.IC_L;
            case KeyEvent.KEYCODE_M: return InputCode.IC_M;
            case KeyEvent.KEYCODE_N: return InputCode.IC_N;
            case KeyEvent.KEYCODE_O: return InputCode.IC_O;
            case KeyEvent.KEYCODE_P: return InputCode.IC_P;
            case KeyEvent.KEYCODE_Q: return InputCode.IC_Q;
            case KeyEvent.KEYCODE_R: return InputCode.IC_R;
            case KeyEvent.KEYCODE_S: return InputCode.IC_S;
            case KeyEvent.KEYCODE_T: return InputCode.IC_T;
            case KeyEvent.KEYCODE_U: return InputCode.IC_U;
            case KeyEvent.KEYCODE_V: return InputCode.IC_V;
            case KeyEvent.KEYCODE_W: return InputCode.IC_W;
            case KeyEvent.KEYCODE_X: return InputCode.IC_X;
            case KeyEvent.KEYCODE_Y: return InputCode.IC_Y;
            case KeyEvent.KEYCODE_Z: return InputCode.IC_Z;
            case KeyEvent.KEYCODE_COMMA: return InputCode.IC_COMMA;
            case KeyEvent.KEYCODE_PERIOD: return InputCode.IC_PERIOD;
            case KeyEvent.KEYCODE_ALT_LEFT: return InputCode.IC_ALT_LEFT;
            case KeyEvent.KEYCODE_ALT_RIGHT: return InputCode.IC_ALT_RIGHT;
            case KeyEvent.KEYCODE_SHIFT_LEFT: return InputCode.IC_SHIFT_LEFT;
            case KeyEvent.KEYCODE_SHIFT_RIGHT: return InputCode.IC_SHIFT_RIGHT;
            case KeyEvent.KEYCODE_TAB: return InputCode.IC_TAB;
            case KeyEvent.KEYCODE_SPACE: return InputCode.IC_SPACE;
            case KeyEvent.KEYCODE_ENTER: return InputCode.IC_ENTER;
            case KeyEvent.KEYCODE_DEL: return InputCode.IC_DEL;
            case KeyEvent.KEYCODE_GRAVE: return InputCode.IC_GRAVE;
            case KeyEvent.KEYCODE_MINUS: return InputCode.IC_MINUS;
            case KeyEvent.KEYCODE_EQUALS: return InputCode.IC_EQUALS;
            case KeyEvent.KEYCODE_PLUS: return InputCode.IC_PLUS;
            case KeyEvent.KEYCODE_LEFT_BRACKET: return InputCode.IC_LEFT_BRACKET;
            case KeyEvent.KEYCODE_RIGHT_BRACKET: return InputCode.IC_RIGHT_BRACKET;
            case KeyEvent.KEYCODE_BACKSLASH: return InputCode.IC_BACKSLASH;
            case KeyEvent.KEYCODE_SEMICOLON: return InputCode.IC_SEMICOLON;
            case KeyEvent.KEYCODE_APOSTROPHE: return InputCode.IC_APOSTROPHE;
            case KeyEvent.KEYCODE_SLASH: return InputCode.IC_SLASH;
            case KeyEvent.KEYCODE_AT: return InputCode.IC_AT;
            case KeyEvent.KEYCODE_PAGE_UP: return InputCode.IC_PAGE_UP;
            case KeyEvent.KEYCODE_PAGE_DOWN: return InputCode.IC_PAGE_DOWN;
            case KeyEvent.KEYCODE_VOLUME_UP: return InputCode.IC_VOLUME_UP;
            case KeyEvent.KEYCODE_VOLUME_DOWN: return InputCode.IC_VOLUME_DOWN;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE: return InputCode.IC_MEDIA_PLAY_PAUSE;
            case KeyEvent.KEYCODE_MEDIA_STOP: return InputCode.IC_MEDIA_STOP;
            case KeyEvent.KEYCODE_MEDIA_NEXT: return InputCode.IC_MEDIA_NEXT;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS: return InputCode.IC_MEDIA_PREVIOUS;
            case KeyEvent.KEYCODE_MEDIA_REWIND: return InputCode.IC_MEDIA_REWIND;
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD: return InputCode.IC_MEDIA_FAST_FORWARD;
            case KeyEvent.KEYCODE_MUTE: return InputCode.IC_MUTE;
            case KeyEvent.KEYCODE_HEADSETHOOK: return InputCode.IC_HEADSETHOOK;
            case KeyEvent.KEYCODE_SYM: return InputCode.IC_SYM;
            case KeyEvent.KEYCODE_PICTSYMBOLS: return InputCode.IC_PICTSYMBOLS;
            case KeyEvent.KEYCODE_SWITCH_CHARSET: return InputCode.IC_SWITCH_CHARSET;
            case KeyEvent.KEYCODE_BUTTON_A: return InputCode.IC_BUTTON_A;
            case KeyEvent.KEYCODE_BUTTON_B: return InputCode.IC_BUTTON_B;
            case KeyEvent.KEYCODE_BUTTON_C: return InputCode.IC_BUTTON_C;
            case KeyEvent.KEYCODE_BUTTON_X: return InputCode.IC_BUTTON_X;
            case KeyEvent.KEYCODE_BUTTON_Y: return InputCode.IC_BUTTON_Y;
            case KeyEvent.KEYCODE_BUTTON_Z: return InputCode.IC_BUTTON_Z;
            case KeyEvent.KEYCODE_BUTTON_L1: return InputCode.IC_BUTTON_L1;
            case KeyEvent.KEYCODE_BUTTON_R1: return InputCode.IC_BUTTON_R1;
            case KeyEvent.KEYCODE_BUTTON_L2: return InputCode.IC_BUTTON_L2;
            case KeyEvent.KEYCODE_BUTTON_R2: return InputCode.IC_BUTTON_R2;
            case KeyEvent.KEYCODE_BUTTON_THUMBL: return InputCode.IC_BUTTON_THUMBL;
            case KeyEvent.KEYCODE_BUTTON_THUMBR: return InputCode.IC_BUTTON_THUMBR;
            case KeyEvent.KEYCODE_BUTTON_START: return InputCode.IC_BUTTON_START;
            case KeyEvent.KEYCODE_BUTTON_SELECT: return InputCode.IC_BUTTON_SELECT;
            case KeyEvent.KEYCODE_BUTTON_MODE: return InputCode.IC_BUTTON_MODE;
            case KeyEvent.KEYCODE_EXPLORER: return InputCode.IC_EXPLORER;
            case KeyEvent.KEYCODE_ENVELOPE: return InputCode.IC_ENVELOPE;
            case KeyEvent.KEYCODE_BACK: return InputCode.IC_BACK;
            case KeyEvent.KEYCODE_CALL: return InputCode.IC_CALL;
            case KeyEvent.KEYCODE_ENDCALL: return InputCode.IC_ENDCALL;
            case KeyEvent.KEYCODE_SOFT_LEFT: return InputCode.IC_SOFT_LEFT;
            case KeyEvent.KEYCODE_SOFT_RIGHT: return InputCode.IC_SOFT_RIGHT;
            case KeyEvent.KEYCODE_HOME: return InputCode.IC_HOME;
            case KeyEvent.KEYCODE_DPAD_UP: return InputCode.IC_DPAD_UP;
            case KeyEvent.KEYCODE_DPAD_DOWN: return InputCode.IC_DPAD_DOWN;
            case KeyEvent.KEYCODE_DPAD_LEFT: return InputCode.IC_DPAD_LEFT;
            case KeyEvent.KEYCODE_DPAD_RIGHT: return InputCode.IC_DPAD_RIGHT;
            case KeyEvent.KEYCODE_DPAD_CENTER: return InputCode.IC_DPAD_CENTER;
            case KeyEvent.KEYCODE_POWER: return InputCode.IC_POWER;
            case KeyEvent.KEYCODE_CAMERA: return InputCode.IC_CAMERA;
            case KeyEvent.KEYCODE_CLEAR: return InputCode.IC_CLEAR;
            case KeyEvent.KEYCODE_NUM: return InputCode.IC_NUM;
            case KeyEvent.KEYCODE_FOCUS: return InputCode.IC_FOCUS;
            case KeyEvent.KEYCODE_MENU: return InputCode.IC_MENU;
            case KeyEvent.KEYCODE_NOTIFICATION: return InputCode.IC_NOTIFICATION;
            case KeyEvent.KEYCODE_SEARCH: return InputCode.IC_SEARCH;
            /*
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

            /// Requires API 16
            case KeyEvent.KEYCODE_EISU: return InputCode.IC_EISU;
            case KeyEvent.KEYCODE_HENKAN: return InputCode.IC_HENKAN;
            case KeyEvent.KEYCODE_KANA: return InputCode.IC_KANA;
            case KeyEvent.KEYCODE_KATAKANA_HIRAGANA: return InputCode.IC_KATAKANA_HIRAGANA;
            case KeyEvent.KEYCODE_MUHENKAN: return InputCode.IC_MUHENKAN;
            case KeyEvent.KEYCODE_RO: return InputCode.IC_RO;
            case KeyEvent.KEYCODE_YEN: return InputCode.IC_YEN;
            case KeyEvent.KEYCODE_ZENKAKU_HANKAKU: return InputCode.IC_ZENKAKU_HANKAKU;

            /// Requires API 18
            case KeyEvent.KEYCODE_BRIGHTNESS_DOWN: return InputCode.IC_BRIGHTNESS_DOWN;
            case KeyEvent.KEYCODE_BRIGHTNESS_UP: return InputCode.IC_BRIGHTNESS_UP;

            /// Requires API 19
            case KeyEvent.KEYCODE_MEDIA_AUDIO_TRACK: return InputCode.IC_MEDIA_AUDIO_TRACK;
            */
        }
        return InputCode.IC_UNKNOWN;
    }
}

