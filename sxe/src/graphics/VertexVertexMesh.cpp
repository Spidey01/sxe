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
using sxe::common::Utils::trim;
using sxe::common::Utils::starts_with;

namespace sxe { namespace graphics {

const VertexVertexMesh::string_type VertexVertexMesh::TAG = "VertexVertexMesh";

VertexVertexMesh::VertexVertexMesh(istream& stream)
{
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

        // split \\s, max 3, and add the vertex to buffer..
    }
}

} }
