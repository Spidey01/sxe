package com.spidey01.sxe.core;

public class Console implements KeyListener {
    /** Sequence of valid characters that can be used in a console command. */
    public static final String VALID_SYMBOLS = 
        "~!@#$%^&*()_+-={}[]:;\"'<,>.?/ ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    protected static final int INITIAL_BUFFER_SIZE = 160; /* works for 7-bit SMS */
    public static final String DEFAULT_TOGGLE_KEY = "GRAVE";

    protected boolean mVisable = false;
    protected String mToggleKey = DEFAULT_TOGGLE_KEY ;
    private StringBuilder mBuffer = new StringBuilder(INITIAL_BUFFER_SIZE);
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


    public boolean onKey(KeyEvent event) {
        if (!event.isKeyDown()) {
            // we don't support key repeat yet but that's trivial to do I guess.
            return false;
        }

        if (event.getKeyName().equals("GRAVE")) {
            setVisable(!isVisable());
            return true;
        }

        if (!isVisable()) {
            return false;
        }


        if (event.getKeyName().equals("ENTER") || event.getKeyName().equals("RETURN")) {
            Log.d(TAG, "Execute console command: "+mBuffer);
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

        if (event.getKeyName().equals("SPACE")) {
            mBuffer.append(' ');
            return true;
        }

        if (VALID_SYMBOLS.contains(event.getKeyName())) {
            // Log.v(TAG, "Append \""+event.getKeyName()+"\" to \""+mBuffer+"\"");
            mBuffer.append(event.getKeyName());
        }
        return true; // consume even if not added to buffer, because the console is OPEN.
    }
}

