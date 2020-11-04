/*-
 * Copyright (c) 2020-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "Player.h"

#include <sxe/graphics/VertexVertexMesh.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/logging.hpp>
#include <sxe/resource/ResourceHandle.hpp>
#include <sxe/scene/SceneManager.hpp>

using std::bind;
using std::make_shared;
using std::make_unique;
using sxe::graphics::VertexVertexMesh;
using sxe::resource::ResourceHandle;

namespace demos {

const Player::string_type Player::TAG = "Player";

Player::Player()
    : mEntity(make_shared<sxe::scene::Entity>())
    , mInputFacet(nullptr)
    , mGraphicsFacet(nullptr)
{
    Log::xtrace(TAG, "Player()");
}

Player::~Player()
{
    Log::xtrace(TAG, "~Player()");

    Log::v(TAG, "mInputFacet.reset()");
    if (mEntity)
        mEntity->setInputFacet(nullptr);
    mInputFacet.reset();
}

Player::Entity::shared_ptr Player::getEntity() const
{
    return mEntity;
}

bool Player::setupResources(sxe::resource::ResourceManager& loader)
{
    Log::xtrace(TAG, "setupResources()");

    try {
        Log::v(TAG, "Loading player.mesh");
        ResourceHandle::unique_ptr res = loader.load(string_type("player.mesh"));
        if (!res) {
            Log::w(TAG, "ResourceManager::load() returned nullptr!");
            return false;
        }

        Log::v(TAG, "Getting player.mesh vertices");
        VertexVertexMesh::unique_ptr mesh = res->asVertexVertexMesh();
        if (!mesh) {
            Log::w(TAG, "ResourceHandle::asVertexVertexMesh() returned nullptr!");
            return false;
        }
        if (mesh->vertices().empty()) {
            Log::e(TAG, "No vertices loaded from player.mesh!");
            return false;
        }

        mesh->solidFill({1, 1, 1, 1});

        Log::v(TAG, "Setting up mGraphicsFacet.");
        mGraphicsFacet = make_shared<sxe::graphics::GraphicsFacet>(mesh->vertices());
        mEntity->setGraphicsFacet(mGraphicsFacet);
    } catch (std::exception& ex) {
        Log::e(TAG, "setupResources() failed", ex);
        return false;
    }

    return true;
}

bool Player::setupInput(sxe::input::InputManager& controller)
{
    Log::xtrace(TAG, "setupInput()");

    mInputFacet = make_shared<sxe::input::InputFacet>(controller);

    mInputFacet->addKeyListener(InputCode::IC_UP_ARROW, bind(&Player::onUpArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_LEFT_ARROW, bind(&Player::onLeftArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_RIGHT_ARROW, bind(&Player::onRightArrow, this, std::placeholders::_1));
    mInputFacet->addKeyListener(InputCode::IC_SPACE, bind(&Player::onSpaceBar, this, std::placeholders::_1));

    mEntity->setInputFacet(mInputFacet);

    return true;
}

bool Player::onUpArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onUpArrow(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "AHEAD, FULL!" << std::endl;

    return true;
}

bool Player::onLeftArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onLeftArrow(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "HARD TO PORT!" << std::endl;

    return true;
}

bool Player::onRightArrow(KeyEvent event)
{
    Log::xtrace(TAG, "onRightArrow(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "HARD TO STARBOARD!" << std::endl;

    return true;
}

bool Player::onSpaceBar(KeyEvent event)
{
    Log::xtrace(TAG, "onSpaceBar(): event.toString(): " + event.toString());
    if (!event.isKeyUp())
        return false;

    std::cout << "PEW, PEW, PEW" << std::endl;

    return true;
}

}
