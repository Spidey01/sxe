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

package com.spidey01.sxe.core.graphics;

import com.spidey01.sxe.core.common.Buffers;
import com.spidey01.sxe.core.logging.Log;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;


/** A simple java.nio.Buffer style interface for vertex data.
 *
 * For a full blown VBO, see {@link VertexBufferObject}. This class is just a dumb buffer.
 */

public class VertexBuffer {
    private static final String TAG = "VertexBuffer";

    /**
     * Simple access to the raw FloatBuffer.
     *
     * This is offered because we can't extend FloatBuffer.
     */

    public FloatBuffer buffer;


    /** Transfer vertices to buffer and flip it.
     *
     * @see {@link java.nio.FloatBuffer#put(float[] src) FloatBuffer.put(float[])}.
     */

    public VertexBuffer(float[] vertices) {
        buffer = Buffers.makeFloat(vertices.length);
        buffer.put(vertices);
        // Basically make sure the bounds is set to vertices.length and rewind
        // the position to be read.
        buffer.flip();
    }


    /** Create a buffer with specified capacity. */

    public VertexBuffer(int capacity) {
        buffer = Buffers.makeFloat(capacity);
    }


    /*
     * These methods are just wrappers around buffer.
     *
     * I'd rather extend FloatBuffer but we can't. And I think
     * that ".buffer.method()" kind of sucks. So do this.
     */


    /** Returns the array that backs this buffer  (optional operation). */

    public float[] array() { return buffer.array(); }


    /** Returns the offset within this buffer's backing array of the first element of the buffer  (optional operation). */

    public int arrayOffset() { return buffer.arrayOffset(); }


    /** Returns this buffer's capacity. */

    public int capacity() { return buffer.capacity(); }


    /** Clears this buffer. */

    public VertexBuffer clear() {
        buffer.clear();
        return this;
    }


    /** Flips this buffer. */

    public VertexBuffer flip() {
        buffer.flip();
        return this;
    }


    /** Tells whether or not this buffer is backed by an accessible array. */

    public boolean hasArray() { return buffer.hasArray(); }


    /** Tells whether there are any elements between the current position and the limit. */

    public boolean hasRemaining() { return buffer.hasRemaining(); }


    /** Tells whether or not this buffer is direct. */

    public boolean isDirect() { return buffer.isDirect(); }


    /** Tells whether or not this buffer is read-only. */

    public boolean isReadOnly() { return buffer.isReadOnly(); }


    /** Returns this buffer's limit. */

    public int limit() { return buffer.limit(); }


    /** Sets this buffer's limit. */

    public VertexBuffer limit(int newLimit) {
        buffer.limit(newLimit);
        return this;
    }


    /** Sets this buffer's mark at its position. */

    public VertexBuffer mark() {
        buffer.mark();
        return this;
    }


    /** Returns this buffer's position. */

    public int position() { return buffer.position(); }


    /** Sets this buffer's position. */

    public VertexBuffer position(int newPosition) {
        buffer.position(newPosition);
        return this;
    }


    /** Returns the number of elements between the current position and the limit. */

    public int	remaining() { return buffer.remaining(); }


    /** Resets this buffer's position to the previously-marked position. */

    public VertexBuffer reset() {
        buffer.reset();
        return this;
    }



    /** Rewinds this buffer. */

    public VertexBuffer rewind() {
        buffer.rewind();
        return this;
    }


    /** Relative get method. */

    public float get() { return buffer.get(); }


    /** Relative bulk get method. */

    public VertexBuffer get(float[] dst) {
        buffer.get(dst);
        return this;
    }


    /** Relative bulk get method. */

    public VertexBuffer get(float[] dst, int offset, int length) {
        buffer.get(dst, offset, length);
        return this;
    }


    /** Absolute get method. */

    public float get(int index) { return buffer.get(index); }


    /** Relative put method  (optional operation). */

    public VertexBuffer put(float f) {
        buffer.put(f);
        return this;
    }


    /** Relative bulk put method  (optional operation). */

    public VertexBuffer put(float[] src) {
        buffer.put(src);
        return this;
    }


    /** Relative bulk put method  (optional operation). */

    public VertexBuffer put(float[] src, int offset, int length) {
        buffer.put(src, offset, length);
        return this;
    }


    /** Relative bulk put method  (optional operation). */

    public VertexBuffer put(FloatBuffer src) {
        buffer.put(src);
        return this;
    }


    /** Absolute put method  (optional operation). */

    public VertexBuffer put(int index, float f) {
        buffer.put(index, f);
        return this;
    }


    public ByteOrder order() { return buffer.order(); }


    /** Returns a string summarizing the state of this buffer. */
    // public String toString() { return buffer.toString(); }
}

