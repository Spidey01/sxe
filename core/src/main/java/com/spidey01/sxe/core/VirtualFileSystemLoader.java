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

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

import java.util.*;

/** Implements a virtual file system for ResourceLoader.
 *
 * This makes a path for {@link ResourceManager#load()} like
 * <code>vfs:path</code> able to query multiple resources based on the
 * available loaders.
 *
 */
public class VirtualFileSystemLoader implements ResourceLoader {
    /*
     * This list is kept sorted in the resolution order.
     */
    private LinkedList<String> mContainers = new LinkedList<String>();
    /*
     * This is a list of loaders to always ignore. Principally so trying to
     * resolve a loader like us, doesn't go bongo.
     */
    private final LinkedList<String> mSkipList = new LinkedList<String>();
    private final ResourceManager mRes;
    private final static String TAG = "VirtualFileSystemLoader";

    /**
     *
     * @param name The container name we are stored in boss as. Failure to set
     *             this may lead to bad mojojo behaviour.
     * @param boss the ResourceManager we are being used with.
     */
    public VirtualFileSystemLoader(String name, ResourceManager boss) {
        mRes = boss;
        mSkipList.add(name);
    }

    public VirtualFileSystemLoader(Collection<String> names, ResourceManager boss) {
        mRes = boss;
        mSkipList.addAll(names);
    }

    public InputStream getInputStream(File path)
        throws IOException
    {
        return getInputStream(path.toString());
    }

    public InputStream getInputStream(String path)
        throws IOException
    {
        for (String key : mRes.getLoaders().keySet()) {
            if (mSkipList.contains(key)|| mContainers.contains(key)) {
                continue;
            }

            mContainers.add(key);
            Log.i(TAG, key+" added to "+this);
        }

        String vfsPath = null;
        ResourceLoader l = null;
        InputStream is = null;
        for (String key : mContainers) {
            // try getting a hold of it.
            vfsPath = path.substring(path.indexOf(":")+1);
            Log.d(TAG, "Checking loader "+key+" for "+vfsPath);
            l =  mRes.getLoaders().get(key);
            is = l.getInputStream(vfsPath);
            if (is != null) {
                break;
            }
        }

        return is;
        // throw new IOException();
    }

    private void defaultContainers() {
        mContainers.add("default");
        // mContainers.add(".zip");

        // for android
        mContainers.add("apk");
    }

}



