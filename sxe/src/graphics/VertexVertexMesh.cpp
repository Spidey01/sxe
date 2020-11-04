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

#include "sxe/graphics/VertexVertexMesh.hpp"

#include <sxe/logging.hpp>
#include <sxe/common/Utils.hpp>

using std::istream;
using std::stof;
using std::to_string;
using sxe::common::Utils::split_regex;
using sxe::common::Utils::starts_with;
using sxe::common::Utils::trim;

namespace sxe { namespace graphics {

const VertexVertexMesh::string_type VertexVertexMesh::TAG = "VertexVertexMesh";

VertexVertexMesh::VertexVertexMesh(istream& stream)
{
    static std::regex pattern("\\s+");

    string_type line;

    while (stream) {
        std::getline(stream, line);
        line = trim(line);
        Log::test(TAG, "line:" + line);

        if (line.empty())
            continue;

        if (line.front() == '#' || line.front() == ';' || starts_with(line, "//") || starts_with(line, "--")) {
            Log::test(TAG, "Skipping line \"" + line + "\" + as comment.");
            continue;
        }

        std::vector<string_type> vertices;
        Vertex buffer;
        split_regex(std::back_inserter(vertices), line, pattern);
        for (size_t i = 0; i < vertices.size(); ++i) {
            Log::test(TAG, "vertices[" + to_string(i) + "]: " + vertices[i]);
            buffer.pos[i] = stof(vertices[i]);
        }

        mVertices.push_back(buffer);
    }
}

const VertexVertexMesh::vertex_vector& VertexVertexMesh::vertices() const
{
    return mVertices;
}

void VertexVertexMesh::solidFill(const Vertex::color_type& color)
{
    for (Vertex& v : mVertices)
        v.color = color;
}

} }
