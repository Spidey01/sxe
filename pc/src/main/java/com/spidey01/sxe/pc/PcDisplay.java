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

package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.FrameEndedListener;
import com.spidey01.sxe.core.FrameListener;
import com.spidey01.sxe.core.FrameStartedListener;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.RateCounter;

import com.spidey01.sxe.core.gl.OpenGL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

import java.util.List;
import java.util.ArrayList;



public class PcDisplay implements com.spidey01.sxe.core.Display {

    private RateCounter mFrameCounter = new RateCounter("Frames");
    /** default to VGA */
    private DisplayMode mDisplayMode = new DisplayMode(640, 480);
    private List<FrameStartedListener> mFrameStartedListeners = new ArrayList<FrameStartedListener>();
    private List<FrameEndedListener> mFrameEndedListeners = new ArrayList<FrameEndedListener>();
    private OpenGL mOpenGL;
    private static final String TAG = "PcDisplay";


    /** Create the display based on the desired parameters.
     *
     * @param desired A string like that used with setMode().
     */
    public PcDisplay(String desired) {
        if (!setMode(desired)) {
            throw new RuntimeException("Can't set displaymode to "+desired);
        }
    }


    /** Create the Display based on the desktops current DisplayMode.
     *
     * This should generally get you a full screen Display instance that
     * matches the users desktop environment. E.g. 1080p@60hz.
     */
    public PcDisplay() {
        if (!setMode(Display.getDesktopDisplayMode())) {
            throw new RuntimeException("Can't set displaymode to match desktop");
        }
    }


    public boolean create() {
        try {
            Display.create();
            mOpenGL = new LwjglOpenGL();
        } catch (LWJGLException e) {
            Log.e(TAG, "create() can't create LWJGL display :'(");
            e.printStackTrace();
            return false;
        }

        Log.i(TAG, "Display supports OpenGL "+getOpenGLVersion());
        return true;
    }


    public void destroy() {
		Display.destroy();
    }


    public void update() {
        try {
            for (FrameStartedListener o : mFrameStartedListeners) {
                o.frameStarted(mOpenGL);
            }

            Display.update();
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
        return Display.isCloseRequested();
    }


    /** Set display mode
     *
     *
     * @param mode A string in the format "width x height x bpp @refresh". E.g.
     * "640 x 480 x 16 @60". Omitted parts will be undefined.
     *
     * @return true if successful; false otherwise.
     */
    public boolean setMode(String mode) {
        DisplayMode p = null;
        DisplayMode[] modes = null;

        try {
            modes = Display.getAvailableDisplayModes();
        } catch (LWJGLException e) {
            return false;
        }

        for (DisplayMode c : modes) {
            Log.xtrace(TAG, "looking at Display Mode", c, "for desired mode", mode);
            if (c.isFullscreenCapable() && c.toString().startsWith(mode)) {
                // Do we really care if it's full screen capable?
                p = c;
                break;
            }
        }

        if (p != null) {
            return setMode(p);
        }
        return false;
    }


    private boolean setMode(DisplayMode mode) {
        try {
            mDisplayMode = mode;
            Log.i(TAG, "Setting display resolution to ", mode);
            Display.setDisplayMode(mDisplayMode);
        } catch (LWJGLException e) {
            Log.e(TAG, "Couldn't set display mode for LWJGL!", e);
            return false;
        }
        return true;
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
        ContextCapabilities ctx = GLContext.getCapabilities();
        // Yes, this is excessive.
        return (ctx.OpenGL42 ? "4.2"
                   : (ctx.OpenGL41 ? "4.1"
                       : (ctx.OpenGL40 ? "4.0"
                           : (ctx.OpenGL33 ? "3.3"
                               : (ctx.OpenGL32 ? "3.2"
                                   : (ctx.OpenGL31 ? "3.1"
                                       : (ctx.OpenGL30 ? "3.0"
                                           : (ctx.OpenGL21 ? "2.1"
                                               : (ctx.OpenGL20 ? "2.0"
                                                   : (ctx.OpenGL15 ? "1.5"
                                                       : (ctx.OpenGL14 ? "1.4"
                                                           : (ctx.OpenGL13 ? "1.3"
                                                               : (ctx.OpenGL12 ? "1.2"
                                                                   : (ctx.OpenGL11 ? "1.1"
                                                                        : "wtf"))))))))))))));
    }

}

