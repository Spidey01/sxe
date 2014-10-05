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

package com.spidey01.sxe.core.entities;

import com.spidey01.sxe.core.graphics.GraphicsFacet;
import com.spidey01.sxe.core.graphics.RenderData;
import com.spidey01.sxe.core.input.InputCode;
import com.spidey01.sxe.core.input.InputFacet;
import com.spidey01.sxe.core.input.InputManager;
import com.spidey01.sxe.core.input.KeyEvent;
import com.spidey01.sxe.core.input.KeyListener;
import com.spidey01.sxe.core.logging.Log;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Entity
{
    private static final String TAG = "Entity";

    protected boolean mIsAlive;

    private InputFacet mInputFacet;
    private GraphicsFacet mGraphicsFacet;
    private RenderData mRenderData;


    public boolean isAlive() {
        return mIsAlive;
    }


    /** Set the InputManager associated with this Entity's {@link InputFacet}.
     *
     * Until this method is called, either in a subclasses constructor or directly, there will
     */
    public void setInputManager(InputManager manager) {
        if (mInputFacet != null) {
            /* InputFacet doesn't have a clean up routine yet! */
            throw new UnsupportedOperationException(
                "InputFacet already configured for this entity. Changing is not supported yet!"
            );
        }
        mInputFacet = new InputFacet(manager);
    }


    public InputFacet getInputFacet() {
        return mInputFacet;
    }


    public void setGraphicsFacet(GraphicsFacet facet) {
        mGraphicsFacet = facet;
    }


    public GraphicsFacet getGraphicsFacet() {
        return mGraphicsFacet;
    }


    public void setRenderData(RenderData data) {
        mRenderData = data;
    }


    public RenderData getRenderData() {
        return mRenderData;
    }
}

