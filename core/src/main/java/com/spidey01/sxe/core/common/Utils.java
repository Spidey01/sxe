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

package com.spidey01.sxe.core.common;

import com.spidey01.sxe.core.config.Settings;
import com.spidey01.sxe.core.logging.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    private static final String TAG = "Utils";


    /** Slurps a File into a String. */
    public static String slurp(File source) throws IOException {
        return Utils.slurp(new BufferedReader(new FileReader(source)));
    }

    /** Slurps a File into a String. */
    public static String slurp(String path) throws IOException {
        return Utils.slurp(new BufferedReader(new FileReader(path)));
    }

    /** Slurps a InputStream into a String. */
    public static String slurp(InputStream source) throws IOException {
        return Utils.slurp(Buffers.makeReader(source));
    }

    /** Slurps a Reader into a String. */
    public static String slurp(BufferedReader source) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = source.readLine()) != null) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }


    public static String join(String[] array, char separator) {
        StringBuilder buf = new StringBuilder(array.length * 16);
        for (int i=0; i < array.length; ++i) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }


    // conversions to unsigned integers.
    // http://jessicarbrown.com/resources/unsignedtojava.html

    public static short uint8(byte i) {
        return (short)(i & 0xff);
    }

    public static int uint16(short i) {
        return i & 0xffff;
    }

    public static long uint32(int i) {
        return i & 0xffffffffL;
    }


    /** Helper for merging Settings instances.
     *
     * Or basically, a implement your Settings.merge() method free.
     */
    public static void merge(Settings parent, Settings child) {
        assert parent != null : "parent=null";
        assert child != null : "child=null";

        for (String key : child.keys()) {
            parent.setString(key, child.getString(key));
        }
    }


    /** Tokenize quoted strings similar to code.
     *
     * <pre>
     *      foo bar     == { "foo", "bar" }
     *      'foo bar'   == { "foo bar" }
     *      "foo bar"   == { "foo bar" }
     *      \"foo bar\" == { "\"foo", "bar\"" }
     *      `foo bar`   == { "foo bar" }
     * </pre>
     *
     * Adapted from http://stackoverflow.com/a/7804472/352455 because Java
     * regexp usage in String makes my Perl brain hurt.
     */
    public static String[] tokenize(String s) {
        String q;
        List<String> list = new ArrayList<String>();
        /*
         * For the less regexp literate:
         *
         *      (           -> start grouping.
         *      [^\"'`]     -> not ', ", ` quote char.
         *      \\S*        -> >= 1 non space char.
         *      |           -> or
         *      (           -> Another grouping (so we can know what to replace).
         *      [\"'`]      -> see above
         *      )           -> end of another grouping.
         *      .+?         -> whatever until next thingy.
         *      [\"'`]      -> see above
         *      )           -> end grouping
         *      \\s*"
         */
        Matcher m = Pattern.compile("([^\"'`]\\S*|([\"'`]).+?[\"'`])\\s*").matcher(s);
        while (m.find()) {
            // Log.d(TAG, "group 1:", m.group(1));
            // Log.d(TAG, "group 2:", m.group(2));
            q = m.group(2);
            list.add(q == null ? m.group(1) : m.group(1).replace(q, ""));
        }
        return list.toArray(new String[list.size()]);
    }
}

