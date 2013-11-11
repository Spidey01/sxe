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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    private static final String TAG = "Utils";

    // Used by getUserDir() to memorize it's return value.
    private static String sUserDir;

    // PLATFORM_NAME?
    public static final String PLATFORM_ARCHITECTURE = System.getProperty("os.arch");
    public static final String PLATFORM_VERSION = System.getProperty("os.name")+" "+System.getProperty("os.version");


    /** InputStream to something with .readLine() :-).
     *
     * Often I need to deal with InputStream but of course, all I reallly want
     * to do is get data out of it line by line.
     */
    public static BufferedReader makeBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in));
    }

    public static BufferedReader makeBufferedReader(File file) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    /* XXX clean up as necessarily.
    public static Shader.Type resourceTypeToShaderType(Resource.Type type) {
        return type == Resource.Type.VERTEX_SHADER ? Shader.Type.VERTEX
            : Shader.Type.FRAGMENT;
    }

    public static Resource.Type shaderTypeToResourceType(Shader.Type type) {
        return type == Shader.Type.VERTEX ? Resource.Type.VERTEX_SHADER
            : Resource.Type.FRAGMENT_SHADER;
    }

    public static Resource.Type getShaderResourceType(String fileName) {
        if (fileName.endsWith(".vert")) {
            return Resource.Type.VERTEX_SHADER;
        } else if (fileName.endsWith(".frag")) {
            return Resource.Type.FRAGMENT_SHADER;
        }
        throw new RuntimeException("Unknown shader type for "+fileName);
    }

    public static Shader.Type getShaderType(String fileName) {
        if (fileName.endsWith(".vert")) {
            return Shader.Type.VERTEX;
        } else if (fileName.endsWith(".frag")) {
            return Shader.Type.FRAGMENT;
        }
        throw new RuntimeException("Unknown shader type for "+fileName);
    }
    */

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
        return Utils.slurp(Utils.makeBufferedReader(source));
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


    /** Get the users personal directory.
     *
     * This is assumed to be HOME or USERPROFILE, which ever is found first.
     */
    public static String getUserDir() {
        if (sUserDir == null) {
            String e = System.getenv("HOME");
            if (e == null) {
                e = System.getenv("USERPROFILE");
                if (e == null) {
                    throw new
                        RuntimeException("Can't determine users home directory.");
                }
            }
            sUserDir = e;
        }

        return sUserDir;
    }

    /** Return the systems bit bucket as a File. */
    public static File getBitBucketFile() {
        /*
        String[] bitBuckets = { "/dev/null", "nul" };

        for (String p : bitBuckets) {
            File device = new File(p);
            if (!device.exists()) {
                continue;
            }
            return device;
        }
        throw new RuntimeException("Can't find a bit bucket.");
        */
        /********** I HATE WINDOWS **********/
        /**
         * Just return /dev/null because Windows/Java is dumb and won't
         * File#exists() for nul. So just assume that the user doesn't have a
         * \dev\null on the current directory. Bastards!
         */
        return new File("/dev/null");
    }

    /** Return the path to the systems bit bucket. */
    public static String getBitBucketPath() {
        return getBitBucketFile().getPath();
    }


    /** Search list of directories for a file.
     *
     * @param dirs The list of directories to search.
     * @param name File or directory to find in dirs.
     *
     * @return Absolute path to name if found; else null.
     */
    public static String find(String[] dirs, String name) {
        for (String d : dirs) {
            File p = new File(d, name);
            if (p.isFile() || p.isDirectory()) {
                return p.getAbsolutePath();
            }
        }
        return null;
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


    /** Utilities for working with buffers.
     *
     * Buffers are created with createByteBuffer in the native byte order (BIG
     * or little). Direct buffers are allocated for performance. Size of the
     * buffer is based on the size of Java datatype being allocated, e.g.
     * Utils.Buffers.createIntBuffer(2) allocates a buffer for 2 Integers which
     * requires 8 Bytes of space.
     */
    public static class Buffers {

        public static ByteBuffer createByteBuffer(int size) {
            return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
        }

        public static DoubleBuffer createDoubleBuffer(int size) {
            return createByteBuffer(size << 3).asDoubleBuffer();
        }

        public static FloatBuffer createFloatBuffer(int size) {
            return createByteBuffer(size << 2).asFloatBuffer();
        }

        public static IntBuffer createIntBuffer(int size) {
            return createByteBuffer(size << 2).asIntBuffer();
        }
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

