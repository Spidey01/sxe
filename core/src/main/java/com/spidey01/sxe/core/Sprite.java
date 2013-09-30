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

import java.nio.FloatBuffer;

public class Sprite implements RenderableObject {
    private final static String TAG = "Sprite";

    private Texture mTexture;

    private final static String sDefaultVertexGLSL = ""
        + "attribute vec4 vPosition;"
        + "void main() {"
        // just enough to compile and run without crashing.
        + "  gl_Position = vPosition;"
        + "}"
        +"\n"
        ;

    private final static Shader sDefaultVertexShader = new Shader(sDefaultVertexGLSL, Shader.Type.VERTEX);

    private final static String sDefaultFragmentGLSL = ""
        + "uniform vec4 vColor;"
        + "void main() {"
        + "  gl_FragColor = vec4(0.5, 0.0, 0.5, 1.0);"
        // + "  gl_FragColor = vColor;"
        + "}"
        +"\n"
        ;
    private final static Shader sDefaultFragmentShader = new Shader(sDefaultFragmentGLSL, Shader.Type.FRAGMENT);

    private GpuProgram mProgram = new GpuProgram();
    private Shader mVertexShader;
    private Shader mFragmentShader;
    private VertexBuffer mVertexBuffer;


    public Sprite(Texture backing) {
        this(backing, sDefaultVertexShader, sDefaultFragmentShader);
    }


    public Sprite(Texture backing, Shader vertex, Shader fragment) {
        mTexture = backing;
        mVertexShader = vertex;
        mFragmentShader = fragment;
        mProgram.attachShader(mVertexShader);
        mProgram.attachShader(mFragmentShader);
        mVertexBuffer = new VertexBuffer();
    }


    public float[] getVertices() {
        // just for testing
        return new float[]{
            // Left bottom triangle
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            // Right top triangle
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
            -0.5f, 0.5f, 0f
        };
    }


    public VertexBuffer getVertexBuffer() {
        return mVertexBuffer;
    }


    public GpuProgram getProgram() {
        return mProgram;
    }

}

