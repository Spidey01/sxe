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

import java.util.List;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class GlslProgram implements GpuProgram {

    private List<Shader> mShaders = new LinkedList();
    private int mProgram;
    private OpenGl mOpenGl;
    private static final String TAG = "GlslProgram";

    public GlslProgram(OpenGl gl) {
        mOpenGl = gl;
        mProgram = mOpenGl.glCreateProgram();

        if (mProgram == 0) {
            // can't make program
        }
    }

    public int getProgram() {
        return mProgram;
    }

    public List<Shader> getShaders() {
        return Collections.unmodifiableList(mShaders);
    }

    public void addShader(Shader shader) {
        mOpenGl.glAttachShader(mProgram, shader.getShader());
        mShaders.add(shader);
    }

    public boolean link() {
        mOpenGl.glLinkProgram(mProgram);

        if (mOpenGl.glGetProgramiv(mProgram, OpenGl.GL_LINK_STATUS)
            == OpenGl.GL_FALSE)
        {
            return false;
        }
        return true;
    }

    public boolean validate() {
        mOpenGl.glValidateProgram(mProgram);
        if (mOpenGl.glGetProgramiv(mProgram, OpenGl.GL_VALIDATE_STATUS)
            == OpenGl.GL_FALSE)
        {
            return false;
        }
        return true;
    }

    public String getInfoLog() {
        return mOpenGl.glGetProgramInfoLog(mProgram);
    }
}

