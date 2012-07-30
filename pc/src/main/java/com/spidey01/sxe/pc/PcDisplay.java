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
import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.Resource;
import com.spidey01.sxe.core.ResourceManager;


public class PcDisplay implements com.spidey01.sxe.core.Display {

    private RateCounter mFrameCounter = new RateCounter("Frames");
    /** default to VGA */
    private DisplayMode mDisplayMode = new DisplayMode(640, 480);
    private List<FrameStartedListener> mFrameStartedListeners = new ArrayList<FrameStartedListener>();
    private List<FrameEndedListener> mFrameEndedListeners = new ArrayList<FrameEndedListener>();
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
            o.frameStarted();
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
    private LwjglGlslProgram mProgram;
    private static IntBuffer mVBO;;
    private void setupTriangle() {
        // Setup shaders.
        LwjglGlslShader vert = null;
        LwjglGlslShader frag = null;
        try {
            LwjglGlslShaderFactory factory = new LwjglGlslShaderFactory();
            vert = (LwjglGlslShader)C.getResources().load("shaders/triangle.vert", factory).getObject();
            frag = (LwjglGlslShader)C.getResources().load("shaders/triangle.frag", factory).getObject();
        } catch(Exception fml) {
            Log.wtf(TAG, "Failed loading shaders", fml);
            fml.printStackTrace();
        }

        // Setup shader program.
        mProgram = new LwjglGlslProgram();
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
        GL15.glGenBuffers(mVBO);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mVBO.get(0));

        // Buffer it to the GPU.
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Create coord2d attribute for our fragment shader.
        mCoord2d = GL20.glGetAttribLocation(mProgram.getProgram(), "coord2d");
        if (mCoord2d == -1) {
            Log.wtf(TAG, "Couldn't bind coord2d attribute!");
        }
    }
    private void draw_triangle() {
        // Clear the background.
        GL11.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // Ready the shader program.
        GL20.glUseProgram(mProgram.getProgram());

        // Lock and load the coord2d attribute for our fragment shader.
        GL20.glEnableVertexAttribArray(mCoord2d);
        GL20.glVertexAttribPointer(mCoord2d, 2, GL11.GL_FLOAT, false, 0, 0);
 
        // feed it to our shader to draw.
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mVBO.get(0));
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);

        GL20.glDisableVertexAttribArray(mCoord2d);
    }
}

