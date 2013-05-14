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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileReader;
import java.io.IOException;

/** Base class for GLSL based Shaders.
 *
 * This will basically implement everything in the Shader interface for you
 * except the OpenGL related calls to compile the shader code.
 *
 * You need to implement the following methods:
 *
 *  - public String getInfoLog()
 *  - private boolean doCompile(String code)
 *
 * It would be nice to implement the same constructor signatures as well. You
 * can just call <code>super(...)</code>.
 */
public class GlslShader implements Shader {

    /** Type of shader. */
    protected Type mType;

    /** OpenGL GLSL Shader ID. */
    protected int mShader = -1;

    /** File name associated with this stream. */
    protected String mFileName = null;

    protected OpenGl mGl;

    private static final String TAG = "GlslShader";

    /** For delayed compilation.
     *
     * You must later call compile() with the shader source before using the
     * shader. You probably don't want this ctor.
     */
    public GlslShader(OpenGl gl) {
        mGl = gl;
    }

    /** Compile shader from fileName.
     *
     * @param fileName the path to load the GLSL code from.
     * @throws RuntimeException if compilation failed.
     */
    public GlslShader(OpenGl gl, String fileName) {
        mGl = gl;
        if (!compile(fileName)) {
            throw new RuntimeException(getInfoLog());
        }
    }

    /** Compile shader from input stream.
     *
     * This will initialize the file name for the shader to /dev/null where
     * applicable.
     *
     * @param type The Type of shader to compile.
     * @throws RuntimeException if compilation failed.
     */
    public GlslShader(OpenGl gl, Type type, InputStream is) {
        this(gl, type, is, "/dev/null");
    }

    /** Compile shader from input stream with a name.
     *
     * @param type The Type of shader to compile.
     * @param name Value for getFileName().
     * @throws RuntimeException if compilation failed.
     */
    public GlslShader(OpenGl gl, Type type, InputStream is, String name) {
        mGl = gl;
        mFileName = name;
        if (!compile(type, is)) {
            throw new RuntimeException(getInfoLog());
        }
    }

    @Override
    public boolean compile(Type type, InputStream source) {
        mType = type;
        try {
            return doCompile(Utils.slurp(source));
        } catch(IOException e) {
            Log.e(TAG, "Unable to load shader from input stream", e);
            return false;
        }
    }

    @Override
    public boolean compile(String fileName) {
        throw new IllegalStateException("Method needs fixing.");
        // mType = Utils.getShaderType(fileName);

        /*
        try {
            return doCompile(Utils.slurp(fileName));
        } catch(IOException e) {
            Log.e(TAG, "Unable to load shader from "+fileName, e);
            return false;
        }
        */
    }

    @Override
    public int getShader() {
        return mShader;
    }

    @Override
    public Type getType() {
        assert mType instanceof Type;
        return mType;
    }

    @Override
    public String getFileName() {
        return mFileName;
    }

    public String getInfoLog() {
        return mGl.glGetShaderInfoLog(mShader);
    }
    
    protected boolean doCompile(String code) {
        int type = -1;

        if (mType == Type.VERTEX) {
            type = OpenGl.GL_VERTEX_SHADER;
        } else if (mType == Type.FRAGMENT) {
            type = OpenGl.GL_FRAGMENT_SHADER;
        } else {
            throw new IllegalStateException("Unknown shader type: "+mType);
        }

        mShader = mGl.glCreateShader(type);
        if (mShader == 0) {
            return false;
        }

        mGl.glShaderSource(mShader, code);
        mGl.glCompileShader(mShader);
        if (mGl.glGetShaderiv(mShader, OpenGl.GL_COMPILE_STATUS)
            == OpenGl.GL_FALSE)
        {
            mGl.glDeleteShader(mShader);
            return false;
        }

        return true;
    }
}

