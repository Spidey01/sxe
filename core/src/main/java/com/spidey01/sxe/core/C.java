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

