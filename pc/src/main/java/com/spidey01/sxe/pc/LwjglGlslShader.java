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

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.GlslShader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.Util;

import java.io.InputStream;

public class LwjglGlslShader extends GlslShader {

    private static final String TAG = "LwjglGlslShader";

    public LwjglGlslShader() {
        super();
    }

    public LwjglGlslShader(String fileName) {
        super(fileName);
    }

    public LwjglGlslShader(Type type, InputStream is) {
        super(type, is);
    }

    public LwjglGlslShader(Type type, InputStream is, String name) {
        super(type, is, name);
    }

    @Override
    public String getInfoLog() {
        int length = GL20.glGetShader(mShader, GL20.GL_INFO_LOG_LENGTH);
        return GL20.glGetShaderInfoLog(mShader, length);
    }

    protected boolean doCompile(String code) {
        int type = -1;

        if (mType == Type.VERTEX) {
            type = ARBVertexShader.GL_VERTEX_SHADER_ARB;
        } else if (mType == Type.FRAGMENT) {
            type = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
        } else {
            throw new IllegalStateException("Unknown shader type: "+mType);
        }

        mShader = ARBShaderObjects.glCreateShaderObjectARB(type);
        if (mShader == 0) {
            return false;
        }

        ARBShaderObjects.glShaderSourceARB(mShader, code);
        ARBShaderObjects.glCompileShaderARB(mShader);
        if (ARBShaderObjects.glGetObjectParameteriARB(mShader,
            ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        {
            mShader = 0;
            return false;
        }

        return true;
    }
}

