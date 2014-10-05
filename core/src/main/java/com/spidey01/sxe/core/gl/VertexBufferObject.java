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

package com.spidey01.sxe.core.gl;

import com.spidey01.sxe.core.common.Buffers;
import com.spidey01.sxe.core.graphics.VertexBuffer;
import com.spidey01.sxe.core.logging.Log;

import java.nio.IntBuffer;
import java.nio.FloatBuffer;

/** Class encapsulating an OpenGL Vertex Buffer Object (VBO).
 */
public class VertexBufferObject {
    private static final String TAG = "VertexBufferObject";
    
    private final int mTarget;
    private final int mUsage;
    private boolean mIsInitialized;
    private int mVertexBufferId;
    private int mVertexCount;


    public VertexBufferObject() {
        mTarget = OpenGLES20.GL_ARRAY_BUFFER;
        mUsage = OpenGLES20.GL_STATIC_DRAW;
    }
    

    public VertexBufferObject(int target, int usage) {
        mTarget = target;
        mUsage = usage;
    }


    /** Obtain the underlaying VertexBuffer ID from OpenGL. */
    public int getId() {
        check();
        return mVertexBufferId;
    }


    /** Initializes and binds an empty VertexBuffer without binding it. */
    public void initialize(OpenGLES20 GL) {
        if (mIsInitialized) return;
        
        IntBuffer b = Buffers.makeInt(1);
        GL.glGenBuffers(b);
        mVertexBufferId = b.get(0);

        mIsInitialized = true;
    }


    /** Initialize, bind, and buffer vertices from array. */
    public void initialize(OpenGLES20 GL, float[] vertices) {
        if (mIsInitialized) return;
        initialize(GL);
        bind(GL);
        buffer(GL, vertices);
    }


    /** Initialize, bind, and buffer vertices from FloatBuffer. */
    public void initialize(OpenGLES20 GL, FloatBuffer vertices) {
        if (mIsInitialized) return;
        initialize(GL);
        bind(GL);
        buffer(GL, vertices);
    }


    /** Bind the VertexBuffer for use.
     *
     * I.e. call glBindBuffer.
     */
    public void bind(OpenGLES20 GL) {
        check();
        GL.glBindBuffer(mTarget, mVertexBufferId);
    }


    public void buffer(OpenGLES20 GL, VertexBuffer vertices) {
        buffer(GL, vertices.buffer);
    }


    /** Buffers vertices to OpenGL memory.  */
    public void buffer(OpenGLES20 GL, FloatBuffer vertices) {
        check();
        GL.glBufferData(mTarget, vertices, mUsage);
        // TODO: set mVertexCount.
    }


    public void buffer(OpenGLES20 GL, float[] vertices) {
        VertexBuffer b = new VertexBuffer(vertices);
        mVertexCount = b.capacity() + 1;
        buffer(GL, b.buffer);
        b.buffer.clear();
    }


    public void deinitialize(OpenGLES20 GL) {
        check();
        // TODO: Delete the buffer.
        mIsInitialized = false;
    }


    private void check() {
        if (!mIsInitialized) {
            throw new IllegalStateException(TAG+" not initialized!");
        }
    }


    public int getVertexCount() {
        return mVertexCount;
    }

}

