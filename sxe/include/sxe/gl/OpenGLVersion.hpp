#ifndef SXE_GL_OPENGLVERSION__HPP
#define SXE_GL_OPENGLVERSION__HPP
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

namespace sxe
{
    namespace gl
    {

        /** Simple access to the OpenGL version.
         */
        class SXE_PUBLIC OpenGLVersion
        {
          public:
            /** glGetString(GL_VENDOR).
             * 
             * @returns the company responsible for this GL implementation.
             * This name does not change from release to release.
             */
            const char* vendor() const;

            /** glGetString(GL_RENDERER).
             * 
             * @returns the name of the renderer. This name is typically
             * specific to a particular configuration of a hardware platform.
             * It does not change from release to release.
             */
            const char* renderer() const;

            /** glGetString(GL_GL_VERSION).
             * 
             * @returns a version or release number of the form OpenGL ES
             * {version number} {vendor-specific information}.
             */
            const char* version() const;

            /** glGetString(GL_SHADING_LANGUAGE_VERSION).
             * 
             * @returns a version or release number for the shading language of
             * the form OpenGL ES GLSL ES {version number} {vendor-specific
             * information}.
             */
            const char* glslLanguageVersion() const;

            /** glGetString(GL_EXTENSIONS).
             * 
             * @returns a space - separated list of supported extensions to GL.
             */
            const char* extensions() const;
        };

    } // namespace gl
} // namespace sxe

#endif // SXE_GL_OPENGLVERSION__HPP