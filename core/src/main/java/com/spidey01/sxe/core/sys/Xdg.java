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

package com.spidey01.sxe.core.sys;

/** Helper for XDG Base Directory Specification (version 0.7).
 *
 * Environment variables referenced by the specification are exported as static
 * fields, along with a few helpers. To find a file/dir located in one of these
 * directories, use one of the provided static methods.
 *
 * No guarantee is made that the directories exist. Only that the values are
 * appropriate with regard to the specification.
 */
public class Xdg {
    private static final String TAG = "Xdg";

    /** Used by static initializers. */
    private static String e;

    /** Application installation prefix.
     *
     * This environment variable should be set by the wrapper scripts installed
     * with the application.  If you use the Gradle application plugin like the
     * demos do, this is taken care of for you.
     */
    public static String APP_HOME = System.getenv("APP_HOME");

    /** What passes for the useres home directory.
     *
     * @see FileSystem.getUserDir
     */
    public static String HOME = FileSystem.getUserDir();

    /** Alias for HOME. */
    public static String USER_DIR = HOME;


    /** Base directory for user-specific data files. */
    public static String XDG_DATA_HOME = System.getenv("XDG_DATA_HOME");
    static {
        if (XDG_DATA_HOME == null) {
            XDG_DATA_HOME = USER_DIR+"/.local/share";
        }
    }


    /** Preference-ordered set of base directories to search for data files. 
     *
     * This is the system search path version of XDG_DATA_HOME.
     */
    public static String[] XDG_DATA_DIRS;
    static {
        e = System.getenv("XDG_DATA_DIRS");
        XDG_DATA_DIRS = (e != null) ?  e.split(":")
            : new String[]{
                "/usr/local/share/",
                "/usr/share/"
            };
    }


    /** Base directory for user specific configuration files. */
    public static String XDG_CONFIG_HOME = System.getenv("XDG_CONFIG_HOME");
    static {
        if (XDG_CONFIG_HOME == null) {
            XDG_CONFIG_HOME = USER_DIR+"/.config";
        }
    }


    /** Preference-ordered set of base directories to search for configuration files.
     *
     * This is the system search path version of XDG_CONFIG_HOME.
     */
    public static String[] XDG_CONFIG_DIRS;
    static {
        e = System.getenv("XDG_CONFIG_DIRS");
        XDG_CONFIG_DIRS = e != null ?  e.split(":")
            : new String[]{ "/etc/xdg" };
    }


    /** Base directory for user specific non-essential data files. */
    public static String XDG_CACHE_HOME = System.getenv("XDG_CACHE_HOME");
    static {
        if (XDG_CACHE_HOME == null) {
            XDG_CACHE_HOME = USER_DIR+"/.cache";
        }
    }


    /** Base directory for user-specific non-essential runtime files and other file objects.
     *
     * There is no default value. So this likely will be null.
     */
    public static String XDG_RUNTIME_DIR = System.getenv("XDG_RUNTIME_DIR");


    public static String getDataHomeDir(String relative) {
        return Xdg.XDG_DATA_HOME+"/"+relative;
    }


    public static String getConfigHomeDir(String relative) {
        return Xdg.XDG_CONFIG_HOME+"/"+relative;
    }


    public static String getCacheDir(String relative) {
        return Xdg.XDG_CACHE_HOME+"/"+relative;
    }


    public static String getDataDir(String relative) {
        return FileSystem.find(Xdg.XDG_DATA_DIRS, relative);
    }


    public static String getConfigDir(String relative) {
        return FileSystem.find(Xdg.XDG_CONFIG_DIRS, relative);
    }

}

