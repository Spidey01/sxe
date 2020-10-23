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

#include <sxe/gl/OpenGLVersion.hpp>

#include <glbinding/gl/gl.h>
#include <glbinding/glbinding.h>
#include <sxe/logging.hpp>

namespace sxe
{
    namespace gl
    {

        const char* OpenGLVersion::vendor() const
        {
            return (const char*)::gl::glGetString(::gl::GL_VENDOR);
        }

        const char* OpenGLVersion::renderer() const
        {
            return (const char*)::gl::glGetString(::gl::GL_RENDERER);
        }

        const char* OpenGLVersion::version() const
        {
            return (const char*)::gl::glGetString(::gl::GL_VERSION);
        }

        const char* OpenGLVersion::glslLanguageVersion() const
        {
            return (const char*)::gl::glGetString(::gl::GL_SHADING_LANGUAGE_VERSION);
        }

        const char* OpenGLVersion::extensions() const
        {
            return (const char*)::gl::glGetString(::gl::GL_EXTENSIONS);
        }

        int OpenGLVersion::majorVersion() const
        {
            ::gl::GLint major = 0;
            ::gl::glGetIntegerv(::gl::GL_MAJOR_VERSION, &major);

            return (int)major;
        }

        int OpenGLVersion::minorVersion() const
        {
            ::gl::GLint minor = 0;
            ::gl::glGetIntegerv(::gl::GL_MAJOR_VERSION, &minor);

            return (int)minor;
        }

    } // namespace gl
} // namespace sxe
