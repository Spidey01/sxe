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

package com.spidey01.sxe.core.graphics;

import com.spidey01.sxe.core.logging.Log;

import java.util.Set;
// reccommended Set implementation.
// import java.util.concurrent.CopyOnWriteArraySet;

/** Facet providing process and resource independent rendering of graphics.
 *
 * To seperate the data to be rendered, {@link GraphicsTechnique}s are used to provide the process of rendering.
 * TODO: How do we stitch then together.
 *
 */

public class GraphicsFacet
    implements RenderableObject, VertexBufferTechnique.Capable
{
    private static final String TAG = "GraphicsFacet";

    private final Display mDisplay;

    private GraphicsTechnique mTechnique = null;

    /** X.
     *
     * @param techniques A set of techniques to be used for rendering our graphics.
     */

    public GraphicsFacet(Display display) {
        mDisplay = display;
    }


    @Override
    public void draw() {
        if (mTechnique == null) {
            mTechnique = mDisplay.getTechnique(self);
        }

        mTechnique.draw(self);
    }
}

