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

import java.util.EventObject;

public class KeyEvent {
    private InputManager mSource;
    private final int mKeyCode;
    private final String mKeyName;
    private final boolean mIsDown;
    private Object mNative;
    private static final String TAG = "KeyEvent";

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

    public int getKeyCode() {
        return mKeyCode;
    }

    public String getKeyName() {
        return mKeyName;
    }

    public boolean isKeyDown() {
        return mIsDown;
    }

    public boolean isKeyUp() {
        return !mIsDown;
    }

    /** Returns the native event object.
     *
     * It's your job to figure out what to cast it to.
     * It may be null.
     */
    public Object getNativeEvent() {
        return mNative;
    }

    /** Format this KeyEvent for pretty printing.
     *
     * <samp>KeyEvent Input=com.spidey01.sxe.pc.PcInputManager@2f3adc56 Code=16 Name=Q Down=false Native=null</samp>
     */
    @Override
    public String toString() {
        return "KeyEvent Input="+mSource+" Code="+mKeyCode+
               " Name="+mKeyName+" Down="+mIsDown+" Native="+mNative;
    }

    /** Compare KeyEvent's.
     *
     * Two KeyEvent are considered equal if the key code, name, and status match.
     * The input source and native event may however differ.
     */
    public boolean equals(KeyEvent other) {
        return mKeyCode == other.getKeyCode()
            && mKeyName == other.getKeyName()
            && mIsDown == other.isKeyDown();
    }
}

