package com.spidey01.sxe.snakegame.lib;

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;

public class SnakeGame extends Game {

    @Override
    public boolean start(final GameEngine ge) {
        super.start(ge);

        System.out.println("Snake Game is starting via thread "+Thread.currentThread().getId()+"!");

        /* setup controls, these could just as easily be from a file */

        ge.getInput().bindKey("W", new Action() {
            @Override public void execute() {
                ge.debug("Move Up");
            }
        });
        ge.getInput().bindKey("S", new Action() {
            @Override public void execute() {
                ge.debug("Move Down");
            }
        });
        ge.getInput().bindKey("A", new Action() {
            @Override public void execute() {
                ge.debug("Move Left");
            }
        });
        ge.getInput().bindKey("D", new Action() {
            @Override public void execute() {
                ge.debug("Move Right");
            }
        });
        ge.getInput().bindKey("ESCAPE", new Action() {
            @Override public void execute() {
                ge.debug("Quit");
                requestStop();
            }
        });

        return true;
    }

    @Override
    public void stop() {
        ge.debug("Snake Game is stopping");
        super.stop();
    }
}

