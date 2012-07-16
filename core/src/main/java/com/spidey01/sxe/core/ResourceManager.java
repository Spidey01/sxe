package com.spidey01.sxe.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        Resource r = new Resource();
        try {
            InputStream is = loader.getInputStream(path);
            mResources.put(r, is);
        } catch (IOException e) {
            Log.wtf(TAG, "load("+path+") failed", e);
        }
        return r;
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

