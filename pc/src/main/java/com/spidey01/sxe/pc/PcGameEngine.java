package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.Display;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.InputManager;

/** Class implementing the game engine for PC/Mac hardware. */
public class PcGameEngine extends GameEngine {

    public PcGameEngine(Display display, InputManager input, Game game) {
        super(display, input, game);
    }

    @Override
    public boolean start() {
        debug("PcGameEngine.start()");

        // mInput = new PcInputManager();

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
		while (!mGame.stopRequested() && !getDisplay().isCloseRequested()) {
            getInput().poll();
			getDisplay().update();

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

