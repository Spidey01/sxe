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

package com.spidey01.sxe.android.common;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.ResourceLoader;

import android.content.res.AssetManager;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

/** Loads an InputStream through the Android AssetManager. */
public class AssetLoader implements ResourceLoader {
    private final static String TAG = "AssetLoader";
    private AssetManager mAssetManager;

    public AssetLoader(AssetManager assetManager) {
        mAssetManager = assetManager;
    }


    /**
     * @param path Note that since the part after 'container:' is handed off to
     *             AssetManager, the path on the right hand side of the colon
     *             should be relative to the 'assets' folder in your APK. I.e.
     *             if you want "/assets/foo.txt" inside "your-app.apk", you
     *             want path to be "container:foo.txt". That's just how Android
     *             works :/.
     *
     * @param mode The mode for AssetManager.open().
     */
    public InputStream getInputStream(String path, int mode)
        throws IOException
    {
        String assetPath = path.substring(path.indexOf(":")+1);

        return mAssetManager.open(assetPath, mode);
    }

    /// Need to fix and test this later.

    public InputStream getInputStream(File from, File what) throws IOException {
        return getInputStream(from.getPath(), what.getPath());
    }
    public InputStream getInputStream(String from, String what) throws IOException {
        // return getInputStream(path, AssetManager.ACCESS_UNKNOWN);
        return null;
    }
}


