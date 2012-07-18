package com.spidey01.sxe.core;

public class ConsoleCommand implements Action {
    private static final String TAG = "ConsoleCommand";

    private String mName = "";

    public void execute() {
        Log.v(TAG, getName()+" executed");
    }

    public String getName() {
        return mName;
    }

    public boolean equals(ConsoleCommand other) {
        return mName.equals(other.getName());
    }
}


