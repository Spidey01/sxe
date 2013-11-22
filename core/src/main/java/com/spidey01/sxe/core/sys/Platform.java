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


/** A bundle of platform information.
 *
 */
public class Platform {
    private static final String TAG = "Platform";

    public static final String ANDROID = "Android";
    public static final String LINUX = "Linux";
    public static final String MAC_OS = "Mac OS";
    public static final String WINDOWS = "Windows";

    /** Name of the platform. */
    public final String name;

    /** Architecture of the platform. */
    public final String arch = System.getProperty("os.arch");

    /** Operating System Version of the platform. */
    public final String version = System.getProperty("os.name")+" "+System.getProperty("os.version");


    /** Guess the current platform name. */
    public Platform() {
        name = guess();
    }


    /** Initialize Platform.
     *
     * @param name The name of the platform. This should match a constant.
     */
    public Platform(String name) {
        this.name = name;
    }


    public boolean isAndroid() {
        return name.startsWith(ANDROID);
    }


    public boolean isMacOS() {
        return name.startsWith(MAC_OS) || name.contains("OS X");
    }


    public boolean isUnix() {
        /*
         * Because this is probably more DWIM to most game developers than just
         * return !isWindows.
         */
        return (isAndroid() || isMacOS() || isWindows()) ?  false : true;
    }


    public boolean isWindows() {
        return name.startsWith(WINDOWS);
    }


    public String toString() {
        return "platform: name=\""+name+"\" version=\""+version+"\" arch=\""+arch+"\"";
    }

    
    /** Guess the current platform name.
     */
    public static String guess() {
        final String os_name = System.getProperty("os.name");
        final String file_sep = System.getProperty("file.separator");
        final String line_sep = System.getProperty("line.separator");
        final String path_sep = System.getProperty("path.separator");

        /*
         * POSIX based OS
         */
        if (file_sep.equals("/") && line_sep.equals("\n") && path_sep.equals(":")) {
            /*
             * Look for Android values.
             */
            if (System.getProperty("java.vm.vendor").equals("The android Project")
                && System.getProperty("java.vm.name").equals("Dalvik")
                && file_sep.equals("/")
                && line_sep.equals("\n")
                && path_sep.equals(":")
                && os_name.startsWith("Linux"))
            {
                return ANDROID;
            }
            else if (os_name.startsWith(LINUX)) {
                return LINUX;
            }
            else if (os_name.startsWith(MAC_OS) || os_name.contains("OS X")) {
                return MAC_OS;
            }
        } else if (file_sep.equals("\\") && line_sep.equals("\r\n")
                   && path_sep.equals(";")
                   && os_name.toLowerCase().startsWith("windows"))
        {
            return WINDOWS;
        }

        throw new RuntimeException("Unknown OS");
    }
}

