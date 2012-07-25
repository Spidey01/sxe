package com.spidey01.sxe.core;

import java.io.InputStream;

public interface Shader {
    public enum Type {
        VERTEX, FRAGMENT
    }
    boolean compile(String fileName);
    boolean compile(Type type, InputStream source);
    int getShader();
    Type getType();
    String getFileName();
    String getInfoLog();
}

