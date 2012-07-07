package com.spidey01.sxe.snakegame.pc;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.pc.PcGameEngine;
import com.spidey01.sxe.snakegame.lib.SnakeGame;

class Main {

    public static void main(String[] args) {
        GameEngine g = new PcGameEngine(new SnakeGame());
        g.start();
        g.mainLoop();
        g.stop();
    }

}

