package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.GlslShader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class LwjglGlslShader implements GlslShader {

    private int mShaderId;
    private static final String TAG = "LwjglGlslShader";

    LwjglGlslShader(String fileName) {
        if (!compile(fileName)) {
            throw new RuntimeException(getInfoLog());
        }
    }

    public int getShader() {
        return mShaderId;
    }

    protected boolean compile(String fileName) {

        int type = -1;

        if (fileName.endsWith(".vert")) {
            type = ARBVertexShader.GL_VERTEX_SHADER_ARB;
        } else if (fileName.endsWith(".frag")) {
            type = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
        } else {
            throw new RuntimeException("Unknown shader type for "+fileName);
        }

        mShaderId = ARBShaderObjects.glCreateShaderObjectARB(type);
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

        ARBShaderObjects.glShaderSourceARB(mShaderId, code);
        ARBShaderObjects.glCompileShaderARB(mShaderId);
        if (ARBShaderObjects.glGetObjectParameteriARB(mShaderId,
            ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        {
            mShaderId = 0;
            return false;
        }

        return true;
    }

    @Override
    public String getInfoLog() {
        int length = GL20.glGetShader(mShaderId, GL20.GL_INFO_LOG_LENGTH);
        return GL20.glGetShaderInfoLog(mShaderId, length);
    }
}

