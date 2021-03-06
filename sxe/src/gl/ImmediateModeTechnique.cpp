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

#include <sxe/gl/ImmediateModeTechnique.hpp>

#include <glbinding/gl10/gl.h>
#include <sxe/graphics/Vertex.hpp>
#include <sxe/logging.hpp>

using sxe::graphics::Vertex;
using sxe::graphics::GraphicsFacet;

namespace sxe { namespace gl {

const ImmediateModeTechnique::string_type ImmediateModeTechnique::TAG = "ImmediateModeTechnique";

ImmediateModeTechnique::ImmediateModeTechnique()
    : DrawingTechnique(TAG, "OpenGL[ES] legacy immediate mode.")
{
}

ImmediateModeTechnique::~ImmediateModeTechnique()
{
}

void ImmediateModeTechnique::frameStarted()
{
    DrawingTechnique::frameStarted();

    auto cc = clearColor();
    gl10::glClearColor(cc.r, cc.g, cc.b, cc.a);
    gl10::ClearBufferMask mask = gl10::ClearBufferMask::GL_COLOR_BUFFER_BIT | gl10::ClearBufferMask::GL_DEPTH_BUFFER_BIT | gl10::ClearBufferMask::GL_STENCIL_BUFFER_BIT;
    gl10::glClear(mask);

    gl10::glLoadIdentity();
}

void ImmediateModeTechnique::draw(GraphicsFacet& facet)
{
    DrawingTechnique::draw(facet);

    gl10::glMatrixMode(gl10::GL_MODELVIEW);
    mat4 modelView = facet.viewMatrix() * facet.modelMatrix() * facet.orientationMatrix();
    gl10::glLoadMatrixf(glm::value_ptr(modelView));

    gl10::glMatrixMode(gl10::GL_PROJECTION);
    mat4 projection = facet.projectionMatrix();
    gl10::glLoadMatrixf(glm::value_ptr(projection));

    gl10::glBegin(gl10::GL_TRIANGLES);
    {
        graphics::SystemMemory& buffer = facet.vertices();
        Vertex* vertices = buffer.map_ptr<Vertex>(graphics::MemoryBuffer::ReadOnlyMapping);
        size_t length = buffer.map_length<Vertex>();
        for (size_t i = 0; i < length; ++i) {
            const Vertex& vert = vertices[i];
            gl10::glColor4f(vert.color.r, vert.color.g, vert.color.b, vert.color.a);
            gl10::glVertex3f(vert.pos.x, vert.pos.y, vert.pos.z);
        }
        buffer.unmap();
    }
    gl10::glEnd();
}

} }
