#ifndef SXE_SCENE_SCENEMANAGER__HPP
#define SXE_SCENE_SCENEMANAGER__HPP
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

#include <sxe/api.hpp>
#include <sxe/common/Subsystem.hpp>
#include <sxe/common/mutextypedefs.hpp>
#include <sxe/graphics/DrawingTechnique.hpp>
#include <sxe/scene/Entity.hpp>

namespace sxe { namespace scene {

    /** Write me.
     */
    class SXE_PUBLIC SceneManager
        : public common::Subsystem
        , public common::recursive_mutex_typedefs
    {
      public:
        SceneManager();
        virtual ~SceneManager();

        bool initialize(GameEngine& engine) override;
        bool uninitialize() override;

        void update() override;

        /** Our settings listener.
         * 
         * @param key the setting that changed.
         */
        virtual void onSettingChanged(string_type key);

        /** Add an Entity to the scene.
         * 
         * @param entity to enter the scene.
         */
        void addEntity(Entity::shared_ptr entity);

        /** Remove an Entity to the scene.
         * 
         * @param entity to exit the scene.
         */
        void removeEntity(Entity::shared_ptr entity);

      protected:
      private:
        static const string_type TAG;
        config::Settings::SettingsManager::size_type mOnChangedListenerId;
        mutex_type mEntityMutex;
        std::list<Entity::shared_ptr> mEntities;
        bool mWarnedNoTechnique;
        graphics::DrawingTechnique::shared_ptr mDrawingTechnique;
    };
} }

#endif // SXE_SCENE_SCENEMANAGER__HPP