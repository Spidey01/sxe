#ifndef SXE_GRAPHICS_VERTEX__HPP
#define SXE_GRAPHICS_VERTEX__HPP
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

#include <glm/vec3.hpp>
#include <glm/vec4.hpp>
#include <sxe/api.hpp>

namespace sxe { namespace graphics {

    /** Structure for vertex data.
     * 
     * In SxE 1.0 this was simply VertexBuffer with a java.nio.Buffer style
     * interface for packing buffers.
     * 
     * In SxE 2.0 this is a structure built on the OpenGL Mathematics library
     * (glm).
     */
    class SXE_PUBLIC Vertex
    {
      public:
        using vector = std::vector<Vertex>;

        using position_type = glm::vec4;
        using position_vector = std::vector<position_type>;

        /** Vertex position attribute.
         */
        position_type pos;

        using color_type = glm::vec4;
        using color_vector = std::vector<color_type>;

        /** Vertex color attribute.
         */
        color_type color;
    };
} }

#endif SXE_GRAPHICS_VERTEX__HPP