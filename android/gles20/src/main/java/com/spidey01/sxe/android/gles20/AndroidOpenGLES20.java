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

package com.spidey01.sxe.android.gles20;

import  com.spidey01.sxe.core.gl.Program;
import  com.spidey01.sxe.core.gl.OpenGLES20;
import  com.spidey01.sxe.core.gl.Shader;

import android.opengl.GLES20;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


public class AndroidOpenGLES20 implements OpenGLES20 {
    private static final String TAG = "AndroidOpenGLES20";

    /* OpenGL functions */

    @Override
    public void glAttachShader(Program p, Shader s) {
        glAttachShader(p.getProgram(), s.getId());
    }

    @Override
    public void glAttachShader(int program, int shader) {
        GLES20.glAttachShader(program, shader);
    }

    @Override
    public void glBindBuffer(int target, int buffer) {
        GLES20.glBindBuffer(t(target), buffer);
    }
        

    @Override
    public void glBindFramebuffer(int target, int framebuffer) {
        GLES20.glBindFramebuffer(target, framebuffer);
    }


    @Override
    public void glBindTexture(int target, int texture) {
        GLES20.glBindTexture(t(target), texture);
    }

        
    @Override
    public void glBufferData(int target, ByteBuffer data, int usage) {
        GLES20.glBufferData(t(target),
            data.capacity() * Byte.SIZE,
            data, t(usage));
    }

    @Override
    public void glBufferData(int target, DoubleBuffer data, int usage) {
        GLES20.glBufferData(t(target),
            data.capacity() * Double.SIZE,
            data, t(usage));
    }

    @Override
    public void glBufferData(int target, FloatBuffer data, int usage) {
        GLES20.glBufferData(t(target),
            data.capacity() * Float.SIZE,
            data, t(usage));
    }

    @Override
    public void glBufferData(int target, IntBuffer data, int usage) {
        GLES20.glBufferData(t(target),
            data.capacity() * Integer.SIZE,
            data, t(usage));
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, ByteBuffer data) {
        GLES20.glBufferSubData(target, offset, size, data);
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, DoubleBuffer data) {
        GLES20.glBufferSubData(target, offset, size, data);
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, FloatBuffer data) {
        GLES20.glBufferSubData(target, offset, size, data);
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, IntBuffer data) {
        GLES20.glBufferSubData(target, offset, size, data);
    }

    @Override
    public void glClear(int mask) {
        GLES20.glClear(t(mask));
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        GLES20.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void glCompileShader(int shader) {
        GLES20.glCompileShader(shader);
    }

    @Override
    public int glCreateProgram() {
        return GLES20.glCreateProgram();
    }

    @Override
    public int glCreateShader(int type) {
        return GLES20.glCreateShader(t(type));
    }


    @Override
    public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
        GLES20.glDeleteFramebuffers(n, framebuffers);
    }


    @Override
    public void glDeleteFramebuffers(int n, int[] framebuffers, int offset) {
        GLES20.glDeleteFramebuffers(n, framebuffers, offset);
    }


    @Override
    public void glDeleteProgram(Program program) {
        GLES20.glDeleteProgram(program.getProgram());
    }


    @Override
    public void glDeleteProgram(int program) {
        GLES20.glDeleteProgram(program);
    }


    @Override
    public void glDeleteShader(int shader) {
        GLES20.glDeleteShader(shader);
    }


    @Override
    public void glDetachShader(Program program, Shader shader) {
        GLES20.glDetachShader(program.getProgram(), shader.getId());
    }


    @Override
    public void glDetachShader(int program, int shader) {
        GLES20.glDetachShader(program, shader);
    }


    @Override
    public void glDisable(int cap) {
        GLES20.glDisable(t(cap));
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        GLES20.glDisableVertexAttribArray(index);
    }


    @Override
    public void glDrawElements(int mode, int count, int type, ByteBuffer indices) {
        GLES20.glDrawElements(mode, count, type, indices);
    }


    @Override
    public void glDrawElements(int mode, int count, int type, int offset) {
        GLES20.glDrawElements(mode, count, type, offset);
    }


    @Override
    public void glDrawArrays(int mode, int first, int count) {
        GLES20.glDrawArrays(t(mode), first, count);
    }

    @Override
    public void glEnable(int cap) {
        GLES20.glEnable(t(cap));
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        GLES20.glEnableVertexAttribArray(index);
    }

    @Override
    public void glGenBuffers(IntBuffer buffers) {
        GLES20.glGenBuffers(1, buffers);
    }


    @Override
    public void glGenFramebuffers(IntBuffer framebuffers) {
        GLES20.glGenFramebuffers(1, framebuffers);
    }


    @Override
    public void glGenFramebuffers(int n, IntBuffer framebuffers) {
        GLES20.glGenFramebuffers(n, framebuffers);
    }


    @Override
    public void glGenTextures(ByteBuffer buffer) {
        glGenTextures(buffer.asIntBuffer());
    }

    @Override
    public void glGenTextures(IntBuffer buffer) {
        GLES20.glGenTextures(1, buffer);
    }


    @Override
    public int glGetAttribLocation(int program, String name) {
        return GLES20.glGetAttribLocation(program, name);
    }

    @Override
    public String glGetProgramInfoLog(int program) {
        return GLES20.glGetProgramInfoLog(program);
    }

    @Override
    public int glGetProgramiv(int program, int pname) {
        int[] status = new int[1];
        GLES20.glGetProgramiv(program, t(pname), status, 0);
        return status[0];
    }

    public String glGetShaderInfoLog(int shader) {
        return GLES20.glGetShaderInfoLog(shader);
    }

    @Override
    public int glGetShaderiv(int shader, int pname) {
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, t(pname), compiled, 0);
        return compiled[0];
    }


    @Override
    public int glGetUniformLocation(Program program, String name) {
        return GLES20.glGetUniformLocation(program.getProgram(), name);
    }


    @Override
    public int glGetUniformLocation(int program, String name) {
        return GLES20.glGetUniformLocation(program, name);
    }


    @Override
    public void glGetUniformfv(Program program, int location, FloatBuffer params) {
        GLES20.glGetUniformfv(program.getProgram(), location, params);
    }


    @Override
    public void glGetUniformfv(int program, int location, FloatBuffer params) {
        GLES20.glGetUniformfv(program, location, params);
    }


    @Override
    public void glGetUniformiv(Program program, int location, IntBuffer params) {
        GLES20.glGetUniformiv(program.getProgram(), location, params);
    }


    @Override
    public void glGetUniformiv(int program, int location, IntBuffer params) {
        GLES20.glGetUniformiv(program, location, params);
    }


    @Override
    public void glLineWidth(float width) {
        GLES20.glLineWidth(width);
    }


    @Override
    public void glLinkProgram(Program p) {
        glLinkProgram(p.getProgram());
    }


    @Override
    public void glLinkProgram(int program) {
        GLES20.glLinkProgram(program);
    }


    @Override
    public void glShaderSource(int shader, String source) {
        GLES20.glShaderSource(shader, source);
    }


    @Override
    public void glTexImage2D(int target, int level, int internalFormat,
                             int width, int height, int border, int format,
                             int type, ByteBuffer pixels) {

        GLES20.glTexImage2D(t(target), level, t(internalFormat), width, height, border, t(format), t(type), pixels);
    }


    @Override
    public void glUniform1f(int location, float v0) {
        GLES20.glUniform1f(location, v0);
    }


    @Override
    public void glUniform1i(int location, int v0) {
        GLES20.glUniform1i(location, v0);
    }


    @Override
    public void glUniform2f(int location, float v0, float v1) {
        GLES20.glUniform2f(location, v0, v1);
    }


    @Override
    public void glUniform2i(int location, int v0, int v1) {
        GLES20.glUniform2i(location, v0, v1);
    }


    @Override
    public void glUniform3f(int location, float v0, float v1, float v2) {
        GLES20.glUniform3f(location, v0, v1, v2);
    }


    @Override
    public void glUniform3i(int location, int v0, int v1, int v2) {
        GLES20.glUniform3i(location, v0, v1, v2);
    }


    @Override
    public void glUniform4f(int location, float v0, float v1, float v2, float v3) {
        GLES20.glUniform4f(location, v0, v1, v2, v3);
    }


    @Override
    public void glUniform4fv(int location, int count, FloatBuffer v) {
        GLES20.glUniform4fv(location, count, v);
    }


    @Override
    public void glUniform4i(int location, int v0, int v1, int v2, int v3) {
        GLES20.glUniform4i(location, v0, v1, v2, v3);
    }


    @Override
    public void glUniform4iv(int location, int count, IntBuffer v) {
        GLES20.glUniform4iv(location, count, v);
    }


    @Override
    public void glUseProgram(Program program) {
        GLES20.glUseProgram(program.getProgram());
    }


    @Override
    public void glUseProgram(int program) {
        GLES20.glUseProgram(program);
    }


    @Override
    public void glValidateProgram(Program p) {
        glValidateProgram(p.getProgram());
    }


    @Override
    public void glValidateProgram(int program) {
        GLES20.glValidateProgram(program);
    }


    @Override
    public void glViewport(int x, int y, int width, int height) {
        GLES20.glViewport(x, y, width, height);
    }


    @Override
    public void glVertexAttribPointer(int index, int size, int type,
        boolean normalized, int stride, int offset)
    {
        GLES20.glVertexAttribPointer(index, size, t(type),
            normalized, stride, offset);
    }

    /* Legacy functions */

    /* Internal functions */
     private static int t(int konst) {
        return konst; // should be same value.
     }

}


