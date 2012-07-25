package com.spidey01.sxe.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileReader;
import java.io.IOException;

/** Base class for GLSL based Shaders.
 *
 * This will basically implement everything in the Shader interface for you
 * except the OpenGL related calls to compile the shader code.
 *
 * You need to implement the following methods:
 *
 *  - public String getInfoLog()
 *  - private boolean doCompile(String code)
 *
 * It would be nice to implement the same constructor signatures as well. You
 * can just call <code>super(...)</code>.
 */
public abstract class GlslShader implements Shader {

    /** Type of shader. */
    protected Type mType;

    /** OpenGL GLSL Shader ID. */
    protected int mShader = -1;

    /** File name associated with this stream. */
    protected String mFileName = null;

    private static final String TAG = "GlslShader";

    /** For delayed compilation.
     *
     * You must later call compile() with the shader source before using the
     * shader. You probably don't want this ctor.
     */
    public GlslShader() {
        super();
    }

    /** Compile shader from fileName.
     *
     * @param fileName the path to load the GLSL code from.
     * @throws RuntimeException if compilation failed.
     */
    public GlslShader(String fileName) {
        if (!compile(fileName)) {
            throw new RuntimeException(getInfoLog());
        }
    }

    /** Compile shader from input stream.
     *
     * This will initialize the file name for the shader to /dev/null where
     * applicable.
     *
     * @param type The Type of shader to compile.
     * @throws RuntimeException if compilation failed.
     */
    public GlslShader(Type type, InputStream is) {
        this(type, is, "/dev/null");
    }

    /** Compile shader from input stream with a name.
     *
     * @param type The Type of shader to compile.
     * @param name Value for getFileName().
     * @throws RuntimeException if compilation failed.
     */
    public GlslShader(Type type, InputStream is, String name) {
        mFileName = name;
        if (!compile(type, is)) {
            throw new RuntimeException(getInfoLog());
        }
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

    /** Helper method for slurping.
     * 
     * This can be used for getting the source code of a shader program.
     */
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

    /** You must implement this for compile() to work.
     */
    protected abstract boolean doCompile(String code);
}

