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

package com.spidey01.sxe.core.testing;

public class TestResources {

    /** Directory containing test resources. */
    public static final String directory = "src/test/resources";

    /** File name of text file used for testing. */
    public static final String textFileName = "PathResourceLoader.txt";

    /** String of text used for testing with textFileName. */
    public static final String textFileContent = "Test dummy for PathResourceLoader.\n";

    /** File name of GZip'd text file used for testing. */
    public static final String gzipFileName = "PathResourceLoader.txt.gz";

    /** String of text used for testing with gzipFileName. */
    public static final String gzipFileContent = textFileContent;

    /** File name of Zip archive used for testing. */
    public static final String zipFileName = "ZipResourceLoader.zip";

    /** String of text used for testing with zipFileName. */
    public static final String zipFileContent = "Test dummy for ZipResourceLoader.\n";

    /** File name of settings file used for testing. */
    public static final String settingsFileName = "SettingsFile.properties";

    /** File name of settings xml file used for testing. */
    public static final String settingsXmlFileName = "SettingsXMLFile.xml";
}

