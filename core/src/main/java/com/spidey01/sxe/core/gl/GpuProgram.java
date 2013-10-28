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

package com.spidey01.sxe.core.gl;

import java.util.Collection;
import java.util.LinkedList;

/** OpenGL Program object.
 *
 * This encapsulates the OpenGL code for working with <em>GPU programs</em>.
 * Direct access to the program (int) is provided with getId(). Most methods
 * will have little value until initinitized with an OpenGL instance.
 */
public class GpuProgram {
    private static final String TAG = "GpuProgram";

    private boolean mIsInitialized;
    private int mProgramId;
    private Collection<Shader> mShaders = new LinkedList<Shader>();


    public GpuProgram() {
    }


    /** Convenience constructor when you just want a vert/frag shader pair. */
    public GpuProgram(Shader vertex, Shader fragment) {
        mShaders.add(vertex);
        mShaders.add(fragment);
    }

    public GpuProgram(Collection<Shader> shaders) {
        mShaders.addAll(shaders);
    }

    /** Initinize the program for use.
     *
     * This creates a new program, initinizes attached shaders, and links the program up for immediate use.
     */
    public void initialize(OpenGLES20 GL) {
        if (mIsInitialized) return;

        mProgramId = GL.glCreateProgram();
        if (mProgramId == 0) {
        }

        for (Shader s : mShaders) {
            s.initialize(GL);
            GL.glAttachShader(mProgramId, s.getId());
        }

        mIsInitialized = true;

        link(GL);

        // Could detach shaders now but would it break deinitialize and how
        // much memory would it usually recover?
        // XXX I think we could delete after link but not detach?
    }


    /** Clean up the program.
     *
     * At this time, any remaining shaders are detached but not cleaned up.
     * Future versions may do so.
     */
    public void deinitialize(OpenGLES20 GL) {
        for (Shader s : mShaders) {
            GL.glDetachShader(mProgramId, s.getId());
            // XXX do we want to do this?
            // s.deinitialize(GL);
        }
        GL.glDeleteProgram(mProgramId);
        mProgramId = 0;
        mIsInitialized = false;
    }


    private String getInfoLog(OpenGLES20 GL) {
        return GL.glGetProgramInfoLog(mProgramId);
    }
 
    
    public void attachShader(Shader shader) {
        mShaders.add(shader);
    }

    public void attachShader(OpenGLES20 GL, Shader shader) {
        GL.glAttachShader(mProgramId, shader.getId());
        mShaders.add(shader);
    }

    public void detachShader(Shader shader) {
        mShaders.remove(shader);
    }

    public void detachShader(OpenGLES20 GL, Shader shader) {
        GL.glDetachShader(mProgramId, shader.getId());
        mShaders.remove(shader);
    }

    /** Links this program object. */
    public void link(OpenGLES20 GL) {
        check();

        GL.glLinkProgram(mProgramId);
        if (GL.glGetProgramiv(mProgramId, OpenGLES20.GL_LINK_STATUS) == OpenGLES20.GL_FALSE) {
            throw new RuntimeException("Failed linking program: "+getInfoLog(GL));
        }

    }


    /** Validates this program object. */
    public boolean validate(OpenGLES20 GL) {
        GL.glValidateProgram(mProgramId);

        return GL.glGetProgramiv(mProgramId, OpenGLES20.GL_VALIDATE_STATUS) != OpenGLES20.GL_FALSE;
    }


    /** Installs this GpuProgram as part of the OpenGL rendering state. */
    public void use(OpenGLES20 GL) {
        GL.glUseProgram(mProgramId);
    }


    /** Alias for getId(). */
    public int getProgram() {
        return mProgramId;
    }


    public int getId() {
        return mProgramId;
    }


    private void check() {
        if (!mIsInitialized) {
            throw new IllegalStateException(TAG+" not yet fully initialized!");
        }
    }
}

