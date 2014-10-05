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

package com.spidey01.sxe.core.graphics;

import com.spidey01.sxe.core.gl.FragmentShader;
import com.spidey01.sxe.core.gl.OpenGLES20;
import com.spidey01.sxe.core.gl.Program;
import com.spidey01.sxe.core.gl.VertexBufferObject;
import com.spidey01.sxe.core.gl.VertexShader;
import com.spidey01.sxe.core.graphics.RenderData;
import com.spidey01.sxe.core.logging.Log;

import java.nio.FloatBuffer;


/** Technique for rendering via {@link VertexBufferObject}.
 *
 * The object to be rendered with this technique is expected to do the following:
 * <ol>
 *  <li>Provide a VertexBuffer</li>
 *  <li></li>
 * </ol>
 *
 */

public class VertexBufferTechnique implements GraphicsTechnique {

    private static final String TAG = "VertexBufferTechnique";

    private OpenGLES20 mGLES20;
    private Program mProgram;


    /*
     * Place holders for now. I just need something I can test this with.
     */
    static final String sVertexShaderCode = "attribute vec4 vPosition; void main() { gl_Position = vPosition; }";
    static final VertexShader sVertexShader = new VertexShader(sVertexShaderCode);
    static final String sFragmentShaderCode = "void main() { gl_FragColor = vec4(0.5, 0.0, 0.5, 1.0); }";
    static final FragmentShader sFragmentShader = new FragmentShader(sFragmentShaderCode);

    public VertexBufferTechnique(OpenGLES20 GLES20) {
        mGLES20 = GLES20;
        mProgram = new Program(sVertexShader, sFragmentShader);
        mProgram.initialize(mGLES20);
    }


    @Override
    public boolean accept(RenderData maybe) {
        boolean result = false;
        Mesh m = maybe.getMesh();

        if (maybe.getVBO() != null) {
            Log.xtrace(TAG, "accept(", maybe, "): has VBO.");
            result = true;
        } else if (m != null) { 
            /*
             * Do I really want to do this versus saying hey, it's not our problem?
             */
            Log.xtrace(TAG, "accept(", maybe, "): has Mesh; making VBO.");

            VertexBufferObject vbo = new VertexBufferObject();
            vbo.initialize(mGLES20, m.asVertexBuffer().buffer);
            maybe.setVBO(vbo);
            result = true;
        }

        Log.xtrace(TAG, "accept(", maybe, "): ", (result ? "accepting." : "rejecting."));
        return result;
    }


    @Override
    public void draw(RenderData data) {
        Log.xtrace(TAG, "Drawing client data ", data);

        VertexBufferObject vbo = data.getVBO();
        if (vbo == null) {
            Log.e(TAG, "Attempted to draw from null VBO.");
            return;
        }
        vbo.bind(mGLES20);

        int pid = mProgram.getId();
        mGLES20.glUseProgram(pid);

        // Get the location (index) of the attribute.
        int vPosition = mGLES20.glGetAttribLocation(pid, "vPosition");

        // Turn it on.
        mGLES20.glEnableVertexAttribArray(vPosition);

        // Pass the data for vPosition
        /* FIXME: set the numbers dynamically! */
        mGLES20.glVertexAttribPointer(vPosition, 3, OpenGLES20.GL_FLOAT, false, 0, 0);


        // get the uniform's index/location and set it.
        /* // FIXME: Yeah!
        int vColor = mGL.glGetUniformLocation(p, "vColor");
            float[] color_v = { 0.0f, 1.0f, 0.0f, 1.0f };
            FloatBuffer color_p = Utils.Buffers.createFloatBuffer(color_v.length);
            color_p.put(color_v);
            color_p.flip();
        mGL.glUniform4fv(vColor, 1, color_p);
        */

        // Draw it.
        mGLES20.glDrawArrays(OpenGLES20.GL_TRIANGLES, 0, vbo.getVertexCount());

        // Clean up.
        mGLES20.glDisableVertexAttribArray(vPosition);
    }

}

