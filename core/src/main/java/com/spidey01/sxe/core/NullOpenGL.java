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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class NullOpenGL implements OpenGLES20, OpenGLES30, OpenGL {
    private final static String TAG = "NullOpenGL";

    public NullOpenGL() {
    }


    /* OpenGL functions */

    @Override
    public void glAttachShader(GpuProgram p, Shader s) {
        glAttachShader(p.getProgram(), s.getId());
    }

    @Override
    public void glAttachShader(int program, int shader) {
        Log.d(TAG, "glAttachShader(", program, ", ", shader, ")");
    }

    @Override
    public void glBindBuffer(int target, int buffer) {
        Log.d(TAG, "glBindBuffer(", target, ", ", buffer, ")");
    }

    @Override
    public void glBindTexture(int target, int texture) {
        Log.d(TAG, "glBindTexture(", target, ", ",  texture, ")");
    }
        
    @Override
    public void glBufferData(int target, ByteBuffer data, int usage) {
        Log.d(TAG, "glBufferData(", target, ", ",  data, ", ",  usage, ")");
    }

    @Override
    public void glBufferData(int target, DoubleBuffer data, int usage) {
        Log.d(TAG, "glBufferData(", target, ", ",  data, ", ",  usage, ")");
    }

    @Override
    public void glBufferData(int target, FloatBuffer data, int usage) {
        Log.d(TAG, "glBufferData(", target, ", ",  data, ", ",  usage, ")");
    }

    @Override
    public void glBufferData(int target, IntBuffer data, int usage) {
        Log.d(TAG, "glBufferData(", target, ", ",  data, ", ",  usage, ")");
    }

    @Override
    public void glClear(int mask) {
        Log.d(TAG, "glClear(", mask, ")");
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        Log.d(TAG, "glClearColor(", red, ", ",  green, ", ",  blue, ", ",  alpha, ")");
    }

    @Override
    public void glCompileShader(int shader) {
        Log.d(TAG, "glCompileShader(", shader, ")");
    }

    @Override
    public int glCreateProgram() {
        Log.d(TAG, "glCreateProgram()");
        return -1;
    }

    @Override
    public int glCreateShader(int type) {
        Log.d(TAG, "glCreateShader(", type, ")");
        return -1;
    }


    @Override
    public void glDeleteProgram(GpuProgram program) {
        glDeleteProgram(program.getProgram());
    }


    @Override
    public void glDeleteProgram(int program) {
        Log.d(TAG, "glDeleteProgram(", program, ")");
    }

    @Override
    public void glDeleteShader(int shader) {
        Log.d(TAG, "glDeleteShader(", shader, ")");
    }


    @Override
    public void glDetachShader(GpuProgram program, Shader shader) {
        glDetachShader(program.getProgram(), shader.getId());
    }


    @Override
    public void glDetachShader(int program, int shader) {
        Log.d(TAG, "glDetachShader(", program, ", ",  shader, ")");
    }


    @Override
    public void glDisable(int cap) {
        Log.d(TAG, "glDisable(", cap, ")");
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        Log.d(TAG, "glDisableVertexAttribArray(", index, ")");
    }


    @Override
    public void glDrawElements(int mode, int count, int type, ByteBuffer indices) {
        Log.d(TAG, "glDrawElements(", mode, ", ",  indices, ")");
    }


    @Override
    public void glDrawElements(int mode, int count, int type, int offset) {
        Log.d(TAG, "glDrawElements(", mode, ", ",  count, ", ",  type, ", ",  offset, ")");
    }


    @Override
    public void glDrawArrays(int mode, int first, int count) {
        Log.d(TAG, "glDrawArrays(", mode, ", ",  first, ", ",  count, ")");
    }

    @Override
    public void glEnable(int cap) {
        Log.d(TAG, "glEnable(", cap, ")");
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        Log.d(TAG, "glEnableVertexAttribArray(", index, ")");
    }

    @Override
    public void glGenBuffers(IntBuffer buffers) {
        Log.d(TAG, "glGenBuffers(", buffers, ")");
    }

    @Override
    public void glGenTextures(ByteBuffer buffer) {
        glGenTextures(buffer.asIntBuffer());
    }

    @Override
    public void glGenTextures(IntBuffer buffer) {
        Log.d(TAG, "glGenTextures(", buffer, ")");
    }

    @Override
    public int glGetAttribLocation(int program, String name) {
        Log.d(TAG, "glGetAttribLocation(", program, ", ",  name, ")");
        return -1;
    }

    @Override
    public String glGetProgramInfoLog(int program) {
        Log.d(TAG, "glGetProgramInfoLog(", program, ")");
        return "";
    }

    @Override
    public int glGetProgramiv(int program, int pname) {
        Log.d(TAG, "glGetProgrami(", program, ", ",  pname, ")");
        return -1;
    }

    @Override
    public String glGetShaderInfoLog(int shader) {
        Log.d(TAG, "glGetShaderInfoLog(", shader, ")");
        return "";
    }

    @Override
    public int glGetShaderiv(int shader, int pname) {
        Log.d(TAG, "glGetShaderi(", shader, ", ",  pname, ")");
        return -1;
    }


    @Override
    public int glGetUniformLocation(GpuProgram program, String name) {
        return glGetUniformLocation(program.getProgram(), name);
    }


    @Override
    public int glGetUniformLocation(int program, String name) {
        Log.d(TAG, "glGetUniformLocation(", program, ", ",  name, ")");
        return -1;
    }


    @Override
    public void glGetUniformfv(GpuProgram program, int location, FloatBuffer params) {
        glGetUniformfv(program.getProgram(), location, params);
    }


    @Override
    public void glGetUniformfv(int program, int location, FloatBuffer params) {
        Log.d(TAG, "glGetUniform(", program, ", ",  location, ", ",  params, ")");
    }


    @Override
    public void glGetUniformiv(GpuProgram program, int location, IntBuffer params) {
        glGetUniformiv(program.getProgram(), location,  params);
    }


    @Override
    public void glGetUniformiv(int program, int location, IntBuffer params) {
        Log.d(TAG, "glGetUniform(", program, ", ",  location, ", ",  params, ")");
    }


    @Override
    public void glLinkProgram(GpuProgram p) {
        glLinkProgram(p.getProgram());
    }

    @Override
    public void glLinkProgram(int program) {
        Log.d(TAG, "glLinkProgram(", program, ")");
    }

    @Override
    public void glShaderSource(int shader, String source) {
        Log.d(TAG, "glShaderSource(", shader, ", ",  source, ")");
    }

    @Override
    public void glTexImage2D(int target, int level, int internalFormat,
                             int width, int height, int border, int format,
                             int type, ByteBuffer pixels) {

        Log.d(TAG, "glTexImage2D(", target, ", ",  level, ", ",  internalFormat, width, ", ",  height, ", ",  border, ", ",  format, ", ", type, ", ", pixels, ")");
    }

    @Override
    public void glUniform1f(int location, float v0) {
        Log.d(TAG, "glUniform1f(", location, ", ",  v0, ")");
    }


    @Override
    public void glUniform1i(int location, int v0) {
        Log.d(TAG, "glUniform1i(", location, ", ",  v0, ")");
    }


    @Override
    public void glUniform2f(int location, float v0, float v1) {
        Log.d(TAG, "glUniform2f(", location, ", ",  v0, ", ", v1, ")");
    }


    @Override
    public void glUniform2i(int location, int v0, int v1) {
        Log.d(TAG, "glUniform2i(", location, ", ",  v0, ", ", v1, ")");
    }


    @Override
    public void glUniform3f(int location, float v0, float v1, float v2) {
        Log.d(TAG, "glUniform3f(", location, ", ",  v0, ", ", v1, ", ", v2, ")");
    }


    @Override
    public void glUniform3i(int location, int v0, int v1, int v2) {
        Log.d(TAG, "glUniform3i(", location, ", ",  v0, ", ", v1, ", ", v2, ")");
    }


    @Override
    public void glUniform4f(int location, float v0, float v1, float v2, float v3) {
        Log.d(TAG, "glUniform4f(", location, ", ",  v0, ", ", v1, ", ", v2, ", ", v3, ")");
    }


    @Override
    public void glUniform4fv(int location, int count, FloatBuffer v) {
        Log.d(TAG, "glUniform4fv(", location, ", ",  v, ")");
    }


    @Override
    public void glUniform4i(int location, int v0, int v1, int v2, int v3) {
        Log.d(TAG, "glUniform4i(", location, ", ", v0, ", ", v1, ", ", v2, ", ", v3, ")");
    }


    @Override
    public void glUniform4iv(int location, int count, IntBuffer v) {
        Log.d(TAG, "glUniform4iv(", location, ", ",  v, ")");
    }


    @Override
    public void glUseProgram(GpuProgram program) {
        glUseProgram(program.getProgram());
    }

    @Override
    public void glUseProgram(int program) {
        Log.d(TAG, "glUseProgram(", program, ")");
    }

    @Override
    public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
        Log.d(TAG, "glVertexAttribPointer(", index, ", ",  size, ", ",  type, ", ",  normalized, ", ",  stride, ", ",  offset, ")");
    }

    @Override
    public void glValidateProgram(GpuProgram p) {
        glValidateProgram(p.getProgram());
    }

    @Override
    public void glValidateProgram(int program) {
        Log.d(TAG, "glValidateProgram(", program, ")");
    }

    @Override
    public void glViewport(int x, int y, int width, int height) {
        Log.d(TAG, "glViewport(", x, ", ",  y, ", ",  width, ", ",  height, ")");
    }


    /* Legacy functions */

    @Override
    public void glBegin(int mode) {
        Log.d(TAG, "glBegin(", mode, ")");
    }

    @Override
    public void glColor3b(byte red, byte green, byte blue) {
        Log.d(TAG, "glColor3b(", red, ", ",  green, ", ",  blue, ")");
    }

    @Override
    public void glColor3d(double red, double green, double blue) {
        Log.d(TAG, "glColor3d(", red, ", ",  green, ", ",  blue, ")");
    }

    @Override
    public void glColor3f(float red, float green, float blue) {
        Log.d(TAG, "glColor3f(", red, ", ",  green, ", ",  blue, ")");
    }
    
    @Override
    public void glEnd() {
        Log.d(TAG, "glEnd()");
    }

    @Override
    public void glLineWidth(float width) {
        Log.d(TAG, "glLineWidth(", width, ")");
    }

    @Override
    public void glVertex2d(double x, double y) { 
        Log.d(TAG, "glVertex2d(", x, ", ",  y, ")");
    }

    @Override
    public void glVertex2f(float x, float y) { 
        Log.d(TAG, "glVertex2f(", x, ", ",  y, ")");
    }

    @Override
    public void glVertex2i(int x, int y) { 
        Log.d(TAG, "glVertex2i(", x, ", ",  y, ")");
    }

    @Override
    public void glVertex3d(double x, double y, double z) { 
        Log.d(TAG, "glVertex3d(", x, ", ",  y, ", ",  z, ")");
    }

    @Override
    public void glVertex3f(float x, float y, float z) { 
        Log.d(TAG, "glVertex3f(", x, ", ",  y, ", ",  z, ")");
    }

    @Override
    public void glVertex3i(int x, int y, int z) { 
        Log.d(TAG, "glVertex3i(", x, ", ",  y, ", ",  z, ")");
    }

    @Override
    public void glVertex4d(double x, double y, double z, double w) { 
        Log.d(TAG, "glVertex4d(", x, ", ",  y, ", ",  z, ", ",  w, ")");
    }

    @Override
    public void glVertex4f(float x, float y, float z, float w) { 
        Log.d(TAG, "glVertex4f(", x, ", ",  y, ", ",  z, ", ",  w, ")");
    }

    @Override
    public void glVertex4i(int x, int y, int z, int w) { 
        Log.d(TAG, "glVertex4i(", x, ", ",  y, ", ",  z, ", ",  w, ")");
    }

}

