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

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.spidey01.sxe.core.ResourceLoader;
import com.spidey01.sxe.core.PathResourceLoader;
import com.spidey01.sxe.core.Utils;

import java.io.File;
import java.io.IOException;

public class ZipResourceLoaderTest {
    private static ZipResourceLoader sLoader = new ZipResourceLoader();

    private static final String dummyText = "Test dummy for ZipResourceLoader.\n";

    /*
    @Test
    public void simplePathTest() throws IOException {
        File txt = TestUtils.getResource("PathLoader.txt");
        Assume.assumeTrue(txt.exists());

        String expected = "Test dummy for PathLoader.\n";
        Assert.assertEquals("Loading a text file.", expected,
                            Utils.slurp(sLoader.getInputStream(txt)));
    }
    */

    @Test
    public void loadTopLevelFile() throws IOException {
        File zip = TestUtils.getResource("ZipResourceLoader.zip");
        Assume.assumeTrue(zip.exists());

        String s;

        s = zip.getPath() + ":/foo.txt";
        Assert.assertEquals("Should work with $zip:/$filename.", dummyText,
                            Utils.slurp(sLoader.getInputStream(s)));

        s = zip.getPath() + ":foo.txt";
        Assert.assertEquals("Should work with $zip:$filename.", dummyText,
                            Utils.slurp(sLoader.getInputStream(s)));
    }

    @Test
    public void loadFileInSubDir() throws IOException {
        File zip = TestUtils.getResource("ZipResourceLoader.zip");
        Assume.assumeTrue(zip.exists());

        String s;

        s = zip.getPath() + ":/blargle/bar.txt";
        Assert.assertEquals("Should work with $zip:/$filename.", dummyText,
                            Utils.slurp(sLoader.getInputStream(s)));

        s = zip.getPath() + ":blargle/bar.txt";
        Assert.assertEquals("Should work with $zip:$filename.", dummyText,
                            Utils.slurp(sLoader.getInputStream(s)));
    }

}

