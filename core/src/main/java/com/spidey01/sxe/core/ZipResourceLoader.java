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

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

/** Loads an InputStream out of a Zip Archive */
public class ZipResourceLoader implements ResourceLoader {
    private final static String TAG = "ZipResourceLoader";

    public InputStream getInputStream(File path)
        throws IOException
    {
        return getInputStream(path.getPath());
    }

    public InputStream getInputStream(String path)
        throws IOException
    {
        String zipPath = path.substring(0, path.lastIndexOf(":"));
        ZipFile zipFile = null;
        
        try {
            zipFile = new ZipFile(zipPath);
        } catch (IOException e) {
            throw new IOException("Failed to open "+zipPath, e);
        }

        String pathInZip = path.substring(path.lastIndexOf(":")+1);

        /* The API borks if we use a leading '/', so take care of it */
        String p = pathInZip.startsWith("/") ? pathInZip.substring(1)
                                             : pathInZip;

        ZipEntry zipEntry = zipFile.getEntry(p);
        if (zipEntry == null) {
            throw new IOException("Zip file "+zipPath+" doesn't contain "+pathInZip);
        }

        if (zipEntry.isDirectory()) {
            Log.w(TAG, "We can't use a directory inside a zip file as a resource!");
        }

        InputStream is = null;
        try {
            is = zipFile.getInputStream(zipEntry);
            /* Instead of one of the documentation exceptions we might just get
             * null in some cases. So we need to test this, as <b>we</b>
             * promise to throw IOException!
             */
            if (is == null) {
                throw new IOException("Zipfile#getInputStream returned null!");
            }
        } catch (IOException e) {
            throw new IOException("Failed to load resource", e);
        }

        return is;
    }
}

