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

import com.spidey01.sxe.core.gl.Program;
import com.spidey01.sxe.core.gl.Shader;

import com.spidey01.sxe.core.graphics.Mesh;
import com.spidey01.sxe.core.graphics.RenderableObject;
import com.spidey01.sxe.core.graphics.VertexBufferTechnique;

import java.nio.FloatBuffer;

public class Sprite
    implements RenderableObject, VertexBufferTechnique.Capable
{
    private final static String TAG = "Sprite";

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

    private Mesh mMesh;
    private Texture mTexture;
    private Program mProgram = new Program();
    private Shader mVertexShader;
    private Shader mFragmentShader;
    private VertexBufferTechnique mTechnique;


    public Sprite(Mesh mesh, Texture texture) {
        this(mesh, texture, sDefaultVertexShader, sDefaultFragmentShader);
    }


    public Sprite(Mesh mesh, Texture backing, Shader vertex, Shader fragment) {
        mMesh = mesh;
        mTexture = backing;
        mVertexShader = vertex;
        mFragmentShader = fragment;
        mProgram.attachShader(mVertexShader);
        mProgram.attachShader(mFragmentShader);
    }


    public void setTechnique(VertexBufferTechnique technique) {
        mTechnique = technique;
    }


    public Mesh getMesh() {
        return mMesh;
    }


    public Program getProgram() {
        return mProgram;
    }


    public void draw() {
        Log.xtrace(TAG, "draw() called.");
        mTechnique.draw(this);
    }

}

