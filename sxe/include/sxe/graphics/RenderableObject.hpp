#ifndef SXE_GRAPHICS_RENDERABLEOBJECT__HPP
#define SXE_GRAPHICS_RENDERABLEOBJECT__HPP
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

#include <sxe/GameEngine.hpp>
#include <sxe/common/stdtypedefs.hpp>
#include <sxe/graphics/GraphicsFacet.hpp>
#include <sxe/graphics/stdmathtypes.hpp>
#include <sxe/input/InputFacet.hpp>
#include <sxe/input/KeyListener.hpp>
#include <sxe/resource/ResourceFacet.hpp>
#include <sxe/scene/Entity.hpp>

namespace sxe { namespace graphics {

    /** @brief Convenience class for renderable objects.
     * 
     * Nicely nessles up common code needed to make some class contain the
     * necessary facets and setup code.
     * 
     * @see sxe::scene::Entity
     */
    class SXE_PUBLIC RenderableObject
        : public virtual common::stdtypedefs<RenderableObject>
        , public virtual graphics::stdmathtypes
    {
      public:

        /** WRITE ME.
         * 
         * @param system the game engine instance.
         * @param resource used for ResourceFacet to load data into GraphicsFacet.
         * @param resourceFilter used for ResourceFacet to load formats other than raw bytes.
         * @param frameListener used for GraphicsFacet to receive frame events.
         * @param keyListener used for InputFacet to receive any key inputs.
         */
        RenderableObject(GameEngine& system,
                         const string_type& resource,
                         std::optional<resource::ResourceFacet::Filter> resourceFilter,
                         std::optional<FrameListener> frameListener,
                         std::optional<input::KeyListener> keyListener);

        virtual ~RenderableObject();

        /** @returns the GameEngine.
         */
        GameEngine& engine() const;

        /** @returns the Entity for this object.
         * 
         * Use this for adding RenderableObjects to the scene.
         * 
         * @see SceneManager
         */
        sxe::scene::Entity::shared_ptr entity();

        /** @returns the ResourceFacet for this object.
         */
        sxe::resource::ResourceFacet::shared_ptr resources();

        /** @returns the GraphicsFacet for this object.
         */
        sxe::graphics::GraphicsFacet::shared_ptr graphics();

        /** @returns the InputFacet for this object.
         */
        sxe::input::InputFacet::shared_ptr input();

      protected:
      private:
        static const string_type TAG;
        sxe::GameEngine& mGameEngine;
        sxe::scene::Entity::shared_ptr mEntity;
    };

} }

#endif // SXE_GRAPHICS_RENDERABLEOBJECT__HPP