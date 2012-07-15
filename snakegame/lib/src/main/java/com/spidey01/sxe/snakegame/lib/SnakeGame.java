package com.spidey01.sxe.snakegame.lib;

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.Console;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.GameEngine;

import java.util.Random;

public class SnakeGame extends Game {
    private static final String TAG = "SnakeGame";
    private Console mConsole;

    @Override
    public boolean start(final GameEngine engine) {
        super.start(engine);

        Log.v(TAG, "Snake Game is starting.");

        mConsole = new Console(/* args we may need from mEngine */);
        setupControls();

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

    private static Random rand = new Random();
    @Override
    public void tick() {
        super.tick();

        try {
            // simulate taking a while to complete a tick.
            Thread.currentThread().sleep(rand.nextInt(25));
        } catch (InterruptedException iex) {
            Log.d(TAG, "tick() interrupted");
            stop();
        }
    }

    public void setupControls() {
        /* setup controls, these could just as easily be from a file */

        mGameEngine.getInput().bindKey("W", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Move Up");
            }
        });
        mGameEngine.getInput().bindKey("S", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Move Down");
            }
        });
        mGameEngine.getInput().bindKey("A", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Move Left");
            }
        });
        mGameEngine.getInput().bindKey("D", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Move Right");
            }
        });
        mGameEngine.getInput().bindKey("ESCAPE", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Quit");
                requestStop();
            }
        });
        mGameEngine.getInput().bindKey("BACK", new Action() {
            @Override public void execute() {
                Log.d(TAG, "Quit");
                requestStop();
            }
        });

    }
}

