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

package com.spidey01.sxe.core.input;


/** Class enumerating key codes used in the input system.
 *
 * Each value has two methods of interest: <dl>
 *      <dt>name()</dt>
 *          <dd>get the name, e.g. "IC_SPACE".</dd>
 *      <dt>code()</dt>
 *          <dd>get the code, e.g. 62.</dd>
 *      <dt>symbol()</dt>
 *          <dd>get the char, e.g. ' '</dd>
 *          should also do upper()/lower().
 * </dl>
 *
 *
 * Remember as this is an enum, if you need to iterate over the entire list of
 * enums, use the values() static method. Like wise you can convert between the
 * String and InputCode values with the static valueOf() and instance
 * toString() methods.
 *
 * A static method called fromChar() can map from a common symbol like '0' or
 * 'A' to the associated InputCode (IC_0, IC_A).
 *
 *
 * While the implementation is very different the naming convention and
 * values are derived from that used by the Android Open Source Projects
 * KeyEvent class.  It's as good an internal convention as any in my books.
 */
public enum InputCode {

    /** Any Unknown key code. */
    IC_UNKNOWN(0),


//////////////////////////////////// Keyboard Keys //////////////////////////////////// 


    IC_0(7, '0'),
    IC_1(8, '1'),
    IC_2(9, '2'),
    IC_3(10, '3'),
    IC_4(11, '4'),
    IC_5(12, '5'),
    IC_6(13, '6'),
    IC_7(14, '7'),
    IC_8(15, '8'),
    IC_9(16, '9'),
    /** '*'. */
    IC_STAR(17),
    /** '#'. */
    IC_POUND(18),
    IC_A(29, 'A'),
    IC_B(30, 'B'),
    IC_C(31, 'C'),
    IC_D(32, 'D'),
    IC_E(33, 'E'),
    IC_F(34, 'F'),
    IC_G(35, 'G'),
    IC_H(36, 'H'),
    IC_I(37, 'I'),
    IC_J(38, 'J'),
    IC_K(39, 'K'),
    IC_L(40, 'L'),
    IC_M(41, 'M'),
    IC_N(42, 'N'),
    IC_O(43, 'O'),
    IC_P(44, 'P'),
    IC_Q(45, 'Q'),
    IC_R(46, 'R'),
    IC_S(47, 'S'),
    IC_T(48, 'T'),
    IC_U(49, 'U'),
    IC_V(50, 'V'),
    IC_W(51, 'W'),
    IC_X(52, 'X'),
    IC_Y(53, 'Y'),
    IC_Z(54, 'Z'),
    IC_COMMA(55, ','),
    IC_PERIOD(56, '.'),
    IC_ALT_LEFT(57),
    IC_ALT_RIGHT(58),
    IC_SHIFT_LEFT(59),
    IC_SHIFT_RIGHT(60),
    IC_TAB(61, '\t'),
    IC_SPACE(62, ' '),
    /** Enter/return key. */
    IC_ENTER(66, '\n'),
    /** Backspace key.
     * Deletes characters before the insertion point, unlike {@link #IC_FORWARD_DEL}. */
    IC_DEL(67, '\b'),
    /** '`' (backtick) key. */
    IC_GRAVE(68, '`'),
    IC_MINUS(69, '-'),
    IC_EQUALS(70, '='),
    IC_PLUS(81, '+'),
    IC_LEFT_BRACKET(71, '['),
    IC_RIGHT_BRACKET(72, ']'),
    IC_BACKSLASH(73, '\\'),
    IC_SEMICOLON(74, ';'),
    /** ''' (apostrophe) key. */
    IC_APOSTROPHE(75, '\''),
    /** '/' key. */
    IC_SLASH(76, '/'),
    /** '@' key. */
    IC_AT(77, '@'),


    IC_PAGE_UP(92),
    IC_PAGE_DOWN(93),


    IC_ESCAPE(111),
    /** Key code constant: Forward Delete key.
     * Deletes characters ahead of the insertion point, unlike {@link #IC_DEL}. */
    IC_FORWARD_DEL(112),
    IC_CTRL_LEFT(113),
    IC_CTRL_RIGHT(114),
    IC_CAPS_LOCK(115),
    IC_SCROLL_LOCK(116),
    /** Left Meta modifier key. */
    IC_META_LEFT(117),
    /** Right Meta modifier key. */
    IC_META_RIGHT(118),
    /** Function modifier key. */
    IC_FUNCTION(119),
    /** System Request / Print Screen key. */
    IC_SYSRQ(120),
    /** Break / Pause key. */
    IC_BREAK(121),
    /** Home Movement key.
     * Used for scrolling or moving the cursor around to the start of a line
     * or to the top of a list. */
    IC_MOVE_HOME(122),
    /** End Movement key.
     * Used for scrolling or moving the cursor around to the end of a line
     * or to the bottom of a list. */
    IC_MOVE_END(123),
    /** Insert key.
     * Toggles insert / overwrite edit mode. */
    IC_INSERT(124),
    IC_F1(131),
    IC_F2(132),
    IC_F3(133),
    IC_F4(134),
    IC_F5(135),
    IC_F6(136),
    IC_F7(137),
    IC_F8(138),
    IC_F9(139),
    IC_F10(140),
    IC_F11(141),
    IC_F12(142),
    /** Key code constant: Num Lock key.
     * This is the Num Lock key; it is different from {@link #IC_NUM}.
     * This key alters the behavior of other keys on the numeric keypad. */
    IC_NUM_LOCK(143),
    IC_NUMPAD_0(144),
    IC_NUMPAD_1(145),
    IC_NUMPAD_2(146),
    IC_NUMPAD_3(147),
    IC_NUMPAD_4(148),
    IC_NUMPAD_5(149),
    IC_NUMPAD_6(150),
    IC_NUMPAD_7(151),
    IC_NUMPAD_8(152),
    IC_NUMPAD_9(153),
    IC_NUMPAD_DIVIDE(154),
    IC_NUMPAD_MULTIPLY(155),
    IC_NUMPAD_SUBTRACT(156),
    IC_NUMPAD_ADD(157),
    IC_NUMPAD_DOT(158),
    IC_NUMPAD_COMMA(159),
    IC_NUMPAD_ENTER(160),
    IC_NUMPAD_EQUALS(161),
    IC_NUMPAD_LEFT_PAREN(162),
    IC_NUMPAD_RIGHT_PAREN(163),


//////////////////////////////////// Multimedia Keys //////////////////////////////////// 


    IC_VOLUME_UP(24),
    IC_VOLUME_DOWN(25),
    /** Play/Pause media key. */
    IC_MEDIA_PLAY_PAUSE(85),
    /** Stop media key. */
    IC_MEDIA_STOP(86),
    /** Play Next media key. */
    IC_MEDIA_NEXT(87),
    /** Play Previous media key. */
    IC_MEDIA_PREVIOUS(88),
    /** Rewind media key. */
    IC_MEDIA_REWIND(89),
    /** Fast Forward media key. */
    IC_MEDIA_FAST_FORWARD(90),
    /** Mute key.
     * Mutes the microphone, unlike {@link #IC_VOLUME_MUTE}. */
    IC_MUTE(91),
    /** Play media key. */
    IC_MEDIA_PLAY(126),
    /** Pause media key. */
    IC_MEDIA_PAUSE(127),
    /** Close media key.
     * May be used to close a CD tray, for example. */
    IC_MEDIA_CLOSE(128),
    /** Eject media key.
     * May be used to eject a CD tray, for example. */
    IC_MEDIA_EJECT(129),
    /** Record media key. */
    IC_MEDIA_RECORD(130),
    /** Audio Track key
     * Switches the audio tracks. */
    IC_MEDIA_AUDIO_TRACK(222),
    /** Headset Hook key.
     * Used to hang up calls and stop media. */
    IC_HEADSETHOOK(79),


//////////////////////////////////// Foreign Language Related //////////////////////////////////// 

    /** Used to enter alternate symbols (e.g. on a phone). */
    IC_SYM(63),
    /** Picture Symbols modifier key.
     * Used to switch symbol sets (Emoji, Kao-moji). */
    IC_PICTSYMBOLS(94),
    /** Switch Charset modifier key.
     * Used to switch character sets (Kanji, Katakana). */
    IC_SWITCH_CHARSET(95),
    /** Language Switch key.
     * Toggles the current input language such as switching between English and Japanese on
     * a QWERTY keyboard.  On some devices, the same function may be performed by
     * pressing Shift+Spacebar. */
    IC_LANGUAGE_SWITCH(204),
    /** Japanese full-width / half-width key. */
    IC_ZENKAKU_HANKAKU(211),
    /** Japanese alphanumeric key. */
    IC_EISU(212),
    /** Japanese non-conversion key. */
    IC_MUHENKAN(213),
    /** Japanese conversion key. */
    IC_HENKAN(214),
    /** Japanese katakana / hiragana key. */
    IC_KATAKANA_HIRAGANA(215),
    /** Japanese Yen key. */
    IC_YEN(216, '\u00A5'),
    /** Japanese Ro key. */
    IC_RO(217),
    /** Japanese kana key. */
    IC_KANA(218),


//////////////////////////////////// Game Controller //////////////////////////////////// 


    /** A Button key.
     * On a game controller, the A button should be either the button labeled A
     * or the first button on the bottom row of controller buttons. */
    IC_BUTTON_A(96),
    /** B Button key.
     * On a game controller, the B button should be either the button labeled B
     * or the second button on the bottom row of controller buttons. */
    IC_BUTTON_B(97),
    /** C Button key.
     * On a game controller, the C button should be either the button labeled C
     * or the third button on the bottom row of controller buttons. */
    IC_BUTTON_C(98),
    /** X Button key.
     * On a game controller, the X button should be either the button labeled X
     * or the first button on the upper row of controller buttons. */
    IC_BUTTON_X(99),
    /** Y Button key.
     * On a game controller, the Y button should be either the button labeled Y
     * or the second button on the upper row of controller buttons. */
    IC_BUTTON_Y(100),
    /** Z Button key.
     * On a game controller, the Z button should be either the button labeled Z
     * or the third button on the upper row of controller buttons. */
    IC_BUTTON_Z(101),
    /** L1 Button key.
     * On a game controller, the L1 button should be either the button labeled L1 (or L)
     * or the top left trigger button. */
    IC_BUTTON_L1(102),
    /** R1 Button key.
     * On a game controller, the R1 button should be either the button labeled R1 (or R)
     * or the top right trigger button. */
    IC_BUTTON_R1(103),
    /** L2 Button key.
     * On a game controller, the L2 button should be either the button labeled L2
     * or the bottom left trigger button. */
    IC_BUTTON_L2(104),
    /** R2 Button key.
     * On a game controller, the R2 button should be either the button labeled R2
     * or the bottom right trigger button. */
    IC_BUTTON_R2(105),
    /** Left Thumb Button key.
     * On a game controller, the left thumb button indicates that the left (or only)
     * joystick is pressed. */
    IC_BUTTON_THUMBL(106),
    /** Right Thumb Button key.
     * On a game controller, the right thumb button indicates that the right
     * joystick is pressed. */
    IC_BUTTON_THUMBR(107),
    /** Start Button key.
     * On a game controller, the button labeled Start. */
    IC_BUTTON_START(108),
    /** Select Button key.
     * On a game controller, the button labeled Select. */
    IC_BUTTON_SELECT(109),
    /** Mode Button key.
     * On a game controller, the button labeled Mode. */
    IC_BUTTON_MODE(110),


//////////////////////////////////// Generic Game Pad //////////////////////////////////// 


    /** Generic Game Pad Button #1.*/
    IC_BUTTON_1(188),
    /** Generic Game Pad Button #2.*/
    IC_BUTTON_2(189),
    /** Generic Game Pad Button #3.*/
    IC_BUTTON_3(190),
    /** Generic Game Pad Button #4.*/
    IC_BUTTON_4(191),
    /** Generic Game Pad Button #5.*/
    IC_BUTTON_5(192),
    /** Generic Game Pad Button #6.*/
    IC_BUTTON_6(193),
    /** Generic Game Pad Button #7.*/
    IC_BUTTON_7(194),
    /** Generic Game Pad Button #8.*/
    IC_BUTTON_8(195),
    /** Generic Game Pad Button #9.*/
    IC_BUTTON_9(196),
    /** Generic Game Pad Button #10.*/
    IC_BUTTON_10(197),
    /** Generic Game Pad Button #11.*/
    IC_BUTTON_11(198),
    /** Generic Game Pad Button #12.*/
    IC_BUTTON_12(199),
    /** Generic Game Pad Button #13.*/
    IC_BUTTON_13(200),
    /** Generic Game Pad Button #14.*/
    IC_BUTTON_14(201),
    /** Generic Game Pad Button #15.*/
    IC_BUTTON_15(202),
    /** Generic Game Pad Button #16.*/
    IC_BUTTON_16(203),


//////////////////////////////////// TV Remotes //////////////////////////////////// 


    /** Volume Mute key.
     * Mutes the speaker, unlike {@link #IC_MUTE}.
     * This key should normally be implemented as a toggle such that the first press
     * mutes the speaker and the second press restores the original volume. */
    IC_VOLUME_MUTE(164),
    /** Info key.
     * Common on TV remotes to show additional information related to what is
     * currently being viewed. */
    IC_INFO(165),
    /** Channel up key.
     * On TV remotes, increments the television channel. */
    IC_CHANNEL_UP(166),
    /** Channel down key.
     * On TV remotes, decrements the television channel. */
    IC_CHANNEL_DOWN(167),
    /** Zoom in key. */
    IC_ZOOM_IN(168),
    /** Zoom out key. */
    IC_ZOOM_OUT(169),
    /** TV key.
     * On TV remotes, switches to viewing live TV. */
    IC_TV(170),
    /** Window key.
     * On TV remotes, toggles picture-in-picture mode or other windowing functions. */
    IC_WINDOW(171),
    /** Guide key.
     * On TV remotes, shows a programming guide. */
    IC_GUIDE(172),
    /** DVR key.
     * On some TV remotes, switches to a DVR mode for recorded shows. */
    IC_DVR(173),
    /** Bookmark key.
     * On some TV remotes, bookmarks content or web pages. */
    IC_BOOKMARK(174),
    /** Toggle captions key.
     * Switches the mode for closed-captioning text, for example during television shows. */
    IC_CAPTIONS(175),
    /** Settings key.
     * Starts the system settings activity. */
    IC_SETTINGS(176),
    /** TV power key.
     * On TV remotes, toggles the power on a television screen. */
    IC_TV_POWER(177),
    /** TV input key.
     * On TV remotes, switches the input on a television screen. */
    IC_TV_INPUT(178),
    /** Set-top-box power key.
     * On TV remotes, toggles the power on an external Set-top-box. */
    IC_STB_POWER(179),
    /** Set-top-box input key.
     * On TV remotes, switches the input mode on an external Set-top-box. */
    IC_STB_INPUT(180),
    /** A/V Receiver power key.
     * On TV remotes, toggles the power on an external A/V Receiver. */
    IC_AVR_POWER(181),
    /** A/V Receiver input key.
     * On TV remotes, switches the input mode on an external A/V Receiver. */
    IC_AVR_INPUT(182),
    /** Red "programmable" key.
     * On TV remotes, acts as a contextual/programmable key. */
    IC_PROG_RED(183),
    /** Green "programmable" key.
     * On TV remotes, actsas a contextual/programmable key. */
    IC_PROG_GREEN(184),
    /** Yellow "programmable" key.
     * On TV remotes, acts as a contextual/programmable key. */
    IC_PROG_YELLOW(185),
    /** Blue "programmable" key.
     * On TV remotes, acts as a contextual/programmable key. */
    IC_PROG_BLUE(186),
    /** App switch key.
     * Should bring up the application switcher dialog. */
    IC_APP_SWITCH(187),


//////////////////////////////////// Special Applications //////////////////////////////////// 


    /** Explorer / web browser special function key. */
    IC_EXPLORER(64),
     /** Used to launch a mail application. */
    IC_ENVELOPE(65),
    /** Contacts special function key.
     * Used to launch an address book application. */
    IC_CONTACTS(207),
    /** Calendar special function key.
     * Used to launch a calendar application. */
    IC_CALENDAR(208),
    /** Music special function key.
     * Used to launch a music player application. */
    IC_MUSIC(209),
    /** Calculator special function key.
     * Used to launch a calculator application. */
    IC_CALCULATOR(210),
    /** Japanese full-width / half-width key. */


///////////////////////////////////////////////////////////////////////////
// These keys have a meaning in Android, who we mimic the name/codes of.
// They however have no or limited meaning inside SxE and may not be relevent
// to binding keys. Just here for in case it makes sense down the road.
// 
///////////////////////////////////////////////////////////////////////////


    IC_BACK(4),
    IC_CALL(5),
    IC_ENDCALL(6),

    /** Soft Left key.
     * Usually situated below the display on phones and used as a multi-function
     * feature key for selecting a software defined function shown on the bottom left
     * of the display. */
    IC_SOFT_LEFT(1),

    /** Soft Right key.
     * Usually situated below the display on phones and used as a multi-function
     * feature key for selecting a software defined function shown on the bottom right
     * of the display. */
    IC_SOFT_RIGHT(2),

    /** Home key.  This
     * key is handled by the framework and
     * is never delivered to applications.
     * */
    IC_HOME(3),


    /** Directional Pad Up key.
     * May also be synthesized from trackball motions. */
    IC_DPAD_UP(19),
    /** Directional Pad Down key.
     * May also be synthesized from trackball motions. */
    IC_DPAD_DOWN(20),
    /** Directional Pad Left key.
     * May also be synthesized from trackball motions. */
    IC_DPAD_LEFT(21),
    /** Directional Pad Right key.
     * May also be synthesized from trackball motions. */
    IC_DPAD_RIGHT(22),
    /** Directional Pad Center key.
     * May also be synthesized from trackball motions. */
    IC_DPAD_CENTER(23),


    IC_POWER(26),
    IC_CAMERA(27),
    IC_CLEAR(28),

    /** Number modifier key.
     * Used to enter numeric symbols.
     * This key is not Num Lock; it is more like {@link #IC_ALT_LEFT} and is
     * interpreted as an ALT key by {@link android.text.method.MetaKeyKeyListener}. */
    IC_NUM(78),

    /** Camera Focus key.
     * Used to focus the camera. */
    IC_FOCUS(80),
    /** Menu key. */
    IC_MENU(82),
    /** Notification key. */
    IC_NOTIFICATION(83),
    /** Search key. */
    IC_SEARCH(84),


    /** Forward key.
     * Navigates forward in the history stack.  Complement of {@link #IC_BACK}. */
    IC_FORWARD(125),


    /** Manner Mode key.
     * Toggles silent or vibrate mode on and off to make the device behave more politely
     * in certain settings such as on a crowded train.  On some devices, the key may only
     * operate when long-pressed. */
    IC_MANNER_MODE(205),
    /** 3D Mode key.
     * Toggles the display between 2D and 3D mode. */
    IC_3D_MODE(206),

    /** Brightness Down key.
     * Adjusts the screen brightness down. */
    IC_BRIGHTNESS_DOWN(220),
    /** Brightness Up key.
     * Adjusts the screen brightness up. */
    IC_BRIGHTNESS_UP(221);


    private final int mCode;
    private final char mChar;


    private InputCode(int keyCode) {
        mCode = keyCode;
        mChar = '\u0000';
    }


    private InputCode(int keyCode, char c) {
        mCode = keyCode;
        mChar = c;
    }


    public final int code() {
        return mCode;
    }


    public final char symbol() {
        return mChar;
    }


    /** Converts a character into a InputCode.
     *
     * @return keycode associated with c or {@link #IC_UNKOWN}.
     */
    public final static InputCode fromChar(char c) {
        for (InputCode ic : InputCode.values()) {
            System.out.println("try ic "+ic.name()+"("+ic.code()+", "+ic.symbol()+") for char "+c);
            if (c == ic.symbol()) {
                System.out.println("return ic "+ic.name()+"("+ic.code()+", "+ic.symbol()+") for char "+c);
                return ic;
            }
        }
        return InputCode.IC_UNKNOWN;
    }


    /** Converts a sequence of characters into an array of InputCodes.
     *
     * @see {@link #fromChar}.
     */
    public final static InputCode[] fromSequence(CharSequence sequence) {
        int length = sequence.length();
        InputCode[] result = new InputCode[length];

        for (int i=0; i < length; ++i) {
            result[i] = fromChar(sequence.charAt(i));
        }
        return result;
    }
}
