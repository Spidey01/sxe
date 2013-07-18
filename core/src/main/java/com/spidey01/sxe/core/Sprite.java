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

public class Sprite implements FrameStartedListener {
    private final static String TAG = "Sprite";

    private boolean mIsInitialized;
    private Texture mTexture;

    private final static String sDefaultVertexGLSL = ""
        + "void main() {"
        // just enough to compile and run without crashing.
        + "  gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;"
        + "}"
        +"\n"
        ;

    private final static Shader sDefaultVertexShader = new Shader(sDefaultVertexGLSL, Shader.Type.VERTEX);

    private final static String sDefaultFragmentGLSL = ""
        + "void main() {"
        // + "  gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);"
        + "}"
        +"\n"
        ;
    private final static Shader sDefaultFragmentShader = new Shader(sDefaultFragmentGLSL, Shader.Type.FRAGMENT);

    private GpuProgram mProgram = new GpuProgram();
    private Shader mVertexShader;
    private Shader mFragmentShader;


    public Sprite(Texture backing) {
        this(backing, sDefaultVertexShader, sDefaultFragmentShader);
    }


    public Sprite(Texture backing, Shader vertex, Shader fragment) {
        mTexture = backing;
        mVertexShader = vertex;
        mFragmentShader = fragment;
        mProgram.attachShader(mVertexShader);
        mProgram.attachShader(mFragmentShader);
    }


    public void frameStarted(OpenGL GL) {
        if (!mIsInitialized) {
            mTexture.initialize(GL);
            // mVertexShader.initialize(GL);
            // mFragmentShader.initialize(GL);
            mProgram.initialize(GL);
            mIsInitialized = true;
        }
        GL.glBindTexture(OpenGL.GL_TEXTURE_2D, mTexture.getId());
    }

}

