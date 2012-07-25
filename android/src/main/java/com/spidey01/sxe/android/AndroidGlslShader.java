package com.spidey01.sxe.android;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.GlslShader;

import android.opengl.GLES20;

import java.io.InputStream;

public class AndroidGlslShader extends GlslShader {

    private static final String TAG = "AndroidGlslShader";

    public AndroidGlslShader() {
        super();
    }

    public AndroidGlslShader(String fileName) {
        super(fileName);
    }

    public AndroidGlslShader(Type type, InputStream is) {
        super(type, is);
    }

    public AndroidGlslShader(Type type, InputStream is, String name) {
        super(type, is, "/dev/null");
    }

    @Override
    public String getInfoLog() {
        return GLES20.glGetShaderInfoLog(mShader);
    }

    protected boolean doCompile(String code) {
        int type = -1;

        if (mType == Type.VERTEX) {
            type = GLES20.GL_VERTEX_SHADER;
        } else if (mType == Type.FRAGMENT) {
            type = GLES20.GL_FRAGMENT_SHADER;
        } else {
            throw new IllegalStateException("Unknown shader type: "+mType);
        }

        mShader = GLES20.glCreateShader(type);
        if (mShader == 0) {
            return false;
        }

        GLES20.glShaderSource(mShader, code);
        GLES20.glCompileShader(mShader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(mShader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == GLES20.GL_FALSE) {
            GLES20.glDeleteShader(mShader);
            return false;
        }

        return true;
    }
}


