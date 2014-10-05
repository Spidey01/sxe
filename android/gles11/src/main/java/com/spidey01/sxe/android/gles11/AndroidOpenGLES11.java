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

package com.spidey01.sxe.android.gles11;

import  com.spidey01.sxe.core.gl.OpenGLES11;

import android.opengl.GLES11;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


public class AndroidOpenGLES11 implements OpenGLES11 {

    private static final String TAG = "AndroidOpenGLES11";

    /* OpenGL functions */

    @Override
    public void glBindBuffer(int target, int buffer) {
        GLES11.glBindBuffer(t(target), buffer);
    }
        

    @Override
    public void glBufferData(int target, ByteBuffer data, int usage) {
        GLES11.glBufferData(t(target),
            data.capacity() * Byte.SIZE,
            data, t(usage));
    }


    @Override
    public void glBufferData(int target, DoubleBuffer data, int usage) {
        GLES11.glBufferData(t(target),
            data.capacity() * Double.SIZE,
            data, t(usage));
    }


    @Override
    public void glBufferData(int target, FloatBuffer data, int usage) {
        GLES11.glBufferData(t(target),
            data.capacity() * Float.SIZE,
            data, t(usage));
    }


    @Override
    public void glBufferData(int target, IntBuffer data, int usage) {
        GLES11.glBufferData(t(target),
            data.capacity() * Integer.SIZE,
            data, t(usage));
    }


    @Override
    public void glBufferSubData(int target, int offset, int size, ByteBuffer data) {
        GLES11.glBufferSubData(target, offset, size, data);
    }


    @Override
    public void glBufferSubData(int target, int offset, int size, DoubleBuffer data) {
        GLES11.glBufferSubData(target, offset, size, data);
    }


    @Override
    public void glBufferSubData(int target, int offset, int size, FloatBuffer data) {
        GLES11.glBufferSubData(target, offset, size, data);
    }


    @Override
    public void glBufferSubData(int target, int offset, int size, IntBuffer data) {
        GLES11.glBufferSubData(target, offset, size, data);
    }


    @Override
    public void glClear(int mask) {
        GLES11.glClear(t(mask));
    }


    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        GLES11.glClearColor(red, green, blue, alpha);
    }


    @Override
    public void glDisable(int cap) {
        GLES11.glDisable(t(cap));
    }


    @Override
    public void glDisableClientState(int cap) {
        GLES11.glDisableClientState(t(cap));
    }


    @Override
    public void glDrawElements(int mode, int count, int type, ByteBuffer indices) {
        GLES11.glDrawElements(mode, count, type, indices);
    }


    @Override
    public void glDrawElements(int mode, int count, int type, int offset) {
        GLES11.glDrawElements(mode, count, type, offset);
    }


    @Override
    public void glDrawArrays(int mode, int first, int count) {
        GLES11.glDrawArrays(t(mode), first, count);
    }


    @Override
    public void glEnable(int cap) {
        GLES11.glEnable(t(cap));
    }


    @Override
    public void glEnableClientState(int cap) {
        GLES11.glEnableClientState(t(cap));
    }


    @Override
    public void glGenBuffers(IntBuffer buffers) {
        GLES11.glGenBuffers(1, buffers);
    }


    @Override
    public void glLineWidth(float width) {
        GLES11.glLineWidth(width);
    }


    /* Internal functions */

    private static int t(int konst) {
        // we should be using the same values.
        return konst;
    }
}

