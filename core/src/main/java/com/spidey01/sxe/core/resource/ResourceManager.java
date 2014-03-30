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
package com.spidey01.sxe.core.resource;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.config.Settings;
import com.spidey01.sxe.core.config.SettingsListener;
import com.spidey01.sxe.core.common.Subsystem;
import com.spidey01.sxe.core.logging.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


/** Class to manage game resources.
 *
 */
public class ResourceManager
    implements Subsystem
{

    /** Settings.OnChangedListener implementation for ResourceManager.
     */
    private class ResourceSettingsListener extends SettingsListener {
        private static final String TAG = ResourceManager.TAG+".SettingsListener";

        private final String PATH;

        public ResourceSettingsListener(GameEngine engine) {
            super(engine.getSettings());
            String prefix = engine.getGame().getName()+".resources";

            PATH = prefix + ".path";
            mSettings.addChangeListener(PATH, this);
        }


        @Override
        public void onChanged(String key) {
            super.onChanged(key);
            Log.xtrace(TAG, "onChanged(String key =>", key);

            if (!key.equals(PATH)) {
                throw new IllegalArgumentException("onChanged: bad key="+key);
            }

            String value = mSettings.getString(key);
            if (!value.isEmpty()) {
                for (String dir : value.split(":")) {
                    ResourceManager.this.addResourceLocation(dir);
                }
            }
        }
    }


    private final static String TAG = "ResourceManager";

    /** The GameEngine we're initialized for use with. */
    private GameEngine mGameEngine;

    private ResourceSettingsListener mSettingsListener;

    /** Map of loaders to container types, e.g. .zip */
    private Map<String, ResourceLoader> mLoaders = new HashMap<String, ResourceLoader>();

    /** Default loader for default://. */
    private static final ResourceLoader sDefaultLoader = new PathResourceLoader();

    /** Locations to load resources from. */
    private List<String> mSearchLocations = new LinkedList<String>();

    /** Maping of URI's to ResourceHandle's. */
    private Map<URI, ResourceHandle> mHandles = new HashMap<URI, ResourceHandle>();


    public ResourceManager() {
    }


    @Override
    public String name() {
        return TAG;
    }


    /** Initialize the ResourceManager Subsystem for use.
     *
     * Handling of file://, zip://, and gzip:// will be setup with the obvious
     * loaders.
     *
     * A scheme called "default" is also provided. This may be freely mapped to
     * whatever the platforms local convention is or your game prefers: either
     * manually or through a configuration directive.
     *
     * By default, default:// is set as an alias for file://. This is
     * appropriate for most platforms.
     *
     *
     * Runtime configuration values from engine will be evaluated for the
     * following purposes:
     *
     * <dl>
     *  <dt>${game name}.resources.path</dt>
     *      <dd>Colon delimited list of paths to addResourceLocation().</dd>
     * </dl>
     *
     * @see #{ResourceManager.load}
     */
    @Override public void initialize(GameEngine engine) {
        Log.d(TAG, "initialize(", engine, ")");

        // Setup standard resource loaders
        mLoaders.put("default", sDefaultLoader);
        mLoaders.put("file", sDefaultLoader);
        mLoaders.put("zip", new ZipResourceLoader());
        mLoaders.put("gzip", new GZipResourceLoader());

        mGameEngine = engine;
        if (mGameEngine == null) {
            return;
        }

        /* Handle runtime configuration Settings. */
        mSettingsListener = new ResourceSettingsListener(mGameEngine);
    }


    @Override
    public void reinitialize(GameEngine engine) {
        uninitialize();
        initialize(engine);
    }


    /** Uninitialize the resource manager and all handles.
     *
     * <ol>
     *  <li>All ResourceLoaders will be removed.</li>
     *  <li>Search locations will be cleared.</li>
     *  <li>All ResourceHandle will be invalidated.</li>
     * </ol>
     */
    @Override
    public void uninitialize() {
        Log.d(TAG, "uninitialize()");

        mSettingsListener.clear();
        mSettingsListener = null;

        mLoaders.clear();
        assert mLoaders.size() == 0 : "mLoaders was not really cleared!";

        mSearchLocations.clear();
        assert mSearchLocations.size() == 0 : "mSearchLocations was not really cleared!";

        for (ResourceHandle h : mHandles.values()) {
            // I wonder if the compiler is able to runroll this?
            try {
                // FIXME
                h.close();
            } catch (IOException e) {
                Log.w(TAG, "Error closing file handle:", h, e);
            }
        }
        mHandles.clear();
        assert mHandles.size() == 0 : "mHandes was not really cleared!";
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

    
    /** Remove ResourceLoader for scheme. */
    public void removeLoader(String scheme) {
        mLoaders.remove(scheme);
    }


    /** Get a view into the available resource loaders.
     *
     * The key is the scheme as given to setLoader(). The value is the
     * corresponding ResourceLoader.
     *
     * You cannot modify the ResourceManager's understanding of
     * ResourceLoader's with this. Use {@link #setLoader} for that. You can
     * however manipulate the loaders themselves.
     */
    public Map<String, ResourceLoader> getLoaders() {
        return Collections.unmodifiableMap(mLoaders);
    }


    public ResourceLoader getDefaultLoader() {
        return sDefaultLoader;
    }


    /** Get ResourceLoader for URI.
     *
     * @return ResourceLoader for URI or null.
     */
    public ResourceLoader getLoader(URI uri) {
        Log.d(TAG, "URI: "+uri.toString());
        Log.d(TAG, "URI Authority: "+uri.getAuthority());
        Log.d(TAG, "URI Fragment: "+uri.getFragment());
        Log.d(TAG, "URI Host: "+uri.getHost());
        Log.d(TAG, "URI Path: "+uri.getPath());
        Log.d(TAG, "URI Port: "+uri.getPort());
        Log.d(TAG, "URI Query: "+uri.getQuery());
        Log.d(TAG, "URI Scheme: "+uri.getScheme());
        Log.d(TAG, "URI SchemeSpecificPart: "+uri.getSchemeSpecificPart());

        if (uri.getScheme() == null) {
            throw new IllegalArgumentException("No scheme in URI: "+uri);
        }
        return mLoaders.get(uri.getScheme());
    }


    /** Convenient way of getting the ResourceLoader for a path.
     *
     * @param path - used as a URI.
     * @return ResourceLoader for specified URI or null.
     */
    public ResourceLoader getLoader(String path) {
        return getLoader(URI.create(path));
    }

    
    public void addResourceLocation(File path) {
        addResourceLocation(path.getPath());
    }


    public void addResourceLocation(String path) {
        mSearchLocations.add(path);
        Log.v(TAG, "Added resource location:", path);
    }


    /** Finds the location for URI.
     *
     * Given a URI, determine the associated source path from mSearchLocations.
     * @return path or the empty string.
     */
    protected String findLocation(URI uri) {
        for (String location : mSearchLocations) {
            Log.xtrace(TAG, "findLocation: looking in", location, "for URI => ", uri);
            File test = new File(location, uri.getAuthority());
            Log.d(TAG, "Search for "+test.getPath());
            if (!test.isFile()) {
                continue;
            }
            // Windows paths used by File will screw up stuff, so...
            String path = location+"/"+uri.getAuthority();
            Log.d(TAG, "Found "+uri+" at "+path);
            return path;
        }
        return "";
    }


    public ResourceHandle load(File path) throws IOException {
        return load(URI.create(path.getPath()));
    }


    public ResourceHandle load(String path) throws IOException {
        return load(URI.create(path));
    }


    /** Load URI as a ResourceHandle.
     *
     * Resources to be loaded from storage are identified using Uniform Resource
     * Identifiers (URIs). This means that a resource takes the format of
     * <samp>[scheme][scheme-specific part][//authority][path][?query][#fragment]</samp>.
     *
     * The scheme portion is taken as a way to specify which ResourceLoader
     * should be used to load the URI. For example <samp>file://foo</samp> or
     * <samp>zip://bar</samp>.
     *
     * You specify the schemes should be loaded by registering instances of
     * ResourceLoader with the ResourceManager#setLoader() instance method.
     *
     * The authority (or host) portion is used by the ResourceLoader to
     * determine the actual source to load from. In the case of the zip scheme,
     * we would use a URI like <samp>zip://my.zip/foo/bar</samp> to refer to
     * the file bar in directory foo inside of my.zip; it would be loaded using
     * whatever ResourceLoader is set for "zip".
     *
     * Resources will be searched for in locations registered with #addResourceLocation().
     *
     * @throws java.lang.IllegalArgumentException if no scheme given in URI.
     *
     * @see @{link }java.net.URI}
     */
    public ResourceHandle load(URI uri) throws IOException {
        Log.v(TAG, "load(): URI => "+uri);
        ResourceHandle h = mHandles.get(uri);
        if (h == null) {
            ResourceLoader loader = getLoader(uri);
            String path = findLocation(uri);
            if (path.isEmpty()) {
                return null;
            }
            h = new ResourceHandle(uri, loader.getInputStream(path, uri.getPath()));
            Log.v(TAG, "Loaded new ResourceHandle for URI "+uri);
            mHandles.put(uri, h);
        }
        return h;
    }


    public void unload(File uri) throws IOException {
        unload(URI.create(uri.getPath()));
    }


    public void unload(String uri) throws IOException {
        unload(URI.create(uri));
    }


    public void unload(URI uri) throws IOException {
        ResourceHandle h = mHandles.get(uri);
        if (h == null) {
            Log.w(TAG, "Attempted to unload "+uri+" which was NOT loaded.");
            return;
        }
        h.close();
        mHandles.remove(uri);
        Log.v(TAG, "unload(): URI => "+uri);
    }


    /** Used to call to ResourceManager#load() in another thread. */
    private class QueuedLoader implements Callable<ResourceHandle> {
        private static final String TAG = "ResourceManager.QueuedLoader";
        private URI mURI;
        QueuedLoader(URI uri) {
            mURI = uri;
        }
        public ResourceHandle call() {
            try {
                return ResourceManager.this.load(mURI);
            } catch (IOException e) {
                Log.w(TAG, "call(): ", e);
            }
            return null;
        }
    }
 

    public Future<ResourceHandle> enqueue(File path) {
        return enqueue(URI.create(path.getPath()));
    }


    public Future<ResourceHandle> enqueue(String path) {
        return enqueue(URI.create(path));
    }


    /** Enqueue resource to be loaded.
     *
     * You can use this method to queue up resources for asynchronous loading.
     * They can be obtained by calling the get() method on the returned Future.
     *
     * <em>Implementation Note:</em>: Currently this is implemented by creating
     * a FutureTask and calling its run() method. In the future this will
     * probably be pawned off to a background worker thread, when SxE or
     * ResourceManager supports this more directly.
     *
     * @param uri - URI to pass to #load().
     * @return A Future that can obtain the result of loading uri.
     */
    public Future<ResourceHandle> enqueue(URI uri) {
        FutureTask<ResourceHandle> task =
            new FutureTask<ResourceHandle>(new QueuedLoader(uri));
        task.run();
        return task;
    }


}

