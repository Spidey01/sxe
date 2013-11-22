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

import com.spidey01.sxe.core.common.Utils;


import java.io.*;
import java.util.*;

/** Pure Java implementation of Font using TrueType.
 *
 * There is no assurance of completeness or even sanity within. It's current purpose is
 * ease of use during testing with hope of being useful later.
 *
 * For performance reasons it may be better to create platform specific
 * wrappers (AWT, Android). Platforms should have code that hooks into native
 * C/C++ code. This class tries to create a "Works just well enough" solution,
 * suitable for running the demos.
 *
 * Some documentation can be found online from Apple and Microsoft.  Apache
 * PDFBox (TTFParser.java) and freetype-go (truetype.go) are also useful
 * examples of how to process TTF files.
 *
 * <ul>
 *  <li>https://developer.apple.com/fonts/TTRefMan/RM06/Chap6.html</li>
 *  <li>http://www.microsoft.com/typography/otspec/</li>
 *  <li>http://svn.apache.org/repos/asf/pdfbox/fontbox/branches/before-package-rename/src/org/fontbox/ttf/TTFParser.java</li>
 *  <li>https://code.google.com/p/freetype-go/source/browse/freetype/truetype/truetype.go</li>
 * </ul>
 *
 * This class is written using the above examples to decipher enough of the
 * format to run demos.
 *
 * Use in production code at your own risk.
 * </ul>
 */
public class TrueTypeFont implements Font {
    private final static String TAG = "TrueTypeFont";

    private int mVersionNumber;
    private String mVersionString;
    private DataInputStream mStream;

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


        /** Read one table from data stream. */
        protected static Table from(DataInputStream data) throws IOException {
            //
            // These are supposed to be uint32/4 bytes each afaict but I don't
            // know a better way of reading these at the moment.
            //
            // TODO: calculating + verifying checksum.
            //

            byte[] nameBuffer = new byte[4];
            data.read(nameBuffer);
            String name = new String(nameBuffer);
            // String name = data.readString(4);
            Log.v(TAG, "TTF Table name: "+name);


            long checksum = Utils.uint32(data.readInt());
            Log.v(TAG, "TTF Table "+name+" checksum: "+checksum);

            long offset = Utils.uint32(data.readInt());
            Log.v(TAG, "TTF Table "+name+" offset: "+offset);

            long length = Utils.uint32(data.readInt());
            Log.v(TAG, "TTF Table "+name+" length: "+length);

            return new Table(name, checksum, offset, length);
        }

    }


    // see: https://developer.apple.com/fonts/TTRefMan/RM06/Chap6.html#Overview
    private Table fftm, gdef, gpos, gsub, os2, cmap, cvt, fpgm, gasp, glyf, head, hhea, hmtx, loca, maxp, name, post, prep;


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

    /** Supposedly required. */
    private static String[] sRequiredTables = {
        "cmap", // character to glyph mapping
        "glyf", // glyph data
        "head", // font header
        "hhea", // horizontal header
        "hmtx", // horizontal metrics
        "loca", // index to location
        "maxp", // maximum profile
        "name", // naming
        "post", // PostScript
    };


    public TrueTypeFont(DataInputStream data) throws IOException {
        parseFontDirectory(data);
        parseTableDirectory(data);

        for (String name : sRequiredTables) {
            if (!mOffsetMap.containsKey(name)) {
                throw new RuntimeException("Required table "+name+" not found in directory.");
            }
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

    public String getVersionAsString() {
        return mVersionString;
    }

    public float getVersionAsFloat() {
        return mVersionNumber;
    }

    /** Parse the font directory.
     *
     * Font Directory must be the first table. It provides an offset subtable
     * and table directory.
     *
     * <pre>
     *  offset subtable - number of tables and offset information.
     *  table directory - entries for each table in font.
     * </pre>
     *
     */
    private void parseFontDirectory(DataInputStream data) throws IOException {
        try {
            parseOffsetTable(data);
            parseTableDirectory(data);
        } catch (IOException e) {
            Log.e(TAG, "Error parsing TTF Font Directory.", e);
            throw e;
        }
    }

    // These are represented as uint16.

    /** Number of tables in font. */
    private int mNumTables;
    /** Largest number of items easily searched. */
    private int mSearchRange;
    /** Loop iterations needed to cover the search range. */
    private int mEntrySelector;
    /** Shift value for search ranges. */
    private int mRangeShift;

    /** Maps table names to the offset tables. */
    private Map<String, Table> mOffsetMap = new HashMap<String, Table>();


    /** Number of tables and their offsets.
     */
    private void parseOffsetTable(DataInputStream data) throws IOException {
        mVersionNumber = data.readUnsignedShort() + data.readUnsignedShort();

        // maybe long VersionNumber = (data.readUnsignedShort() << 16) | (data.readUnsignedShort() & 0xffff);
        // http://stackoverflow.com/questions/1294649/cleanest-way-to-combine-two-shorts-to-an-int

        mVersionString = String.valueOf(mVersionNumber);
        Log.i(TAG, "TTF Version: "+mVersionString);
        /*
        if (!mVersionString.equals("1.0")) {
            // this could be other values than 1, if the font comes from other systems, like Mac.
            throw new RuntimeException("Unsupported format version: "+mVersionString);
        }
        */

        mNumTables = data.readUnsignedShort();
        Log.v(TAG, "numTables = "+mNumTables);
        mSearchRange = data.readUnsignedShort();
        Log.v(TAG, "searchRange = "+mSearchRange);
        mEntrySelector = data.readUnsignedShort();
        Log.v(TAG, "entrySelector = "+mEntrySelector);

        mRangeShift = data.readUnsignedShort();
        Log.v(TAG, "rangeShift = "+mRangeShift);
        if (mRangeShift != (mNumTables * 16 - mSearchRange)) {
            throw new RuntimeException("Bad rangeShift field.");
        }
    }

    // These are represented as uint32.


    /** ...
     *
     * Entries must be sorted in ascending order by tag.
     * Each table in the font file must have it's own directory.
     */
    private void parseTableDirectory(DataInputStream data) throws IOException {
        for (int i=0; i < mNumTables; ++i) {
            Table t = Table.from(data);
            mOffsetMap.put(t.name, t);
        }
    }

}

