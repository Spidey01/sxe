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

#include "Quad.h"

#include <sxe/logging.hpp>

using std::string;
using sxe::GameEngine;

namespace demos {

const string Quad::TAG = "Quad";

const string Quad::MESH_RESOURCE_PATH = "default://quad.dat";

Quad::Quad(GameEngine& engine)
    : mGameEngine(engine)
    , mInputFacet(engine.getInputManager())
{
    Log::i(TAG, "Quad object created.");

    /*
     * Setup our vertices to be rendered.
     */

    try {
        #if 0
        mRenderData.setMesh(
                            mGameEngine.getResourceManager().load(
                                                                  MESH_RESOURCE_PATH).asVertexVertexMesh());
        #endif
    } catch(std::exception& ex) {
        Log::wtf(TAG, "Failed loading " + MESH_RESOURCE_PATH, ex);
    }
}


Quad::~Quad()
{
    Log::i(TAG, "Quad object destroyed.");
}

}


