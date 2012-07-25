package com.spidey01.sxe.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/** Class to manage game resources.
 *
 */
public class ResourceManager {
    private final static String TAG = "ResourceManager";

    /** Map of loaders to container types, e.g. .zip */
    private Map<String, ResourceLoader> mLoaders = new HashMap<String, ResourceLoader>();
    /** Map of resources loaded */
    private Map<Resource, InputStream> mResources = new HashMap<Resource, InputStream>();

    /** Loader used when there isn't a container */
    private ResourceLoader mDefaultLoader;

    public ResourceManager() {
        // Setup standard resource loaders
        mDefaultLoader = new PathResourceLoader();
        mLoaders.put(".zip", new ZipResourceLoader());
    }

    /**
     * @param container a string like ".zip"
     * @param loader an implementation of ResourceLoader
     * @return The old loader associated for container, or null.
     * @see ResourceLoader
     */
    public ResourceLoader setLoader(String container, ResourceLoader loader) {
        ResourceLoader old = mLoaders.get(container);
        mLoaders.put(container, loader);

        return old;
    }

    public Resource load(File path) {
        return load(path.getPath());
    }

    /*
     * @param path Paths can be specified in a simple syntax of unix style file
     *             paths, or in a CP/M style where "Containers" are used like
     *             driver letters. A container is something like a zip file. So
     *             Foo.zip:/ham/spam.png should make sense ;).
     */
    public Resource load(String path) {
        Log.i(TAG, "load("+path+")");

        ResourceLoader loader = getLoader(path);
        try {
            InputStream is = loader.getInputStream(path);
            Resource r = new Resource(Resource.Type.UNKNOWN, path, is, null);
            mResources.put(r, is);
            return r;
        } catch (IOException e) {
            Log.wtf(TAG, "load("+path+") failed", e);
            return null;
        }
    }

    /** Like load() but setups up a Shader.
     *
     * @param path Same as per load() but must end with a suitable file
     *             extension for a shader.
     * @param shaderImplClass TheGlSlShaderImpl.class to use. It must implement
     *                        a public constructor taking a GlslShader.Type,
     *                        InputStream, and path.
     * @return Resource with it's Object field set to a GlslShader.
     */
    // XXX a interface for a factory method could work too
    public Resource loadShader(String path,
        Class<? extends GlslShader> shaderImplClass)
    {
        GlslShader.Type shaderType;

        if (path.endsWith(".vert")) {
            shaderType = GlslShader.Type.VERTEX;
        } else if (path.endsWith(".frag")) {
            shaderType = GlslShader.Type.FRAGMENT;
        } else {
            throw new IllegalArgumentException(path+" doesn't appear to be a shader");
        }

        Log.i(TAG, "loadShader("+path+")");

        ResourceLoader loader = getLoader(path);

        try {
            Constructor ctor = shaderImplClass.getDeclaredConstructor(
                GlslShader.Type.class,
                InputStream.class,
                String.class);

            InputStream is = loader.getInputStream(path);

            GlslShader shader = (GlslShader)ctor.newInstance(
                GlslShader.Type.VERTEX,
                (InputStream)is,
                path);

            Resource r = new Resource(Resource.Type.VERTEX_SHADER, path, is, shader);

            mResources.put(r, is);

            return r;
        } catch(Exception fml) {
            Log.w(TAG, "Can't find a suitable ctor for "+shaderImplClass, fml);
            return null;
        }
    }

    /** Get an InputStream for a loaded Resource */
    public InputStream get(Resource r) throws IOException {
        return mResources.get(r);
    }

    public ResourceLoader getLoader(String path) {
        int colon = path.indexOf(":");
        int dot = path.indexOf(".");

        if (colon == -1) {
            // assume regular path
            return mDefaultLoader;
        } else if (dot == -1 || (dot > colon)) {
            // for containers that don't work like '.ext'
            String container = path.substring(0, colon);
            Log.d(TAG, "container for "+path+" is "+container);
            return mLoaders.get(container);
        } else {
            // for containers that do work like '.ext'
            assert dot < colon : "path is funky";
            String container = path.substring(dot, colon);
            Log.d(TAG, "container for "+path+" is "+container);
            return mLoaders.get(container);
        }
    }
}

