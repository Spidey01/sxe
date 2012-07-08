package com.spidey01.sxe.android;

import com.spidey01.sxe.android.AndroidDisplay;
import com.spidey01.sxe.android.AndroidGameEngine;
import com.spidey01.sxe.core.Display;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    protected AndroidDisplay mDisplay;
    protected AndroidGameEngine mEngine;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.main);
        mDisplay = new AndroidDisplay(this);
    }
}
