package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.Shader;
import com.spidey01.sxe.core.ResourceFactory;
import com.spidey01.sxe.core.Log;

import java.io.InputStream;

public class LwjglGlslShaderFactory
    implements ResourceFactory<LwjglGlslShader>
{
    private static final String TAG = "LwjglGlslShaderFactory";

    public Shader make(Shader.Type type, InputStream is, final String path) {
        return new LwjglGlslShader(type, is, path);
    }
}

