package com.spidey01.sxe.android;

import com.spidey01.sxe.android.AndroidDisplay;
import com.spidey01.sxe.android.AndroidGameEngine;
import com.spidey01.sxe.android.AndroidInputManager;
import com.spidey01.sxe.core.Display;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    protected AndroidDisplay mDisplay;
    protected AndroidGameEngine mEngine;
    protected AndroidInputManager mInput;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.main);

        mDisplay = new AndroidDisplay(this);
        mInput = new AndroidInputManager(mDisplay);
        mEngine.getResources().setLoader("assets", new AssetLoader(getAssets()));
    }

    protected void onDestroy() {
        // not sure if this will really make a difference yet.
        mEngine.stop();
    }
}

