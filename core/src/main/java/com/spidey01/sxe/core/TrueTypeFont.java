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

import java.io.*;
import java.util.*;

public class TrueTypeFont {
    private final static String TAG = "TrueTypeFont";

    private float mVersionNumber = 0;
    private final String mVersionString;

    /* Wrapper for the fields of the font directory. */
    private static class Table {
        private static final String TAG = "TrueTypeFont.Table";
        public final String name;
        public final long checksum;
        public final long offset;
        public final long length;

        Table(String name, long checksum, long offset, long length) {
            this.name = name;
            this.checksum = checksum;
            this.offset = offset;
            this.length = length;
        }

        protected static Table from(DataInputStream data) throws IOException {
            byte[] nameBuffer = new byte[4];
            data.read(nameBuffer);
            String name = new String(nameBuffer);
            // String name = data.readString(4);
            Log.v(TAG, "TTF Table name: "+name);

            //
            // the rest of these are supposed to be uint32 afaict but I
            // don't know a better way of reading these at the moment.
            //
            // http://jessicarbrown.com/resources/unsignedtojava.html
            //

            long checksum = data.readInt() & 0xffffffffL;
            Log.v(TAG, "TTF Table "+name+" checksum: "+checksum);

            long offset = data.readInt() & 0xffffffffL;
            Log.v(TAG, "TTF Table "+name+" offset: "+offset);

            long length = data.readInt() & 0xffffffffL;
            Log.v(TAG, "TTF Table "+name+" length: "+length);

            return new Table(name, checksum, offset, length);
        }
    }

    public TrueTypeFont(DataInputStream data) throws IOException {
        //
        // Parse the font directory.
        //

        // Get the version/scalar type thingy
        try {
            mVersionNumber = data.readUnsignedShort() + data.readUnsignedShort();

            // maybe long VersionNumber = (data.readUnsignedShort() << 16) | (data.readUnsignedShort() & 0xffff);
            // http://stackoverflow.com/questions/1294649/cleanest-way-to-combine-two-shorts-to-an-int
        } catch (IOException e) {
            throw new IOException("Couldn't read TTF version.", e);
        }
        mVersionString = String.valueOf(mVersionNumber);
        Log.i(TAG, "TTF Version: "+mVersionString);

        // Parse the offset subtable (https://developer.apple.com/fonts/TTRefMan/RM06/Chap6.html)
        int numberOfTables = 0;
        try {
            numberOfTables = data.readUnsignedShort();
        } catch(IOException e) {
            throw new IOException("Couldn't read number of tables.", e);
        }

        try {
            int searchRange = data.readUnsignedShort();
            int entrySelector = data.readUnsignedShort();
            int rangeShift = data.readUnsignedShort();
        } catch(IOException e) {
            throw new IOException("Error reading top matter.", e);
        }

        try { 
            for (int i=0; i < numberOfTables; ++i) {
                Table.from(data);
            }
        } catch (IOException e) {
            throw new IOException("Error parsing TTF font directory.", e);
        }

        try { // read random junk
            for (int i=0; i < 10; i++) {
                int p = data.readUnsignedShort();
                Log.i(TAG, "readshit: "+p);
            }
        } catch(IOException e) {
            throw new IOException("bad I/O", e);
        }

    }

    /*
    private static int readUnsignedInt(DataInputStream data) {
        byte[] input = new byte[4];
        data.read(input);

        // Does TTF use big or little endian? (this is big.)
        ByteBuffer buffer = ByteBuffer.wrap(input);
        int signed = buffer.getInt();
    }
    */

    public static TrueTypeFont from(File path) throws IOException {
        return TrueTypeFont.from(path.getPath());
    }

    public static TrueTypeFont from(String path) throws IOException {
        Log.v(TAG, "Loading TTF Font from "+path);
        return TrueTypeFont.from(new FileInputStream(path));
    }

    public static TrueTypeFont from(InputStream stream) throws IOException {
        return new TrueTypeFont(new DataInputStream(stream));

    }

    public String getVersionAsString() {
        return mVersionString;
    }

    public float getVersionAsFloat() {
        return mVersionNumber;
    }
}

