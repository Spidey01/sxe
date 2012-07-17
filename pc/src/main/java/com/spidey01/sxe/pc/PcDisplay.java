package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.RateCounter;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.OpenGl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

// for testing stuff
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
        debuggy();
        Display.update();
        mFrameCounter.update();
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

    private static final float[] vertexPositions = {
        0.75f, 0.75f, 0.0f, 1.0f,
        0.75f, -0.75f, 0.0f, 1.0f,
        -0.75f, -0.75f, 0.0f, 1.0f,
    };
    private static final float[] vertexColors = {
        1.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 1.1f,
    };
    private static final int mNumberOfVertices = 3;
    // private static int positionBufferObject;
    private static IntBuffer positionBufferObject;
    private static IntBuffer colorBufferObject;
    private static boolean mDoneSetup = false;

    private static OpenGl mGL = new LwjglOpenGl();

    private void debuggy() { // place to test shit
        if (mDoneSetup == false) {
            FloatBuffer verticesBuffer = mGL.createFloatBuffer(vertexPositions.length);
            verticesBuffer.put(vertexPositions);
            verticesBuffer.flip();

            positionBufferObject = mGL.createIntBuffer(1);
            mGL.glGenBuffers(positionBufferObject);

            mGL.glBindBuffer(mGL.GL_ARRAY_BUFFER, 1);
            mGL.glBufferData(mGL.GL_ARRAY_BUFFER, verticesBuffer, mGL.GL_STATIC_DRAW);
            mGL.glBindBuffer(mGL.GL_ARRAY_BUFFER, 0);



            FloatBuffer colorBuffer = mGL.createFloatBuffer(vertexColors.length);
            colorBuffer.put(vertexColors);
            colorBuffer.flip();

            colorBufferObject = mGL.createIntBuffer(1);
            mGL.glGenBuffers(colorBufferObject);
            mGL.glBindBuffer(mGL.GL_ARRAY_BUFFER, 1);
            mGL.glBufferData(mGL.GL_ARRAY_BUFFER, colorBuffer, mGL.GL_STATIC_DRAW);
            mGL.glBindBuffer(mGL.GL_ARRAY_BUFFER, 0);

            mDoneSetup = true;

            /*
glGenBuffers(1, &positionBufferObject);

glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject);
glBufferData(GL_ARRAY_BUFFER, sizeof(vertexPositions), vertexPositions, GL_STATIC_DRAW);
glBindBuffer(GL_ARRAY_BUFFER, 0);
            */
        }
        mGL.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        mGL.glClear(mGL.GL_COLOR_BUFFER_BIT);

        mGL.glBindBuffer(mGL.GL_ARRAY_BUFFER, 1);
        mGL.glEnableVertexAttribArray(0);
        mGL.glVertexAttribPointer(0, 4/*because vertexPositions is made up of groups of 4*/ , mGL.GL_FLOAT, false, 0, 0);

        mGL.glDrawArrays(mGL.GL_TRIANGLES, 0, mNumberOfVertices);

        mGL.glDisableVertexAttribArray(0);
        /*
glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject);
glEnableVertexAttribArray(0);
glVertexAttribPointer(0, 4, GL_FLOAT, GL_FALSE, 0, 0);

glDrawArrays(GL_TRIANGLES, 0, 3);

glDisableVertexAttribArray(0);
        */
    }
}

