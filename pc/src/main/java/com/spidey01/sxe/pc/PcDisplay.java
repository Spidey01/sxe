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
import com.spidey01.sxe.core.C;
import com.spidey01.sxe.core.Resource;
import com.spidey01.sxe.core.ResourceManager;


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
            debuggy_init();
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
        debuggy_draw();
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

    private static OpenGl mGL = new LwjglOpenGl();

    private static IntBuffer m_geometryBuffer;
    private static IntBuffer m_colorBuffer;
    private static LwjglGlslProgram m_program;
    private static LwjglGlslShader m_vertShader;
    private static LwjglGlslShader m_fragShader;
    private static int m_positionLocation;
    private static int m_colorLocation;

    private void debuggy_init() { // place to test shit

        // 128,0,128 -> for my Firefly.
        GL11.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);

        mGL.glEnable(OpenGl.GL_CULL_FACE);
        mGL.glEnable(OpenGl.GL_DEPTH_TEST);

        //---------------------------------------------------
        //create a triangle
        float[] geometryData = {
            -0.5f, -0.5f, 0.0f, 1.0f, //4 floats define one vertex (x, y, z and w), first one is lower left
            0.5f,  -0.5f, 0.0f, 1.0f, //we go counter clockwise, so lower right vertex next
            0.0f,  0.5f,  0.0f, 1.0f  //top vertex is last
        };
        FloatBuffer buffer = mGL.createFloatBuffer(geometryData.length);
        buffer.put(geometryData);
        buffer.flip();

        //generate an ID for our geometry buffer in the video memory and make it the active one
        m_geometryBuffer = mGL.createIntBuffer(1);
        mGL.glGenBuffers(m_geometryBuffer);
        mGL.glBindBuffer(OpenGl.GL_ARRAY_BUFFER, m_geometryBuffer.get(0));

        //send the data to the video memory
        mGL.glBufferData(OpenGl.GL_ARRAY_BUFFER, buffer, OpenGl.GL_STATIC_DRAW);


        //create a color buffer, to make our triangle look pretty
        float[] colorData = {
            //3 floats define one color value (red, green and blue) with 0 no intensity and 1 full intensity
            //each color triplet is assigned to the vertex at the same position in the buffer, so first color -> first vertex
            1.0f, 0.0f, 0.0f, //first vertex is red
            0.0f, 1.0f, 0.0f, //lower right vertex is green
            0.0f, 0.0f, 1.0f, //top vertex is blue
        };
        buffer = mGL.createFloatBuffer(colorData.length);
        buffer.put(colorData);
        buffer.flip();

        //generate an ID for the color buffer in the video memory and make it the active one
        m_colorBuffer = mGL.createIntBuffer(1);
        mGL.glGenBuffers(m_colorBuffer);
        mGL.glBindBuffer(OpenGl.GL_ARRAY_BUFFER, m_colorBuffer.get(0));

        //send the data to the video memory
        mGL.glBufferData(OpenGl.GL_ARRAY_BUFFER, buffer, OpenGl.GL_STATIC_DRAW);

        //---------------------------------------------------
        //load our shaders
        try {
            m_vertShader = (LwjglGlslShader)C.getResources().load("shaders/shader.vert", LwjglGlslShader.class).getObject();
            m_fragShader = (LwjglGlslShader)C.getResources().load("shaders/shader.frag", LwjglGlslShader.class).getObject();
        } catch(Exception fml) {
            Log.wtf(TAG, "Failed loading shaders", fml);
        }


        m_program = new LwjglGlslProgram();
        m_program.addShader(m_vertShader);
        m_program.addShader(m_fragShader);
        if (!m_program.link()) {
            throw new RuntimeException("couldn't compile m_program: "+m_program.getInfoLog());
        }
        if (!m_program.validate()) {
            throw new RuntimeException("couldn't validate m_program: "+m_program.getInfoLog());
        }

        //tell OpenGL to use this shader for all coming rendering
        mGL.glUseProgram(m_program.getProgram());

        //get the attachment points for the attributes position and color
        m_positionLocation = mGL.glGetAttribLocation(m_program.getProgram(), "position");
        m_colorLocation = mGL.glGetAttribLocation(m_program.getProgram(), "color");

        //check that the locations are valid, negative value means invalid
        if(m_positionLocation < 0 || m_colorLocation < 0) {
            Log.w(TAG, "Could not query attribute locations");
        }

        //enable these attributes
        mGL.glEnableVertexAttribArray(m_positionLocation);
        mGL.glEnableVertexAttribArray(m_colorLocation);

    }

    private void debuggy_draw() { // place to test shit
        //clear the color buffer
        mGL.glClear(OpenGl.GL_COLOR_BUFFER_BIT);

        //bind the geometry VBO
        mGL.glBindBuffer(OpenGl.GL_ARRAY_BUFFER, m_geometryBuffer.get(0));
        //point the position attribute to this buffer, being tuples of 4 floats for each vertex
        mGL.glVertexAttribPointer(m_positionLocation, 4 , mGL.GL_FLOAT, false, 0, 0);

        //bint the color VBO
        mGL.glBindBuffer(OpenGl.GL_ARRAY_BUFFER, m_colorBuffer.get(0));
        //this attribute is only 3 floats per vertex
        mGL.glVertexAttribPointer(m_colorLocation, 3 , mGL.GL_FLOAT, false, 0, 0);

        //initiate the drawing process, we want a triangle, start at index 0 and draw 3 vertices
        mGL.glDrawArrays(mGL.GL_TRIANGLES, 0, 3);
    }
}

