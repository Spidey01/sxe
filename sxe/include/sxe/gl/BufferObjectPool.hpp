#ifndef SXE_GL_BUFFEROBJECTPOOL__HPP
#define SXE_GL_BUFFEROBJECTPOOL__HPP
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
#include <sxe/gl/BufferObject.hpp>
#include <sxe/graphics/MemoryBuffer.hpp>
#include <sxe/graphics/MemoryPool.hpp>

namespace sxe { namespace gl {

    /** Implements MemoryPool for OpenGL BufferObjects.
     */
    class SXE_PUBLIC BufferObjectPool : public graphics::MemoryPool
    {
      public:
        using target_type = BufferObject::target_type;
        using usage_type = BufferObject::usage_type;

        BufferObjectPool(size_type unit, target_type target, usage_type usage);

        /** BufferObjectPool(unit, GL_ARRAY_BUFFER, GL_STATIC_DRAW).
         */
        BufferObjectPool(size_type unit);

        virtual ~BufferObjectPool();

      protected:
        buffer_ptr create() override;

      private:
        static const string_type TAG;
        target_type mTarget;
        usage_type mUsage;
    };
} }

#endif // SXE_GL_BUFFEROBJECTPOOL__HPP