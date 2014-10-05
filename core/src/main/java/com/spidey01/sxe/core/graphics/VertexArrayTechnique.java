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
import com.spidey01.sxe.core.gl.VertexBuffer;
import com.spidey01.sxe.core.logging.Log;


/** GraphicsTechnique that uses OpenGL Vertex Arrays for rendering.
 * OpenGLES11 compatible 
 *
 * The basic concept is 
 */

public class VertexArrayTechnique
    implements GraphicsTechnique
{
    private static final String TAG = "ImmediateModeTechnique";

    private OpenGLES11 mOpenGLES11;

    /** Interface {@link #accept()}'d by this technique. */

    public interface Capable {
        VertexBuffer getVertexBuffer();
    }


    public VertexArrayTechnique(OpenGLES11 GLES11) {
        mOpenGLES11 = GLES11;
    }


    public void draw(RenderData data) {
        // glEnableClientState(OpenGLES11.GL_VERTEX_ARRAY);
        // glVertexPointer(3, OpenGLES11.GL_FLOAT, 0, vertices);
        // glDrawArrays(OpenGLES11.GL_TRIANGLES, 0, 3);
        // glDisableClientState(OpenGLES11.GL_VERTEX_ARRAY);

        /* C code:

            GLfloat vertices[] = {1,0,0, 0,1,0, -1,0,0};
            glEnableClientState(GL_VERTEX_ARRAY);
            glVertexPointer(3, GL_FLOAT, 0, vertices);
            glDrawArrays(GL_TRIANGLES, 0, 3);
            glDisableClientState(GL_VERTEX_ARRAY);

        */
    }


    public boolean accept(RenderData maybe) {
        boolean result = false;
        try {
            Capable p = (Capable)maybe;
            result = true;
        } catch(ClassCastException ex) {
            result = false;
        }

        Log.xtrace(TAG, "accept(", maybe, "): ", (result ? "accepting." : "rejecting."));
        return result;
    }

}

