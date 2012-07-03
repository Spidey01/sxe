package com.spidey01.sxe.snakegame;

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;

class SnakeGame extends Game {

    @Override
    public boolean start(GameEngine ge) {
        super.start(ge);

        System.out.println("Snake Game is starting via thread "+Thread.currentThread().getId()+"!");

        /* setup controls, these could just as easily be from a file */

        ge.getInput().bindKey("W", new Action() {
            @Override public void execute() {
                System.out.println("Move Up via thread "+Thread.currentThread().getId()+"!");
            }
        });
        ge.getInput().bindKey("S", new Action() {
            @Override public void execute() {
                System.out.println("Move Down via thread "+Thread.currentThread().getId()+"!");
            }
        });
        ge.getInput().bindKey("A", new Action() {
            @Override public void execute() {
                System.out.println("Move Left via thread "+Thread.currentThread().getId()+"!");
            }
        });
        ge.getInput().bindKey("D", new Action() {
            @Override public void execute() {
                System.out.println("Move Right via thread "+Thread.currentThread().getId()+"!");
            }
        });
        ge.getInput().bindKey("ESCAPE", new Action() {
            @Override public void execute() {
                System.out.println("Quit via thread "+Thread.currentThread().getId()+"!");
                requestStop();
            }
        });

        return true;
    }

    @Override
    public void stop() {
        System.out.println("Snake Game is stopping via thread "+Thread.currentThread().getId()+"!");
        super.stop();
    }
}

