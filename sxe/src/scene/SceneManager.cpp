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
using sxe::graphics::DrawingTechnique;
using sxe::graphics::GraphicsFacet;

namespace sxe { namespace scene {

const SceneManager::string_type SceneManager::TAG = "SceneManager";

SceneManager::SceneManager()
    : Subsystem(TAG)
    , mOnChangedListenerId(SIZE_MAX)
    , mEntityMutex()
    , mEntities()
    , mWarnedNoTechnique(false)
    , mLastDrawingTechniqueName("N/A")
    , mDrawingTechnique(nullptr)
    , mCamera(nullptr)
{
}

SceneManager::~SceneManager()
{
}

bool SceneManager::initialize(GameEngine& engine)
{
    Log::xtrace(TAG, "initialize()");

    auto listener = std::bind(&SceneManager::onSettingChanged, this, std::placeholders::_1);
    mOnChangedListenerId = engine.getSettings().addChangeListener(listener, "sxe.graphics.method");
    mWarnedNoTechnique = true;
    mCamera = std::make_shared<Camera>();

    return Subsystem::initialize(engine);
}

bool SceneManager::uninitialize()
{
    Log::xtrace(TAG, "uninitialize()");

    getSettings().removeChangeListener(mOnChangedListenerId);
    mOnChangedListenerId = SIZE_MAX;

    for (auto eptr : mEntities)
        removeEntity(eptr);

    mCamera.reset();
    mDrawingTechnique.reset();
    mWarnedNoTechnique = false;

    return Subsystem::uninitialize();
}


void SceneManager::update()
{
    if (mEntities.empty())
        return;

    lock_guard synchronized(mEntityMutex);

    if (!mDrawingTechnique) {
        Log::d(TAG, "update(): lazy of initialization of mDrawingTechnique");
        mDrawingTechnique = getGameEngine().getDisplayManager().getTechnique();

        if (!mDrawingTechnique) {
            if (!mWarnedNoTechnique) {
                Log::w(TAG, "update(): DisplayManager has no DrawingTechnique!");
                mWarnedNoTechnique = true;
            }
            return;
        }
    }

    if (mLastDrawingTechniqueName != mDrawingTechnique->name()) {
        Log::d(TAG, "update(): mDrawingTechnique changed from " + mLastDrawingTechniqueName + " to " + mDrawingTechnique->name());
        mLastDrawingTechniqueName = mDrawingTechnique->name();
    }

    mDrawingTechnique->frameStarted();

    for (Entity::shared_ptr entity : mEntities) {
        mDrawingTechnique->draw(*entity->getGraphicsFacet());
    }

    mDrawingTechnique->frameEnded();
}

void SceneManager::onSettingChanged(string_type key)
{
    Log::xtrace(TAG, "onSettingChanged(): key: " + key);

    config::Settings& settings = getSettings();

    if (key == "sxe.graphics.method") {
        lock_guard synchronized(mEntityMutex);

        string_type name = settings.getString(key);

        Log::v(TAG, "onSettingChanged(): mDrawingTechnique = " + name);
        mDrawingTechnique = getGameEngine().getDisplayManager().getTechnique();
        if (!mDrawingTechnique) {
            Log::v(TAG, "onSettingChanged(): no drawing technique set");
            return;
        }
        mWarnedNoTechnique = false;
    }
}

bool SceneManager::prepare(GraphicsFacet& facet)
{
    Log::xtrace(TAG, "prepare()");

    if (mDrawingTechnique) {
        if (facet.getSegment().buffer != nullptr) {
            Log::w(TAG, "prepare(): GraphicsFacet already had a Segment with a buffer_ptr set.");
        }
        mDrawingTechnique->buffer(facet);
    }

    return true;
}

void SceneManager::addEntity(Entity::shared_ptr entity)
{
    Log::test(TAG, "addEntity(): (uintptr_t)entity.get(): " + to_string((uintptr_t)entity.get()));

    lock_guard synchronized(mEntityMutex);

    entity->setSceneManager(this);

    GraphicsFacet::shared_ptr gfx = entity->getGraphicsFacet();
    if (gfx) {
        // XXX: getting bad_alloc because !glIsBuffer if we call prepare here.
    } else {
        Log::w(TAG, "addEntity(): no GraphicsFacet for (uintptr_t)entity.get(): " + to_string((uintptr_t)entity.get()));
    }

    mEntities.push_back(entity);
}

void SceneManager::removeEntity(Entity::shared_ptr entity)
{
    Log::test(TAG, "removeEntity(): (uintptr_t)entity.get(): " + to_string((uintptr_t)entity.get()));

    lock_guard synchronized(mEntityMutex);

    GraphicsFacet::shared_ptr gfx = entity->getGraphicsFacet();
    if (gfx) {
        Log::v(TAG, "removeEntity(): unbuffering");
        if (mDrawingTechnique)
            mDrawingTechnique->unbuffer(*gfx);
    } else {
        Log::w(TAG, "removeEntity(): no GraphicsFacet for (uintptr_t)entity.get(): " + to_string((uintptr_t)entity.get()));
    }

    entity->setSceneManager(nullptr);

    mEntities.remove(entity);
}

Camera::shared_ptr SceneManager::camera() const
{
    return mCamera;
}

} }