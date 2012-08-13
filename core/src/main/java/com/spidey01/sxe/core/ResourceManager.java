/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

package com.spidey01.sxe.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
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
    private Map<String, Resource> mResources = new HashMap<String, Resource>();

    /** Loader used when there isn't a container */
    private ResourceLoader mDefaultLoader;

    public ResourceManager() {
        // Setup standard resource loaders
        mDefaultLoader = new PathResourceLoader();
        mLoaders.put("default", mDefaultLoader);
        mLoaders.put("vfs", new VirtualFileSystemLoader("vfs", this));
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

        Resource r = new FileResource(Resource.Type.TEXT_FILE, path, getLoader(path));
        r.load();
        mResources.put(path, r);
        return r;
    }

    /** Loads a shader from path.
     *
     * You would call this like:
     *
     * <code>
     *  try {
     *      rm.load("foo.zip:/shaders/myshader.vert", MyShader.class);
     *  } catch (IOException e) {
     *      // ...
     *  }
     *  </code>
     *
     * @param path Resource path that must end with a suitable file extension
     *             for a shader.
     * @param gl an OpenGl implementation to use with the shader.
     * @param shaderImplClass TheShaderImpl.class to use. It must implement a
     *                        public constructor matching the argument
     *                        signature of (OpenGl, Resource.Type, String,
     *                            ResourceLoader, ShaderClass)
     * @return Resource with it's Object field set to a GlslShader. null on failure.
     * @throws IOException
     */
    public Resource load(String path, OpenGl gl, Class<? extends Shader> shaderImplClass)
        throws IOException, InvocationTargetException
    {
        Log.i(TAG, "load("+path+", "+shaderImplClass+")");

        Resource r = new ShaderResource(Utils.getShaderResourceType(path), path, getLoader(path), gl, shaderImplClass);
        r.load();
        mResources.put(path, r);
        return r;
    }

    /** Loads a shader from path.
     *
     * You would call this like:
     *
     * <code>
     *  try {
     *      rm.load("foo.zip:/shaders/myshader.vert", new ShaderFactory<MyShader>(){
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
     * @param factory A ShaderFactory for make()'ing a suitable Shader.
     * @return Resource with it's Object field set to a GlslShader. null on failure.
     * @throws IOException
     */
    public Resource load(String path, ShaderFactory<? extends Shader> factory)
        throws IOException
    {
        Log.i(TAG, "load("+path+", "+factory+")");

        Resource r = new ShaderResource(Utils.getShaderResourceType(path), path, getLoader(path), factory);
        r.load();
        mResources.put(path, r);
        return r;
    }

    /** Get a handle to an already loaded resource.
     *
     * @param path the path previously loaded
     * @return Resource previously loaded for path, or null if  not found.
     */
    public Resource get(String path) {
        return mResources.get(path);
    }

    /** Get a view of the resource loaders available.
     *
     * The key is the container as given to setLoader().
     * The value is the corresponding ResourceLoader.
     *
     * You cannot modify the ResourceManagers understanding of loaders with
     * this. Use {@link #setLoader} for that. You can however manipulate the
     * loaders themselves.
     */
    public final Map<String, ResourceLoader> getLoaders() {
        return Collections.unmodifiableMap(mLoaders);
    }

    public ResourceLoader getLoader(String path) {
        int colon = path.indexOf(":");
        int dot = path.indexOf(".");

        if (colon == -1) {
            // assume regular path
            ResourceLoader l = mLoaders.get("default");
            return l == null ? mDefaultLoader : l;
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

