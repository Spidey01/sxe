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
import java.io.IOException;

public class FileResource implements Resource {

    protected final Type mType;
    protected final String mFileName;
    protected InputStream mInputStream;
    protected final ResourceLoader mLoader;
    private final static String TAG = "FileResource";

    /** Resource from a plane file.
     *
     * @param Type either Resource.Type.TEXT_FILE or (someday) Resource.Type.BINARY_FILE.
     */
    public FileResource(Type type, String fileName, ResourceLoader loader)
    {
        mType = type;
        mFileName = fileName;
        mInputStream = null;
        mLoader = loader;
    }

    public boolean load() {
        try {
            mInputStream = mLoader.getInputStream(mFileName);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "load() failure. mFileName="+mFileName);
        }
        return false;
    }

    public boolean unload() {
        try {
            mInputStream.close();
            return true;
        } catch (IOException e) {
            Log.w(TAG, "failed closing mInputStream.", e);
        }
        return false;
    }

    public boolean isLoaded() {
        return mInputStream == null;
    }

    public String getFileName() {
        return mFileName;
    }

    public InputStream getInputStream() {
        return mInputStream;
    }

    public Object getObject() {
        return mInputStream;
    }

    public ResourceLoader getLoader() {
        return mLoader;
    }
}

