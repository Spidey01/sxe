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


    public void initialize(OpenGL GL) {
        if (mIsInitialized) return;
        
        IntBuffer b = GL.createIntBuffer(1);
        GL.glGenBuffers(b);
        mVertexBufferId = b.get(0);

        GL.glBindBuffer(mTarget, mVertexBufferId);
        // TODO: How do we want to setup the contents 'buffer' to upload?
        // GL.glBufferData(mTarget, buffer, mUsage);

        mIsInitialized = true;
    }


    public void deinitialize(OpenGL GL) {
        check();
        mIsInitialized = false;
    }


    private void check() {
        if (!mIsInitialized) {
            throw new IllegalStateException(TAG+" not yet fully initialized!");
        }
    }

}

/*
*/
