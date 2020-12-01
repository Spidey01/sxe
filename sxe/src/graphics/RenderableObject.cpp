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

#include <sxe/graphics/RenderableObject.hpp>

#include <sxe/graphics/SystemMemory.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/logging.hpp>
#include <sxe/resource/ResourceManager.hpp>
#include <sxe/scene/SceneManager.hpp>

using std::make_shared;
using std::make_unique;
using std::to_string;
using sxe::graphics::GraphicsFacet;
using sxe::input::InputFacet;
using sxe::resource::ResourceFacet;
using sxe::scene::Entity;

namespace sxe { namespace graphics {

const RenderableObject::string_type RenderableObject::TAG = "RenderableObject";

RenderableObject::RenderableObject(GameEngine& system,
                                   const string_type& resource,
                                   std::optional<resource::ResourceFacet::Filter> resourceFilter,
                                   std::optional<FrameListener> frameListener, std::optional<input::KeyListener> keyListener)
    : mGameEngine(system)
    , mEntity(make_shared<Entity>())
{
    Log::test(TAG, "Setting SceneManager");
    mEntity->setSceneManager(&mGameEngine.getSceneManager());

    Log::test(TAG, "Creating resources");
    mEntity->setResourceFacet(make_shared<ResourceFacet>(mGameEngine.getResourceManager()));

    graphics::SystemMemory buffer;
    mEntity->getResourceFacet()->load(resource, resourceFilter.value_or(&ResourceFacet::filter), buffer);

    Log::test(TAG, "Creating graphics");
    mEntity->setGraphicsFacet(make_shared<GraphicsFacet>(buffer));
    mEntity->getGraphicsFacet()->setCamera(mEntity->getSceneManager()->camera());
    if (frameListener.has_value())
        mEntity->getGraphicsFacet()->setFrameListener(frameListener.value());

    Log::test(TAG, "Creating input");
    if (keyListener)
        mEntity->setInputFacet(make_shared<InputFacet>(mGameEngine.getInputManager(), keyListener.value()));
    else
        mEntity->setInputFacet(make_shared<InputFacet>(mGameEngine.getInputManager()));
}

RenderableObject::~RenderableObject()
{

}

GameEngine& RenderableObject::engine() const
{
    return mGameEngine;
}

Entity::shared_ptr RenderableObject::entity()
{
    return mEntity;
}

GraphicsFacet::shared_ptr RenderableObject::graphics()
{
    return mEntity->getGraphicsFacet();
}

InputFacet::shared_ptr RenderableObject::input()
{
    return mEntity->getInputFacet();
}

} }

