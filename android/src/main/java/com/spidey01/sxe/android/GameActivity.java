package com.spidey01.sxe.android;

import com.spidey01.sxe.core.Display;
import com.spidey01.sxe.core.GameEngine;

import android.app.Activity;
import android.os.Bundle;

public abstract class GameActivity extends Activity {
    private static final String TAG = "GameActivity";

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract GameEngine getGameEngine();

    protected void onDestroy() {
        // not sure if this will really make a difference yet.
        getGameEngine().stop();
    }

}

