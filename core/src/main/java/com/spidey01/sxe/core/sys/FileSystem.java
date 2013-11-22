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

package com.spidey01.sxe.core.sys;

import com.spidey01.sxe.core.Log;

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


public class FileSystem {


    // Used by getUserDir() to memorize it's return value.
    private static String sUserDir;


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


    /** Return the systems bit bucket as a File.
     *
     * Currently this always returns "/dev/null" because Java/Windows always
     * returns false for File.exists() on the nul special file.
     */
    public static File getBitBucketFile() {
        /********** I HATE WINDOWS **********/
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

}

