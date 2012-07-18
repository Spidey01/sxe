package com.spidey01.sxe.android;

import com.spidey01.sxe.core.GlslProgram;
import com.spidey01.sxe.core.GlslShader;

import android.opengl.GLES20;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class AndroidGlslProgram implements GlslProgram {

    private List<GlslShader> mShaders = new LinkedList();
    private int mProgram;
    private static final String TAG = "AndroidGlslProgram";

    public AndroidGlslProgram() {
        mProgram = GLES20.glCreateProgram();

        if (mProgram == 0) {
            // can't make program
        }
    }

    public int getProgram() {
        return mProgram;
    }

    public List<GlslShader> getShaders() {
        return Collections.unmodifiableList(mShaders);
    }

    public void addShader(GlslShader shader) {
        GLES20.glAttachShader(mProgram, shader.getShader());
        mShaders.add(shader);
    }

    public boolean link() {
        GLES20.glLinkProgram(mProgram);
        
        int[] status = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, status, 0);
        if (status[0] == GLES20.GL_FALSE) {
            return false;
        }
        return true;
    }

    public boolean validate() {
        GLES20.glValidateProgram(mProgram);

        int[] status = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_VALIDATE_STATUS, status, 0);
        if (status[0] == GLES20.GL_FALSE) {
            return false;
        }
        return true;
    }

    public String getInfoLog() {
        return GLES20.glGetProgramInfoLog(mProgram);
    }
}

