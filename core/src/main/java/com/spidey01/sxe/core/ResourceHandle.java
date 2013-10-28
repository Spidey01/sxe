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

import com.spidey01.sxe.core.gl.Shader;
import com.spidey01.sxe.core.gl.OpenGLES20;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

public class ResourceHandle implements Closeable {
    private final static String TAG = "ResourceHandle";

    private final URI mURI;

    //
    // Handle to whatever the underlaying resource is.
    //
    // I assume the JVM/Bytecode has to allocate the extra pointers under the
    // hood, so perhaps it might be more worth while (time vs space) to just
    // use an Object and casting rather than fields for each possible resource.
    //
    private Object mData;
    private InputStream mInputStream;

    private enum Type {
        RAW,
        READER,
        TEXTURE,
        SHADER,
    };
    Type mType = Type.RAW;


    public ResourceHandle(URI uri, InputStream stream) {
        mURI = uri;
        mInputStream = stream;
    }


    public URI getURI() {
        return mURI;
    }


    public BufferedReader asReader() throws IOException {
        if (mData == null && checkType(Type.READER)) {
            mType = Type.READER;
            mData = new BufferedReader(new InputStreamReader(mInputStream));
        }
        return (BufferedReader)mData;
    }


    public InputStream asInputStream() {
        return mInputStream;
    }


    public Texture asTexture() throws IOException {
        if (mData == null && checkType(Type.TEXTURE)) {
            mData = new Texture(new PngBitmap(mInputStream));
            close();
        }
        return (Texture)mData;
    }

    public Shader asShader(Shader.Type type) throws IOException {
        if (mData == null && checkType(Type.SHADER)) {
            mData = new Shader(asReader(), type);
            close();
        }
        return (Shader)mData;
    }


    public void close() throws IOException {
        if (mInputStream != null) {
            mInputStream.close();
        }
    }

    private boolean checkType(Type supposed) {
        return mType == supposed || mType == Type.RAW;
    }
}

