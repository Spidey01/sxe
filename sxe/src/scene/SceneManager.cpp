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

#include "sxe/scene/SceneManager.hpp"

#include <sxe/GameEngine.hpp>
#include <sxe/graphics/DisplayManager.hpp>
#include <sxe/logging.hpp>

using std::to_string;

namespace sxe { namespace scene {

const SceneManager::string_type SceneManager::TAG = "SceneManager";

SceneManager::SceneManager()
    : Subsystem(TAG)
    , mEntityMutex()
    , mEntities()
    , mDrawingTechnique(nullptr)
{
}

SceneManager::~SceneManager()
{
}

bool SceneManager::initialize(GameEngine& engine)
{
    Log::xtrace(TAG, "initialize()");

    return Subsystem::initialize(engine);
}

bool SceneManager::uninitialize()
{
    Log::xtrace(TAG, "uninitialize()");

    lock_guard synchronized(mEntityMutex);

    for (auto eptr : mEntities)
        removeEntity(eptr);

    mDrawingTechnique.reset();

    return Subsystem::uninitialize();
}


void SceneManager::update()
{
    if (!mDrawingTechnique) {
        Log::d(TAG, "update(): lazy of initialization of mDrawingTechnique");
        mDrawingTechnique = getGameEngine().getDisplayManager().getTechnique();

        if (!mDrawingTechnique) {
            Log::e(TAG, "update(): DisplayManager has no DrawingTechnique!");
            return ;
        }
    }

    lock_guard synchronized(mEntityMutex);

    mDrawingTechnique->frameStarted();

    for (Entity::shared_ptr entity : mEntities) {
        mDrawingTechnique->draw(*entity->getGraphicsFacet());
    }

    mDrawingTechnique->frameEnded();
}

void SceneManager::addEntity(Entity::shared_ptr entity)
{
    Log::test(TAG, "addEntity(): (uintptr_t)entity.get(): " + to_string((uintptr_t)entity.get()));

    lock_guard synchronized(mEntityMutex);

    entity->setSceneManager(this);

    mEntities.push_back(entity);
}

void SceneManager::removeEntity(Entity::shared_ptr entity)
{
    Log::test(TAG, "removeEntity(): (uintptr_t)entity.get(): " + to_string((uintptr_t)entity.get()));

    lock_guard synchronized(mEntityMutex);

    entity->setSceneManager(nullptr);

    mEntities.remove(entity);
}

} }