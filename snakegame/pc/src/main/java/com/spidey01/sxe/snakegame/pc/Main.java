package com.spidey01.sxe.snakegame.pc;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.pc.PcDisplay;
import com.spidey01.sxe.pc.PcInputManager;
import com.spidey01.sxe.snakegame.lib.SnakeGame;

class Main {

    public static void main(String[] args) {
        GameEngine g = new GameEngine(
                new PcDisplay(),
                new PcInputManager(),
                new SnakeGame());

        g.start();
        g.mainLoop();
        g.stop();
    }

}

