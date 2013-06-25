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
    private GameContext mCtx;
    private GpuProgram mProgram;
    private IntBuffer mVBO;;
    private boolean mInitialized = false;
    private float[] mVertices;
    private int mCoord2d = -1;
    private static final String TAG = "Mesh";

    public Mesh(GameContext ctx, float[] vertices) {
        mCtx = ctx;
        mVertices = vertices;
    }

    public void frameStarted(OpenGL GL20) {
        if (!mInitialized) {
            initialize(GL20);
        }

        // Clear the background.
        GL20.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        GL20.glClear(OpenGL.GL_COLOR_BUFFER_BIT);

        // Ready the shader program.
        GL20.glUseProgram(mProgram.getProgram());

        // Lock and load the coord2d attribute for our fragment shader.
        GL20.glEnableVertexAttribArray(mCoord2d);
        GL20.glVertexAttribPointer(mCoord2d, 2, OpenGL.GL_FLOAT, false, 0, 0);
 
        // feed it to our shader to draw.
        GL20.glBindBuffer(OpenGL.GL_ARRAY_BUFFER, mVBO.get(0));
        GL20.glDrawArrays(OpenGL.GL_TRIANGLES, 0, 3);

        GL20.glDisableVertexAttribArray(mCoord2d);
    }

    public void initialize(final OpenGL GL20) {
    /* FIXME
        // Setup shaders.
        GlslShader vert = null;
        GlslShader frag = null;
        try {
            ShaderFactory<GlslShader> factory = new ShaderFactory<GlslShader>(){
                public Shader make(Shader.Type type, InputStream is, final String path) {
                    return new GlslShader(GL20, type, is, path);
                }
            };
            // vert = (GlslShader)mCtx.getResources().load("shaders/triangle.vert", factory).getObject();
            // frag = (GlslShader)mCtx.getResources().load("shaders/triangle.frag", factory).getObject();
            vert = new GlslShader(GL20, "shaders/triangle.vert");
            frag = new GlslShader(GL20, "shaders/triangle.frag");
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
        GL20.glBindBuffer(OpenGL.GL_ARRAY_BUFFER, mVBO.get(0));

        // Buffer it to the GPU.
        GL20.glBufferData(OpenGL.GL_ARRAY_BUFFER, buffer, OpenGL.GL_STATIC_DRAW);

        // Create coord2d attribute for our fragment shader.
        mCoord2d = GL20.glGetAttribLocation(mProgram.getProgram(), "coord2d");
        if (mCoord2d == -1) {
            Log.wtf(TAG, "Couldn't bind coord2d attribute!");
        }

        mInitialized = true;
    */
    }
}

