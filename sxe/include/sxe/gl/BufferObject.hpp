#ifndef SXE_GL_BUFFEROBJECT__HPP
#define SXE_GL_BUFFEROBJECT__HPP
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

#include <glbinding/gl20/gl.h>
#include <sxe/api.hpp>
#include <sxe/graphics/MemoryBuffer.hpp>
#include <sxe/logging/Log.hpp>

namespace sxe { namespace gl {

    /** Class encapsulating OpenGL Buffer Objects.
     * 
     * Provides a generic glGenBuffers() / glDeleteBuffers() implementation of
     * MemoryBuffer. Suitable for use with OpenGL[ES] 2.0 and later.
     * 
     * @see sxe::gl::VertexBufferObject, sxe::graphics::MemoryBuffer.
     */
    class SXE_PUBLIC BufferObject
        : public sxe::graphics::MemoryBuffer
    {
      public:
        using target_type = gl20::GLenum;
        using usage_type = gl20::GLenum;

        /** Specifies the type and usage of the buffer.
         * 
         * @param target binding target like GL_ARRAY_BUFFER.
         * @param usage use case like GL_STATIC_DRAW.
         * @param level used for logging.
         * @param tag used for logging.
         */
        BufferObject(target_type target, usage_type usage, int level, const string_type& tag);

        /** Specifies the type and usage of the buffer.
         * 
         * The default log level of TEST and tag "BufferObject" will be used.
         * 
         * @param target binding target like GL_ARRAY_BUFFER.
         * @param usage use case like GL_STATIC_DRAW.
         */
        BufferObject(target_type target, usage_type usage);

        virtual ~BufferObject();

        /** Bind the buffer object for use.
         */
        void bind();

        virtual void allocate(size_type size, const void* data) override;
        
        virtual void buffer(difference_type offset, size_type size, const void* data) override;

        virtual void* map(MapType access) override;

        virtual bool unmap() override;

        /** @returns the target.
         */
        target_type getTarget() const;
        
        /** @returns the target.
         */
        usage_type getUsage() const;
        
      private:
        target_type mTarget;
        usage_type mUsage;
    };
} }

#endif // SXE_GL_BUFFEROBJECT__HPP