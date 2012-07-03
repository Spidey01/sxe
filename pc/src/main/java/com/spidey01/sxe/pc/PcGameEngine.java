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

        mGame = app;
        if (mGame == null) {
            throw new IllegalArgumentException("app can't be null!");
        }
    }

    @Override
    public boolean start() {
        System.out.println("PcGameEngine.start() in thread "+Thread.currentThread().getId());

        mInput = new PcInputManager();

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

    @Override
    public void stop() {
        super.stop();
		Display.destroy();

        System.out.println("PcGameEngine.stop() done in thread "+Thread.currentThread().getId());
    }

    public void mainLoop() {
		while (!mGame.stopRequested() && !Display.isCloseRequested()) {
            getInput().poll();
			Display.update();

            System.out.println("GameEngine.mainLoop() spun in thread "+Thread.currentThread().getId());
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException iex) {
                System.out.println("GameEngine.mainLoop() interrupted in thread "+Thread.currentThread().getId());
                stop();
            }
		}
    }

}

