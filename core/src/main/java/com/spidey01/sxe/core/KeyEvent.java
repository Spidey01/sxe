package com.spidey01.sxe.core;

import java.util.EventObject;

public class KeyEvent {
    private InputManager mSource;
    private final int mKeyCode;
    private final String mKeyName;
    private final boolean mIsDown;
    private Object mNative;

    public KeyEvent(InputManager source, int keyCode,
        String keyName, boolean keyDown)
    {
        mSource = source;
        mKeyCode = keyCode;
        mKeyName = keyName;
        mIsDown = keyDown;
        mNative = null;
    }

    public KeyEvent(InputManager source, Object nativeEvent,
        int keyCode,  String keyName, boolean keyDown)
    {
        this(source, keyCode, keyName, keyDown);
        mNative = nativeEvent;
    }

    public InputManager getSource() {
        return mSource;
    }

    public final int getKeyCode() {
        return mKeyCode;
    }

    public final String getKeyName() {
        return mKeyName;
    }

    public final boolean isKeyDown() {
        return mIsDown;
    }

    /** Returns the native event object.
     *
     * It's your job to figure out what to cast it to.
     * It may be null.
     */
    public Object getNativeEvent() {
        return mNative;
    }

    public String toString() {
        return "KeyEvent Input="+mSource+" Code="+mKeyCode+
               " Name="+mKeyName+" Down="+mIsDown+" Native="+mNative;
    }
}

