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

import com.spidey01.sxe.core.gl.VertexBufferObject;

/**
 * Container object for holding data to be rendered.
 */
public class RenderData {

    public RenderData() {
    }


    /*
     * We can contain a Mesh to be rendered.
     */

    private Mesh mMesh;


    public Mesh getMesh() {
        return mMesh;
    }


    public void setMesh(Mesh mesh) {
        mMesh = mesh;
    }


    /*
     * We can contain a VertexBufferObject associated with our data.
     */

    private VertexBufferObject mVBO;


    public VertexBufferObject getVBO() {
        return mVBO;
    }


    public void setVBO(VertexBufferObject vbo) {
        mVBO = vbo;
    }
}

