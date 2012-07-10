package com.spidey01.sxe.snakegame.lib;

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.GameEngine;

public class SnakeGame extends Game {
    private static final String TAG = "SnakeGame";

    @Override
    public boolean start(final GameEngine ge) {
        super.start(ge);

        Log.v(TAG, "Snake Game is starting.");

        /* setup controls, these could just as easily be from a file */

        ge.getInput().bindKey("W", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Move Up");
            }
        });
        ge.getInput().bindKey("S", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Move Down");
            }
        });
        ge.getInput().bindKey("A", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Move Left");
            }
        });
        ge.getInput().bindKey("D", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Move Right");
            }
        });
        ge.getInput().bindKey("ESCAPE", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Quit");
                requestStop();
            }
        });
        ge.getInput().bindKey("BACK", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Quit");
                requestStop();
            }
        });

        return true;
    }

    @Override
    public void stop() {
        Log.v(TAG, "Snake Game is stopping.");
        super.stop();
    }

    @Override
    public int getTickRate() {
        return 40; // ticks per second
    }
}

