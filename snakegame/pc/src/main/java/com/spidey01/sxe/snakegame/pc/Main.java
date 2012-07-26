package com.spidey01.sxe.snakegame.pc;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.LogSink;
import com.spidey01.sxe.pc.PcConfiguration;
import com.spidey01.sxe.pc.PcDisplay;
import com.spidey01.sxe.pc.PcInputManager;
import com.spidey01.sxe.snakegame.lib.SnakeGame;

import com.spidey01.sxe.core.*;
import com.spidey01.sxe.pc.*;
import java.io.*;


class Main {

    public static void main(String[] args) {
        // for debugging
        Log.add(new LogSink(System.out, Log.DEBUG));

        GameEngine g = PcConfiguration.setup(new SnakeGame(), "640 x 480");
        g.start();
        g.mainLoop();
        g.stop();
    }
}

