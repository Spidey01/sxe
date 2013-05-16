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

public class Utils {

    private final static String TAG = "Utils";

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
        String e = System.getenv("HOME");
        if (e == null) {
            e = System.getenv("USERPROFILE");
            if (e == null) {
                throw new
                    RuntimeException("Can't determine users home directory.");
            }
        }
        return e;
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

    /** Helper for XDG Base Directory Specification (version 0.7).
     *
     * Environment variables like XDG_WHAT_EVER will generally be gotten with
     * <code>getWhatEver()</code>.  To find a file/spec located in one of these
     * directories, you will usually want <code>getWhatEver("your/path")</code>.
     */
    public static class Xdg {

        /** Get XDG_DATA_HOME. */
        public static String getDataHome() {
            String e = System.getenv("XDG_DATA_HOME");

            if (e != null) {
                return e;
            }

            return Utils.getUserDir()+"/.local/share";
        }

        /** Get XDG_CONFIG_HOME. */
        public static String getConfigHome() {
            String e = System.getenv("XDG_CONFIG_HOME");

            if (e != null) {
                return e;
            }

            return Utils.getUserDir()+"/.config";
        }

        /** Get XDG_CACHE_HOME. */
        public static String getCacheDir() {
            String e = System.getenv("XDG_CACHE_HOME");

            if (e != null) {
                return e;
            }

            return Utils.getUserDir()+"/.cache";
        }

        /** Get XDG_RUNTIME_DIR. */
        public static String getRuntimeDir() {
            String e = System.getenv("XDG_RUNTIME_DIR");

            if (e != null) {
                return e;
            }

            // We more or less would want File.createTempFile, but for a directory.
            // The delete->mkdir trick is obviously a race condition.
            //
            throw new UnsupportedOperationException("No default");
        }

        /** Get XDG_DATA_DIRS. */
        public static String[] getDataDirs() {
            String e = System.getenv("XDG_DATA_DIRS");

            if (e != null) {
                return e.split(":");
            }

            return new String[]{
                "/usr/local/share/",
                    "/usr/share/"
            };
        }

        /** Get XDG_CONFIG_DIRS. */
        public static String[] getConfigDirs() {
            String e = System.getenv("XDG_CONFIG_DIRS");

            if (e != null) {
                return e.split(":");
            }

            return new String[]{ "/etc/xdg" };
        }

        public static String getDataHomeDir(String relative) {
            return getDataHome()+"/"+relative;
        }

        public static String getConfigHomeDir(String relative) {
            return getConfigHome()+"/"+relative;
        }

        public static String getCacheDir(String relative) {
            return getCacheDir()+"/"+relative;
        }

        public static String getDataDir(String relative) {
            return Xdg.pick(getDataDirs(), relative);
        }

        public static String getConfigDir(String relative) {
            return Xdg.pick(getConfigDirs(), relative);
        }

        private static String pick(String[] dirs, String relative) {
            for (String d : dirs) {
                File p = new File(d+"/"+relative);
                if (p.isFile() || p.isDirectory()) {
                    return p.getAbsolutePath();
                }
            }
            return null;
        }
    }
}

