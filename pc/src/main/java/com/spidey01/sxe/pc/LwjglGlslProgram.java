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

