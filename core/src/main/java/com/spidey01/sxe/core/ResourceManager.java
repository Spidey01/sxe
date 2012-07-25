package com.spidey01.sxe.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    /** Loads a shader from path.
     *
     * You would call this like:
     *
     * <code>
     *  try {
     *      rm.loadShader("foo.zip:/shaders/myshader.vert", MyShader.class);
     *  } catch (IOException e) {
     *      // ...
     *  }
     *  </code>
     *
     * @param path Resource path that must end with a suitable file extension
     *             for a shader.
     * @param shaderImplClass TheShaderImpl.class to use. It must implement a
     *                        public constructor matching the argument
     *                        signature of ResourceFactory.make().
     * @return Resource with it's Object field set to a GlslShader. null on failure.
     * @throws IOException
     */
    public Resource load(String path, Class<? extends Shader> shaderImplClass)
        throws IOException, InvocationTargetException
    {
        Log.i(TAG, "loadShader("+path+")");

        Shader.Type shaderType = GlslShader.getType(path);
        ResourceLoader loader = getLoader(path);

        try {
            Constructor ctor = shaderImplClass.getDeclaredConstructor(
                Shader.Type.class,
                InputStream.class,
                String.class);
            ctor.setAccessible(true);

            InputStream is = loader.getInputStream(path);

            Shader shader = (Shader)ctor.newInstance(
                shaderType, (InputStream)is, path);

            Resource r = new Resource(Resource.Type.VERTEX_SHADER, path, is, shader);

            mResources.put(r, is);

            return r;
        } catch(NoSuchMethodException e) {
            Log.e(TAG, "Can't find a suitable ctor for "+shaderImplClass, e);
        } catch(InstantiationException e) {
            Log.w(TAG, "Couldn't instantiate a "+shaderImplClass, e);
            /* ^ Shouldn't be reached if setAccessible(true) above ^ */
        } catch(IllegalAccessException e) {
            Log.e(TAG, "Ctor for "+shaderImplClass+"Is not accessible here.", e);
        }

        return null;
    }

    /** Loads a shader from path.
     *
     * You would call this like:
     *
     * <code>
     *  try {
     *      rm.loadShader("foo.zip:/shaders/myshader.vert", new ResourceFactory<MyShader>(){
     *          public Shader make(Shader.Type type, InputStream is, final String path) {
     *              // ...
     *          }
     *      });
     *  } catch (IOException e) {
     *      // ...
     *  }
     *  </code>
     *
     * @param path Resource path that must end with a suitable file extension
     *             for a shader.
     * @param factory A ResourceFactory for make()'ing a suitable Shader.
     * @return Resource with it's Object field set to a GlslShader. null on failure.
     * @throws IOException
     */
    public Resource load(String path, ResourceFactory<? extends Shader> factory)
        throws IOException
    {
        Log.i(TAG, "loadShader("+path+")");

        Shader.Type shaderType = GlslShader.getType(path);
        ResourceLoader loader = getLoader(path);

        // try {
            InputStream is = loader.getInputStream(path);

            Shader shader = factory.make(shaderType, (InputStream)is, path);

            Resource r = new Resource(Resource.Type.VERTEX_SHADER, path, is, shader);

            mResources.put(r, is);

            return r;
        // } catch(Exception fml) {
            // Log.w(TAG, "Couldn't loadShader", fml);
            // return null;
        // }

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

