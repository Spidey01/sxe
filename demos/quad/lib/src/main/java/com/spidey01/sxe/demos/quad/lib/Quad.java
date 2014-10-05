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

package com.spidey01.sxe.demos.quad.lib;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.entities.Entity;
import com.spidey01.sxe.core.gl.Program;
import com.spidey01.sxe.core.gl.Shader;
import com.spidey01.sxe.core.graphics.GraphicsFacet;
import com.spidey01.sxe.core.graphics.Mesh;
import com.spidey01.sxe.core.graphics.RenderData;
import com.spidey01.sxe.core.graphics.RenderableObject;
import com.spidey01.sxe.core.graphics.VertexBufferTechnique;
import com.spidey01.sxe.core.logging.Log;

import java.io.IOException;

public class Quad
    extends Entity
    // implements RenderableObject, VertexBufferTechnique.Capable
{
    private static final String TAG = "Quad";

    private final GameEngine mGameEngine;

    /** Resource URI for our meshes 3D vertex data.
     */
    private static final String MESH_RESOURCE_PATH = "default://quad.dat";


    /** Stores the data necessary for rendering our quad.
     */
    private final RenderData mRenderData = new RenderData();

    public Quad(GameEngine engine) {
        Log.i(TAG, "Quad object created.");

        mGameEngine = engine;

        /*
         * Setup our vertices to be rendered.
         */
        try {
            mRenderData.setMesh(
                mGameEngine.getResourceManager().load(
                    MESH_RESOURCE_PATH).asVertexVertexMesh());
        } catch(IOException ex) {
            Log.wtf(TAG, "Failed loading ",MESH_RESOURCE_PATH, ex);
        }

        /*
         * Configure a GraphicsFacet to render this Entity.
         */
        setGraphicsFacet(new GraphicsFacet(mRenderData, mGameEngine.getDisplay()));

        /*
         * We don't need to listen for input but this is how you would do it.
         */
        // setInputManager(mGameEngine.getInputManager());
    }

}

