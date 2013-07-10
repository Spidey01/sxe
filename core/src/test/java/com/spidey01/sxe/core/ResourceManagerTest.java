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
import java.util.concurrent.Future;

public class ResourceManagerTest extends UnitTest {
    private static final String TAG = "ResourceManagerTest";
    private static ResourceManager sResourceManager;


    /** Various URIs for testing.
     *
     * @see TestResources
     */
    protected static final String[] sTestURIs = new String[]{
        "default://"+TestResources.textFileName,
        "file://"+TestResources.textFileName,
        "zip://"+TestResources.zipFileName+"/blargle/bar.txt",
    };


    @BeforeClass
    public static void setUpClass() {
        UnitTest.setup();
        sResourceManager = new ResourceManager();
        sResourceManager.addResourceLocation(TestResources.directory);
    }


    @Test
    public void checkDefaults() {
        Map<String, ResourceLoader> loaders = sResourceManager.getLoaders();
        Assert.assertTrue(
                "ResourceManager is supposed to contain a default ResourceLoader.",
                loaders.containsKey("default"));

        Assert.assertThat(loaders.get("default"),
                          CoreMatchers.instanceOf(PathResourceLoader.class));

        Assert.assertThat("ResourceManager should handle .zip out of box.", loaders.get("zip"),
                          CoreMatchers.instanceOf(ZipResourceLoader.class));
    }


    @Test
    public void addLoader() {
        final String prefix = "dummy";
        DummyResourceLoader dummy = new DummyResourceLoader();

        Assert.assertNull("There shouldn't be a default ResourceLoader for dummies.",
                          sResourceManager.setLoader(prefix, dummy));

        Assert.assertThat(sResourceManager.getLoaders().get(prefix),
                          CoreMatchers.instanceOf(DummyResourceLoader.class));

        Assert.assertThat(sResourceManager.getLoader(prefix+"://foo/bar/ham"),
                          CoreMatchers.instanceOf(DummyResourceLoader.class));
    }


    @Test
    public void setLoader() {
        ResourceLoader foo = new DummyResourceLoader();
        sResourceManager.setLoader("foo", foo);
        Assert.assertEquals("setLoader() should work.", foo,
                sResourceManager.getLoader("foo://ham/spam"));

        sResourceManager.setLoader("bar", sResourceManager.getDefaultLoader());
        Assert.assertEquals("setLoader() should allow aliasing.",
                sResourceManager.getDefaultLoader(), sResourceManager.getLoader("bar://ham/spam"));
    }


    @Test
    public void load() throws IOException {
        for (String file : sTestURIs) {
            Log.i(TAG, "Testing load(\""+file+"\")");
            testResourceHandle(sResourceManager.load(file));
        }
    }


    @Test
    public void enqueue() throws Exception {
        for (String file : sTestURIs) {
            Log.i(TAG, "Testing enqueue(\""+file+"\")");
            testResourceHandle(sResourceManager.enqueue(file).get());
        }
    }


    private void testResourceHandle(ResourceHandle resource) throws IOException {
        Assert.assertNotNull("Never leak a null here.", resource);

        InputStream data = resource.asInputStream();
        Assert.assertNotNull("Never leak a null here either.", data);
        Assert.assertFalse(data.read() == -1);

        //
        // These should hold true however the ResourceHandle was obtained from ResourceManager.
        //

        Assert.assertSame("Loading same URI == same resource", resource, sResourceManager.load(resource.getURI()));
        Assert.assertSame("Loading same URI == same resource data", data, sResourceManager.load(resource.getURI()).asInputStream());
        sResourceManager.unload(resource.getURI());
        Assert.assertNotSame("Really reloading is really reloading.", resource, sResourceManager.load(resource.getURI()));
    }
}

