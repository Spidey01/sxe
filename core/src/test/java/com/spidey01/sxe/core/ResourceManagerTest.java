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

import org.hamcrest.CoreMatchers;
import org.junit.*;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;

public class ResourceManagerTest extends UnitTest {
    private static ResourceManager sResourceManager;

    @BeforeClass
    public static void setUpClass() {
        UnitTest.setup();
        sResourceManager = new ResourceManager();
    }


    @Test
    public void checkDefaults() {
        Map<String, ResourceLoader> loaders = sResourceManager.getLoaders();
        Assert.assertTrue(
                "ResourceManager is supposed to contain a default ResourceLoader.",
                loaders.containsKey("default"));

        Assert.assertThat(loaders.get("default"),
                          CoreMatchers.instanceOf(PathResourceLoader.class));

        Assert.assertThat("ResourceManager should handle .zip out of box.", loaders.get(".zip"),
                          CoreMatchers.instanceOf(ZipResourceLoader.class));
    }


    @Test
    public void addLoader() {
        final String prefix = "dummy:";
        DummyResourceLoader dummy = new DummyResourceLoader();

        Assert.assertNull("There shouldn't be a default ResourceLoader for dummies.",
                          sResourceManager.setLoader(prefix, dummy));

        Assert.assertThat(sResourceManager.getLoaders().get(prefix),
                          CoreMatchers.instanceOf(DummyResourceLoader.class));

        // FIXME
        // Assert.assertThat(sResourceManager.getLoader(prefix+"/foo/bar/ham"),
                          // CoreMatchers.instanceOf(DummyResourceLoader.class));
    }


    @Test
    public void load() {
        // long rid = sResourceManager.load("dummy:/foo/bar");
        long rid = sResourceManager.load(TestUtils.getResource("ZipResourceLoader.zip:/blargle/bar.txt"));
        Assert.assertTrue("Resource ID -1 is reserved for failure.", rid > -1);

        ResourceHandle resource = sResourceManager.get(rid);
        Assert.assertNotNull("Never leak a null here.", resource);

        // all fine unless we throw exceptions.
        try {
            InputStream data = resource.asInputStream();
            Assert.assertNotNull("Never leak a null here either.", data);

            data.read();

            Assert.assertSame("Getting same rid == same resource",
                    resource, sResourceManager.get(rid));

            Assert.assertSame("Getting same handle == same resource",
                    data, sResourceManager.get(rid).asInputStream());
        } catch(IOException e) {
            Assert.fail(e.toString());
        }

        sResourceManager.unload(rid);
    }


}

