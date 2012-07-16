package com.spidey01.sxe.pc;

import  com.spidey01.sxe.core.OpenGl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;


public class LwjglOpenGl implements OpenGl {

        public void glBindBuffer(int target, int buffer) {
            GL15.glBindBuffer(t(target), buffer);
        }
        
        public void glClear(int mask) {
            GL11.glClear(t(mask));
        }

        public void glClearColor(float red, float green, float blue, float alpha) {
            GL11.glClearColor(red, green, blue, alpha);
        }

        public void glDisableVertexAttribArray(int index) {
            GL20.glEnableVertexAttribArray(index);
        }

        public void glDrawArrays(int mode, int first, int count) {
            GL11.glDrawArrays(t(mode), first, count);
        }

        public void glEnableVertexAttribArray(int index) {
            GL20.glDisableVertexAttribArray(index);
        }

        public void glGenBuffers(IntBuffer buffers) {
            // On android it ought to be buffers.length, buffers
            GL15.glGenBuffers(buffers);
        }

        public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, Buffer buffer) {

            if ((buffer instanceof ByteBuffer ? (ByteBuffer)buffer
                    : (buffer instanceof DoubleBuffer ? (DoubleBuffer)buffer
                        : (buffer instanceof FloatBuffer ? (FloatBuffer)buffer
                            : (buffer instanceof IntBuffer ? (IntBuffer)buffer
                                : null)))) == null)
            {
                throw new IllegalArgumentException("bad buffer");
            }
            
            // prob is arg 3 (type) is supposed to be bool?
            GL20.glVertexAttribPointer(index, size, t(type), normalized, stride,
                (buffer instanceof ByteBuffer ? (ByteBuffer)buffer
                    : (buffer instanceof DoubleBuffer ? (DoubleBuffer)buffer
                        : (buffer instanceof FloatBuffer ? (FloatBuffer)buffer
                            : (IntBuffer)buffer /* last option */))));
        }

        public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
            GL20.glVertexAttribPointer(index, size, t(type), normalized, stride, offset);
        }

        private static int t(int konst) {
            // If Java doesn't allow meta programming this away, it can kiss my ass.
            switch (konst) {
                case OpenGl.GL_ARRAY_BUFFER:
                    return GL15.GL_ARRAY_BUFFER;

                case OpenGl.GL_COLOR_BUFFER_BIT:
                    return GL11.GL_COLOR_BUFFER_BIT;

                case OpenGl.GL_FALSE:
                    return GL11.GL_FALSE;

                case OpenGl.GL_FLOAT:
                    return GL11.GL_FLOAT;

                case OpenGl.GL_TRIANGLES:
                    return GL11.GL_TRIANGLES;

                default:
                    throw new IllegalArgumentException("Don't know how to remap "+konst+" to a LWJGL OpenGL constant");
            }
        }
}


