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

package com.spidey01.sxe.android;

import com.spidey01.sxe.core.FrameEndedListener;
import com.spidey01.sxe.core.FrameListener;
import com.spidey01.sxe.core.FrameStartedListener;
import com.spidey01.sxe.core.RateCounter;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.util.List;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLES20Renderer implements GLSurfaceView.Renderer {

    private RateCounter mFrameCounter = new RateCounter("Frames");
    private List<FrameStartedListener> mFrameStartedListeners = new ArrayList<FrameStartedListener>();
    private List<FrameEndedListener> mFrameEndedListeners = new ArrayList<FrameEndedListener>();
    private static final String TAG = "OpenGLES20Renderer";

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        for (FrameStartedListener o : mFrameStartedListeners) {
            o.frameStarted(null/*fix me*/);
        }

        // 128,0,128 -> for my Firefly.
        // GLES20.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        for (FrameEndedListener o : mFrameEndedListeners) {
            o.frameEnded();
        }
    }

    public void onDrawFrame(GL10 unused) {
        mFrameCounter.update();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void addFrameListener(FrameListener listener) {
        mFrameStartedListeners.add(listener);
        mFrameEndedListeners.add(listener);
    }

    public void addFrameStartedListener(FrameStartedListener listener) {
        mFrameStartedListeners.add(listener);
    }

    public void addFrameEndedListener(FrameEndedListener listener) {
        mFrameEndedListeners.add(listener);
    }

    public void removeFrameListener(FrameListener listener) {
        mFrameStartedListeners.remove(listener);
        mFrameEndedListeners.remove(listener);
    }

    public void removeFrameStartedListener(FrameStartedListener listener) {
        mFrameStartedListeners.remove(listener);
    }

    public void removeFrameEndedListener(FrameEndedListener listener) {
        mFrameEndedListeners.remove(listener);
    }
}

