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

package com.spidey01.sxe.core.io;

import org.junit.*;

import com.spidey01.sxe.core.Utils;
import com.spidey01.sxe.core.testing.TestResources;
import com.spidey01.sxe.core.testing.UnitTest;

import java.io.File;
import java.io.IOException;

public class PathResourceLoaderTest extends UnitTest {
    private static PathResourceLoader sLoader = new PathResourceLoader();


    @BeforeClass
    public static void setup() {
        UnitTest.setup();
    }


    @Test
    public void simplePathTest() throws IOException {
        String from = TestResources.directory;
        String what = TestResources.textFileName;
        File txt = new File(from, what);
        Assume.assumeTrue(txt.exists());

        Assert.assertEquals("Loading a text file.", 
                            TestResources.textFileContent,
                            Utils.slurp(sLoader.getInputStream(from, what)));
    }

}
