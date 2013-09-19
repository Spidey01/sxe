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

    private Bitmap mBitmap;

    public Texture(Bitmap data) {
        mBitmap = data;
    }


    /** Get underlaying OpenGL Texture ID. */
    public int getId() {
        return mTextureId;
    }


    /** Width of this texture in pixels. */
    public int getWidth() {
        return mBitmap.getWidth();
    }


    /** Height of this texture in pixels. */
    public int getHeight() {
        return mBitmap.getHeight();
    }


    public void initialize(OpenGL GL) {
        if (!mIsInitialized) return;

        GL.glEnable(OpenGL.GL_TEXTURE_2D);

        GL.glGenTextures(mBitmap.getImageData());
        mTextureId = mBitmap.getImageData().get(0);

        bind(GL);

        GL.glTexImage2D(OpenGL.GL_TEXTURE_2D, 0, OpenGL.GL_RGBA, mBitmap.getWidth(), mBitmap.getHeight(),
                        0, OpenGL.GL_RGBA, OpenGL.GL_UNSIGNED_BYTE, mBitmap.getImageData());

        // Can we clear() mImageData and set it to null because OpenGL made an
        // internal copy for  it's own business or does OpenGL just take our
        // pointer and assume we promise not to screw with the system memory?

        // shaders and program issues?

        mIsInitialized = true;
    }


    public void bind(OpenGL GL) {
        GL.glBindTexture(OpenGL.GL_TEXTURE_2D, mTextureId);
    }
}

