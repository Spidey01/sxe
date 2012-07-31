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

import com.spidey01.sxe.core.GlslProgram;
import com.spidey01.sxe.core.Shader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class LwjglGlslProgram implements GlslProgram {

    private List<Shader> mShaders = new LinkedList();
    private int mProgram;
    private static final String TAG = "LwjglGlslProgram";

    public LwjglGlslProgram() {
        mProgram = GL20.glCreateProgram();

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
        GL20.glAttachShader(mProgram, shader.getShader());
        mShaders.add(shader);
    }

    public boolean link() {
        GL20.glLinkProgram(mProgram);

        if (ARBShaderObjects.glGetObjectParameteriARB(mProgram,
            ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE)
        {
            return false;
        }
        return true;
    }

    public boolean validate() {
        GL20.glValidateProgram(mProgram);
        if (ARBShaderObjects.glGetObjectParameteriARB(mProgram,
            ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE)
        {
            return false;
        }
        return true;
    }

    public String getInfoLog() {
        int length = GL20.glGetProgram(mProgram, GL20.GL_INFO_LOG_LENGTH);
        return GL20.glGetProgramInfoLog(mProgram, length);
    }
}

