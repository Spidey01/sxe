package com.spidey01.sxe.core;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface OpenGl {

    /* these values are just ripped off from /usr/include/GL/**.h on my linux box. */

    static final int GL_ARRAY_BUFFER = 0x8892;
    static final int GL_COLOR_BUFFER_BIT = 0x00004000;
    static final int GL_CULL_FACE = 0x0B44;
    static final int GL_CULL_FACE_MODE = 0x0B45;
    static final int GL_DEPTH_TEST = 0x0B71;
    static final int GL_FALSE = 0x0;
    static final int GL_FLOAT = 0x1406;
    static final int GL_STATIC_DRAW = 0x88E4;
    static final int GL_TRIANGLES = 0x0004;

    /* Utility functions */

    ByteBuffer createByteBuffer(int size);
    DoubleBuffer createDoubleBuffer(int size);
    FloatBuffer createFloatBuffer(int size);
    IntBuffer createIntBuffer(int size);


    /* OpenGL functions */

    void glBindBuffer(int target, int buffer);
    void glBufferData(int target, ByteBuffer data, int usage);
    void glBufferData(int target, DoubleBuffer data, int usage);
    void glBufferData(int target, FloatBuffer data, int usage);
    void glBufferData(int target, IntBuffer data, int usage);
    void glClear(int mask);
    void glClearColor(float red, float green, float blue, float alpha);
    void glDisable(int cap);
    void glDisableVertexAttribArray(int index);
    void glDrawArrays(int mode, int first, int count);
    void glEnable(int cap);
    void glEnableVertexAttribArray(int index);
    void glGenBuffers(IntBuffer buffers);
    int glGetAttribLocation(int program, String name);
    void glUseProgram(int program);

    // void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer buffer);
    void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int offset);
    void glViewport(int x, int y, int width, int height);
}

