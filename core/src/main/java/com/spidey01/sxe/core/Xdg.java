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

/** Helper for XDG Base Directory Specification (version 0.7).
 *
 * Environment variables like <samp>XDG_WHAT_EVER</samp> are exported as static
 * fields, along with a few helpers. To find a file/dir located in one of
 * these directories, you will want <code>getWhatEver("your/path")</code>.
 *
 * You can obtain an instance of the data with Xdg.getInstance().
 */
public class Xdg {
    private static final String TAG = "Xdg";
    private static Xdg sInstance;

    public static String APP_HOME;

    public static String HOME;
    public static String USER_DIR = HOME;

    public static String XDG_DATA_HOME;
    public static String[] XDG_DATA_DIRS;

    public static String XDG_CONFIG_HOME;
    public static String[] XDG_CONFIG_DIRS;

    public static String XDG_CACHE_HOME;
    public static String XDG_RUNTIME_DIR;


    protected Xdg() {
        String e;

        APP_HOME = System.getenv("APP_HOME");

        USER_DIR = HOME = Utils.getUserDir();

        e = System.getenv("XDG_DATA_HOME");
        XDG_DATA_HOME = e == null ? USER_DIR+"/.local/share" : e;
        
        e = System.getenv("XDG_CONFIG_HOME");
        XDG_CONFIG_HOME = e == null ? USER_DIR+"/.config" : e;

        e = System.getenv("XDG_CACHE_HOME");
        XDG_CACHE_HOME = e == null ? USER_DIR+"/.cache" : e;

        // no other default.
        XDG_RUNTIME_DIR = System.getenv("XDG_RUNTIME_DIR");


        e = System.getenv("XDG_DATA_DIRS");
        XDG_DATA_DIRS = e == null ?  e.split(":")
            : new String[]{
                "/usr/local/share/",
                "/usr/share/"
            };


        e = System.getenv("XDG_CONFIG_DIRS");
        XDG_CONFIG_DIRS = e == null ?  e.split(":")
            : new String[]{ "/etc/xdg" };

        sInstance = this;
    }


    public static Xdg getInstance() {
        return sInstance == null ? new Xdg() : sInstance;
    }


    public String getDataHomeDir(String relative) {
        return Xdg.XDG_DATA_HOME+"/"+relative;
    }


    public String getConfigHomeDir(String relative) {
        return Xdg.XDG_CONFIG_HOME+"/"+relative;
    }


    public String getCacheDir(String relative) {
        return Xdg.XDG_CACHE_HOME+"/"+relative;
    }


    public String getDataDir(String relative) {
        return Utils.find(Xdg.XDG_DATA_DIRS, relative);
    }


    public String getConfigDir(String relative) {
        return Utils.find(Xdg.XDG_CONFIG_DIRS, relative);
    }

}

