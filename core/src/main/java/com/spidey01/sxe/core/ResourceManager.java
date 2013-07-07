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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** Class to manage game resources.
 *
 * Resources to be loaded from storage are identified using Uniform Resource
 * Identifiers (URIs). This means that a resource takes the format of
 * <samp>[scheme][scheme-specific part][//authority][path][?query][#fragment]</samp>.
 *
 * For ease of resource loading, scheme is taken as a way to specify which
 * ResourceLoader should be used to load the URI. For example file://foo or
 * zip://bar. You specify how schemes should be handled by registering
 * instances of ResourceLoader with the ResourceManager#setLoader() instance
 * method. If no scheme is given then default will be used; see below.
 *
 * The authority (or host) portion is used by the ResourceLoader to determine
 * the source to load from. In the case of the zip scheme, we would use a URI
 * like <samp>zip://my.zip/foo/bar</samp> to refer to the file bar in directory
 * foo inside of my.zip; it would be loaded using whatever ResourceLoader is
 * set for "zip".
 *
 * When the scheme is missing it is mapped to the "default" scheme. So a URI of
 * "foo" is treated as "default://foo". This allows mapping paths to whatever
 * the platforms local convention is. By default, default:// refers to file://.
 *
 * Handling of file:// and zip:// will be created by default.
 *
 * TODO: tar://; maybe http://, ftp:// etc.
 *
 * Resources will be searched for in locations registered with #addResourceLocation().
 *
 * @see URI
 */
public class ResourceManager {
    private final static String TAG = "ResourceManager";

    /** Map of loaders to container types, e.g. .zip */
    private Map<String, ResourceLoader> mLoaders = new HashMap<String, ResourceLoader>();

    /** Loader used when there isn't a container */
    private ResourceLoader mDefaultLoader;

    /** Locations to load resources from. */
    private List<String> mSearchLocations = new LinkedList<String>();

    /** Maping of URI's to ResourceHandle's. */
    private Map<URI, ResourceHandle> mHandles = new HashMap<URI, ResourceHandle>();


    public ResourceManager() {
        // Setup standard resource loaders
        mDefaultLoader = new PathResourceLoader();
        mLoaders.put("default", mDefaultLoader);
        mLoaders.put("file", mDefaultLoader);
        mLoaders.put("zip", new ZipResourceLoader());
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
        return mDefaultLoader;
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

        return mLoaders.get(uri.getScheme());
    }


    /** Convenient way of getting the ResourceLoader for a path.
     *
     * @param path - used as a URI.
     * @return ResourceLoader for specified URI or null.
     */
    public ResourceLoader getLoader(String path) {
        try {
            return getLoader(new URI(path));
        } catch (URISyntaxException e) {
            Log.w(TAG, "getLoader(String): URISyntaxException.", e);
            return null;
        }
    }

    
    public void addResourceLocation(File path) {
        addResourceLocation(path.getPath());
    }


    public void addResourceLocation(String path) {
        mSearchLocations.add(path);
    }


    /** Finds the location for URI.
     *
     * Given a URI, determine the associated source path from mSearchLocations.
     * @return path or the empty string.
     */
    protected String findLocation(URI uri) {
        for (String location : mSearchLocations) {
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
        return load(path.getPath());
    }


    public ResourceHandle load(String path) throws IOException {
        try {
            return load(new URI(path));
        } catch(URISyntaxException e) {
            Log.w(TAG, "load(): URISyntaxException.", e);
            return null;
        }
    }


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
        unload(uri.getPath());
    }


    public void unload(String uri) throws IOException {
        try {
            unload(new URI(uri));
        } catch(URISyntaxException e) {
            Log.w(TAG, "unload(): URISyntaxException.", e);
        }
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

}

