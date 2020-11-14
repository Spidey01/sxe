/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <sxe/graphics/GraphicsFacet.hpp>

using string_type = sxe::graphics::GraphicsFacet::string_type;
using vertex_vector = sxe::graphics::GraphicsFacet::vertex_vector;

namespace sxe { namespace graphics {

const string_type GraphicsFacet::TAG = "GraphicsFacet";

GraphicsFacet::GraphicsFacet()
    : mOnDraw()
    , mVertices()
{
}

GraphicsFacet::GraphicsFacet(const vertex_vector& vertices)
    : mOnDraw()
    , mVertices(vertices)
{
}

GraphicsFacet::GraphicsFacet(const vertex_vector& vertices, callable_type callback)
    : mOnDraw(callback)
    , mVertices(vertices)
{
}

GraphicsFacet::~GraphicsFacet()
{
}

GraphicsFacet::callable_type& GraphicsFacet::onDraw()
{
    return mOnDraw;
}

const vertex_vector& GraphicsFacet::verticesAsVector() const
{
    return mVertices;
}

} }
