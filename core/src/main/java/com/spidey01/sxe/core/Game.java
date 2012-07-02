package com.spidey01.sxe.core;

import com.spidey01.sxe.core.GameEngine;

public class Game {

    protected GameEngine ge;

    public boolean start(GameEngine ge) {
        System.out.println("Game.start() called");

        this.ge = ge;

        return true;
    }

    public void stop() {
        System.out.println("Game.stop() called");
    }

}

