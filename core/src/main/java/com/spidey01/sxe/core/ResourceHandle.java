/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Closeable;

public class ResourceHandle implements Closeable {
    private final static String TAG = "ResourceHandle";

    private final long mRid;
    private final ResourceLoader mLoadedBy;
    private final String mResourcePath;

    private boolean mIsLoaded = false;
    private IOException mFailure = null;

    // Handles to whatever the underlaying resource is.
    // Remember that the Java defaults them to null.
    //
    private BufferedReader mReader;
    private InputStream mInputStream;
    private Texture mTexture;
    private Shader mShader;
    private Font mFont;


    public ResourceHandle(ResourceLoader loader, long rid, String resource) {
        mLoadedBy = loader;
        mRid = rid;
        mResourcePath = resource;
    }


    public boolean isLoaded() {
        return false;
    }


    public ResourceLoader getLoader() {
        return mLoadedBy;
    }


    public BufferedReader asReader() throws IOException {
        if (mReader == null) {
            mReader = new BufferedReader(new InputStreamReader(getLoader().getInputStream(mResourcePath)));
        }
        return mReader;
    }


    public InputStream asInputStream() throws IOException {
        if (mInputStream == null) {
            mInputStream = getLoader().getInputStream(mResourcePath);
        }
        return mInputStream;
    }


    public Texture asTexture() throws IOException {
        if (mTexture == null) {
            throw new IOException("Blarg");
        }
        return mTexture;
    }

    public Shader asShader(Shader.Type type) throws IOException {
        if (mShader == null) {
            mShader = new Shader(asReader(), type);
            close();
        }
        return mShader;
    }


    public Font asFont() throws IOException {
        if (mFont == null) {
            throw new IOException("Nadda typeface.");
        }
        return mFont;
    }


    public void close() throws IOException {
        if (mInputStream != null) {
            mInputStream.close();
        }
        if (mReader != null) {
            mReader.close();
        }
    }

}

