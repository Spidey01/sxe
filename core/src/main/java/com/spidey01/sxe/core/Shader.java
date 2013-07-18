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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class Shader {
    private static final String TAG = "Shader";
    
    private boolean mIsInitialized;
    private int mShaderId;
    private final String mSourceCode;

    public enum Type {
        VERTEX, FRAGMENT
    }

    private final Type mType;


    /** Shader source code from a String. */
    public Shader(String code, Type type) {
        mSourceCode = code;
        mType = type;
    }


    /** Shader source code from a File. */
    public Shader(File file, Type type) throws IOException {
        mSourceCode = Utils.slurp(file);
        mType = type;
    }


    /** Shader source code from a Reader. */
    public Shader(BufferedReader reader, Type type) throws IOException {
        mSourceCode = Utils.slurp(reader);
        mType = type;
    }


    /** Shader source code from a InputStream. */
    public Shader(InputStream stream, Type type) throws IOException {
        mSourceCode = Utils.slurp(stream);
        mType = type;
    }


    /** Obtain the underlaying Shader ID from OpenGL. */
    public int getId() {
        check();
        return mShaderId;
    }


    public Type getType() {
        return mType;
    }


    public void initialize(OpenGL GL) {
        if (mIsInitialized) return;

        int type =
            mType == Type.VERTEX ? OpenGL.GL_VERTEX_SHADER
                                 : (mType == Type.FRAGMENT ? OpenGL.GL_FRAGMENT_SHADER : -1);
        assert (type == OpenGL.GL_VERTEX_SHADER) || (type == OpenGL.GL_FRAGMENT_SHADER);

        mShaderId = GL.glCreateShader(type);
        if (mShaderId == 0) {
            throw new RuntimeException("Failed creating shader: "+getInfoLog(GL));
        }

        GL.glShaderSource(mShaderId, mSourceCode);
        GL.glCompileShader(mShaderId);
        if (GL.glGetShaderiv(mShaderId, OpenGL.GL_COMPILE_STATUS) == OpenGL.GL_FALSE) {
            String log = getInfoLog(GL);
            GL.glDeleteShader(mShaderId);
            throw new RuntimeException("Failed compiling shader: "+log);
        }

        mIsInitialized = true;
    }


    public void deinitialize(OpenGL GL) {
        check();
        GL.glDeleteShader(mShaderId);
        mIsInitialized = false;
    }


    private String getInfoLog(OpenGL GL) {
        check();
        return GL.glGetShaderInfoLog(mShaderId);
    }

    private void check() {
        if (!mIsInitialized) {
            throw new IllegalStateException(TAG+" not yet fully initialized!");
        }
    }

}

