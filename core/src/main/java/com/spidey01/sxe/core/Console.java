package com.spidey01.sxe.core;

public class Console implements KeyListener {
    protected boolean mVisable = false;
    private static final String TAG = "Console";

    public boolean isVisable() {
        return mVisable;
    }

    public void setVisable(boolean visable) {
        Log.v(TAG, "console " + (visable == true ? "opened" : "closed"));
        mVisable = visable;
    }

    public boolean onKey(KeyEvent event) {
        if (isVisable()) {
            Log.v(TAG, "console rec'd "+event);
        }
        return false;
    }
}

