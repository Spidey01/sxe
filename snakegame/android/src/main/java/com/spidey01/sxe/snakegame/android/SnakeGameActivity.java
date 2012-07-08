package com.spidey01.sxe.snakegame.android;

import com.spidey01.sxe.android.GameActivity;
import com.spidey01.sxe.android.AndroidGameEngine;
import com.spidey01.sxe.snakegame.lib.SnakeGame;

import android.app.Activity;
import android.os.Bundle;

public class SnakeGameActivity extends GameActivity {

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEngine = new AndroidGameEngine(mDisplay, mInput, new SnakeGame());
        setContentView(mDisplay);
        mEngine.start();
    }
}
