package com.spidey01.sxe.android;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.GlslProgram;
import com.spidey01.sxe.core.GlslShader;

import android.opengl.GLES20;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class AndroidGlslShader implements GlslShader {

    private int mShaderId;
    private static final String TAG = "AndroidGlslShader";

    AndroidGlslShader(String filename) {
    }

    public int getShader() {
        return mShaderId;
    }

    protected boolean compile(String fileName) {
        int type = -1;

        if (fileName.endsWith(".vert")) {
            type = GLES20.GL_VERTEX_SHADER;
        } else if (fileName.endsWith(".frag")) {
            type = GLES20.GL_FRAGMENT_SHADER;
        } else {
            throw new RuntimeException("Unknown shader type for "+fileName);
        }

        mShaderId = GLES20.glCreateShader(type);
        if (mShaderId == 0) {
            return false;
        }

        String code = "";
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                code += line + "\n";
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed loading shader file "+fileName, e);
            return false;
        }

        GLES20.glShaderSource(mShaderId, code);
        GLES20.glCompileShader(mShaderId);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(mShaderId, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == GLES20.GL_FALSE) {
            GLES20.glDeleteShader(mShaderId);
            return false;
        }

        return true;
    }
}


