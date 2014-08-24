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

package com.spidey01.sxe.core.graphics;

import com.spidey01.sxe.core.common.Buffers;
import com.spidey01.sxe.core.common.Utils;
import com.spidey01.sxe.core.logging.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Simple vertex-vertex mesh implementation.
 *
 * A vertex-vertex or VV mesh is a list of connected vertices.
 *
 * We can parse this from a file containing 3 numbers seperated by white space.
 * Blank lines and comment lines are ignored.
 *
 * Here is an example file:
 * <samp><pre>
 *      // Let bottom triangle.
 *      -0.5 0.5 0
 *      -0.5 -0.5 0
 *      0.5 -0.5 0
 *      # Right top triangle.
 *      0.5 -0.5 0
 *      0.5 0.5 0
 *      -0.5 0.5 0
 *      -- blargle
 *      ; narble
 * </pre></samp>
 */

public class VertexVertexMesh {
    private static final String TAG = "VertexVertexMesh";

    private final float[] mVertices;

    /** Constructor taking a prepaired list of vertices. */
    public VertexVertexMesh(float[] vertices) {
        mVertices = vertices;
    }


    public VertexVertexMesh(BufferedReader buffer) throws IOException {
        String line;
        ArrayList<String> vertices = new ArrayList<String>();
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            Log.xtrace(TAG, "line="+line);

            if (line.isEmpty()) continue;
            if ((line.charAt(0) == '#')
                || (line.charAt(0) == ';')
                || (line.charAt(0) == '/' && line.charAt(1) == '/')
                || (line.charAt(0) == '-' && line.charAt(1) == '-'))
            {
                Log.xtrace(TAG, "Skipping line \""+line+"\" as comment.");
                continue;
            }

            for (String vertex : line.split("\\s", 3)) {
                vertices.add(vertex);
            }
        }

        mVertices = new float[vertices.size()];
        for (int n=0; n < vertices.size(); ++n) {
            mVertices[n] = Float.valueOf(vertices.get(n));
        }
    }


    public VertexVertexMesh(File file) throws FileNotFoundException, IOException {
        this(Buffers.makeReader(file));
    }


    public VertexVertexMesh(InputStream stream) throws IOException {
        this(Buffers.makeReader(stream));
    }


    /** Get vertex value at index.
     *
     * @param index between 0 and size()-1.
     */
    public float get(int index) {
        return mVertices[index];
    }


    public int size() {
        return mVertices.length;
    }

}

