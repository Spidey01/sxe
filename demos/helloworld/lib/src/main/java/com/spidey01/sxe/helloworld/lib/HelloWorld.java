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

package com.spidey01.sxe.helloworld.lib;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;

import com.spidey01.sxe.core.*;
import java.io.IOException;

/** Hello World demo.
 */
public class HelloWorld
    extends Game
    implements KeyListener
{
    private static final String TAG = "HelloWorld";

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);

        Log.v(TAG, "Hello World demo is starting.");

        mGameEngine.getInputManager().addKeyListener("Q", this);

        /*
        try {
            TrueTypeFont mFont = TrueTypeFont.from("tmp/HelloWorld.ttf");
            Text s = new Text("Hello, world!", mFont);
            // mGameEngine.getDisplay().addFrameStartedListener(mFont);
        } catch(IOException e) {
            Log.e(TAG, "Caught IOExeption from TrueTypeFont.from(String)", e);
        }
        */
        ResourceHandle spriteResource;
        Texture spriteTexture;
        Sprite sprite;
        try {
            spriteResource = mGameEngine.getResourceManager().load("default://Sprite.png");
            spriteTexture = spriteResource.asTexture();
        } catch (IOException e) {
            Log.w(TAG, "Failed loading Sprite:", e);
            return false;
        }
        sprite = new Sprite(spriteTexture);
        // mGameEngine.getDisplay().addFrameStartedListener(sprite);
        mGameEngine.getSceneManager().add(sprite);

        return true;
    }

    @Override
    public void stop() {
        super.stop();
        if (isStopped()) {
            return;
        }

        Log.v(TAG, "Hello World demo is stopping.");
    }

    @Override
    public void tick() {
        switch (mState) {
            case STARTING: {
            } break;
            case RUNNING: {
            } break;
            case STOPPING: {
            } break;
        }
    }

    public boolean onKey(KeyEvent event) {
        if (event.isKeyUp()) {
            if (event.getKeyName().equals("Q")) {
                Log.d(TAG, "Q key released");
                requestStop();
                return true;
            }
        }
        return false;
    }
}

