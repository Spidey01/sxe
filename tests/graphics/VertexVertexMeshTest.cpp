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

#include "./VertexVertexMeshTest.hpp"

#include <sxe/logging.hpp>

using std::endl;
using std::string;
using std::stringstream;
using std::to_string;
using sxe::graphics::Vertex;
using sxe::graphics::VertexVertexMesh;

static const string TAG = "VertexVertexMeshTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(VertexVertexMeshTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void VertexVertexMeshTest::parse()
{
    Log::xtrace(TAG, "parse()");
    static const Vertex::vector vertices = {
        {{-0.5f, 0.5f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f}},
        {{-0.5f, -0.5f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f}},
        {{0.5f, -0.5f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f}},
        {{0.5f, -0.5f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f}},
        {{0.5f, 0.5f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f}},
        {{-0.5f, 0.5f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f}},
    };

    stringstream input;

    input
        << "# comment hash style" << endl
        << "; comment semicolon style" << endl
        << "// comment C++ style" << endl
        << "-0.5 0.5 0" << endl
        << "-0.5  -0.5 0" << endl
        << "0.5 -0.5 0     " << endl
        << "0.5 -0.5 0\t" << endl
        << "0.5 0.5\t0" << endl
        << "-0.5\t0.5 0" << endl
        << endl;

    VertexVertexMesh vvm(input);

    CPPUNIT_ASSERT(vvm.vertices().size() == vertices.size());
    for (size_t i = 0; i < vertices.size(); ++i) {
        CPPUNIT_ASSERT_MESSAGE("index " + to_string(i) + " does not match.",
                               vvm.vertices().at(i).pos == vertices.at(i).pos);
    }
}
