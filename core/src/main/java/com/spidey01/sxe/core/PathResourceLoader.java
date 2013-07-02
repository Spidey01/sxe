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
import java.net.URI;

/** Loads an InputStream out of a file path */
public class PathResourceLoader implements ResourceLoader {
    private final static String TAG = "PathResourceLoader";

    public InputStream getInputStream(File path)
        throws IOException
    {
        return new FileInputStream(path);
    }


    public InputStream getInputStream(String path)
        throws IOException
    {
        return new FileInputStream(path);
    }


    public InputStream getInputStream(URI uri)
        throws IOException
    {
        return null;
    }
    //-------------------

    public InputStream getInputStream(File from, File what)
        throws IOException
    {
        return new FileInputStream(new File(from, what.getPath()));
    }


    public InputStream getInputStream(String from, String what)
        throws IOException
    {
        return new FileInputStream(new File(from, what));
    }
}


