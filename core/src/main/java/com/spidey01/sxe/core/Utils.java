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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {

    private final static String TAG = "Utils";

    /** InputStream to something with .readLine() :-).
     *
     * Often I need to deal with InputStream but of course, all I reallly want
     * to do is get data out of it line by line.
     */
    public static BufferedReader makeBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in));
    }

    public static Shader.Type resourceTypeToShaderType(Resource.Type type) {
        return type == Resource.Type.VERTEX_SHADER ? Shader.Type.VERTEX
            : Shader.Type.FRAGMENT;
    }

    public static Resource.Type shaderTypeToResourceType(Shader.Type type) {
        return type == Shader.Type.VERTEX ? Resource.Type.VERTEX_SHADER
            : Resource.Type.FRAGMENT_SHADER;
    }

    public static Resource.Type getShaderResourceType(String fileName) {
        if (fileName.endsWith(".vert")) {
            return Resource.Type.VERTEX_SHADER;
        } else if (fileName.endsWith(".frag")) {
            return Resource.Type.FRAGMENT_SHADER;
        }
        throw new RuntimeException("Unknown shader type for "+fileName);
    }

    public static Shader.Type getShaderType(String fileName) {
        if (fileName.endsWith(".vert")) {
            return Shader.Type.VERTEX;
        } else if (fileName.endsWith(".frag")) {
            return Shader.Type.FRAGMENT;
        }
        throw new RuntimeException("Unknown shader type for "+fileName);
    }

    /** Slurps a File into a String. */
    public static String slurp(File source) throws IOException {
        return Utils.slurp(new BufferedReader(new FileReader(source)));
    }

    /** Slurps a File into a String. */
    public static String slurp(String path) throws IOException {
        return Utils.slurp(new BufferedReader(new FileReader(path)));
    }

    /** Slurps a InputStream into a String. */
    public static String slurp(InputStream source) throws IOException {
        return Utils.slurp(Utils.makeBufferedReader(source));
    }

    /** Slurps a BufferedReader into a String. */
    public static String slurp(BufferedReader source) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = source.readLine()) != null) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }
}

