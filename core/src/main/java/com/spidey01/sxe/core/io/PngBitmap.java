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

package com.spidey01.sxe.core.io;

import com.spidey01.sxe.core.graphics.Bitmap;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.io.InputStream;

import java.nio.ByteBuffer;

public class PngBitmap implements Bitmap {
    private final static String TAG = "PngBitmap";

    private final int mWidth;
    private final int mHeight;
    private final int mBytesPerPixel;
    private ByteBuffer mImageData;

    /* Unused.
    public PngBitmap() {
    }
    */


    public PngBitmap(InputStream source) throws IOException {
        PNGDecoder d = new PNGDecoder(source);

        mWidth = d.getWidth();
        mHeight = d.getHeight();


        // We're assuming 4 bytes per pixel, RGBA
        int bpp = mBytesPerPixel = 4;
        int size = bpp * d.getWidth() * d.getHeight();
        mImageData = ByteBuffer.allocateDirect(size);

        d.decode(mImageData, (d.getWidth() * bpp), PNGDecoder.Format.RGBA);
        mImageData.flip();
    }


    public ByteBuffer getImageData() {
        return mImageData;
    }


    public int getWidth() {
        return mWidth;
    }


    public int getHeight() {
        return mHeight;
    }


    public int getBytesPerPixel() {
        return mBytesPerPixel;
    }

}

