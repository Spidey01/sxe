/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

package com.spidey01.sxe.modelviewer.lib;

import com.spidey01.sxe.core.*;

import java.io.IOException;


/** ModelViewer.
 */
public class ModelViewer
    extends Game
    implements KeyListener
{
    private static final String TAG = "ModelViewer";

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);
        Log.v(TAG, "ModelViewer is starting.");

        mGameEngine.getInputManager().addKeyListener("Q", this);

        { // debug
            Sprite sprite = new Sprite(new Mesh(new float[]{
                // Left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                // Right top triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
            }), null);

            // A better way of doing this might be nice!
            sprite.setTechnique(new VertexBufferTechnique(engine.getDisplay().getOpenGL()));
            mGameEngine.getSceneManager().add(sprite);
        }

        return true;
    }

    @Override
    public void stop() {
        super.stop();
        if (isStopped()) {
            return;
        }

        Log.v(TAG, "ModelViewer is stopping.");
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


