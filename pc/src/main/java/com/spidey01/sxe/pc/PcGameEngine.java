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
        debug("PcGameEngine.start()");

        mInput = new PcInputManager();

        super.start();

        return true;
    }

    @Override
    public void stop() {
        super.stop();

        debug("PcGameEngine.stop()");
    }

    /**
     * An implementation of GameEngine.mainLoop() suitable for PC/Mac systems.
     */
    @Override
    public void mainLoop() {
		while (!mGame.stopRequested() && !mDisplay.isCloseRequested()) {
            getInput().poll();
			Display.update();

            // debug("GameEngine.mainLoop() spun");
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException iex) {
                debug("GameEngine.mainLoop() interrupted");
                stop();
            }
		}
    }

}

