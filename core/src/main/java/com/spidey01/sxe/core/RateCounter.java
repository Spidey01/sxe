package com.spidey01.sxe.core;

// TODO: use Log.
public class RateCounter {
    private long mPerSecond;
    private long ms;

    private String mName;
    private boolean mDebuggingEnabled;
    private static final String TAG = "RateCounter";

    public RateCounter(String name, boolean debug) {
        mName = name;
        mDebuggingEnabled = debug;
        ms = System.currentTimeMillis();
    }

    public RateCounter(String name) {
        this(name, true);
    }

    public String getName() {
        return mName;
    }

    public void disableDebugging() {
        mDebuggingEnabled = false;
    }
    public void enableDebugging() {
        mDebuggingEnabled = true;
    }
    public boolean isDebuggingEnabled() {
        return mDebuggingEnabled;
    }

    public void update() {
        long now = System.currentTimeMillis();

        if (now > (ms+1000)) {
            if (isDebuggingEnabled()) {
                System.out.println(getName()+": "+(mPerSecond-1)+" per second");
            }
            ms = now;
            mPerSecond = 0;
        }
        mPerSecond++;
    }

}
