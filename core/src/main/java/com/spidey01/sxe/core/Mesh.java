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

import com.spidey01.sxe.core.*;
import com.spidey01.sxe.core.gl.*;

import java.nio.*;
import java.io.*;
import java.util.*;

public class Mesh {
    private static final String TAG = "Mesh";

    private VertexBuffer mVertexBuffer;
    private float[] mArrayOfVertices;
    private FloatBuffer mBufferOfVertices;
    private boolean mIsInitialized;


    private Mesh() {
        mVertexBuffer = new VertexBuffer();
    }


    public Mesh(float[] vertices) {
        this();
        mArrayOfVertices = vertices;
    }


    public Mesh(FloatBuffer vertices) {
        this();
        mBufferOfVertices = vertices;
    }

    
    /** Creates a Mesh from a prepared VertexBuffer.
     */
    public Mesh(VertexBuffer buffer) {
        mVertexBuffer = buffer;
    }


    public void initialize(OpenGLES20 GLES20) {
        if (mIsInitialized) return;

        mVertexBuffer.initialize(GLES20);
        mVertexBuffer.bind(GLES20);

        if (mArrayOfVertices != null) {
            Log.xtrace(TAG, "initialized from array of vertices.");
            mVertexBuffer.buffer(GLES20, mArrayOfVertices);
        } else if (mBufferOfVertices != null) {
            Log.xtrace(TAG, "initialized from buffer of vertices.");
            mVertexBuffer.buffer(GLES20, mBufferOfVertices);
        } else {
            // We can't tell if we used the ctor with the vbo.
            // throw new IllegalStateException("No vertex data.");
            Log.xtrace(TAG, "initialized from VertexBuffer or state is bad.");
        }

        mIsInitialized = true;
    }


    public void deinitialize(OpenGLES20 GLES20) {
        if (!mIsInitialized) throw new IllegalStateException(TAG+": not initialized!");

        mVertexBuffer.deinitialize(GLES20);

        mIsInitialized = false;
    }


    public VertexBuffer getVertexBuffer() {
        return mVertexBuffer;
    }

}

