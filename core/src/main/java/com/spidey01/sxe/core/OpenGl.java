package com.spidey01.sxe.core;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface OpenGl {

    /* these values are just ripped off from /usr/include/GL/**.h on my linux box. */

    static final int GL_ARRAY_BUFFER = 0x8892;
    static final int GL_COLOR_BUFFER_BIT = 0x00004000;
    static final int GL_FALSE = 0x0;
    static final int GL_FLOAT = 0x1406;
    static final int GL_TRIANGLES = 0x0004;


    /* functions */

    void glBindBuffer(int target, int buffer);
    void glClear(int mask);
    void glClearColor(float red, float green, float blue, float alpha);
    void glDisableVertexAttribArray(int index);
    void glDrawArrays(int mode, int first, int count);
    void glEnableVertexAttribArray(int index);
    void glGenBuffers();
    void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer buffer);
    void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int offset);
}

