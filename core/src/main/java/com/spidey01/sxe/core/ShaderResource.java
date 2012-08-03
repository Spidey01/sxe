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

package com.spidey01.sxe.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ShaderResource extends FileResource {
    private Shader mShader;
    private final OpenGl mOpenGl;
    private final ShaderFactory<? extends Shader> mShaderFactory;
    private final Class<? extends Shader> mShaderClass;
    private final static String TAG = "ShaderResource";

    public ShaderResource(Type type, String fileName, ResourceLoader loader,
        ShaderFactory<? extends Shader> factory)
    {
        super(type, fileName, loader);
        mShaderFactory = factory;
        mOpenGl = null;
        mShaderClass = null;

        assert Utils.shaderTypeToResourceType(GlslShader.getType(mFileName)) == mType
            : "Shader/Resource type should match :'(";
    }

    public ShaderResource(Type type, String fileName, ResourceLoader loader,
        OpenGl gl, Class<? extends Shader> shaderImplClass)
    {
        super(type, fileName, loader);
        mShaderFactory = null;
        mOpenGl = gl;
        mShaderClass = shaderImplClass;
    }

    public boolean load() {
        if (!super.load()) {
            return false;
        }

        if (mShaderFactory != null) {
            // user gave us a handy factory
            mShader = mShaderFactory.make(GlslShader.getType(mFileName), mInputStream, mFileName);
        } else if (mShaderClass != null) {
            // user gave us the bloody class!
            try {
                mShader = shaderFromClass(mShaderClass);
            } catch(NoSuchMethodException e) {
                Log.e(TAG, "Can't find a suitable ctor for "+mShaderClass, e);
            } catch(InstantiationException e) {
                Log.w(TAG, "Couldn't instantiate a "+mShaderClass, e);
                /* ^ Shouldn't be reached if setAccessible(true) above ^ */
            } catch (InvocationTargetException e) {
                Log.w(TAG, "No dice.", e);
            } catch(IllegalAccessException e) {
                Log.e(TAG, "Ctor for "+mShaderClass+"Is not accessible here.", e);
            }
        }

        return isLoaded();
    }

    public boolean isLoaded() {
        return mShader != null;
    }

    public Object getObject() {
        return mShader;
    }

    public Shader getShader() {
        return mShader;
    }

    /** Create a Shader from this class the resource.
     */
    public Shader shaderFromClass(Class<? extends Shader> shaderClass)
        throws NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException 
    {
        Shader.Type shaderType = GlslShader.getType(mFileName);

        Constructor ctor =
            shaderClass.getDeclaredConstructor(OpenGl.class,
                Shader.Type.class, InputStream.class, String.class);
        ctor.setAccessible(true);

        return (Shader)ctor.newInstance(mOpenGl, shaderType, mInputStream, mFileName);
    }
}

