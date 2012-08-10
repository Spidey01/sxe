package com.spidey01.sxe.core;


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
import com.spidey01.sxe.core.*;
import java.nio.*;
import java.io.*;
import java.util.*;

public class Mesh implements FrameStartedListener {
    private GpuProgram mProgram;
    private IntBuffer mVBO;;
    private boolean mInitialized = false;
    private int mCoord2d = -1;
    private float[] mVertices;
    private static final String TAG = "Mesh";

    public Mesh(float[] vertices) {
        mVertices = vertices;
    }

    public void frameStarted(OpenGl GL20) {
        if (!mInitialized) {
            initialize(GL20);
        }

        // Clear the background.
        GL20.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        GL20.glClear(OpenGl.GL_COLOR_BUFFER_BIT);

        // Ready the shader program.
        GL20.glUseProgram(mProgram.getProgram());

        // Lock and load the coord2d attribute for our fragment shader.
        GL20.glEnableVertexAttribArray(mCoord2d);
        GL20.glVertexAttribPointer(mCoord2d, 2, OpenGl.GL_FLOAT, false, 0, 0);
 
        // feed it to our shader to draw.
        GL20.glBindBuffer(OpenGl.GL_ARRAY_BUFFER, mVBO.get(0));
        GL20.glDrawArrays(OpenGl.GL_TRIANGLES, 0, 3);

        GL20.glDisableVertexAttribArray(mCoord2d);
    }

    public void initialize(final OpenGl GL20) {
        // Setup shaders.
        GlslShader vert = null;
        GlslShader frag = null;
        try {
            ShaderFactory<GlslShader> factory = new ShaderFactory<GlslShader>(){
                public Shader make(Shader.Type type, InputStream is, final String path) {
                    return new GlslShader(GL20, type, is, path);
                }
            };
            vert = (GlslShader)C.getResources().load("shaders/triangle.vert", factory).getObject();
            frag = (GlslShader)C.getResources().load("shaders/triangle.frag", factory).getObject();
        } catch(Exception fml) {
            Log.wtf(TAG, "Failed loading shaders", fml);
            fml.printStackTrace();
        }

        // Setup shader program.
        mProgram = new GlslProgram(GL20);
        mProgram.addShader(vert);
        mProgram.addShader(frag);
        if (!mProgram.link() || !mProgram.validate()) {
            Log.wtf(TAG, "Shader failure: "+mProgram.getInfoLog());
        }
        // Setup vertex data to feed into a VBO.
        FloatBuffer buffer = GL20.createFloatBuffer(mVertices.length);
        buffer.put(mVertices);
        buffer.flip();
 
        // Generate an ID for our VBO in the video memory and bind it.
        mVBO = GL20.createIntBuffer(1);
        GL20.glGenBuffers(mVBO);
        GL20.glBindBuffer(OpenGl.GL_ARRAY_BUFFER, mVBO.get(0));

        // Buffer it to the GPU.
        GL20.glBufferData(OpenGl.GL_ARRAY_BUFFER, buffer, OpenGl.GL_STATIC_DRAW);

        // Create coord2d attribute for our fragment shader.
        mCoord2d = GL20.glGetAttribLocation(mProgram.getProgram(), "coord2d");
        if (mCoord2d == -1) {
            Log.wtf(TAG, "Couldn't bind coord2d attribute!");
        }

        mInitialized = true;
    }
}

