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
package com.spidey01.sxe.core.testing;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.RateCounter;
import com.spidey01.sxe.core.common.AbstractSubsystem;
import com.spidey01.sxe.core.gl.OpenGL;
import com.spidey01.sxe.core.gl.OpenGLES11;
import com.spidey01.sxe.core.gl.OpenGLES20;
import com.spidey01.sxe.core.gl.OpenGLES30;
import com.spidey01.sxe.core.graphics.Display;
import com.spidey01.sxe.core.graphics.FrameEndedListener;
import com.spidey01.sxe.core.graphics.FrameListener;
import com.spidey01.sxe.core.graphics.FrameStartedListener;
import com.spidey01.sxe.core.graphics.GraphicsTechnique;
import com.spidey01.sxe.core.graphics.RenderData;
import com.spidey01.sxe.core.graphics.RenderableObject;
import com.spidey01.sxe.core.logging.Log;

import java.util.List;
import java.util.ArrayList;


public class NullDisplay
    extends AbstractSubsystem
    implements Display
{

    private RateCounter mFrameCounter = new RateCounter("NullFrames");
    private String mDisplayMode = "640 x 480";
    private List<FrameStartedListener> mFrameStartedListeners = new ArrayList<FrameStartedListener>();
    private List<FrameEndedListener> mFrameEndedListeners = new ArrayList<FrameEndedListener>();
    private OpenGL mOpenGL;
    private final boolean mDefaultAnswer;
    private static final String TAG = "NullDisplay";


    /** Creates a NULL Display.
     *
     * @param GL implementation of OpenGL to utilize.
     * @param answer whether to succeed or fail operations.
     */
    public NullDisplay(OpenGL GL, boolean answer) {
        mOpenGL = GL;
        mDefaultAnswer = answer;
    }

    /** Creates a NULL Display and OpenGL. */
    public NullDisplay(boolean answer) {
        mOpenGL = new NullOpenGL();
        mDefaultAnswer = answer;
    }


    public boolean create() {
        Log.i(TAG, "Display supports OpenGL "+getOpenGLVersion());
        Log.d(TAG, "create()");
        return mDefaultAnswer;
    }


    public void destroy() {
        Log.d(TAG, "destroy()");
    }


    public void update() {
        try {
            for (FrameStartedListener o : mFrameStartedListeners) {
                o.frameStarted(mOpenGL);
            }

            mFrameCounter.update();

            for (FrameEndedListener o : mFrameEndedListeners) {
                o.frameEnded();
            }
        } catch(Exception e) {
            Log.wtf(TAG, "Exception under Display.update(), halting.", e);
            destroy();
        }
    }


    public boolean isCloseRequested() {
        return !mDefaultAnswer;
    }


    public boolean setMode(String mode) {
        Log.d(TAG, "setMode(", mode, ")");

        if (mDefaultAnswer) {
            mDisplayMode = mode;
        }
        return mDefaultAnswer;
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


    public OpenGL getOpenGL() {
        return mOpenGL;
    }


    public String getOpenGLVersion() {
        // this is what the PC version returns if unknown.
        return "wtf";
    }


    @Override
    public String name() {
        return TAG;
    }


    @Override
    public void initialize(GameEngine engine) {
        Log.d(TAG, "initialize(", engine, ")");
    }


    @Override
    public void reinitialize(GameEngine engine) {
        uninitialize();
        initialize(engine);
    }


    @Override
    public void uninitialize() {
        Log.d(TAG, "uninitialize()");
    }


    @Override
    public GraphicsTechnique getTechnique(RenderData object) {
        Log.d(TAG, "getTechnique(", object, ")");

        // TODO: a null technique implementation.
        return null;
    }
}

