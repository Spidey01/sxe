package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.GlslProgram;
import com.spidey01.sxe.core.GlslShader;

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

    private List<GlslShader> mShaders = new LinkedList();
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

    public List<GlslShader> getShaders() {
        return Collections.unmodifiableList(mShaders);
    }

    public void addShader(GlslShader shader) {
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
        IntBuffer v = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterARB(mProgram, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, v);

        int length = v.get();
        if (length > 1) {
            // We have some info we need to output.
            ByteBuffer log = BufferUtils.createByteBuffer(length);
            v.flip();
            ARBShaderObjects.glGetInfoLogARB(mProgram, v, log);
            byte[] info = new byte[length];
            log.get(info);
            return new String(info);
        }
        return "";
    }
}

