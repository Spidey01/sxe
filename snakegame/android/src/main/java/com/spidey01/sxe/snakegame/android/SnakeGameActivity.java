package com.spidey01.sxe.snakegame.android;

import com.spidey01.sxe.android.AndroidDisplay;
import com.spidey01.sxe.core.Display;

import android.app.Activity;
import android.os.Bundle;

public class SnakeGameActivity extends Activity {


    private AndroidDisplay mDisplay;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.main);

        mDisplay = new AndroidDisplay(this);
        setContentView(mDisplay);
    }
}
