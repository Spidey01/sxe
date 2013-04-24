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
 * The resource path defines which sources are available to the VFS. These can
 * be anything suitable for ResourceManager.load() and will be used as a prefix
 * to path look ups in the Virtual File System. Assuming the necessary
 * ResourceLoader's and data are present, if our search path is this:
 *
 *  <code>new String[]{ "debug", "pak0.zip:", "pak1.zip:" }</code>
 *
 *  Then trying to resolve "vfs:/weapons/textures/rocket.png" will look for the
 *  following files: <ol>
 *      <li>debug/weapons/textures/rocket.png</li>
 *      <li>pak0.zip:/weapons/textures/rocket.png</li>
 *      <li>pak1.zip:/weapons/textures/rocket.png</li>
 *  </ol>
 *  And the first one found will be returned by getInputStream().
 *
 *  The embedding of colons as needed, is because the search path is literally
 *  uesd as a prefix for invoking the associated ResourceManager. No intimate
 *  knowledge of the ResourceManager is needed, and it need not be the same
 *  manager as VirtualFileSystemLoader the is registered with. Thus this
 *  ResourceLoader does not know how to tell if an entry in the resource path
 *  is a container or a directory/file name. This is both a feature and a bug,
 *  in favour of Keeping It Simple Stupid.
 */
public class VirtualFileSystemLoader implements ResourceLoader {
    private String[] mResourcePath;
    private final ResourceManager mRes;
    private final static String TAG = "VirtualFileSystemLoader";

    /** Initialize a ResourceLoader for a virtual resource file system.
     *
     * @param boss the ResourceManager used for loading. We need not be registered with it.
     * @param resourcePath the sequence to look up resources in.
     */
    public VirtualFileSystemLoader(ResourceManager boss, String[] resourcePath) {
        mRes = boss;
        mResourcePath = resourcePath;
    }
    
    public InputStream getInputStream(File path)
        throws IOException
    {
        return getInputStream(path.toString());
    }

    public InputStream getInputStream(String path)
        throws IOException
    {
        // converts vfs:path to path.
        String vpath = path.substring(path.indexOf(":")+1);
        InputStream is = null;

        for (String p : mResourcePath) {
            Log.v(TAG, "Trying \""+p+vpath+"\" for \""+path+"\"");
            // where p is sth like "pak0.zip:" or "assets".
            is = mRes.get(mRes.load(p + vpath));
            if (is != null) {
                return is;
            }
        }

        throw new IOException("\""+vpath+"\" not found in the vfs of "+this);
    }
}


