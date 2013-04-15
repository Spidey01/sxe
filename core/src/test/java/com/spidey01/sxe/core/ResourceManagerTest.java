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
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.spidey01.sxe.core.PathResourceLoader;
import com.spidey01.sxe.core.Resource;
import com.spidey01.sxe.core.ResourceLoader;
import com.spidey01.sxe.core.ResourceManager;
import com.spidey01.sxe.core.ZipResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ResourceManagerTest {
    private static ResourceManager sResourceManager;

    @BeforeClass
    public static void setUpClass() {
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

}

