package com.spidey01.sxe.snakegame.android;

import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.GameEngine;

import com.spidey01.sxe.android.AndroidConfiguration;
import com.spidey01.sxe.android.AndroidDisplay;
import com.spidey01.sxe.android.AndroidGameEngine;
import com.spidey01.sxe.android.GameActivity;

import com.spidey01.sxe.snakegame.lib.SnakeGame;

import android.app.Activity;
import android.os.Bundle;

public class SnakeGameActivity extends GameActivity {

    protected AndroidGameEngine mEngine;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEngine = (AndroidGameEngine)AndroidConfiguration.setup(new SnakeGame(), this);

        setContentView((AndroidDisplay)C.getDisplay());

        mEngine.start();
    }

    public GameEngine getGameEngine() {
        return mEngine;
    }
}
