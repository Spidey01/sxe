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

import java.util.Map;
import java.util.HashMap;

/** WRITE ME.
 *
 * This is here to make it easier to reduce the amount of passing things around
 * as part of SxE. It contains accessors for all the common components in a
 * configuration, as well as a map to incorporate your own at runtime.
 *
 * @see com.spidey01.sxe.pc.PcConfiguration
 * @see com.spidey01.sxe.android.AndroidConfiguration
 */
public class GameContext {
    private static final String TAG = "Context";

    private Console mConsole;
    private Display mDisplay;
    private Game mGame;
    private GameEngine mEngine;
    private InputManager mInput;
    private ResourceManager mResources;
    private Map<String, Object> mOptions;

    public GameContext() {
        mOptions = new HashMap<String, Object>();
    }

    /** The constructor call from hell.
     *
     * Initialize the various parameters of the SxE game context.  This is a
     * simple an effective way to allow you to "Bundle" up data that many
     * subsystems may need access to without  having to rely on a singleton.
     *
     * @param opts A Map to for the *Opt methods. If null, an empty HashMap will be used.
     */
    public GameContext(Console console, Display display, Game game,
        GameEngine engine, InputManager input, ResourceManager resources,
        Map<String, Object> opts)
    {
        mConsole = console;
        mDisplay = display;
        mGame = game;
        mEngine = engine;
        mInput = input;
        mResources = resources;

        if (opts == null) {
            mOptions = opts;
        } else {
            mOptions = new HashMap<String, Object>();
        }
    }

    public Console getConsole() {
        return mConsole;
    }

    public Display getDisplay() {
        return mDisplay;
    }

    public Game getGame() {
        return mGame;
    }

    public GameEngine getEngine() {
        return mEngine;
    }

    public InputManager getInput() {
        return mInput;
    }

    public ResourceManager getResources() {
        return mResources;
    }

    public Object getOptions() {
        return mOptions;
    }

    public Object getOpt(String name) {
        return mOptions.get(name);
    }

    public void setOpt(String name, Object o) {
        mOptions.put(name, o);
    }

    public void removeOpt(String name) {
        mOptions.remove(name);
    }

    public GameContext setConsole(Console value) {
        mConsole = value;
        return this;
    }

    public GameContext setDisplay(Display value) {
        mDisplay = value;
        return this;
    }

    public GameContext setGame(Game value) {
        mGame = value;
        return this;
    }

    public GameContext setEngine(GameEngine value) {
        mEngine = value;
        return this;
    }

    public GameContext setInput(InputManager value) {
        mInput = value;
        return this;
    }

    public GameContext setResources(ResourceManager value) {
        mResources = value;
        return this;
    }

    public GameContext setOptions(Map<String, Object> value) {
        mOptions = value;
        return this;
    }
}

