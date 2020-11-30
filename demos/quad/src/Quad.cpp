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

#include <sxe/graphics/GraphicsFacet.hpp>
#include <sxe/graphics/VertexVertexMesh.hpp>
#include <sxe/input/InputFacet.hpp>
#include <sxe/logging.hpp>
#include <sxe/resource/ResourceManager.hpp>
#include <sxe/scene/SceneManager.hpp>

using std::string;
using sxe::GameEngine;
using std::runtime_error;

namespace demos {

const string Quad::TAG = "Quad";

/* Don't have fancy resource loader yet. */
// const string Quad::MESH_RESOURCE_PATH = "default://quad.dat";
const string Quad::MESH_RESOURCE_PATH = "quad.dat";

Quad::Quad(GameEngine& engine)
    : sxe::scene::Entity()
    , mGameEngine(engine)
{
    Log::i(TAG, "Quad object created.");

    setInputFacet(std::make_shared<sxe::input::InputFacet>(engine.getInputManager()));

    /*
     * Setup our vertices to be rendered.
     */

    try {
        // Don't have ResourceHandle::asWhatWeWant() yet, so just print the vertex data.
        auto rh = mGameEngine.getResourceManager().load(MESH_RESOURCE_PATH);
        if (!rh)
            throw runtime_error(TAG + ": ResourceManager::load() failed!");

        auto input = rh->asInputStream();
        if (input == nullptr)
            throw std::runtime_error(TAG + ": Failed openning " + MESH_RESOURCE_PATH);

        sxe::graphics::VertexVertexMesh mesh(*input);

        // Copy with color info added.
        sxe::graphics::GraphicsFacet::vertex_vector vertices;

        for (sxe::graphics::Vertex v : mesh.vertices()) {
            v.color = {0.5, 0.0, 0.5, 1.0};
            vertices.push_back(v);
        }

        setGraphicsFacet(std::make_shared<sxe::graphics::GraphicsFacet>(vertices));
        getGraphicsFacet()->setCamera(mGameEngine.getSceneManager().camera());

    } catch (std::exception& ex) {
        Log::wtf(TAG, "Failed loading " + MESH_RESOURCE_PATH, ex);
    }
}

Quad::~Quad()
{
    Log::i(TAG, "Quad object destroyed.");
}

}


