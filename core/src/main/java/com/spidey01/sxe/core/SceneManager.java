/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.common.AbstractSubsystem;
import com.spidey01.sxe.core.graphics.RenderableObject;
import com.spidey01.sxe.core.logging.Log;

import java.util.List;
import java.util.ArrayList;

public class SceneManager extends AbstractSubsystem {
    private final static String TAG = "SceneManager";

    private List<RenderableObject> mRenderableObjects = new ArrayList<RenderableObject>();

    public SceneManager() {
    }


    @Override
    public String name() {
        return TAG;
    }


    @Override
    public void initialize(GameEngine engine) {
        Log.d(TAG, "initialize(", engine, ")");
        super.initialize(engine);
    }


    @Override
    public void reinitialize(GameEngine engine) {
        super.reinitialize(engine);
    }


    @Override
    public void uninitialize() {
        Log.d(TAG, "uninitialize()");
        super.uninitialize();
    }


    /** Adds a renderable object to the scene.
     */
    public void add(RenderableObject object) {
        mRenderableObjects.add(object);
    }


    /** Update the scene.
     */
    public void update() {
        Log.xtrace(TAG, "update()");
        // try {
            for (RenderableObject o : mRenderableObjects) {
                Log.xtrace(TAG, "Updating scene member "+o);
                o.draw();
            }
        // } catch(RuntimeException e) {
            // Log.wtf(TAG, "Error updating scene:", e);
        // }
    }


}

