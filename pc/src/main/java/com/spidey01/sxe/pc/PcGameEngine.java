package com.spidey01.sxe.pc;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;

/** Class implementing the game engine for PC/Mac hardware. */
public class PcGameEngine extends GameEngine {

    public PcGameEngine(Game app) {
        super();

        mApp = app;
        if (mApp == null) {
            throw new IllegalArgumentException("app can't be null!");
        }
    }

    @Override
    public boolean start() {
        System.out.println("PcGameEngine.start() called");

        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            return false;
        }
        super.start();

        return true;
    }
}

