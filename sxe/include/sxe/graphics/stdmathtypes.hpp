#ifndef SXE_GRAPHICS_STDMATHTYPES__HPP
#define SXE_GRAPHICS_STDMATHTYPES__HPP
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

#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <glm/mat4x4.hpp>
#include <glm/vec2.hpp>
#include <glm/vec3.hpp>
#include <glm/vec4.hpp>
#include <sxe/api.hpp>
#include <sxe/graphics/Rectangle.hpp>
#include <sxe/stdheaders.hpp>

namespace sxe { namespace graphics {

    /** Mix-in for common type definitions.
     *
     */
    class SXE_PUBLIC stdmathtypes
    {
      public:
        /** 2 element vector (x, y).
         */
        using vec2 = glm::vec2;

        /** 3 element vector (x, y, z).
         */
        using vec3 = glm::vec3;

        /** 4 element vector (x, y, z, w).
         */
        using vec4 = glm::vec4;

        /** 4 x 4 matrix.
         */
        using mat4 = glm::mat4;

        /* A simple rectangle.
         */
        using rect = sxe::graphics::rect;
    };

    /** Simple string representation of a vector type.
     */
    template <class vec_type, class string_type = std::string>
    string_type vec_to_string(vec_type v)
    {
        string_type s;

        s.append("{");
        if constexpr(vec_type::length() > 0) {
            s.append("x: ").append(std::to_string(v.x));
        }
        if constexpr(vec_type::length() > 1) {
            s.append(",");
            s.append("y: ").append(std::to_string(v.y));
        }
        if constexpr(vec_type::length() > 2) {
            s.append(",");
            s.append("z: ").append(std::to_string(v.z));
        }
        if constexpr(vec_type::length() > 3) {
            s.append(",");
            s.append("w: ").append(std::to_string(v.w));
        }
        s.append("}");

        return s;
    }
} }

#endif // SXE_GRAPHICS_STDMATHTYPES__HPP