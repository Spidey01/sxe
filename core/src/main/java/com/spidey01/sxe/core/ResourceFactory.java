package com.spidey01.sxe.core;

import java.io.InputStream;

public interface ResourceFactory<T extends Shader> {
    public Shader make(Shader.Type type, InputStream is, final String path);
}
