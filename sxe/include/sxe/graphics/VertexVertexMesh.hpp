#ifndef SXE_GRAPHICS_VERTEXVERTEXMESHTEST__HPP
#define SXE_GRAPHICS_VERTEXVERTEXMESHTEST__HPP
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

#include <sxe/api.hpp>
#include <sxe/graphics/Vertex.hpp>

namespace sxe { namespace graphics {
        class MemoryBuffer;

        /** Simple vertex-vertex mesh implementation.
         *
         * A vertex-vertex or VV mesh is a list of connected vertices.
         *
         * We can parse this from a file containing 3 numbers seperated by white space.
         * Blank lines and comment lines are ignored.
         *
         * Here is an example file:
         * <pre>
         *      // Let bottom triangle.
         *      -0.5 0.5 0
         *      -0.5 -0.5 0
         *      0.5 -0.5 0
         *      # Right top triangle.
         *      0.5 -0.5 0
         *      0.5 0.5 0
         *      -0.5 0.5 0
         *      -- blargle
         *      ; narble
         * </pre>
         */
        class SXE_PUBLIC VertexVertexMesh
            : public virtual common::stdtypedefs<VertexVertexMesh>
        {
          public:
            using vertex_vector = std::vector<Vertex>;

            /** Loads the mesh from a stream.
             * 
             * @param stream the input data.
             */
            VertexVertexMesh(std::istream& stream);

            const vertex_vector& vertices() const;

            /** Sets the default Vertex::color for the ctor.
             * 
             * The vertex description format doesn't do colors. As such this
             * will be used when creating the vertices(). By default the value
             * is white.
             */
            static void setDefaultFill(const Vertex::color_type& color);

            /** Sets each Vertex::color to color. */
            void solidFill(const Vertex::color_type& color);

            static bool resourceFilter(std::istream& input, sxe::graphics::MemoryBuffer& buffer);

          protected:
          private:
            static const string_type TAG;
            static Vertex::color_type sDefaultColor;
            vertex_vector mVertices;
        };
} }

#endif // SXE_GRAPHICS_VERTEXVERTEXMESHTEST__HPP