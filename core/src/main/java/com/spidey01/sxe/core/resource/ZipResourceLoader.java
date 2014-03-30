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

import com.spidey01.sxe.core.logging.Log;

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.net.URI;


/** Loads an InputStream out of a Zip Archive. */
public class ZipResourceLoader implements ResourceLoader {
    private final static String TAG = "ZipResourceLoader";


    public InputStream getInputStream(File from, File what)
        throws IOException
    {
        return getInputStream(from.getPath(), what.getPath());
    }


    // TODO: normalize to /paths/ and ban retard \paths\; make it a method of Utils.
    public InputStream getInputStream(String from, String what)
        throws IOException
    {
        ZipFile zipFile;
        
        try {
            zipFile = new ZipFile(from);
        } catch (IOException e) {
            throw new IOException("Failed to open "+from, e);
        }

        /* The API borks if we use a leading '/', so take care of it */
        String p = what.startsWith("/") ? what.substring(1)
                                        : what;

        ZipEntry zipEntry = zipFile.getEntry(p);
        if (zipEntry == null) {
            throw new IOException("Zip file "+from+" doesn't contain "+p);
        }

        if (zipEntry.isDirectory()) {
            Log.w(TAG, "We can't use a directory inside a zip file as a resource!");
        }

        InputStream is;
        try {
            is = zipFile.getInputStream(zipEntry);
            /* Instead of one of the documentation exceptions we might just get
             * null in some cases. So we need to test this, as <b>we</b>
             * promise to throw IOException rather than return null!
             */
            if (is == null) {
                throw new IOException("ZipFile#getInputStream returned null!");
            }
        } catch (IOException e) {
            throw new IOException("Failed to load resource", e);
        }

        return is;
    }

}

