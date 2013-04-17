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

    @Test
    public void loadTopLevelFile() throws IOException {
        test("foo.txt");
    }

    @Test
    public void loadFileInSubDir() throws IOException {
        test("blargle/bar.txt");
    }

    @Test(expected = IOException.class)
    public void loadNonExistantFile() throws IOException {
        test("guux.blarg");
    }

    /** You can't load directories. */
    @Test(expected = IOException.class)
    public void loadDirectory() throws IOException {
        sLoader.getInputStream(getZip().getPath()+":/blargle");
    }

    @Test(expected = IOException.class)
    public void garbageZipFile() throws IOException {
        sLoader.getInputStream(Utils.getBitBucketPath() +":/foo");
    }

    @Test(expected = IOException.class)
    public void nullFile() throws IOException {
        sLoader.getInputStream((File)null);
    }

    @Test(expected = IOException.class)
    public void nullString() throws IOException {
        sLoader.getInputStream((String)null);
    }

    private File getZip() {
        File zip = TestUtils.getResource("ZipResourceLoader.zip");
        // desired side effect >_>.
        Assume.assumeTrue(zip.exists());
        return zip;
    }

    /** Tests that it works with and without a leading "/" in the path name. */
    private void test(String path) throws IOException {
        String s;
        File zip = getZip();

        s = zip.getPath() + ":/" + path;
        Assert.assertEquals("With $zip:/$filename.", dummyText,
                            Utils.slurp(sLoader.getInputStream(s)));

        s = zip.getPath() + ":" + path;
        Assert.assertEquals("With $zip:$filename.", dummyText,
                            Utils.slurp(sLoader.getInputStream(s)));
    }

}

