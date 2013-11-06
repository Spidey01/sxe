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

package com.spidey01.sxe.android.common;

import com.spidey01.sxe.android.common.AndroidDisplay;

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.InputManager;
import com.spidey01.sxe.core.KeyListener;
import com.spidey01.sxe.core.graphics.Display;

import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.view.View;
import android.view.MotionEvent;
    import android.content.Context;
    import android.widget.Toast;

public class AndroidInputManager extends InputManager {

    private AndroidDisplay mWidget;
    private static final String TAG = "AndroidInputManager";

    public AndroidInputManager(AndroidDisplay display) {
        super();
        mWidget = display;
        mWidget.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // only display on keyup, for ease of testing
                // if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    // return true;
                // }

                String keyName = translateKeyName(keyCode);

                com.spidey01.sxe.core.KeyEvent e =
                    new com.spidey01.sxe.core.KeyEvent(
                        AndroidInputManager.this,   // cool trick!
                        event,
                        keyCode,
                        keyName,
                        (event.getAction() == KeyEvent.ACTION_DOWN));
                notifyKeyListeners(e);

                // Context context = ((AndroidDisplay)mWidget).getContext();
                // String m = keyName+" was pressed from thread "+Thread.currentThread().getId();
                // int duration = Toast.LENGTH_LONG;

                // Toast toast = Toast.makeText(context, m, duration);
                // toast.show();

                return true;
            }
        });
        /* works
        mWidget.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Context context = ((AndroidDisplay)mWidget).getContext();
                String m = "onTouch called from thread "+Thread.currentThread().getId();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, m, duration);
                toast.show();
                return true;
            }
        });
        */
    }

    public void poll() {
        // is there something we can do to poke the GUI to do this, just in case?
        throw new UnsupportedOperationException("poll() not implemented on Android");
    }

    // public Action bindKey(String keyName, Action newAction) {
    // }

    /** Translates from the Android key name to the one used in SXE */
    protected String translateKeyName(String keyName) {
        return keyName.substring(8);
    }

    /** Translates from the Android key code to the one used in SXE */
    protected String translateKeyName(int keyCode) {
        // Blargle, requires Android 3.1 :X
        //return translateKeyName(KeyEvent.keyCodeToString(keyCode));
        return translateKeyName(androidKeyCodeToString(keyCode));
    }

    private String androidKeyCodeToString(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
                return "KEYCODE_0";
            case KeyEvent.KEYCODE_1:
                return "KEYCODE_1";
            case KeyEvent.KEYCODE_2:
                return "KEYCODE_2";
            case KeyEvent.KEYCODE_3:
                return "KEYCODE_3";
            // case KeyEvent.KEYCODE_3D_MODE:
                // return "KEYCODE_3D_MODE";
            case KeyEvent.KEYCODE_4:
                return "KEYCODE_4";
            case KeyEvent.KEYCODE_5:
                return "KEYCODE_5";
            case KeyEvent.KEYCODE_6:
                return "KEYCODE_6";
            case KeyEvent.KEYCODE_7:
                return "KEYCODE_7";
            case KeyEvent.KEYCODE_8:
                return "KEYCODE_8";
            case KeyEvent.KEYCODE_9:
                return "KEYCODE_9";
            case KeyEvent.KEYCODE_A:
                return "KEYCODE_A";
            case KeyEvent.KEYCODE_ALT_LEFT:
                return "KEYCODE_ALT_LEFT";
            case KeyEvent.KEYCODE_ALT_RIGHT:
                return "KEYCODE_ALT_RIGHT";
            case KeyEvent.KEYCODE_APOSTROPHE:
                return "KEYCODE_APOSTROPHE";
            // case KeyEvent.KEYCODE_APP_SWITCH:
                // return "KEYCODE_APP_SWITCH";
            // case KeyEvent.KEYCODE_ASSIST:
                // return "KEYCODE_ASSIST";
            case KeyEvent.KEYCODE_AT:
                return "KEYCODE_AT";
            // case KeyEvent.KEYCODE_AVR_INPUT:
                // return "KEYCODE_AVR_INPUT";
            // case KeyEvent.KEYCODE_AVR_POWER:
                // return "KEYCODE_AVR_POWER";
            case KeyEvent.KEYCODE_B:
                return "KEYCODE_B";
            case KeyEvent.KEYCODE_BACK:
                return "KEYCODE_BACK";
            case KeyEvent.KEYCODE_BACKSLASH:
                return "KEYCODE_BACKSLASH";
            // case KeyEvent.KEYCODE_BOOKMARK:
                // return "KEYCODE_BOOKMARK";
            // case KeyEvent.KEYCODE_BREAK:
                // return "KEYCODE_BREAK";
            // case KeyEvent.KEYCODE_BUTTON_1:
                // return "KEYCODE_BUTTON_1";
            // case KeyEvent.KEYCODE_BUTTON_10:
                // return "KEYCODE_BUTTON_10";
            // case KeyEvent.KEYCODE_BUTTON_11:
                // return "KEYCODE_BUTTON_11";
            // case KeyEvent.KEYCODE_BUTTON_12:
                // return "KEYCODE_BUTTON_12";
            // case KeyEvent.KEYCODE_BUTTON_13:
                // return "KEYCODE_BUTTON_13";
            // case KeyEvent.KEYCODE_BUTTON_14:
                // return "KEYCODE_BUTTON_14";
            // case KeyEvent.KEYCODE_BUTTON_15:
                // return "KEYCODE_BUTTON_15";
            // case KeyEvent.KEYCODE_BUTTON_16:
                // return "KEYCODE_BUTTON_16";
            // case KeyEvent.KEYCODE_BUTTON_2:
                // return "KEYCODE_BUTTON_2";
            // case KeyEvent.KEYCODE_BUTTON_3:
                // return "KEYCODE_BUTTON_3";
            // case KeyEvent.KEYCODE_BUTTON_4:
                // return "KEYCODE_BUTTON_4";
            // case KeyEvent.KEYCODE_BUTTON_5:
                // return "KEYCODE_BUTTON_5";
            // case KeyEvent.KEYCODE_BUTTON_6:
                // return "KEYCODE_BUTTON_6";
            // case KeyEvent.KEYCODE_BUTTON_7:
                // return "KEYCODE_BUTTON_7";
            // case KeyEvent.KEYCODE_BUTTON_8:
                // return "KEYCODE_BUTTON_8";
            // case KeyEvent.KEYCODE_BUTTON_9:
                // return "KEYCODE_BUTTON_9";
            case KeyEvent.KEYCODE_BUTTON_A:
                return "KEYCODE_BUTTON_A";
            case KeyEvent.KEYCODE_BUTTON_B:
                return "KEYCODE_BUTTON_B";
            case KeyEvent.KEYCODE_BUTTON_C:
                return "KEYCODE_BUTTON_C";
            case KeyEvent.KEYCODE_BUTTON_L1:
                return "KEYCODE_BUTTON_L1";
            case KeyEvent.KEYCODE_BUTTON_L2:
                return "KEYCODE_BUTTON_L2";
            case KeyEvent.KEYCODE_BUTTON_MODE:
                return "KEYCODE_BUTTON_MODE";
            case KeyEvent.KEYCODE_BUTTON_R1:
                return "KEYCODE_BUTTON_R1";
            case KeyEvent.KEYCODE_BUTTON_R2:
                return "KEYCODE_BUTTON_R2";
            case KeyEvent.KEYCODE_BUTTON_SELECT:
                return "KEYCODE_BUTTON_SELECT";
            case KeyEvent.KEYCODE_BUTTON_START:
                return "KEYCODE_BUTTON_START";
            case KeyEvent.KEYCODE_BUTTON_THUMBL:
                return "KEYCODE_BUTTON_THUMBL";
            case KeyEvent.KEYCODE_BUTTON_THUMBR:
                return "KEYCODE_BUTTON_THUMBR";
            case KeyEvent.KEYCODE_BUTTON_X:
                return "KEYCODE_BUTTON_X";
            case KeyEvent.KEYCODE_BUTTON_Y:
                return "KEYCODE_BUTTON_Y";
            case KeyEvent.KEYCODE_BUTTON_Z:
                return "KEYCODE_BUTTON_Z";
            case KeyEvent.KEYCODE_C:
                return "KEYCODE_C";
            // case KeyEvent.KEYCODE_CALCULATOR:
                // return "KEYCODE_CALCULATOR";
            // case KeyEvent.KEYCODE_CALENDAR:
                // return "KEYCODE_CALENDAR";
            case KeyEvent.KEYCODE_CALL:
                return "KEYCODE_CALL";
            case KeyEvent.KEYCODE_CAMERA:
                return "KEYCODE_CAMERA";
            // case KeyEvent.KEYCODE_CAPS_LOCK:
                // return "KEYCODE_CAPS_LOCK";
            // case KeyEvent.KEYCODE_CAPTIONS:
                // return "KEYCODE_CAPTIONS";
            // case KeyEvent.KEYCODE_CHANNEL_DOWN:
                // return "KEYCODE_CHANNEL_DOWN";
            // case KeyEvent.KEYCODE_CHANNEL_UP:
                // return "KEYCODE_CHANNEL_UP";
            case KeyEvent.KEYCODE_CLEAR:
                return "KEYCODE_CLEAR";
            case KeyEvent.KEYCODE_COMMA:
                return "KEYCODE_COMMA";
            // case KeyEvent.KEYCODE_CONTACTS:
                // return "KEYCODE_CONTACTS";
            // case KeyEvent.KEYCODE_CTRL_LEFT:
                // return "KEYCODE_CTRL_LEFT";
            // case KeyEvent.KEYCODE_CTRL_RIGHT:
                // return "KEYCODE_CTRL_RIGHT";
            case KeyEvent.KEYCODE_D:
                return "KEYCODE_D";
            case KeyEvent.KEYCODE_DEL:
                return "KEYCODE_DEL";
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return "KEYCODE_DPAD_CENTER";
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return "KEYCODE_DPAD_DOWN";
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return "KEYCODE_DPAD_LEFT";
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return "KEYCODE_DPAD_RIGHT";
            case KeyEvent.KEYCODE_DPAD_UP:
                return "KEYCODE_DPAD_UP";
            // case KeyEvent.KEYCODE_DVR:
                // return "KEYCODE_DVR";
            case KeyEvent.KEYCODE_E:
                return "KEYCODE_E";
            // case KeyEvent.KEYCODE_EISU:
                // return "KEYCODE_EISU";
            case KeyEvent.KEYCODE_ENDCALL:
                return "KEYCODE_ENDCALL";
            case KeyEvent.KEYCODE_ENTER:
                return "KEYCODE_ENTER";
            case KeyEvent.KEYCODE_ENVELOPE:
                return "KEYCODE_ENVELOPE";
            case KeyEvent.KEYCODE_EQUALS:
                return "KEYCODE_EQUALS";
            // case KeyEvent.KEYCODE_ESCAPE:
                // return "KEYCODE_ESCAPE";
            case KeyEvent.KEYCODE_EXPLORER:
                return "KEYCODE_EXPLORER";
            case KeyEvent.KEYCODE_F:
                return "KEYCODE_F";
            // case KeyEvent.KEYCODE_F1:
                // return "KEYCODE_F1";
            // case KeyEvent.KEYCODE_F10:
                // return "KEYCODE_F10";
            // case KeyEvent.KEYCODE_F11:
                // return "KEYCODE_F11";
            // case KeyEvent.KEYCODE_F12:
                // return "KEYCODE_F12";
            // case KeyEvent.KEYCODE_F2:
                // return "KEYCODE_F2";
            // case KeyEvent.KEYCODE_F3:
                // return "KEYCODE_F3";
            // case KeyEvent.KEYCODE_F4:
                // return "KEYCODE_F4";
            // case KeyEvent.KEYCODE_F5:
                // return "KEYCODE_F5";
            // case KeyEvent.KEYCODE_F6:
                // return "KEYCODE_F6";
            // case KeyEvent.KEYCODE_F7:
                // return "KEYCODE_F7";
            // case KeyEvent.KEYCODE_F8:
                // return "KEYCODE_F8";
            // case KeyEvent.KEYCODE_F9:
                // return "KEYCODE_F9";
            case KeyEvent.KEYCODE_FOCUS:
                return "KEYCODE_FOCUS";
            // case KeyEvent.KEYCODE_FORWARD:
                // return "KEYCODE_FORWARD";
            // case KeyEvent.KEYCODE_FORWARD_DEL:
                // return "KEYCODE_FORWARD_DEL";
            // case KeyEvent.KEYCODE_FUNCTION:
                // return "KEYCODE_FUNCTION";
            case KeyEvent.KEYCODE_G:
                return "KEYCODE_G";
            case KeyEvent.KEYCODE_GRAVE:
                return "KEYCODE_GRAVE";
            // case KeyEvent.KEYCODE_GUIDE:
                // return "KEYCODE_GUIDE";
            case KeyEvent.KEYCODE_H:
                return "KEYCODE_H";
            case KeyEvent.KEYCODE_HEADSETHOOK:
                return "KEYCODE_HEADSETHOOK";
            // case KeyEvent.KEYCODE_HENKAN:
                // return "KEYCODE_HENKAN";
            case KeyEvent.KEYCODE_HOME:
                return "KEYCODE_HOME";
            case KeyEvent.KEYCODE_I:
                return "KEYCODE_I";
            // case KeyEvent.KEYCODE_INFO:
                // return "KEYCODE_INFO";
            // case KeyEvent.KEYCODE_INSERT:
                // return "KEYCODE_INSERT";
            case KeyEvent.KEYCODE_J:
                return "KEYCODE_J";
            case KeyEvent.KEYCODE_K:
                return "KEYCODE_K";
            // case KeyEvent.KEYCODE_KANA:
                // return "KEYCODE_KANA";
            // case KeyEvent.KEYCODE_KATAKANA_HIRAGANA:
                // return "KEYCODE_KATAKANA_HIRAGANA";
            case KeyEvent.KEYCODE_L:
                return "KEYCODE_L";
            // case KeyEvent.KEYCODE_LANGUAGE_SWITCH:
                // return "KEYCODE_LANGUAGE_SWITCH";
            case KeyEvent.KEYCODE_LEFT_BRACKET:
                return "KEYCODE_LEFT_BRACKET";
            case KeyEvent.KEYCODE_M:
                return "KEYCODE_M";
            // case KeyEvent.KEYCODE_MANNER_MODE:
                // return "KEYCODE_MANNER_MODE";
            // case KeyEvent.KEYCODE_MEDIA_CLOSE:
                // return "KEYCODE_MEDIA_CLOSE";
            // case KeyEvent.KEYCODE_MEDIA_EJECT:
                // return "KEYCODE_MEDIA_EJECT";
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                return "KEYCODE_MEDIA_FAST_FORWARD";
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                return "KEYCODE_MEDIA_NEXT";
            // case KeyEvent.KEYCODE_MEDIA_PAUSE:
                // return "KEYCODE_MEDIA_PAUSE";
            // case KeyEvent.KEYCODE_MEDIA_PLAY:
                // return "KEYCODE_MEDIA_PLAY";
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                return "KEYCODE_MEDIA_PLAY_PAUSE";
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                return "KEYCODE_MEDIA_PREVIOUS";
            // case KeyEvent.KEYCODE_MEDIA_RECORD:
                // return "KEYCODE_MEDIA_RECORD";
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                return "KEYCODE_MEDIA_REWIND";
            case KeyEvent.KEYCODE_MEDIA_STOP:
                return "KEYCODE_MEDIA_STOP";
            case KeyEvent.KEYCODE_MENU:
                return "KEYCODE_MENU";
            // case KeyEvent.KEYCODE_META_LEFT:
                // return "KEYCODE_META_LEFT";
            // case KeyEvent.KEYCODE_META_RIGHT:
                // return "KEYCODE_META_RIGHT";
            case KeyEvent.KEYCODE_MINUS:
                return "KEYCODE_MINUS";
            // case KeyEvent.KEYCODE_MOVE_END:
                // return "KEYCODE_MOVE_END";
            // case KeyEvent.KEYCODE_MOVE_HOME:
                // return "KEYCODE_MOVE_HOME";
            // case KeyEvent.KEYCODE_MUHENKAN:
                // return "KEYCODE_MUHENKAN";
            // case KeyEvent.KEYCODE_MUSIC:
                // return "KEYCODE_MUSIC";
            case KeyEvent.KEYCODE_MUTE:
                return "KEYCODE_MUTE";
            case KeyEvent.KEYCODE_N:
                return "KEYCODE_N";
            case KeyEvent.KEYCODE_NOTIFICATION:
                return "KEYCODE_NOTIFICATION";
            // case KeyEvent.KEYCODE_NUM:
                // return "KEYCODE_NUM";
            // case KeyEvent.KEYCODE_NUMPAD_0:
                // return "KEYCODE_NUMPAD_0";
            // case KeyEvent.KEYCODE_NUMPAD_1:
                // return "KEYCODE_NUMPAD_1";
            // case KeyEvent.KEYCODE_NUMPAD_2:
                // return "KEYCODE_NUMPAD_2";
            // case KeyEvent.KEYCODE_NUMPAD_3:
                // return "KEYCODE_NUMPAD_3";
            // case KeyEvent.KEYCODE_NUMPAD_4:
                // return "KEYCODE_NUMPAD_4";
            // case KeyEvent.KEYCODE_NUMPAD_5:
                // return "KEYCODE_NUMPAD_5";
            // case KeyEvent.KEYCODE_NUMPAD_6:
                // return "KEYCODE_NUMPAD_6";
            // case KeyEvent.KEYCODE_NUMPAD_7:
                // return "KEYCODE_NUMPAD_7";
            // case KeyEvent.KEYCODE_NUMPAD_8:
                // return "KEYCODE_NUMPAD_8";
            // case KeyEvent.KEYCODE_NUMPAD_9:
                // return "KEYCODE_NUMPAD_9";
            // case KeyEvent.KEYCODE_NUMPAD_ADD:
                // return "KEYCODE_NUMPAD_ADD";
            // case KeyEvent.KEYCODE_NUMPAD_COMMA:
                // return "KEYCODE_NUMPAD_COMMA";
            // case KeyEvent.KEYCODE_NUMPAD_DIVIDE:
                // return "KEYCODE_NUMPAD_DIVIDE";
            // case KeyEvent.KEYCODE_NUMPAD_DOT:
                // return "KEYCODE_NUMPAD_DOT";
            // case KeyEvent.KEYCODE_NUMPAD_ENTER:
                // return "KEYCODE_NUMPAD_ENTER";
            // case KeyEvent.KEYCODE_NUMPAD_EQUALS:
                // return "KEYCODE_NUMPAD_EQUALS";
            // case KeyEvent.KEYCODE_NUMPAD_LEFT_PAREN:
                // return "KEYCODE_NUMPAD_LEFT_PAREN";
            // case KeyEvent.KEYCODE_NUMPAD_MULTIPLY:
                // return "KEYCODE_NUMPAD_MULTIPLY";
            // case KeyEvent.KEYCODE_NUMPAD_RIGHT_PAREN:
                // return "KEYCODE_NUMPAD_RIGHT_PAREN";
            // case KeyEvent.KEYCODE_NUMPAD_SUBTRACT:
                // return "KEYCODE_NUMPAD_SUBTRACT";
            // case KeyEvent.KEYCODE_NUM_LOCK:
                // return "KEYCODE_NUM_LOCK";
            case KeyEvent.KEYCODE_O:
                return "KEYCODE_O";
            case KeyEvent.KEYCODE_P:
                return "KEYCODE_P";
            case KeyEvent.KEYCODE_PAGE_DOWN:
                return "KEYCODE_PAGE_DOWN";
            case KeyEvent.KEYCODE_PAGE_UP:
                return "KEYCODE_PAGE_UP";
            case KeyEvent.KEYCODE_PERIOD:
                return "KEYCODE_PERIOD";
            case KeyEvent.KEYCODE_PICTSYMBOLS:
                return "KEYCODE_PICTSYMBOLS";
            case KeyEvent.KEYCODE_PLUS:
                return "KEYCODE_PLUS";
            case KeyEvent.KEYCODE_POUND:
                return "KEYCODE_POUND";
            case KeyEvent.KEYCODE_POWER:
                return "KEYCODE_POWER";
            // case KeyEvent.KEYCODE_PROG_BLUE:
                // return "KEYCODE_PROG_BLUE";
            // case KeyEvent.KEYCODE_PROG_GREEN:
                // return "KEYCODE_PROG_GREEN";
            // case KeyEvent.KEYCODE_PROG_RED:
                // return "KEYCODE_PROG_RED";
            // case KeyEvent.KEYCODE_PROG_YELLOW:
                // return "KEYCODE_PROG_YELLOW";
            case KeyEvent.KEYCODE_Q:
                return "KEYCODE_Q";
            case KeyEvent.KEYCODE_R:
                return "KEYCODE_R";
            case KeyEvent.KEYCODE_RIGHT_BRACKET:
                return "KEYCODE_RIGHT_BRACKET";
            // case KeyEvent.KEYCODE_RO:
                // return "KEYCODE_RO";
            case KeyEvent.KEYCODE_S:
                return "KEYCODE_S";
            // case KeyEvent.KEYCODE_SCROLL_LOCK:
                // return "KEYCODE_SCROLL_LOCK";
            case KeyEvent.KEYCODE_SEARCH:
                return "KEYCODE_SEARCH";
            case KeyEvent.KEYCODE_SEMICOLON:
                return "KEYCODE_SEMICOLON";
            // case KeyEvent.KEYCODE_SETTINGS:
                // return "KEYCODE_SETTINGS";
            case KeyEvent.KEYCODE_SHIFT_LEFT:
                return "KEYCODE_SHIFT_LEFT";
            case KeyEvent.KEYCODE_SHIFT_RIGHT:
                return "KEYCODE_SHIFT_RIGHT";
            case KeyEvent.KEYCODE_SLASH:
                return "KEYCODE_SLASH";
            case KeyEvent.KEYCODE_SOFT_LEFT:
                return "KEYCODE_SOFT_LEFT";
            case KeyEvent.KEYCODE_SOFT_RIGHT:
                return "KEYCODE_SOFT_RIGHT";
            case KeyEvent.KEYCODE_SPACE:
                return "KEYCODE_SPACE";
            case KeyEvent.KEYCODE_STAR:
                return "KEYCODE_STAR";
            // case KeyEvent.KEYCODE_STB_INPUT:
                // return "KEYCODE_STB_INPUT";
            // case KeyEvent.KEYCODE_STB_POWER:
                // return "KEYCODE_STB_POWER";
            case KeyEvent.KEYCODE_SWITCH_CHARSET:
                return "KEYCODE_SWITCH_CHARSET";
            case KeyEvent.KEYCODE_SYM:
                return "KEYCODE_SYM";
            // case KeyEvent.KEYCODE_SYSRQ:
                // return "KEYCODE_SYSRQ";
            case KeyEvent.KEYCODE_T:
                return "KEYCODE_T";
            case KeyEvent.KEYCODE_TAB:
                return "KEYCODE_TAB";
            // case KeyEvent.KEYCODE_TV:
                // return "KEYCODE_TV";
            // case KeyEvent.KEYCODE_TV_INPUT:
                // return "KEYCODE_TV_INPUT";
            // case KeyEvent.KEYCODE_TV_POWER:
                // return "KEYCODE_TV_POWER";
            case KeyEvent.KEYCODE_U:
                return "KEYCODE_U";
            case KeyEvent.KEYCODE_V:
                return "KEYCODE_V";
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return "KEYCODE_VOLUME_DOWN";
            // case KeyEvent.KEYCODE_VOLUME_MUTE:
                // return "KEYCODE_VOLUME_MUTE";
            case KeyEvent.KEYCODE_VOLUME_UP:
                return "KEYCODE_VOLUME_UP";
            case KeyEvent.KEYCODE_W:
                return "KEYCODE_W";
            // case KeyEvent.KEYCODE_WINDOW:
                // return "KEYCODE_WINDOW";
            case KeyEvent.KEYCODE_X:
                return "KEYCODE_X";
            case KeyEvent.KEYCODE_Y:
                return "KEYCODE_Y";
            // case KeyEvent.KEYCODE_YEN:
                // return "KEYCODE_YEN";
            case KeyEvent.KEYCODE_Z:
                return "KEYCODE_Z";
            // case KeyEvent.KEYCODE_ZENKAKU_HANKAKU:
                // return "KEYCODE_ZENKAKU_HANKAKU";
            // case KeyEvent.KEYCODE_ZOOM_IN:
                // return "KEYCODE_ZOOM_IN";
            // case KeyEvent.KEYCODE_ZOOM_OUT:
                // return "KEYCODE_ZOOM_OUT";

                // error duplicate case label!?
            // case KeyEvent.MAX_KEYCODE:
            // case KeyEvent.META_ALT_LEFT_ON:
 
            // case KeyEvent.META_ALT_MASK:

                // error duplicate case label!?
            // case KeyEvent.META_ALT_ON:
            // case KeyEvent.META_ALT_RIGHT_ON:
 
            // case KeyEvent.META_CAPS_LOCK_ON:
            // case KeyEvent.META_CTRL_LEFT_ON:
            // case KeyEvent.META_CTRL_MASK:
            // case KeyEvent.META_CTRL_ON:
            // case KeyEvent.META_CTRL_RIGHT_ON:
            // case KeyEvent.META_FUNCTION_ON:
            // case KeyEvent.META_META_LEFT_ON:
            // case KeyEvent.META_META_MASK:
            // case KeyEvent.META_META_ON:
            // case KeyEvent.META_META_RIGHT_ON:
            // case KeyEvent.META_NUM_LOCK_ON:
            // case KeyEvent.META_SCROLL_LOCK_ON:
            // case KeyEvent.META_SHIFT_LEFT_ON:
            // case KeyEvent.META_SHIFT_MASK:
            // case KeyEvent.META_SHIFT_ON:
            // case KeyEvent.META_SHIFT_RIGHT_ON:
            // case KeyEvent.META_SYM_ON:
            case KeyEvent.KEYCODE_UNKNOWN:
            default:
                return "KEYCODE_UNKOWN";
        }
    }
}

