/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

import java.nio.IntBuffer;

/** Class encapsulating an OpenGL Vertex Buffer Object (VBO).
 */
public class VertexBuffer {
    private static final String TAG = "VertexBuffer";
    
    private final int mTarget;
    private final int mUsage;
    private boolean mIsInitialized;
    private int mVertexBufferId;


    public VertexBuffer() {
        mTarget = OpenGL.GL_ARRAY_BUFFER;
        mUsage = OpenGL.GL_STATIC_DRAW;
    }
    

    public VertexBuffer(int target, int usage) {
        mTarget = target;
        mUsage = usage;
    }


    /** Obtain the underlaying VertexBuffer ID from OpenGL. */
    public int getId() {
        check();
        return mVertexBufferId;
    }


    /** Initializes an empty VertexBuffer. */
    public void initialize(OpenGL GL) {
        if (mIsInitialized) return;
        
        IntBuffer b = Utils.Buffers.createIntBuffer(1);
        GL.glGenBuffers(b);
        mVertexBufferId = b.get(0);

        mIsInitialized = true;
    }


    /** Initialize, bind, and buffer vertices from array. */
    public void initialize(OpenGL GL, float[] vertices) {
        if (mIsInitialized) return;
        initialize(GL);
        bind(GL);
        buffer(vertices);
    }


    /** Initialize, bind, and buffer vertices from FloatBuffer. */
    public void initialize(OpenGL GL, FloatBuffer vertices) {
        if (mIsInitialized) return;
        initialize(GL);
        bind(GL);
        buffer(vertices);
    }


    /** Bind the VertexBuffer for use.
     *
     * I.e. call glBindBuffer.
     */
    public void bind(OpenGL GL) {
        check();
        GL.glBindBuffer(mTarget, mVertexBufferId);
    }


    /** Buffers vertices to OpenGL memory.  */
    public void buffer(OpenGL GL, FloatBuffer vertices) {
        check();
        GL.glBufferData(mTarget, vertices, mUsage);
    }


    public void buffer(OpenGL GL, float[] vertices) {
        check();
        FloatBuffer b = BufferUtils.createFloatBuffer(vertices.length);
        b.put(vertices);
        // Basically make sure the bounds is set to vertices.length and rewind the position.
        b.flip();
        int vertexCount = vertices+1;
        buffer(b);
        b.clear();
    }


    public void deinitialize(OpenGL GL) {
        check();
        // TODO: Delete the buffer.
        mIsInitialized = false;
    }


    private void check() {
        if (!mIsInitialized) {
            throw new IllegalStateException(TAG+" not initialized!");
        }
    }

}

