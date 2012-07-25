package com.spidey01.sxe.snakegame.pc;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.pc.PcConfiguration;
import com.spidey01.sxe.pc.PcDisplay;
import com.spidey01.sxe.pc.PcInputManager;
import com.spidey01.sxe.snakegame.lib.SnakeGame;

import com.spidey01.sxe.core.*;
import com.spidey01.sxe.pc.*;
import java.io.*;


class Main {

    public static void main(String[] args) {
        GameEngine g = PcConfiguration.setup(new SnakeGame(), "640 x 480");
        g.start();
            try {
                Resource vert = g.getResources().load("shaders/shader.vert", new ResourceFactory<GlslShader>(){
                    public Shader make(Shader.Type type, InputStream is, final String path) {
                        return new LwjglGlslShader(type, is, path);
                    }
                });
                Resource frag = g.getResources().load("shaders/shader.frag", LwjglGlslShader.class);
            } catch(Exception fml) {
                Log.wtf("Main", "Failed resource loads", fml);
            }
        g.mainLoop();
        g.stop();
    }
}

