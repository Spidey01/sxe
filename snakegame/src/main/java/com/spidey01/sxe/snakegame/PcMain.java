package com.spidey01.sxe.snakegame;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.pc.PcGameEngine;

class PcMain {

    public static void main(String[] args) {
        GameEngine g = new PcGameEngine(new SnakeGame());
        System.out.println("OKAY!");
    }

}

