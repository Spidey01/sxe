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

package com.spidey01.sxe.pc;

import  com.spidey01.sxe.core.GpuProgram;
import  com.spidey01.sxe.core.OpenGL;
import  com.spidey01.sxe.core.OpenGLES11;
import  com.spidey01.sxe.core.OpenGLES20;
import  com.spidey01.sxe.core.OpenGLES30;
import  com.spidey01.sxe.core.Shader;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;


public class LwjglOpenGL implements OpenGLES11, OpenGLES20, OpenGLES30, OpenGL {
    private static final String TAG = "LwjglOpenGL";

    /* OpenGL functions */

    @Override
    public void glAttachShader(GpuProgram p, Shader s) {
        GL20.glAttachShader(p.getProgram(), s.getId());
    }

    @Override
    public void glAttachShader(int program, int shader) {
        GL20.glAttachShader(program, shader);
    }

    @Override
    public void glBindBuffer(int target, int buffer) {
        GL15.glBindBuffer(t(target), buffer);
    }

    @Override
    public void glBindTexture(int target, int texture) {
        GL11.glBindTexture(t(target), texture);
    }
        
    @Override
    public void glBufferData(int target, ByteBuffer data, int usage) {
        GL15.glBufferData(t(target), data, t(usage));
    }

    @Override
    public void glBufferData(int target, DoubleBuffer data, int usage) {
        GL15.glBufferData(t(target), data, t(usage));
    }

    @Override
    public void glBufferData(int target, FloatBuffer data, int usage) {
        GL15.glBufferData(t(target), data, t(usage));
    }

    @Override
    public void glBufferData(int target, IntBuffer data, int usage) {
        GL15.glBufferData(t(target), data, t(usage));
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, ByteBuffer data) {
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, DoubleBuffer data) {
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, FloatBuffer data) {
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, IntBuffer data) {
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void glClear(int mask) {
        GL11.glClear(t(mask));
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void glCompileShader(int shader) {
        GL20.glCompileShader(shader);
    }

    @Override
    public int glCreateProgram() {
        return GL20.glCreateProgram();
    }

    @Override
    public int glCreateShader(int type) {
        return GL20.glCreateShader(t(type));
    }


    @Override
    public void glDeleteProgram(GpuProgram program) {
        GL20.glDeleteProgram(program.getProgram());
    }


    @Override
    public void glDeleteProgram(int program) {
        GL20.glDeleteProgram(program);
    }

    @Override
    public void glDeleteShader(int shader) {
        GL20.glDeleteShader(shader);
    }


    @Override
    public void glDetachShader(GpuProgram program, Shader shader) {
        GL20.glDetachShader(program.getProgram(), shader.getId());
    }


    @Override
    public void glDetachShader(int program, int shader) {
        GL20.glDetachShader(program, shader);
    }


    @Override
    public void glDisable(int cap) {
        GL11.glDisable(t(cap));
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        GL20.glDisableVertexAttribArray(index);
    }


    @Override
    public void glDrawElements(int mode, int count, int type, ByteBuffer indices) {
        GL11.glDrawElements(mode, indices);
    }


    @Override
    public void glDrawElements(int mode, int count, int type, int offset) {
        GL11.glDrawElements(mode, count, type, offset) ;
    }


    @Override
    public void glDrawArrays(int mode, int first, int count) {
        GL11.glDrawArrays(t(mode), first, count);
    }

    @Override
    public void glEnable(int cap) {
        GL11.glEnable(t(cap));
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        GL20.glEnableVertexAttribArray(index);
    }

    @Override
    public void glGenBuffers(IntBuffer buffers) {
        GL15.glGenBuffers(buffers);
    }

    @Override
    public void glGenTextures(ByteBuffer buffer) {
        glGenTextures(buffer.asIntBuffer());
    }

    @Override
    public void glGenTextures(IntBuffer buffer) {
        GL11.glGenTextures(buffer);
    }

    @Override
    public int glGetAttribLocation(int program, String name) {
        return GL20.glGetAttribLocation(program, name);
    }

    @Override
    public String glGetProgramInfoLog(int program) {
        int length = GL20.glGetProgrami(program, GL20.GL_INFO_LOG_LENGTH);
        return GL20.glGetProgramInfoLog(program, length);
    }

    @Override
    public int glGetProgramiv(int program, int pname) {
        return GL20.glGetProgrami(program, pname);
    }

    @Override
    public String glGetShaderInfoLog(int shader) {
        int length = GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH);
        return GL20.glGetShaderInfoLog(shader, length);
    }

    @Override
    public int glGetShaderiv(int shader, int pname) {
        return GL20.glGetShaderi(shader, pname);
    }


    @Override
    public int glGetUniformLocation(GpuProgram program, String name) {
        return GL20.glGetUniformLocation(program.getProgram(), name);
    }


    @Override
    public int glGetUniformLocation(int program, String name) {
        return GL20.glGetUniformLocation(program, name);
    }


    @Override
    public void glGetUniformfv(GpuProgram program, int location, FloatBuffer params) {
        GL20.glGetUniform(program.getProgram(), location, params);
    }


    @Override
    public void glGetUniformfv(int program, int location, FloatBuffer params) {
        GL20.glGetUniform(program, location, params);
    }


    @Override
    public void glGetUniformiv(GpuProgram program, int location, IntBuffer params) {
        GL20.glGetUniform(program.getProgram(), location, params);
    }


    @Override
    public void glGetUniformiv(int program, int location, IntBuffer params) {
        GL20.glGetUniform(program, location, params);
    }


    @Override
    public void glLinkProgram(GpuProgram p) {
        glLinkProgram(p.getProgram());
    }

    @Override
    public void glLinkProgram(int program) {
        GL20.glLinkProgram(program);
    }

    @Override
    public void glShaderSource(int shader, String source) {
        GL20.glShaderSource(shader, source);
    }

    @Override
    public void glTexImage2D(int target, int level, int internalFormat,
                             int width, int height, int border, int format,
                             int type, ByteBuffer pixels) {

        GL11.glTexImage2D(t(target), level, t(internalFormat), width, height, border, t(format), t(type), pixels);
    }

    @Override
    public void glUniform1f(int location, float v0) {
        GL20.glUniform1f(location, v0);
    }


    @Override
    public void glUniform1i(int location, int v0) {
        GL20.glUniform1i(location, v0);
    }


    @Override
    public void glUniform2f(int location, float v0, float v1) {
        GL20.glUniform2f(location, v0, v1);
    }


    @Override
    public void glUniform2i(int location, int v0, int v1) {
        GL20.glUniform2i(location, v0, v1);
    }


    @Override
    public void glUniform3f(int location, float v0, float v1, float v2) {
        GL20.glUniform3f(location, v0, v1, v2);
    }


    @Override
    public void glUniform3i(int location, int v0, int v1, int v2) {
        GL20.glUniform3i(location, v0, v1, v2);
    }


    @Override
    public void glUniform4f(int location, float v0, float v1, float v2, float v3) {
        GL20.glUniform4f(location, v0, v1, v2, v3);
    }


    @Override
    public void glUniform4fv(int location, int count, FloatBuffer v) {
            /* I'm not sure if this is the correct function but it's the closest I could find :*/
        GL20.glUniform4(location, v);
    }


    @Override
    public void glUniform4i(int location, int v0, int v1, int v2, int v3) {
        GL20.glUniform4i(location, v0, v1, v2, v3);
    }


    @Override
    public void glUniform4iv(int location, int count, IntBuffer v) {
            /* I'm not sure if this is the correct function but it's the closest I could find :*/
        GL20.glUniform4(location, v);
    }


    @Override
    public void glUseProgram(GpuProgram program) {
        GL20.glUseProgram(program.getProgram());
    }

    @Override
    public void glUseProgram(int program) {
        GL20.glUseProgram(program);
    }

    @Override
    public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
        GL20.glVertexAttribPointer(index, size, t(type), normalized, stride, offset);
    }

    @Override
    public void glValidateProgram(GpuProgram p) {
        glValidateProgram(p.getProgram());
    }

    @Override
    public void glValidateProgram(int program) {
        GL20.glValidateProgram(program);
    }

    @Override
    public void glViewport(int x, int y, int width, int height) {
        GL11.glViewport(x, y, width, height);
    }

    /* Legacy functions */

    @Override
    public void glLineWidth(float width) {
        GL11.glLineWidth(width);
    }


    /*
    @Override
    public void glVertex2d(double x, double y) { 
        GL11.glVertex2d(x, y);
    }

    @Override
    public void glVertex2f(float x, float y) { 
        GL11.glVertex2f(x, y);
    }

    @Override
    public void glVertex2i(int x, int y) { 
        GL11.glVertex2i(x, y);
    }

    @Override
    public void glVertex3d(double x, double y, double z) { 
        GL11.glVertex3d(x, y, z);
    }

    @Override
    public void glVertex3f(float x, float y, float z) { 
        GL11.glVertex3f(x, y, z);
    }

    @Override
    public void glVertex3i(int x, int y, int z) { 
        GL11.glVertex3i(x, y, z);
    }

    @Override
    public void glVertex4d(double x, double y, double z, double w) { 
        GL11.glVertex4d(x, y, z, w);
    }

    @Override
    public void glVertex4f(float x, float y, float z, float w) { 
        GL11.glVertex4f(x, y, z, w);
    }

    @Override
    public void glVertex4i(int x, int y, int z, int w) { 
        GL11.glVertex4i(x, y, z, w);
    }
    */


    /* Internal functions */

    private static int t(int konst) {
        // seems LWJGL uses the same constants we're using.
        return konst;
    }
}


