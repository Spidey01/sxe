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

package com.spidey01.sxe.core;

import java.util.HashMap;

/** C -- static bundle of configuration class.
 *
 * The following configuration properties are provided with quick getters.
 *
 *      console   = Console
 *      display   = Display
 *      engine    = GameEngine
 *      game      = Game
 *      input     = InputManager
 *      resources = ResourcesManager
 *
 * Example: C.getEngine() returns a GameEngine from the 'engine' field.
 *
 * This is here to make it easier to reduce the amount of setup required for
 * launching the GameEngine.
 *
 * This class also serves as a way to simplify certain aspects of making a game, and
 * could be thought of as a runtime Singleton repository just as easily as a
 * global configuration.
 *
 * Client code (a Game) can use this as they see fit.
 *
 * SxE code (core, pc, android, ...) should support this but only use it for
 * dirty debug, testing, etc. Engine code should _never_ require that this
 * class be used but it may interact with it.
 *
 * @see com.spidey01.sxe.pc.PcConfiguration
 * @see com.spidey01.sxe.android.AndroidConfiguration
 */
public class C {
    private static HashMap<String, Object> mProps = new HashMap<String, Object>();
    private static final String TAG = "C";

    public static Object get(String what) {
        return mProps.get(what);
    }

    public static Object put(String what, Object value) {
        return mProps.put(what, value);
    }


    public static Game getGame() {
        Object o = mProps.get("game");
        assert o instanceof Game;

        return (Game)o;
    }

    public static Console getConsole() {
        Object o = mProps.get("console");
        assert o instanceof Console;

        return (Console)o;
    }

    public static Display getDisplay() {
        Object o = mProps.get("display");
        assert o instanceof Display;

        return (Display)o;
    }

    public static GameEngine getEngine() {
        Object o = mProps.get("engine");
        assert o instanceof GameEngine;

        return (GameEngine)o;
    }

    public static InputManager getInput() {
        Object o = mProps.get("input");
        assert o instanceof InputManager;

        return (InputManager)o;
    }

    public static ResourceManager getResources() {
        Object o = mProps.get("resources");
        assert o instanceof ResourceManager;

        return (ResourceManager)o;
    }
}

