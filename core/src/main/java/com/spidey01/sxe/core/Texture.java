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

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.InputStream;
import java.io.IOException;

import java.nio.ByteBuffer;

public class Texture {

    private boolean mIsInitialized;
    private int mTextureId;
    private int mWidth;
    private int mHeight;

    private ByteBuffer mImageData;

    public Texture(InputStream data) throws IOException {
        mImageData = decodePng(data);
    }


    /** Get underlaying OpenGL Texture ID. */
    public int getId() {
        return mTextureId;
    }


    /** Width of this texture in pixels. */
    public int getWidth() {
        return mWidth;
    }


    /** Height of this texture in pixels. */
    public int getHeight() {
        return mHeight;
    }


    public void initialize(OpenGl GL) {
        if (!mIsInitialized) return;

        GL.glEnable(OpenGl.GL_TEXTURE_2D);

        GL.glGenTextures(mImageData);
        mTextureId = mImageData.get(0);

        GL.glBindTexture(OpenGl.GL_TEXTURE_2D, mTextureId);

        GL.glTexImage2D(OpenGl.GL_TEXTURE_2D, 0, OpenGl.GL_RGBA, mWidth, mHeight,
                        0, OpenGl.GL_RGBA, OpenGl.GL_UNSIGNED_BYTE, mImageData);

        // Can we clear() mImageData and set it to null because OpenGL made an
        // internal copy for  it's own business or does OpenGL just take our
        // pointer and assume we promise not to screw with the system memory?

        // shaders and program issues?

        mIsInitialized = true;
    }


    private ByteBuffer decodePng(InputStream in) throws IOException {
        PNGDecoder d = new PNGDecoder(in);

        mWidth = d.getWidth();
        mHeight = d.getHeight();

        // We're assuming 4 bytes per pixel, RGBA
        int bpp = 4;
        int size = bpp * d.getWidth() * d.getHeight();
        ByteBuffer buffer = ByteBuffer.allocateDirect(size);

        d.decode(buffer, (d.getWidth() * bpp), PNGDecoder.Format.RGBA);
        buffer.flip();

        return buffer;
    }
}

