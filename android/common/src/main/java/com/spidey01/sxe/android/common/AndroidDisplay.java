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

package com.spidey01.sxe.android.common;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.common.Subsystem;
import com.spidey01.sxe.core.gl.OpenGL;
import com.spidey01.sxe.core.graphics.Display;
import com.spidey01.sxe.core.graphics.FrameEndedListener;
import com.spidey01.sxe.core.graphics.FrameListener;
import com.spidey01.sxe.core.graphics.FrameStartedListener;
import com.spidey01.sxe.core.logging.Log;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class AndroidDisplay 
    extends GLSurfaceView
    implements Display
{
    private OpenGLES20Renderer mRenderer = new OpenGLES20Renderer();
    private OpenGL mOpenGL;
    private static final String TAG = "AndroidDisplay";
    private boolean mIsInitialized = false;

    public AndroidDisplay(Context context) {
        super(context);

        // FIXME
        // mOpenGL = new AndroidOpenGLES();
        mOpenGL = null;

        // required so OnKey works.
        setFocusable(true);

        // setup for OpenGL ES 2
        setEGLContextClientVersion(2);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    @Override
    public String name() {
        return TAG;
    }


    @Override
    public boolean isInitialized() {
        return mIsInitialized;
    }


    @Override
    public void initialize(GameEngine engine) {
        Log.d(TAG, "initialize(", engine, ")");
        mIsInitialized = true;
    }


    @Override
    public void reinitialize(GameEngine engine) {
        uninitialize();
        initialize(engine);
    }


    @Override
    public void uninitialize() {
        Log.d(TAG, "uninitialize()");
        mIsInitialized = false;
    }

    public boolean create() {
        return requestFocus();
    }

    public void destroy() {
    }

    public void update() {
        // except for the input polling, this ought to mimic the LWJGL
        // Display.update() well enough.
        invalidate();
        requestRender();
    }

    public boolean isCloseRequested() {
        // This is probably correct given that we expect to be created from an
        // Activity 97.9+% of the time.  But I'm not sure I want this to be
        // called frequently, nor do I really think it's a good way of doing
        // it. So for now we don't rely on this method under Android.
        /*
        if (${our saved Context} instanceof Activity) {
            return (Activity)${ctx^}.isFinishing();
        }
        */
        return false;
    }

    public boolean setMode(String mode) {
        return false;
    }

    public OpenGL getOpenGL() {
        return mOpenGL;
    }

    public void addFrameListener(FrameListener listener) {
        mRenderer.addFrameListener(listener);
    }

    public void addFrameStartedListener(FrameStartedListener listener) {
        mRenderer.addFrameStartedListener(listener);
    }

    public void addFrameEndedListener(FrameEndedListener listener) {
        mRenderer.addFrameEndedListener(listener);
    }

    public void removeFrameListener(FrameListener listener) {
        mRenderer.removeFrameListener(listener);
    }

    public void removeFrameStartedListener(FrameStartedListener listener) {
        mRenderer.removeFrameStartedListener(listener);
    }

    public void removeFrameEndedListener(FrameEndedListener listener) {
        mRenderer.removeFrameEndedListener(listener);
    }
}

