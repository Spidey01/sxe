package com.spidey01.sxe.snakegame;

import com.spidey01.sxe.core.Game;

class SnakeGame extends Game {

    @Override
    public boolean start() {
        super.start();

        System.out.println("Snake Game is starting!");
        return true;
    }

    @Override
    public void stop() {
        System.out.println("Snake Game is stopping!");
        super.stop();
    }
}

