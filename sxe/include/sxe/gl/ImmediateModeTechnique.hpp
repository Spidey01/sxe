#ifndef SXE_GL_IMMEDIATEMODETECHNIQUE__HPP
#define SXE_GL_IMMEDIATEMODETECHNIQUE__HPP
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

#include <sxe/api.hpp>
#include <sxe/graphics/DrawingTechnique.hpp>
#include <sxe/graphics/GraphicsFacet.hpp>

namespace sxe { namespace gl {

    /** Implements drawing technique with OpenGLES 1.0.
     * 
     * In virtually all cases you shouldn't use this technique. But it
     * should work on anything that supports the early way of drawing via
     * OpenGL.
     */
    class SXE_PUBLIC ImmediateModeTechnique
        : public graphics::DrawingTechnique
    {
      public:
        ImmediateModeTechnique();
        virtual ~ImmediateModeTechnique();

        /** Per frame startup.
         */
        void frameStarted() override;

        /** Draw the graphics facet.
         * 
         * It is assumed that vertex information is in system memory, and
         * suitable for use with GL_TRIANGLES.
         */
        void draw(graphics::GraphicsFacet& facet) override;

      private:
        static const string_type TAG;
    };

} }

#endif // SXE_GL_IMMEDIATEMODETECHNIQUE__HPP