/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

import java.io.InputStream;
import java.io.IOException;

/** A resource bucket.
 *
 * A resource will consist of either an InputStream that can be used for
 * accessing it's data, or an Object of some relevant type.
 *
 *  Resources always have a file name. Note however that it may not be a valid
 *  file namer in the conventional sense, i.e. it's what ResourceManager.load()
 *  uses not what the file system necessarily uses.
 */
public interface Resource {

    public enum Type {
        UNKNOWN,
        TEXT_FILE,
        VERTEX_SHADER,
        FRAGMENT_SHADER
    };

    /** Performs the actual load of the resource from Loader.
     */
    boolean load();

    /** Unloads the resource. */
    boolean unload();

    boolean isLoaded();

    String getFileName();
    InputStream getInputStream();
    Object getObject();
    ResourceLoader getLoader();
}

