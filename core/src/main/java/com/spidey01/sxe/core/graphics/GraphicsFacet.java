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

/** Facet providing combining the process and resources needed to render to a {@link Display}.
 *
 * Data to be rendered is provided to the constructor through a {@link
 * RenderData}. You will need to setup this RenderData instance with sufficent
 * information for an available GraphicsTechnique.
 *
 * Exact process for rendering is performed by a {@link GraphicsTechnique}.
 *
 */

public class GraphicsFacet implements RenderableObject
{
    private static final String TAG = "GraphicsFacet";

    private final RenderData mRenderData;
    private final Display mDisplay;

    private GraphicsTechnique mTechnique = null;

    /** X.
     *
     * @param data Some {@link RenderData} to be drawn.
     * @param display The {@link Display} to render to.
     * @see GraphicsTechnique
     */

    public GraphicsFacet(RenderData data, Display display) {
        mRenderData = data;
        mDisplay = display;
    }


    /** Draw using a GraphicsTechnique.
     *
     * Note: we currently cache the first technique given by the Display; we do
     * not change this on subsequent draws even if the Display changes its
     * mind.
     */

    @Override
    public void draw() {
        if (mTechnique == null) {
            mTechnique = mDisplay.getTechnique(mRenderData);
        }

        mTechnique.draw(mRenderData);
    }
}

