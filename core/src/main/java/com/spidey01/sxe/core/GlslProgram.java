package com.spidey01.sxe.core;

import java.util.List;

public interface GlslProgram {
    int getProgram();
    List<Shader> getShaders();
    void addShader(Shader shader);
    boolean link();
    boolean validate();
    String getInfoLog();
}

