package com.spidey01.sxe.snakegame;

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;

class SnakeGame extends Game {

    @Override
    public boolean start(GameEngine ge) {
        super.start(ge);

        System.out.println("Snake Game is starting!");

        /* setup controls, these could just as easily be from a file */

        ge.getInput().bindKey("W", new Action() {
            @Override public void execute() {
                System.out.println("Move Up");
            }
        });
        ge.getInput().bindKey("S", new Action() {
            @Override public void execute() {
                System.out.println("Move Down");
            }
        });
        ge.getInput().bindKey("A", new Action() {
            @Override public void execute() {
                System.out.println("Move Left");
            }
        });
        ge.getInput().bindKey("D", new Action() {
            @Override public void execute() {
                System.out.println("Move Right");
            }
        });

        return true;
    }

    @Override
    public void stop() {
        System.out.println("Snake Game is stopping!");
        super.stop();
    }
}

