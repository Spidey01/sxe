package com.spidey01.sxe.core;

import java.util.Map;
import java.util.HashMap;

// TODO: clear mBuffer when console is hidden. IDK if this will need to be
//       kept until the rendering phase thought so for right now it's retained.
public class Console implements KeyListener {

    public static final int DEFAULT_REPEAT_DELAY = 2;
    public static final String DEFAULT_TOGGLE_KEY = "GRAVE";
    /** Sequence of valid characters that can be used in a console command. */
    public static final String VALID_SYMBOLS = 
        "~!@#$%^&*()_+-={}[]:;\"'<,>.?/ ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    protected static final int INITIAL_BUFFER_SIZE = 160; /* works for 7-bit SMS */

    private String mToggleKey = DEFAULT_TOGGLE_KEY ;

    private KeyEvent mLastKeyEvent = null;
    private boolean mHandleRepeat = true;
    private int mRepeatDelay = DEFAULT_REPEAT_DELAY;
    private long mRepeatCount = 0;

    private boolean mVisable = false;

    private StringBuilder mBuffer = new StringBuilder(INITIAL_BUFFER_SIZE);
    private Map<String,ConsoleCommand> mCommands = new HashMap<String,ConsoleCommand>();

    private static final String TAG = "Console";

    public Console() {
    }

    public Console(String toggleKey) {
        mToggleKey = toggleKey;
    }

    public boolean isVisable() {
        return mVisable;
    }

    public void setVisable(boolean visable) {
        Log.v(TAG, "console " + (visable == true ? "opened" : "closed"));
        mVisable = visable;
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
            if (event.getKeyName().equals("GRAVE")) {
                setVisable(!isVisable());
                return true;
            }
        }
        if (!isVisable()) {
            return false;
        }

        // stuff that only fires if the key was released
        if (event.isKeyUp()) {
            if (event.getKeyName().equals("ENTER")
                || event.getKeyName().equals("RETURN"))
            {
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
            assert (!mLastKeyEvent.equals(event)) : "The key events are supposed to be equal here";

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


        if (event.getKeyName().equals("SPACE")) {
            mBuffer.append(' ');
            return true;
        }

        if (VALID_SYMBOLS.contains(event.getKeyName())) {
            Log.v(TAG, "Append \""+event.getKeyName()+"\" to \""+mBuffer+"\"");
            mBuffer.append(event.getKeyName());
        }
        return true; // consume even if not added to buffer, because the console is OPEN.
    }

    public void execute(String line) {
        Log.d(TAG, "Execute console command: "+line);

        int split = line.indexOf(" ");
        String args = null;

        if (split == -1) {
            split = line.length();
        } else {
            args = line.substring(split).trim();
        }

        ConsoleCommand c = mCommands.get(line.substring(0, split));
        if (c != null) {
            c.execute();
        }
    }

    public void addCommand(ConsoleCommand command) {
        if (command == null
            || command.getName() == null
            || command.getName().equals(""))
        {
            throw new IllegalArgumentException("Console doesn't allow null or empty command names");
        }
        mCommands.put(command.getName(), command);
    }

    public void removeCommand(ConsoleCommand command) {
        removeCommand(command.getName());
    }

    public void removeCommand(String command) {
        mCommands.remove(command);
    }
}

