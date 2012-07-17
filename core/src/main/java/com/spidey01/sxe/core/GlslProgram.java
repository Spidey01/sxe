package com.spidey01.sxe.core;

import java.util.List;

public interface GlslProgram {
    int getProgram();
    List<GlslShader> getShaders();
    void addShader(GlslShader shader);
    boolean link();
    boolean validate();
    String getInfoLog();
}

