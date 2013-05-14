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
    private Map<Long, ResourceHandle> mResourceHandles = new HashMap<Long, ResourceHandle>();
    /** Last RID given; used by load() to generate a new rid. */
    private long mLastResourceId = -1;

    /** Loader used when there isn't a container */
    private ResourceLoader mDefaultLoader;

    public ResourceManager() {
        // Setup standard resource loaders
        mDefaultLoader = new PathResourceLoader();
        mLoaders.put("default", mDefaultLoader);
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

    /** Get a view of the resource loaders available.
     *
     * The key is the container as given to setLoader().
     * The value is the corresponding ResourceLoader.
     *
     * You cannot modify the ResourceManagers understanding of loaders with
     * this. Use {@link #setLoader} for that. You can however manipulate the
     * loaders themselves.
     */
    public Map<String, ResourceLoader> getLoaders() {
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

    public long load(File path) {
        return load(path.getPath());
    }


    public long load(String path) {
        ResourceLoader loader = getLoader(path);
        mLastResourceId++;
        ResourceHandle r = new ResourceHandle(loader, mLastResourceId, path);
        mResourceHandles.put((Long)mLastResourceId, r);
        Log.d(TAG, "Loaded "+path+" with "+loader+" and rid="+mLastResourceId);
        return mLastResourceId;
    }


    public ResourceHandle get(long rid) {
        // TODO: don't leak null
        return mResourceHandles.get((Long)rid);
    }

    public ResourceHandle get(String path) {
        return get(load(path));
    }

    public void unload(long rid) {
        // TODO: only unload if no other users.
        try {
            mResourceHandles.get((Long)rid).close();
        } catch (IOException e) {
            Log.w(TAG, "Exception while closing rid="+rid, e);
        }
    }

}

