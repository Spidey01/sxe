package com.spidey01.sxe.core;

import java.io.InputStream;

public interface ResourceFactory<T extends GlslShader> {
    public GlslShader make(GlslShader.Type type, InputStream is, final String path);
}
