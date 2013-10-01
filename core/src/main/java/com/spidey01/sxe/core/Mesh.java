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
    private IntBuffer mVBO;
    private boolean mInitialized = false;
    private float[] mVertices;
    private int mCoord2d = -1;
    private static final String TAG = "Mesh";

    public Mesh(GameContext ctx, float[] vertices) {
        mCtx = ctx;
        mVertices = vertices;
    }

    public void frameStarted(OpenGL GL) {
        if (!mInitialized) {
            // initialize(GL);
        }

        // Clear the background.
        GL.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        GL.glClear(OpenGLES20.GL_COLOR_BUFFER_BIT);

        // Ready the shader program.
        GL.glUseProgram(mProgram.getProgram());

        // Lock and load the coord2d attribute for our fragment shader.
        GL.glEnableVertexAttribArray(mCoord2d);
        GL.glVertexAttribPointer(mCoord2d, 2, OpenGLES20.GL_FLOAT, false, 0, 0);
 
        // feed it to our shader to draw.
        GL.glBindBuffer(OpenGLES20.GL_ARRAY_BUFFER, mVBO.get(0));
        GL.glDrawArrays(OpenGLES20.GL_TRIANGLES, 0, 3);

        GL.glDisableVertexAttribArray(mCoord2d);
    }

}

