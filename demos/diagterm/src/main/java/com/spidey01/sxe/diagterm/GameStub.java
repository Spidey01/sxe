/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

package com.spidey01.sxe.diagterm;

import com.spidey01.sxe.core.Console;
import com.spidey01.sxe.core.Game;
import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.cmds.EchoCommand;
import com.spidey01.sxe.core.cmds.QuitCommand;
import com.spidey01.sxe.core.cmds.SetCommand;


public class GameStub
    extends Game
{
    private static final String TAG = "GameStub";

    private Console mConsole = new Console();

    @Override
    public String getName() {
        return "diagterm";
    }


    @Override
    public boolean start(GameEngine engine) {
        if (!super.start(engine)) {
            return false;
        }
        mGameEngine.getInputManager().addKeyListener(mConsole);
        mConsole.setVisible(true);
        mConsole.addCommand(new QuitCommand(mGameEngine));
        mConsole.addCommand(new EchoCommand());
        mConsole.addCommand(new SetCommand(mGameEngine.getSettings()));
        return mConsole.isVisible();
    }


    @Override
    public void stop() {
        mConsole.setVisible(false);
        super.stop();
    }


    @Override
    public void tick() {
    }


}

