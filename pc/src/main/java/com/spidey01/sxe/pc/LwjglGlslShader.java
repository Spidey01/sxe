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
        super(type, is, "/dev/null");
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

