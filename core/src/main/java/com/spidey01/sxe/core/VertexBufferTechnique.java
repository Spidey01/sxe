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

import java.nio.FloatBuffer;

/** Technique for rendering via vertex buffer.
 *
 * The object to be rendered with this technique is expected to do the following:
 * <ol>
 *  <li>Provide a float[] of vertices to render.</li>
 *  <li>Provide a GpuProgram with shaders attached.</li>
 *  <li>Provide a VertexBuffer</li>
 *  <li></li>
 * </ol>
 *
 */
public class VertexBufferTechnique implements RenderingTechnique {

    private static final String TAG = "VertexBufferTechnique";


    private boolean mIsInitialized;
    private OpenGLES20 mGL;

    public interface Capable extends RenderableObject {
        float[] getVertices();
        VertexBuffer getVertexBuffer();
        GpuProgram getProgram();
    }


    public VertexBufferTechnique(OpenGLES20 GLES20) {
        mGL = GLES20;
    }


    /** Initializes client for rendering.
     *
     * <ol>
     *  <li>Initialize the clients vertex buffer.</li>
     *  <li>Initialize the clients program. Shaders must already be attached.</li>
     * </ol>
     */
    public void initialize(Capable client) {
        if (mIsInitialized) throw new IllegalStateException(TAG+": already initialized!");

        Log.d(TAG, "Initilizing client "+client);

        client.getVertexBuffer().initialize(mGL, client.getVertices());
        client.getProgram().initialize(mGL);

        mIsInitialized = true;
    }


    /** Draws the client.
     *
     * This is still a WIP and dirty hacky, hacky. It's evolving.
     *
     * Current API with Shaders:
     *
     *  vPosition vertex attribute for passing vertices to the vertex shader.
     *  vColor fragment uniform for passing color to the fragment shader. (Removed for right now!)
     */
    public void draw(Capable client) {
        if (!mIsInitialized) {
            initialize(client);
        }
        Log.d(TAG, "Drawing client "+client);

        client.getVertexBuffer().bind(mGL);
        GpuProgram p = client.getProgram();
        p.use(mGL);

        // get the location (index) of the attribute.
        int vPosition = mGL.glGetAttribLocation(p.getId(), "vPosition");
        // turn it on.
        mGL.glEnableVertexAttribArray(vPosition);

        // Pass the data for vPosition
        // FIXME: set the numbers dynamically!
        mGL.glVertexAttribPointer(vPosition, 3, OpenGLES20.GL_FLOAT, false, 0, 0);

        // get the uniform's index/location and set it.
        // FIXME: Yeah!
        /*
        int vColor = mGL.glGetUniformLocation(p, "vColor");
            float[] color_v = { 0.0f, 1.0f, 0.0f, 1.0f };
            FloatBuffer color_p = Utils.Buffers.createFloatBuffer(color_v.length);
            color_p.put(color_v);
            color_p.flip();
        mGL.glUniform4fv(vColor, 1, color_p);
        */

        mGL.glDrawArrays(OpenGLES20.GL_TRIANGLES, 0, client.getVertexBuffer().getVertexCount());

        mGL.glDisableVertexAttribArray(vPosition);
    }


    /** WRITEME
     */
    public void deinitialize(Capable client) {
        if (!mIsInitialized) throw new IllegalStateException(TAG+": not initialized!");

        Log.d(TAG, "Deinitializing client "+client);

        mIsInitialized = false;
    }

}

