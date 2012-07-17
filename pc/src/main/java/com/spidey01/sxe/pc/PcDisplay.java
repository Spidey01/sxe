package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.RateCounter;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.OpenGl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.DoubleBuffer;
import java.nio.ByteOrder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class PcDisplay implements com.spidey01.sxe.core.Display {

    private RateCounter mFrameCounter = new RateCounter("Frames");
    private static final String TAG = "PcDisplay";

    public PcDisplay(String desired) {
        // default to VGA if desired not found
        DisplayMode wanted = new DisplayMode(640, 480);

        try {
            DisplayMode[] modes = Display.getAvailableDisplayModes();

            for (int i=0; i < modes.length; i++) {
                DisplayMode c = modes[i];
                if (c.isFullscreenCapable() && c.toString().startsWith(desired)) {
                    wanted = c;
                    break;
                }
            }

            Display.setDisplayMode(wanted);

        } catch (LWJGLException e) {
            Log.e(TAG, "PcDisplay() couldn't set display mode for LWJGL!");
            e.printStackTrace();
        }
 
    }

    public PcDisplay() {
        this("640 x 480 x 16 @60");
    }

    public boolean create() {
        try {
            Display.create();
        } catch (LWJGLException e) {
            Log.e(TAG, "create() can't create LWJGL display :'(");
            e.printStackTrace();
            return false;
        }
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

    private static final float[] vertexPositions = {
        0.75f, 0.75f, 0.0f, 1.0f,
        0.75f, -0.75f, 0.0f, 1.0f,
        -0.75f, -0.75f, 0.0f, 1.0f,
    };
    private static final int mNumberOfVertices = 4;
    // private static int positionBufferObject;
    private static IntBuffer positionBufferObject;
    private static boolean mDoneSetup = false;

    private static OpenGl mGL = new LwjglOpenGl();

    private void debuggy() { // place to test shit
        if (mDoneSetup == false) {
            FloatBuffer verticesBuffer;

            verticesBuffer = mGL.createFloatBuffer(vertexPositions.length);

            verticesBuffer.put(vertexPositions);
            verticesBuffer.flip();

            positionBufferObject = mGL.createIntBuffer(1);
            mGL.glGenBuffers(positionBufferObject);


            mGL.glBindBuffer(mGL.GL_ARRAY_BUFFER, 1);
            mGL.glBufferData(mGL.GL_ARRAY_BUFFER, verticesBuffer, mGL.GL_STATIC_DRAW);
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

