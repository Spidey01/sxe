#ifndef SXE_GL_VERTEXBUFFEROBJECT__HPP
#define SXE_GL_VERTEXBUFFEROBJECT__HPP
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

#include <glbinding/gl20/types.h>
#include <sxe/api.hpp>
#include <sxe/common/Initializable.hpp>
#include <sxe/common/stdtypedefs.hpp>
#include <sxe/gl/BufferObject.hpp>
#include <sxe/graphics/Vertex.hpp>

namespace sxe { namespace gl {

    /** Class encapsulating an OpenGL Vertex Buffer Object (VBO).
     */
    class SXE_PUBLIC VertexBufferObject
        : virtual public BufferObject<VertexBufferObject>
    {
      public:
        VertexBufferObject(bool automatic);
        VertexBufferObject(bool automatic, target_type target, usage_type usage, int level);
        virtual ~VertexBufferObject();

      private:
        static const string_type TAG;
    };
} }

#endif // SXE_GL_VERTEXBUFFEROBJECT__HPP