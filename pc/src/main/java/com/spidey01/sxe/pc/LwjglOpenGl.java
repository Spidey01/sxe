package com.spidey01.sxe.pc;

import  com.spidey01.sxe.core.OpenGl;
import  com.spidey01.sxe.core.GlslProgram;

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


public class LwjglOpenGl implements OpenGl {
    private static final String TAG = "LwjglopenGl";

    /* Utility functions */

    @Override
    public ByteBuffer createByteBuffer(int size) {
        return BufferUtils.createByteBuffer(size);
    }

    @Override
    public DoubleBuffer createDoubleBuffer(int size) {
        return BufferUtils.createDoubleBuffer(size);
    }

    @Override
    public FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    @Override
    public IntBuffer createIntBuffer(int size) {
        return BufferUtils.createIntBuffer(size);
    }

    /* OpenGL functions */

    @Override
    public void glBindBuffer(int target, int buffer) {
        GL15.glBindBuffer(t(target), buffer);
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
    public void glClear(int mask) {
        GL11.glClear(t(mask));
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
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
    public int glGetAttribLocation(int program, String name) {
        return GL20.glGetAttribLocation(program, name);
    }

    @Override
    public void glUseProgram(GlslProgram program) {
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
    public void glViewport(int x, int y, int width, int height) {
        GL11.glViewport(x, y, width, height);
    }

    /* Internal functions */

    private static int t(int konst) {
        // If Java doesn't allow meta programming this away, it can kiss my ass.
        switch (konst) {
            case OpenGl.GL_ARRAY_BUFFER:
                return GL15.GL_ARRAY_BUFFER;

            case OpenGl.GL_COLOR_BUFFER_BIT:
                return GL11.GL_COLOR_BUFFER_BIT;

            case OpenGl.GL_CULL_FACE:
                return GL11.GL_CULL_FACE;

            case OpenGl.GL_CULL_FACE_MODE:
                return GL11.GL_CULL_FACE_MODE;

            case OpenGl.GL_DEPTH_TEST:
                return GL11.GL_DEPTH_TEST;

            case OpenGl.GL_FALSE:
                return GL11.GL_FALSE;

            case OpenGl.GL_FLOAT:
                return GL11.GL_FLOAT;

            case OpenGl.GL_STATIC_DRAW:
                return GL15.GL_STATIC_DRAW;

            case OpenGl.GL_TRIANGLES:
                return GL11.GL_TRIANGLES;

            default:
                throw new IllegalArgumentException("Don't know how to remap "+konst+" to a LWJGL OpenGL constant");
        }
    }
}


