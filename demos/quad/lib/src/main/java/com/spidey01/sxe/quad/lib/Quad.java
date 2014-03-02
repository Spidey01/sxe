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

package com.spidey01.sxe.quad.lib;


import com.spidey01.sxe.core.*;
import com.spidey01.sxe.core.gl.*;
import com.spidey01.sxe.core.graphics.*;
import com.spidey01.sxe.core.input.*;

import java.io.IOException;


/** Quad demo.
 *
 * Draw a simple coloured quad to the screen until the user exists.
 * Hit the Q key to exit.
 *
 * This serves as a demo of how to implement something like this in SxE.
 */
public class Quad
    extends Game
    implements KeyListener, RenderableObject, VertexBufferTechnique.Capable
{
    private static final String TAG = "Quad";

    private static float[] sVertices = new float[]{
        /* Left bottom triangle. */
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        /* Right top triangle. */
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f,
        -0.5f, 0.5f, 0f,
    };


    private static VertexBufferTechnique sTechnique;
    private static Mesh sMesh;
    private static Program sProgram;


    /** Return the name of this game. */
    @Override
    public String getName() {
        return TAG;
    }


    @Override
    public boolean start(GameEngine engine) {
        super.start(engine);

        /* Add our demo Quad to the scene. */
        mGameEngine.getSceneManager().add(this);

        /* Bind ourself to handle the 'Q' key press. */
        mGameEngine.getInputManager().addKeyListener("Q", this);
        return true;
    }


    @Override
    public boolean onKey(KeyEvent event) {
        if (event.getKeyName().equals("Q")) {
            requestStop();
            return true;
        }
        return false;
    }


    @Override
    public void draw() {
        if (sTechnique == null) {
            sTechnique = new VertexBufferTechnique(mGameEngine.getDisplay().getOpenGL());
            sMesh = new Mesh(sVertices);
            try {
                sProgram = new Program(
                    mGameEngine.getResourceManager().load("default://default.frag").asShader(Shader.Type.FRAGMENT),
                    mGameEngine.getResourceManager().load("default://default.vert").asShader(Shader.Type.VERTEX)
                );
            } catch (IOException e) {
                // throw RuntimeException("Error loading shaders.");
            }
        }
        sTechnique.draw(this);
    }


    @Override public Program getProgram() { return sProgram; }
    @Override public Mesh getMesh() { return sMesh; }


}

