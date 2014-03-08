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
package com.spidey01.sxe.core.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** Utilities for working with buffers.
 *
 * Buffers are created with make<samp>Type</samp>(size) in the native byte
 * order (BIG or little). Direct buffers are allocated for performance. Size of
 * the buffer is based on the size of Java datatype being allocated, e.g.
 * Utils.Buffers.createIntBuffer(2) allocates a buffer for 2 Integers which
 * requires 8 Bytes of space.
 *
 * Other things, well read the doc ;).
 */
public class Buffers {


    /** InputStream to something with .readLine() :-).
     *
     * Often I need to deal with InputStream but of course, all I reallly want
     * to do is get data out of it line by line.
     */
    public static BufferedReader makeReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in));
    }

    public static BufferedReader makeReader(File file) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }


    public static ByteBuffer makeByte(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }


    public static DoubleBuffer makeDouble(int size) {
        return makeByte(size << 3).asDoubleBuffer();
    }


    public static FloatBuffer makeFloat(int size) {
        return makeByte(size << 2).asFloatBuffer();
    }


    public static IntBuffer makeInt(int size) {
        return makeByte(size << 2).asIntBuffer();
    }

}

