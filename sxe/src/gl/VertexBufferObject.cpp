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

#include <sxe/gl/VertexBufferObject.hpp>

#include <glbinding/gl20/gl.h>
#include <sxe/logging.hpp>

using std::to_string;

namespace sxe { namespace gl {

const VertexBufferObject::string_type VertexBufferObject::TAG = "VertexBufferObject";

VertexBufferObject::VertexBufferObject(bool automatic)
    : VertexBufferObject(automatic, gl20::GL_ARRAY_BUFFER, gl20::GL_STATIC_DRAW, Log::TEST)
{
}

VertexBufferObject::VertexBufferObject(bool automatic, target_type target, usage_type usage, int level)
    : BufferObject(automatic, target, usage, TAG, level)
{
    Log::test(TAG, "VertexBufferObject(): automatic: " + to_string(isAutomaticMode()) + " target: " + to_string((int)getTarget()) + " usage: " + to_string((int)getUsage()));
}

VertexBufferObject::~VertexBufferObject()
{
    Log::test(TAG, "~VertexBufferObject(): automatic: " + to_string(isAutomaticMode()) + " target: " + to_string((int)getTarget()) + " usage: " + to_string((int)getUsage()));
}

} }
