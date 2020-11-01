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

#include <sxe/gl/VertexArrayTechnique.hpp>

#include <glbinding/gl11/gl.h>
#include <sxe/logging.hpp>

using sxe::graphics::Vertex;
using sxe::graphics::GraphicsFacet;

namespace sxe { namespace gl {

const VertexArrayTechnique::string_type VertexArrayTechnique::TAG = "VertexArrayTechnique";

VertexArrayTechnique::VertexArrayTechnique()
    : DrawingTechnique(TAG, "OpenGL[ES] 1.1 vertex arrays.")
{
}

VertexArrayTechnique::~VertexArrayTechnique()
{

}

void VertexArrayTechnique::frameStarted()
{
    DrawingTechnique::frameStarted();

    gl11::glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    gl11::ClearBufferMask mask = gl11::ClearBufferMask::GL_COLOR_BUFFER_BIT | gl11::ClearBufferMask::GL_DEPTH_BUFFER_BIT | gl11::ClearBufferMask::GL_STENCIL_BUFFER_BIT;
    gl11::glClear(mask);

    gl11::glLoadIdentity();
}

void VertexArrayTechnique::draw(GraphicsFacet& facet)
{
    const GraphicsFacet::vertex_vector& vertices = facet.verticesAsVector();

    gl11::glEnableClientState(gl11::GL_VERTEX_ARRAY);
    gl11::glEnableClientState(gl11::GL_COLOR_ARRAY);

    /* Make sure we're using bytes for our maths. */
    uint8_t *ptr = (uint8_t*)&vertices[0];
    size_t vertexOffset = offsetof(Vertex, pos)   + offsetof(Vertex::position_type, x);
    gl11::glVertexPointer(3, gl11::GL_FLOAT, sizeof(Vertex), ptr + vertexOffset);
    size_t colorOffset = offsetof(Vertex, color) + offsetof(Vertex::color_type, r);
    gl11::glColorPointer( 4, gl11::GL_FLOAT, sizeof(Vertex), ptr + colorOffset);

    gl11::glDrawArrays(gl11::GL_TRIANGLES, 0, static_cast<gl11::GLsizei>(vertices.size()));

    gl11::glDisableClientState(gl11::GL_VERTEX_ARRAY);
    gl11::glDisableClientState(gl11::GL_COLOR_ARRAY);
}

} }
