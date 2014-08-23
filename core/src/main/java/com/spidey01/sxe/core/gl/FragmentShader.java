/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

package com.spidey01.sxe.core.gl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/** Convenience subclass of Shader for loading fragment shaders. */
public class FragmentShader
    extends Shader
{
    private static final String TAG = "FragmentShader";

    /** Shader source code from a String. */
    public FragmentShader(String code) {
        super(code, Type.FRAGMENT);
    }


    /** Shader source code from a File. */
    public FragmentShader(File file) throws IOException {
        super(file, Type.FRAGMENT);
    }


    /** Shader source code from a Reader. */
    public FragmentShader(BufferedReader reader) throws IOException {
        super(reader, Type.FRAGMENT);
    }


    /** Shader source code from a InputStream. */
    public FragmentShader(InputStream stream) throws IOException {
        super(stream, Type.FRAGMENT);
    }
}

