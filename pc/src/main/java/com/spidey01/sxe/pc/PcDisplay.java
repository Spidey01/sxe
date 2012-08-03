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
import com.spidey01.sxe.core.OpenGl;
import com.spidey01.sxe.core.RateCounter;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.List;
import java.util.ArrayList;

// for testing stuff
import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.GlslProgram;
import com.spidey01.sxe.core.GlslShader;
import com.spidey01.sxe.core.GpuProgram;
import com.spidey01.sxe.core.Resource;
import com.spidey01.sxe.core.ShaderFactory;
import com.spidey01.sxe.core.ResourceManager;
import com.spidey01.sxe.core.Shader;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;



public class PcDisplay implements com.spidey01.sxe.core.Display {

    private RateCounter mFrameCounter = new RateCounter("Frames");
    /** default to VGA */
    private DisplayMode mDisplayMode = new DisplayMode(640, 480);
    private List<FrameStartedListener> mFrameStartedListeners = new ArrayList<FrameStartedListener>();
    private List<FrameEndedListener> mFrameEndedListeners = new ArrayList<FrameEndedListener>();
    private OpenGl mOpenGl;
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
            mOpenGl = new LwjglOpenGl();
            setupTriangle();
        } catch (LWJGLException e) {
            Log.e(TAG, "create() can't create LWJGL display :'(");
            e.printStackTrace();
            return false;
        }

        Log.i(TAG, "Display supports OpenGL "+getOpenGlVersion());
        return true;
    }

    public void destroy() {
		Display.destroy();
    }

    public void update() {
        for (FrameStartedListener o : mFrameStartedListeners) {
            o.frameStarted(mOpenGl);
        }

        draw_triangle();
        Display.update();
        mFrameCounter.update();

        for (FrameEndedListener o : mFrameEndedListeners) {
            o.frameEnded();
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

        for (int i=0; i < modes.length; i++) {
            DisplayMode c = modes[i];
            if (c.isFullscreenCapable() && c.toString().startsWith(mode)) {
                p = c;
                break;
            }
        }

        if (p != null) {
            return setMode(mDisplayMode);
        }
        return false;
    }

    private boolean setMode(DisplayMode mode) {
        try {
            mDisplayMode = mode;
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

    public OpenGl getOpenGl() {
        return mOpenGl;
    }

    public String getOpenGlVersion() {
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

///////////////////////////////////////////////////////////////////////////////
// Testing stuff
///////////////////////////////////////////////////////////////////////////////

/* triangle.vert:

// shader for the Triangle rendering test code in SnakeGame
attribute vec2 coord2d;
void main(void) {
  gl_Position = vec4(coord2d, 0.0, 1.0);
}
 */
/* triangle.frag:

// shader for the Triangle rendering test code in SnakeGame
void main(void) {
  gl_FragColor[0] = 0.0;
  gl_FragColor[1] = 1.0;
  gl_FragColor[2] = 0.0;
}
 */

    // simple 2D triangle
    private int mCoord2d = -1;
    private GlslProgram mProgram;
    private static IntBuffer mVBO;;
    private void setupTriangle() {
        // Setup shaders.
        GlslShader vert = null;
        GlslShader frag = null;
        try {
            ShaderFactory<GlslShader> factory = new ShaderFactory<GlslShader>(){
                public Shader make(Shader.Type type, InputStream is, final String path) {
                    return new GlslShader(mOpenGl, type, is, path);
                }
            };
            vert = (GlslShader)C.getResources().load("shaders/triangle.vert", factory).getObject();
            frag = (GlslShader)C.getResources().load("shaders/triangle.frag", factory).getObject();
        } catch(Exception fml) {
            Log.wtf(TAG, "Failed loading shaders", fml);
            fml.printStackTrace();
        }

        // Setup shader program.
        mProgram = new GlslProgram(mOpenGl);
        mProgram.addShader(vert);
        mProgram.addShader(frag);
        if (!mProgram.link() || !mProgram.validate()) {
            Log.wtf(TAG, "Shader failure: "+mProgram.getInfoLog());
        }

        // Setup vertex data to feed into a VBO.
        float[] triangleVertices = {
            0.0f,  0.8f,
            -0.8f, -0.8f,
            0.8f,  -0.8f,
        };
        FloatBuffer buffer = BufferUtils.createFloatBuffer(triangleVertices.length);
        buffer.put(triangleVertices);
        buffer.flip();

        // Generate an ID for our VBO in the video memory and bind it.
        mVBO = BufferUtils.createIntBuffer(1);
        mOpenGl.glGenBuffers(mVBO);
        mOpenGl.glBindBuffer(OpenGl.GL_ARRAY_BUFFER, mVBO.get(0));

        // Buffer it to the GPU.
        mOpenGl.glBufferData(OpenGl.GL_ARRAY_BUFFER, buffer, OpenGl.GL_STATIC_DRAW);

        // Create coord2d attribute for our fragment shader.
        mCoord2d = mOpenGl.glGetAttribLocation(mProgram.getProgram(), "coord2d");
        if (mCoord2d == -1) {
            Log.wtf(TAG, "Couldn't bind coord2d attribute!");
        }
    }
    private void draw_triangle() {
        // Clear the background.
        mOpenGl.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        mOpenGl.glClear(mOpenGl.GL_COLOR_BUFFER_BIT);

        // Ready the shader program.
        mOpenGl.glUseProgram(mProgram.getProgram());

        // Lock and load the coord2d attribute for our fragment shader.
        mOpenGl.glEnableVertexAttribArray(mCoord2d);
        mOpenGl.glVertexAttribPointer(mCoord2d, 2, mOpenGl.GL_FLOAT, false, 0, 0);
 
        // feed it to our shader to draw.
        mOpenGl.glBindBuffer(mOpenGl.GL_ARRAY_BUFFER, mVBO.get(0));
        mOpenGl.glDrawArrays(mOpenGl.GL_TRIANGLES, 0, 3);

        mOpenGl.glDisableVertexAttribArray(mCoord2d);
    }
}

