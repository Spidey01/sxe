package com.spidey01.sxe.snakegame.lib;

import com.spidey01.sxe.core.Action;
import com.spidey01.sxe.core.Console;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.KeyEvent;
import com.spidey01.sxe.core.KeyListener;

import java.util.Random;

public class SnakeGame
    extends Game
    implements KeyListener
{
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

        String[] keys = new String[]{
            "ESCAPE", "BACK",
            "W", "A", "S", "D",
        };
        for (String k : keys) {
            mGameEngine.getInput().addKeyListener(k, this);
        }

        // the console will only steal key events when it is visable.
        mGameEngine.getInput().addKeyListener(mConsole);
    }

    // Very simple way of doing some key binds
    public boolean onKey(KeyEvent event) {
        // if (Log.isLoggable(TAG, Log.VERBOSE)) {
            // Log.v(TAG, "onKey() called for event "+event);
        // }

        if (!event.isKeyDown()) {

            // quit game
            if ((event.getKeyName().equals("ESCAPE")
                || event.getKeyName().equals("BACK")))
            {
                Log.d(TAG, "Quit");
                requestStop();
                return true;
            }
        } else {

            // movement keys
            if (event.getKeyName().equals("W")) {
                Log.d(TAG, "Move Up");
                return true;
            }

            if (event.getKeyName().equals("S")) {
                Log.d(TAG, "Move Down");
                return true;
            }
            
            if (event.getKeyName().equals("A")) {
                Log.d(TAG, "Move Left");
                return true;
            }
            
            if (event.getKeyName().equals("D")) {
                Log.d(TAG, "Move Right");
                return true;
            }
        }

        return false;
    }
}

