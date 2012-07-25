package com.spidey01.sxe.snakegame.pc;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.pc.PcConfiguration;
import com.spidey01.sxe.pc.PcDisplay;
import com.spidey01.sxe.pc.PcInputManager;
import com.spidey01.sxe.snakegame.lib.SnakeGame;



class Main {

    public static void main(String[] args) {
        GameEngine g = PcConfiguration.setup(new SnakeGame(), "640 x 480");
        g.start();
        g.mainLoop();
        g.stop();
    }
}

