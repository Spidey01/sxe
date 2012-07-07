package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/** Class implementing the game engine for PC/Mac hardware. */
public class PcGameEngine extends GameEngine {

    public PcGameEngine(com.spidey01.sxe.core.Display display, Game app) {
        super(display, app);
    }

    @Override
    public boolean start() {
        System.out.println("PcGameEngine.start() in thread "+Thread.currentThread().getId());

        mInput = new PcInputManager();

        super.start();

        return true;
    }

    @Override
    public void stop() {
        super.stop();

        System.out.println("PcGameEngine.stop() done in thread "+Thread.currentThread().getId());
    }

    /**
     * An implementation of GameEngine.mainLoop() suitable for PC/Mac systems.
     */
    @Override
    public void mainLoop() {
		while (!mGame.stopRequested() && !mDisplay.isCloseRequested()) {
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

