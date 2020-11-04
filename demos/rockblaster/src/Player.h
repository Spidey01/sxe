#ifndef SXE_DEMOS_ROCKBLASTER_PLAYER__H
#define SXE_DEMOS_ROCKBLASTER_PLAYER__H
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

#include <sxe/common/stdtypedefs.hpp>
#include <sxe/graphics/GraphicsFacet.hpp>
#include <sxe/input/InputFacet.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/input/KeyEvent.hpp>
#include <sxe/scene/Entity.hpp>
#include <sxe/resource/ResourceManager.hpp>

namespace demos {

    /** The player.
     */
    class Player
        : public sxe::common::stdtypedefs<Player>
    {
      public:
        using KeyEvent = sxe::input::KeyEvent;
        using InputCode = sxe::input::InputCode;
        using Entity = sxe::scene::Entity;

        Player();
        ~Player();

        /** Return the entity used for scene management.
         */
        sxe::scene::Entity::shared_ptr getEntity() const;

        /** Setup resources for player.
         * 
         * @param laoder the resource manager.
         */
        bool setupResources(sxe::resource::ResourceManager& loader);

        /** Setup input for controlling the player.
         * 
         * @param controller the input manager.
         */
        bool setupInput(sxe::input::InputManager& controller);

        bool onUpArrow(KeyEvent event);
        bool onLeftArrow(KeyEvent event);
        bool onRightArrow(KeyEvent event);
        bool onSpaceBar(KeyEvent event);

      private:
        static const string_type TAG;
        Entity::shared_ptr mEntity;
        sxe::input::InputFacet::shared_ptr mInputFacet;
        sxe::graphics::GraphicsFacet::shared_ptr mGraphicsFacet;
    };
}

#endif // SXE_DEMOS_ROCKBLASTER_PLAYER__H