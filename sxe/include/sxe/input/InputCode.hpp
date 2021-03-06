#ifndef SXE_INPUT_INPUTCODE__HPP
#define SXE_INPUT_INPUTCODE__HPP
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

#include <sxe/api.hpp>

namespace sxe { namespace input {

    class SXE_PUBLIC InputCode {
      public:

        /* Enumeration of input codes.
         */
        enum {

            /** Any Unknown key code. */
            IC_UNKNOWN,


            //////////////////////////////////// Keyboard Keys //////////////////////////////////// 

            IC_ESCAPE,
            IC_F1,
            IC_F2,
            IC_F3,
            IC_F4,
            IC_F5,
            IC_F6,
            IC_F7,
            IC_F8,
            IC_F9,
            IC_F10,
            IC_F11,
            IC_F12,

            IC_TILDE,
            IC_GRAVE,
            IC_EXCLAMATION_MARK,
            IC_1,
            IC_AT_SIGN,
            IC_2,
            IC_NUMBER_SIGN,
            IC_3,
            IC_DOLLAR_SIGN,
            IC_4,
            IC_PERCENT_SIGN,
            IC_5,
            IC_CARET,
            IC_6,
            IC_AMPERSAND,
            IC_7,
            IC_ASTERISK,
            IC_8,
            IC_LEFT_PAREN,
            IC_9,
            IC_RIGHT_PAREN,
            IC_0,
            IC_UNDERSCORE,
            IC_HYPHEN_MINUS,
            IC_PLUS_SIGN,
            IC_EQUAL_SIGN,
            IC_BACKSPACE,

            IC_TAB,
            IC_Q,
            IC_W,
            IC_E,
            IC_R,
            IC_T,
            IC_Y,
            IC_U,
            IC_I,
            IC_O,
            IC_P,
            IC_LEFT_CURLY_BRACE,
            IC_LEFT_SQUARE_BRACKET,
            IC_RIGHT_CURLY_BRACE,
            IC_RIGHT_SQUARE_BRACKET,
            IC_PIPE,
            IC_BACKSLASH,

            IC_CAPS_LOCK,
            IC_A,
            IC_S,
            IC_D,
            IC_F,
            IC_G,
            IC_H,
            IC_J,
            IC_K,
            IC_L,
            IC_COLON,
            IC_SEMICOLON,
            IC_DOUBLE_QUOTE,
            IC_APOSTROPHE,
            IC_ENTER,

            IC_SHIFT_LEFT,
            IC_Z,
            IC_X,
            IC_C,
            IC_V,
            IC_B,
            IC_N,
            IC_M,
            IC_LESS_THAN_SIGN,
            IC_COMMA,
            IC_GREATER_THAN_SIGN,
            IC_PERIOD,
            IC_QUESTION_MARK,
            IC_SLASH,
            IC_SHIFT_RIGHT,

            IC_CTRL_LEFT,
            IC_META_LEFT,
            IC_ALT_LEFT,
            IC_SPACE,
            IC_ALT_RIGHT,
            IC_META_RIGHT,
            IC_MENU,
            IC_CTRL_RIGHT,

            IC_PRINTSCREEN,
            IC_SCROLL_LOCK,
            IC_PAUSE,
            IC_BREAK,
            IC_SYSRQ,

            IC_INSERT,
            IC_HOME,
            IC_PAGE_UP,
            IC_DELETE,
            IC_END,
            IC_PAGE_DOWN,

            IC_UP_ARROW,
            IC_DOWN_ARROW,
            IC_LEFT_ARROW,
            IC_RIGHT_ARROW,

            IC_NUM_LOCK,
            IC_NUMPAD_0=IC_0,
            IC_NUMPAD_1=IC_1,
            IC_NUMPAD_2=IC_2,
            IC_NUMPAD_3=IC_3,
            IC_NUMPAD_4=IC_4,
            IC_NUMPAD_5=IC_5,
            IC_NUMPAD_6=IC_6,
            IC_NUMPAD_7=IC_7,
            IC_NUMPAD_8=IC_8,
            IC_NUMPAD_9=IC_9,

            IC_NUMPAD_DIVIDE=IC_SLASH,
            IC_NUMPAD_MULTIPLY=IC_ASTERISK,
            IC_NUMPAD_SUBTRACT=IC_HYPHEN_MINUS,
            IC_NUMPAD_ADD=IC_PLUS_SIGN,
            IC_NUMPAD_DOT=IC_PERIOD,
            IC_NUMPAD_COMMA=IC_COMMA,
            IC_NUMPAD_ENTER=IC_ENTER,
            IC_NUMPAD_EQUAL_SIGN=IC_EQUAL_SIGN,
            IC_NUMPAD_LEFT_PAREN=IC_LEFT_PAREN,
            IC_NUMPAD_RIGHT_PAREN=IC_RIGHT_PAREN,

            //////////////////////////////////// Multimedia Keys //////////////////////////////////// 


            IC_VOLUME_UP,
            IC_VOLUME_DOWN,
            /** Play/Pause media key. */
            IC_MEDIA_PLAY_PAUSE,
            /** Stop media key. */
            IC_MEDIA_STOP,
            /** Play Next media key. */
            IC_MEDIA_NEXT,
            /** Play Previous media key. */
            IC_MEDIA_PREVIOUS,
            /** Rewind media key. */
            IC_MEDIA_REWIND,
            /** Fast Forward media key. */
            IC_MEDIA_FAST_FORWARD,
            /** Mute key.
             * Mutes the microphone, unlike {@link #IC_VOLUME_MUTE}. */
            IC_MUTE,
            /** Play media key. */
            IC_MEDIA_PLAY,
            /** Pause media key. */
            IC_MEDIA_PAUSE,
            /** Close media key.
             * May be used to close a CD tray, for example. */
            IC_MEDIA_CLOSE,
            /** Eject media key.
             * May be used to eject a CD tray, for example. */
            IC_MEDIA_EJECT,
            /** Record media key. */
            IC_MEDIA_RECORD,
            /** Audio Track key
             * Switches the audio tracks. */
            IC_MEDIA_AUDIO_TRACK,
            /** Headset Hook key.
             * Used to hang up calls and stop media. */
            IC_HEADSETHOOK,


            //////////////////////////////////// Foreign Language Related //////////////////////////////////// 

            /** Used to enter alternate symbols (e.g. on a phone). */
            IC_SYM,
            /** Picture Symbols modifier key.
             * Used to switch symbol sets (Emoji, Kao-moji). */
            IC_PICTSYMBOLS,
            /** Switch Charset modifier key.
             * Used to switch character sets (Kanji, Katakana). */
            IC_SWITCH_CHARSET,
            /** Language Switch key.
             * Toggles the current input language such as switching between English and Japanese on
             * a QWERTY keyboard.  On some devices, the same function may be performed by
             * pressing Shift+Spacebar. */
            IC_LANGUAGE_SWITCH,
            /** Japanese full-width / half-width key. */
            IC_ZENKAKU_HANKAKU,
            /** Japanese alphanumeric key. */
            IC_EISU,
            /** Japanese non-conversion key. */
            IC_MUHENKAN,
            /** Japanese conversion key. */
            IC_HENKAN,
            /** Japanese katakana / hiragana key. */
            IC_KATAKANA_HIRAGANA,
            /** Japanese Yen key. */
            IC_YEN,
            /** Japanese Ro key. */
            IC_RO,
            /** Japanese kana key. */
            IC_KANA,


            //////////////////////////////////// Game Controller //////////////////////////////////// 


            /** A Button key.
             * On a game controller, the A button should be either the button labeled A
             * or the first button on the bottom row of controller buttons. */
            IC_BUTTON_A,
            /** B Button key.
             * On a game controller, the B button should be either the button labeled B
             * or the second button on the bottom row of controller buttons. */
            IC_BUTTON_B,
            /** C Button key.
             * On a game controller, the C button should be either the button labeled C
             * or the third button on the bottom row of controller buttons. */
            IC_BUTTON_C,
            /** X Button key.
             * On a game controller, the X button should be either the button labeled X
             * or the first button on the upper row of controller buttons. */
            IC_BUTTON_X,
            /** Y Button key.
             * On a game controller, the Y button should be either the button labeled Y
             * or the second button on the upper row of controller buttons. */
            IC_BUTTON_Y,
            /** Z Button key.
             * On a game controller, the Z button should be either the button labeled Z
             * or the third button on the upper row of controller buttons. */
            IC_BUTTON_Z,
            /** L1 Button key.
             * On a game controller, the L1 button should be either the button labeled L1 (or L)
             * or the top left trigger button. */
            IC_BUTTON_L1,
            /** R1 Button key.
             * On a game controller, the R1 button should be either the button labeled R1 (or R)
             * or the top right trigger button. */
            IC_BUTTON_R1,
            /** L2 Button key.
             * On a game controller, the L2 button should be either the button labeled L2
             * or the bottom left trigger button. */
            IC_BUTTON_L2,
            /** R2 Button key.
             * On a game controller, the R2 button should be either the button labeled R2
             * or the bottom right trigger button. */
            IC_BUTTON_R2,
            /** Left Thumb Button key.
             * On a game controller, the left thumb button indicates that the left (or only)
             * joystick is pressed. */
            IC_BUTTON_THUMBL,
            /** Right Thumb Button key.
             * On a game controller, the right thumb button indicates that the right
             * joystick is pressed. */
            IC_BUTTON_THUMBR,
            /** Start Button key.
             * On a game controller, the button labeled Start. */
            IC_BUTTON_START,
            /** Select Button key.
             * On a game controller, the button labeled Select. */
            IC_BUTTON_SELECT,
            /** Mode Button key.
             * On a game controller, the button labeled Mode. */
            IC_BUTTON_MODE,


            //////////////////////////////////// Generic Game Pad //////////////////////////////////// 


            /** Generic Game Pad Button #1.*/
            IC_BUTTON_1,
            /** Generic Game Pad Button #2.*/
            IC_BUTTON_2,
            /** Generic Game Pad Button #3.*/
            IC_BUTTON_3,
            /** Generic Game Pad Button #4.*/
            IC_BUTTON_4,
            /** Generic Game Pad Button #5.*/
            IC_BUTTON_5,
            /** Generic Game Pad Button #6.*/
            IC_BUTTON_6,
            /** Generic Game Pad Button #7.*/
            IC_BUTTON_7,
            /** Generic Game Pad Button #8.*/
            IC_BUTTON_8,
            /** Generic Game Pad Button #9.*/
            IC_BUTTON_9,
            /** Generic Game Pad Button #10.*/
            IC_BUTTON_10,
            /** Generic Game Pad Button #11.*/
            IC_BUTTON_11,
            /** Generic Game Pad Button #12.*/
            IC_BUTTON_12,
            /** Generic Game Pad Button #13.*/
            IC_BUTTON_13,
            /** Generic Game Pad Button #14.*/
            IC_BUTTON_14,
            /** Generic Game Pad Button #15.*/
            IC_BUTTON_15,
            /** Generic Game Pad Button #16.*/
            IC_BUTTON_16,


            //////////////////////////////////// TV Remotes //////////////////////////////////// 


            /** Volume Mute key.
             * Mutes the speaker, unlike {@link #IC_MUTE}.
             * This key should normally be implemented as a toggle such that the first press
             * mutes the speaker and the second press restores the original volume. */
            IC_VOLUME_MUTE,
            /** Info key.
             * Common on TV remotes to show additional information related to what is
             * currently being viewed. */
            IC_INFO,
            /** Channel up key.
             * On TV remotes, increments the television channel. */
            IC_CHANNEL_UP,
            /** Channel down key.
             * On TV remotes, decrements the television channel. */
            IC_CHANNEL_DOWN,
            /** Zoom in key. */
            IC_ZOOM_IN,
            /** Zoom out key. */
            IC_ZOOM_OUT,
            /** TV key.
             * On TV remotes, switches to viewing live TV. */
            IC_TV,
            /** Window key.
             * On TV remotes, toggles picture-in-picture mode or other windowing functions. */
            IC_WINDOW,
            /** Guide key.
             * On TV remotes, shows a programming guide. */
            IC_GUIDE,
            /** DVR key.
             * On some TV remotes, switches to a DVR mode for recorded shows. */
            IC_DVR,
            /** Bookmark key.
             * On some TV remotes, bookmarks content or web pages. */
            IC_BOOKMARK,
            /** Toggle captions key.
             * Switches the mode for closed-captioning text, for example during television shows. */
            IC_CAPTIONS,
            /** Settings key.
             * Starts the system settings activity. */
            IC_SETTINGS,
            /** TV power key.
             * On TV remotes, toggles the power on a television screen. */
            IC_TV_POWER,
            /** TV input key.
             * On TV remotes, switches the input on a television screen. */
            IC_TV_INPUT,
            /** Set-top-box power key.
             * On TV remotes, toggles the power on an external Set-top-box. */
            IC_STB_POWER,
            /** Set-top-box input key.
             * On TV remotes, switches the input mode on an external Set-top-box. */
            IC_STB_INPUT,
            /** A/V Receiver power key.
             * On TV remotes, toggles the power on an external A/V Receiver. */
            IC_AVR_POWER,
            /** A/V Receiver input key.
             * On TV remotes, switches the input mode on an external A/V Receiver. */
            IC_AVR_INPUT,
            /** Red "programmable" key.
             * On TV remotes, acts as a contextual/programmable key. */
            IC_PROG_RED,
            /** Green "programmable" key.
             * On TV remotes, actsas a contextual/programmable key. */
            IC_PROG_GREEN,
            /** Yellow "programmable" key.
             * On TV remotes, acts as a contextual/programmable key. */
            IC_PROG_YELLOW,
            /** Blue "programmable" key.
             * On TV remotes, acts as a contextual/programmable key. */
            IC_PROG_BLUE,
            /** App switch key.
             * Should bring up the application switcher dialog. */
            IC_APP_SWITCH,


            //////////////////////////////////// Special Applications //////////////////////////////////// 


            /** Explorer / web browser special function key. */
            IC_EXPLORER,
            /** Used to launch a mail application. */
            IC_ENVELOPE,
            /** Contacts special function key.
             * Used to launch an address book application. */
            IC_CONTACTS,
            /** Calendar special function key.
             * Used to launch a calendar application. */
            IC_CALENDAR,
            /** Calculator special function key.
             * Used to launch a calculator application. */
            IC_CALCULATOR,
            /** Japanese full-width / half-width key. */
            /** Search key. */
            /** Music special function key.
             * Used to launch a music player application. */
            IC_MUSIC,
            IC_SEARCH,


            ///////////////////////////////////////////////////////////////////////////
            // These keys have a meaning in Android, who we mimic the name/codes of.
            // They however have no or limited meaning inside SxE and may not be relevent
            // to binding keys. Just here for in case it makes sense down the road.
            // 
            ///////////////////////////////////////////////////////////////////////////


            IC_BACK,
            IC_CALL,
            IC_ENDCALL,

            /** Soft Left key.
             * Usually situated below the display on phones and used as a multi-function
             * feature key for selecting a software defined function shown on the bottom left
             * of the display. */
            IC_SOFT_LEFT,

            /** Soft Right key.
             * Usually situated below the display on phones and used as a multi-function
             * feature key for selecting a software defined function shown on the bottom right
             * of the display. */
            IC_SOFT_RIGHT,


            IC_DPAD_UP,
            IC_DPAD_DOWN,
            IC_DPAD_LEFT,
            IC_DPAD_RIGHT,
            IC_DPAD_CENTER,


            IC_POWER,
            IC_CAMERA,
            IC_CLEAR,

            /** Number modifier key.
             * Used to enter numeric symbols.
             * This key is not Num Lock; it is more like {@link #IC_ALT_LEFT} and is
             * interpreted as an ALT key by [android.text.method.MetaKeyKeyListener](https://developer.android.com/reference/android/text/method/MetaKeyKeyListener). */
            IC_NUM,

            /** Camera Focus key.
             * Used to focus the camera. */
            IC_FOCUS,
            /** Notification key. */
            IC_NOTIFICATION,


            /** Forward key.
             * Navigates forward in the history stack.  Complement of {@link #IC_BACK}. */
            IC_FORWARD,


            /** Manner Mode key.
             * Toggles silent or vibrate mode on and off to make the device behave more politely
             * in certain settings such as on a crowded train.  On some devices, the key may only
             * operate when long-pressed. */
            IC_MANNER_MODE,
            /** 3D Mode key.
             * Toggles the display between 2D and 3D mode. */
            IC_3D_MODE,

            /** Brightness Down key.
             * Adjusts the screen brightness down. */
            IC_BRIGHTNESS_DOWN,
            /** Brightness Up key.
             * Adjusts the screen brightness up. */
            IC_BRIGHTNESS_UP,

            /** Special code meant to be 'Any key'
             *
             * Used by KeyEventManager and such, that want maps of keys / ids
             * but also want broadcast capabilities.
             */
            IC_ANYKEY=INT_MAX
        };

        /** Converts a character into a InputCode.
         *
         * @return keycode associated with ch or {@link #IC_UNKNOWN}.
         */
        static InputCode fromChar(char ch);

        /** Converts a sequence of characters into a sequence of InputCodes.
         *
         * @param first beginning of range to convert.
         * @param last of range to convert.
         * @param out position to begin inserting.
         *
         * You probably want out to be something like std::back_inserter(yourContainer).
         *
         * @returns OutputIt iterator to the element in the destination range, one past the last element copied.
         */
        template <class InputIt, class OutputIt>
        static OutputIt fromSequence(InputIt first, InputIt last, OutputIt out)
        {
            while (first != last) {
                *out++ = fromChar(*first);
                first++;
            }

            return out;
        }

        /** Alias for upper().
         */
        char symbol() const;

        /** Returns upper case representation.
         */
        char upper() const;

        /** Returns lower case representation.
         */
        char lower() const;

        /** Returns ordinal representation.
         */
        int code() const;

        /** Dumps a friendly description of this code.
         */
        std::string dump() const;

        /* Compare to our code() field. */
        bool operator== (int value) const;
        bool operator!= (int value) const;

        bool operator== (InputCode value) const;
        bool operator!= (InputCode value) const;

        /** Creates an IC_UNKNOWN. */
        InputCode();

        /** Creates specified IC_*.
         */
        InputCode(int ordinal);

      protected:

        InputCode(int ordinal, char upper, char lower);

      private:

        static const std::string TAG;

        int mOrdinal;
        char mUpperCase;
        char mLowerCase;

        static char lowerForOrdinal(int ic);
        static char upperForOrdinal(int ic);
    };

} }

namespace std {
    template <> struct hash<sxe::input::InputCode>
    {
        size_t operator()(const sxe::input::InputCode& event) const
        {
            return hash<int>()(event.code());
        }
    };
}

#endif // SXE_INPUT_INPUTCODE__HPP
