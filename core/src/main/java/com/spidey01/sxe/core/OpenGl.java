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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface OpenGl {

    /* these values are just ripped off from /usr/include/GL/**.h on my linux box. */

    static final int GL_ARRAY_BUFFER = 0x8892;
    static final int GL_COLOR_BUFFER_BIT = 0x00004000;
    static final int GL_COMPILE_STATUS = 0x8B81;
    static final int GL_CULL_FACE = 0x0B44;
    static final int GL_CULL_FACE_MODE = 0x0B45;
    static final int GL_DEPTH_TEST = 0x0B71;
    static final int GL_FALSE = 0x0;
    static final int GL_FLOAT = 0x1406;
    static final int GL_FRAGMENT_SHADER = 0x8B30;
    static final int GL_LINK_STATUS = 0x8B82;
    static final int GL_STATIC_DRAW = 0x88E4;
    static final int GL_TRIANGLES = 0x0004;
    static final int GL_VALIDATE_STATUS = 0x8B83;
    static final int GL_VERTEX_SHADER = 0x8B31;


    /* Utility functions */

    ByteBuffer createByteBuffer(int size);
    DoubleBuffer createDoubleBuffer(int size);
    FloatBuffer createFloatBuffer(int size);
    IntBuffer createIntBuffer(int size);


    /* OpenGL functions */

    void glAttachShader(GpuProgram p, Shader s);
    void glAttachShader(int program, int shader);
    void glBindBuffer(int target, int buffer);
    void glBufferData(int target, ByteBuffer data, int usage);
    void glBufferData(int target, DoubleBuffer data, int usage);
    void glBufferData(int target, FloatBuffer data, int usage);
    void glBufferData(int target, IntBuffer data, int usage);
    void glClear(int mask);
    void glClearColor(float red, float green, float blue, float alpha);
    void glCompileShader(int shader);
    int glCreateProgram();
    int glCreateShader(int type);
    void glDeleteShader(int shader);
    void glDisable(int cap);
    void glDisableVertexAttribArray(int index);
    void glDrawArrays(int mode, int first, int count);
    void glEnable(int cap);
    void glEnableVertexAttribArray(int index);
    void glGenBuffers(IntBuffer buffers);
    int glGetAttribLocation(int program, String name);
    String glGetProgramInfoLog(int program);
    int glGetProgramiv(int program, int pname);
    int glGetShaderiv(int shader, int pname);
    String glGetShaderInfoLog(int shader);
    void glLinkProgram(GpuProgram p);
    void glLinkProgram(int program);
    void glShaderSource(int shader, String source);
    void glUseProgram(GpuProgram program);
    void glUseProgram(int program);
    void glValidateProgram(GpuProgram p);
    void glValidateProgram(int program);
    void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int offset);
    void glViewport(int x, int y, int width, int height);

    /** OpenGL functions for the old Fixed Function Pipeline.
     *
     * Implementations may throw an unchecked exception if OpenGL legacy /
     * depreciated functionality is unavailable.  Attempting to intermix things
     * in a bad way may but is not required to throw an IllegalStateException.
     */

    void glBegin(int mode);
    void glColor3b(byte red, byte green, byte blue);
    void glColor3d(double red, double green, double blue);
    void glColor3f(float red, float green, float blue);
    void glEnd();
    void glLineWidth(float width);
    void glVertex2d(double x, double y);
    void glVertex2f(float x, float y);
    void glVertex2i(int x, int y);
    void glVertex3d(double x, double y, double z);
    void glVertex3f(float x, float y, float z);
    void glVertex3i(int x, int y, int z);
    void glVertex4d(double x, double y, double z, double w);
    void glVertex4f(float x, float y, float z, float w);
    void glVertex4i(int x, int y, int z, int w);
}

