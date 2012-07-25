package com.spidey01.sxe.core;

import java.io.InputStream;

public interface GlslShader {
    public enum Type {
        VERTEX, FRAGMENT
    }
    boolean compile(String fileName);
    boolean compile(Type type, InputStream source);
    int getShader();
    String getInfoLog();
}

