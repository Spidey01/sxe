#ifndef SXE_GRAPHICS_SPRITE__HPP
#define SXE_GRAPHICS_SPRITE__HPP
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
#include <sxe/graphics/Vertex.hpp>

namespace sxe { namespace graphics {

    /** @brief Convenience class for using Sprites.
     * 
     * Sprites are 2D like models that face the camera like a Billboard.
     */
    class SXE_PUBLIC Sprite
        : public RenderableObject
    {
      public:
        Sprite(GameEngine& system,
               const string_type& resource,
               std::optional<resource::ResourceFacet::Filter> resourceFilter,
               std::optional<sxe::graphics::FrameListener> frameListener,
               std::optional<input::KeyListener> keyListener);
        virtual ~Sprite();

      protected:
      private:
    };
} }

#endif // SXE_GRAPHICS_SPRITE__HPP