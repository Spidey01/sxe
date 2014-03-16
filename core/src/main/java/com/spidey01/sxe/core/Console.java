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

package com.spidey01.sxe.core;

import com.spidey01.sxe.core.cmds.Command;
import com.spidey01.sxe.core.gl.OpenGL;
import com.spidey01.sxe.core.graphics.FrameListener;
import com.spidey01.sxe.core.input.InputCode;
import com.spidey01.sxe.core.input.KeyEvent;
import com.spidey01.sxe.core.input.KeyListener;
import com.spidey01.sxe.core.logging.Log;

import java.util.Map;
import java.util.HashMap;


// TODO: clear mBuffer when console is hidden. IDK if this will need to be
//       kept until the rendering phase thought so for right now it's retained.
public class Console
    implements KeyListener, FrameListener
{

    public static final int DEFAULT_REPEAT_DELAY = 2;

    public static final InputCode DEFAULT_TOGGLE_KEY = InputCode.IC_GRAVE;

    /** Sequence of valid characters that can be used in a console command. */
    public static final InputCode[] VALID_SYMBOLS = new InputCode[]{
        InputCode.IC_TILDE,
        InputCode.IC_EXCLAMATION_MARK,
        InputCode.IC_AT_SIGN,
        InputCode.IC_NUMBER_SIGN,
        InputCode.IC_DOLLAR_SIGN,
        InputCode.IC_PERCENT_SIGN,
        InputCode.IC_CARET,
        InputCode.IC_AMPERSAND,
        InputCode.IC_ASTERISK,
        InputCode.IC_LEFT_PAREN, InputCode.IC_NUMPAD_LEFT_PAREN,
        InputCode.IC_RIGHT_PAREN, InputCode.IC_NUMPAD_RIGHT_PAREN,
        InputCode.IC_UNDERSCORE,
        InputCode.IC_PLUS_SIGN,
        InputCode.IC_HYPHEN_MINUS,
        InputCode.IC_EQUAL_SIGN,
        InputCode.IC_LEFT_CURLY_BRACE,
        InputCode.IC_RIGHT_CURLY_BRACE,
        InputCode.IC_LEFT_SQUARE_BRACKET,
        InputCode.IC_RIGHT_SQUARE_BRACKET,
        InputCode.IC_COLON,
        InputCode.IC_SEMICOLON,
        InputCode.IC_DOUBLE_QUOTE,
        InputCode.IC_APOSTROPHE,
        InputCode.IC_GRAVE,
        InputCode.IC_LESS_THAN_SIGN,
        InputCode.IC_GREATER_THAN_SIGN,
        InputCode.IC_PERIOD,
        InputCode.IC_QUESTION_MARK,
        InputCode.IC_BACKSLASH,
        InputCode.IC_SLASH,
        InputCode.IC_SPACE,
        InputCode.IC_0, InputCode.IC_NUMPAD_0,
        InputCode.IC_1, InputCode.IC_NUMPAD_1,
        InputCode.IC_2, InputCode.IC_NUMPAD_2,
        InputCode.IC_3, InputCode.IC_NUMPAD_3,
        InputCode.IC_4, InputCode.IC_NUMPAD_4,
        InputCode.IC_5, InputCode.IC_NUMPAD_5,
        InputCode.IC_6, InputCode.IC_NUMPAD_6,
        InputCode.IC_7, InputCode.IC_NUMPAD_7,
        InputCode.IC_8, InputCode.IC_NUMPAD_8,
        InputCode.IC_9, InputCode.IC_NUMPAD_9,
        InputCode.IC_A,
        InputCode.IC_B,
        InputCode.IC_C,
        InputCode.IC_D,
        InputCode.IC_E,
        InputCode.IC_F,
        InputCode.IC_G,
        InputCode.IC_H,
        InputCode.IC_I,
        InputCode.IC_J,
        InputCode.IC_K,
        InputCode.IC_L,
        InputCode.IC_M,
        InputCode.IC_N,
        InputCode.IC_O,
        InputCode.IC_P,
        InputCode.IC_Q,
        InputCode.IC_R,
        InputCode.IC_S,
        InputCode.IC_T,
        InputCode.IC_U,
        InputCode.IC_V,
        InputCode.IC_W,
        InputCode.IC_X,
        InputCode.IC_Y,
        InputCode.IC_Z,
    };

    protected static final int INITIAL_BUFFER_SIZE = 160; /* works for 7-bit SMS */

    private InputCode mToggleKey = DEFAULT_TOGGLE_KEY ;

    private KeyEvent mLastKeyEvent = null;
    private boolean mHandleRepeat = true;
    private int mRepeatDelay = DEFAULT_REPEAT_DELAY;
    private long mRepeatCount = 0;

    private boolean mVisible = false;

    private StringBuilder mBuffer = new StringBuilder(INITIAL_BUFFER_SIZE);
    private Map<String,Command> mCommands = new HashMap<String,Command>();

    private static final String TAG = "Console";

    public Console() {
    }

    public Console(InputCode toggleKey) {
        mToggleKey = toggleKey;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setVisible(boolean visable) {
        Log.v(TAG, "console " + (visable ? "opened" : "closed"));
        mVisible = visable;
    }

    /** Whether or not holding down a key repeats the input character */
    public void allowRepeating(boolean repeating) {
        Log.v(TAG, "mHandleRepeat = "+repeating);
        mHandleRepeat = repeating;
    }
    public boolean repeatingAllowed() {
        return mHandleRepeat;
    }

    public int getRepeatDelay() {
        return mRepeatDelay;
    }
    public void setRepeatDelay(int count) {
        mRepeatDelay = count;
    }


    public boolean onKey(KeyEvent event) {

        // House keeping for toggling the console
        if (event.isKeyUp()) {
            if (event.getKeyCode().equals(mToggleKey)) {
                setVisible(!isVisible());
                return true;
            }
        }
        if (!isVisible()) {
            return false;
        }

        // stuff that only fires if the key was released
        if (event.isKeyUp()) {
            if (event.getKeyCode().equals(InputCode.IC_ENTER)) {
                execute(mBuffer.toString());
                /*
                 * food for thought: what's worse on a mobile phone: letting the
                 * buffer remain at whatever length it has /become/ and clearing
                 * it. Or just quickly allocate a new one and hope the GC isn't
                 * thrashing?
                 *
                 * Messages are usually short, so let's just clear the buffer and
                 * hope the implementation is so nice as to flick a flag instead of
                 * zeroing the buffer.
                 */
                mBuffer.delete(0, mBuffer.length());
                return true;
            }

            if (mHandleRepeat) {
                mLastKeyEvent = null;
                mRepeatCount = 0;
            }
        }

        if (!mHandleRepeat && event.isKeyDown()) {
            // consume but don't repeat
            return true;
        } else if (mHandleRepeat) {
            // house keeping for repeat
            mLastKeyEvent = event;

            if (event.isKeyDown()) {

                if (mRepeatCount < getRepeatDelay()) {
                    // not long enough to trigger a repeat.
                    mRepeatCount++;
                    return true;
                } else {
                    // clear repeat count so we repeat at smoother rate.
                    mRepeatCount = 0;
                }
            }
        }

        boolean mIsShiftDown = false;
        InputCode thisCode = event.getKeyCode();
        for (InputCode code : VALID_SYMBOLS) {
            if (code.equals(thisCode)) {
                char c = mIsShiftDown ? code.upper() : code.lower();
                Log.xtrace(TAG, "Append '"+c+"' to \""+mBuffer+"\"");
                mBuffer.append(c);
                break;
            }
        }

        // consume even if not added to buffer, because the console is OPEN.
        return true;
    }

    public void execute(String line) {
        Log.v(TAG, "execute(\""+line+"\")");

        int split = line.indexOf(" ");
        String args = null;

        if (split == -1) {
            split = line.length();
        } else {
            args = line.substring(split).trim();
        }

        // Log.xtrace(TAG, "Command is", "|"+line.substring(0, split)+"|");
        // Log.xtrace(TAG, "Args are", "|"+args+"|");
        Command c = mCommands.get(line.substring(0, split));
        // Log.xtrace(TAG, "Command object:", c);
        if (c != null) {
            c.setArgs(args);
            c.run();
        }
    }

    public void addCommand(Command command) {
        if (command == null
            || command.getName() == null
            || command.getName().equals(""))
        {
            throw new IllegalArgumentException("Console doesn't allow null or empty command names");
        }
        Log.xtrace(TAG, "add command", command.getName(), "to console", this);
        mCommands.put(command.getName(), command);
    }

    public void removeCommand(Command command) {
        removeCommand(command.getName());
    }

    public void removeCommand(String command) {
        mCommands.remove(command);
    }

    public void frameStarted(OpenGL GL20) {
        if (!isVisible()) {
            return;
        }
    }

    public void frameEnded() {
    }
}

