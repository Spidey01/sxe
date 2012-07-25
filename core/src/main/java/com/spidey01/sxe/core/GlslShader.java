package com.spidey01.sxe.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public abstract class GlslShader implements Shader {
    protected Type mType;
    protected int mShader = -1;
    protected String mFileName = null;
    private static final String TAG = "GlslShader";

    /** For delayed compilation.
     *
     * You must call compile() with the shader source before using the shader.
     */
    public GlslShader() {
        super();
    }

    public GlslShader(String fileName) {
        if (!compile(fileName)) {
            throw new RuntimeException(getInfoLog());
        }
    }

    public GlslShader(Type type, InputStream is) {
        this(type, is, "/dev/null");
    }
    public GlslShader(Type type, InputStream is, String name) {
        mFileName = name;
        if (!compile(type, is)) {
            throw new RuntimeException(getInfoLog());
        }
    }

    public abstract boolean compile(String fileName);
    public abstract boolean compile(Type type, InputStream source);

    @Override
    public int getShader() {
        return mShader;
    }

    @Override
    public Type getType() {
        assert mType instanceof Type;
        return mType;
    }

    @Override
    public String getFileName() {
        return mFileName;
    }

    public abstract String getInfoLog();
    
    public static Type getType(String path) {
        if (path.endsWith(".vert")) {
            return Type.VERTEX;
        } else if (path.endsWith(".frag")) {
            return Type.FRAGMENT;
        }
        throw new RuntimeException("Unknown shader type for "+path);
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

}

