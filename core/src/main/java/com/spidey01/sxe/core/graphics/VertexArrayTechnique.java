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

import com.spidey01.sxe.core.gl.OpenGLES11;
import com.spidey01.sxe.core.graphics.VertexBuffer;
import com.spidey01.sxe.core.logging.Log;


/** GraphicsTechnique that uses simple Vertex Arrays for rendering.
 *
 * This is a fairly simple OpenGLES11 compatible method of drawing from vertex
 * arrays.
 */

public class VertexArrayTechnique
    implements GraphicsTechnique
{
    private static final String TAG = "VertexArrayTechnique";

    private OpenGLES11 mOpenGLES11;


    public VertexArrayTechnique(OpenGLES11 GLES11) {
        mOpenGLES11 = GLES11;
    }


    @Override
    public void draw(RenderData data) {
        Log.xtrace(TAG, "draw(", data, ")");

        mOpenGLES11.glEnableClientState(OpenGLES11.GL_VERTEX_ARRAY);

        VertexBuffer vertices = data.getMesh().asVertexBuffer();
        mOpenGLES11.glVertexPointer(3, 0, vertices);

        mOpenGLES11.glDrawArrays(OpenGLES11.GL_TRIANGLES, 0, vertices.capacity());

        mOpenGLES11.glDisableClientState(OpenGLES11.GL_VERTEX_ARRAY);
    }


    /** We require a Mesh with a VertexBuffer. 
     */

    @Override
    public boolean accept(RenderData maybe) {
        boolean result = maybe.getMesh() == null ? false : true;
        Log.xtrace(TAG, "accept(", maybe, "): ", (result ? "accepting." : "rejecting."));
        return result;
    }

}

