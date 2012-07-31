/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

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

