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

public class LwjglGlslShader implements GlslShader {

    private Type mType;
    private int mShader = -1;
    private String mFileName = null;
    private static final String TAG = "LwjglGlslShader";

    /** For delayed compilation.
     *
     * You must call compile() with the shader source before using the shader.
     */
    public LwjglGlslShader() {
    }

    public LwjglGlslShader(String fileName) {
        if (!compile(fileName)) {
            throw new RuntimeException(getInfoLog());
        }
    }

    public LwjglGlslShader(Type type, InputStream is) {
        this(type, is, "/dev/null");
    }

    public LwjglGlslShader(Type type, InputStream is, String name) {
        mFileName = name;
        if (!compile(type, is)) {
            throw new RuntimeException(getInfoLog());
        }
    }

    public int getShader() {
        return mShader;
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
        if (fileName.endsWith(".vert")) {
            mType = Type.VERTEX;
        } else if (fileName.endsWith(".frag")) {
            mType = Type.FRAGMENT;
        } else {
            throw new RuntimeException("Unknown shader type for "+fileName);
        }

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

    public Type getType() {
        assert mType instanceof Type;
        return mType;
    }

    protected final String getSource(BufferedReader reader)
        throws IOException
    {
        String code = "";
        String line;

        while ((line = reader.readLine()) != null) {
            code += line + "\n";
        }

        return code;
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

