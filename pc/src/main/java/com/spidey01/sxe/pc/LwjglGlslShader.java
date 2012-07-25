package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.GlslShader;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.GlslShader;
import com.spidey01.sxe.core.Utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class LwjglGlslShader extends GlslShader {

    private static final String TAG = "LwjglGlslShader";

    /** For delayed compilation.
     *
     * You must call compile() with the shader source before using the shader.
     */
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
    public boolean compile(Type type, InputStream source) {
        mType = type;
        try {
            return doCompile(
                getSource(Utils.makeBufferedReader(source)));
        } catch(IOException e) {
            Log.e(TAG, "Unable to load shader from input stream", e);
            return false;
        }
    }

    @Override
    public boolean compile(String fileName) {
        mType = GlslShader.getType(fileName);

        try {
            return doCompile(
                getSource(new BufferedReader(new FileReader(fileName))));
        } catch(IOException e) {
            Log.e(TAG, "Unable to load shader from "+fileName, e);
            return false;
        }
    }

    @Override
    public String getInfoLog() {
        int length = GL20.glGetShader(mShader, GL20.GL_INFO_LOG_LENGTH);
        return GL20.glGetShaderInfoLog(mShader, length);
    }

    private boolean doCompile(String code) {
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

