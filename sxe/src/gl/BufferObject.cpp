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

#include <sxe/gl/BufferObject.hpp>
#include <sxe/logging/Log.hpp>

#include <sxe/logging.hpp>

using std::to_string;
using sxe::logging::Log;

namespace sxe { namespace gl {

BufferObject::BufferObject(target_type target, usage_type usage, int level, const string_type& tag)
    : sxe::graphics::MemoryBuffer(0, level, tag)
    , mTarget(target)
    , mUsage(usage)
{
    buffer_id theId;
    gl20::glGenBuffers(1, &theId);

    /* Until bound it isn't "Real" in the glIsBuffer() sense. */
    gl20::glBindBuffer(mTarget, theId);

    id(theId);

    if (gl20::glIsBuffer(id()) != gl20::GL_TRUE)
        throw bad_alloc();
}

BufferObject::BufferObject(target_type target, usage_type usage)
    : BufferObject(target, usage, logging::Log::TEST, "BufferObject")
{
}

BufferObject::~BufferObject()
{
    buffer_id theId = id();
    gl20::glDeleteBuffers(1, &theId);
}


void BufferObject::bind()
{
    log("bind(): id(): " + std::to_string(id()));
    gl20::glBindBuffer(mTarget, id());
}

void BufferObject::allocate(size_type size, const void* data)
{
    log("allocate(): id(): " + std::to_string(id()) + " size: " + std::to_string(size) + " (uintptr_t)data: " + std::to_string((uintptr_t)data));
    this->size(size);
    bind();
    gl20::glBufferData(mTarget, size, data, mUsage);
}

void BufferObject::buffer(difference_type offset, size_type size, const void* data)
{
    log("buffer(): id(): " + std::to_string(id()) + " offset: " + std::to_string(offset) + " size: " + std::to_string(size) + " (uintptr_t)data: " + std::to_string((uintptr_t)data));
    bind();
    gl20::glBufferSubData(mTarget, offset, size, data);
}

void* BufferObject::map(MapType access)
{
    log("map(): id(): " + std::to_string(id()) + " access: " + std::to_string((int)access));
    gl20::GLenum glAccess;

    switch (access) {
        case ReadWriteMapping:
            glAccess = gl20::GL_READ_WRITE;
            break;
        case ReadOnlyMapping:
            glAccess = gl20::GL_READ_ONLY;
            break;
        case WriteOnlyMapping:
            glAccess = gl20::GL_WRITE_ONLY;
            break;
        default:
            throw std::invalid_argument("BufferObject::map(): unsupported access type: " + to_string((int)access));
    }

    bind();
    // OpenGL auto unmaps on glDeleteBuffers().
    return gl20::glMapBuffer(mTarget, glAccess);
}

bool BufferObject::unmap()
{
    log("unmap(): id(): " + std::to_string(id()));
    bind();
    return gl20::glUnmapBuffer(mTarget) == gl20::GL_TRUE;
}

BufferObject::target_type BufferObject::getTarget() const
{
    return mTarget;
}

BufferObject::usage_type BufferObject::getUsage() const
{
    return mUsage;
}

} }
