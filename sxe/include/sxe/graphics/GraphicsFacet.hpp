#ifndef SXE_GRAPHICS_GRAPHICSFACET__HPP
#define SXE_GRAPHICS_GRAPHICSFACET__HPP
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
#include <sxe/common/stdtypedefs.hpp>
#include <sxe/graphics/Vertex.hpp>

namespace sxe { namespace graphics {

    /** Facet for rendering entities in a scene.
     */
    class SXE_PUBLIC GraphicsFacet
        : public common::stdtypedefs<GraphicsFacet>
    {
      public:
        using vertex_vector = std::vector<Vertex>;
        using callable_type = std::function<void(void)>;

        /** No data.
         */
        GraphicsFacet();

        ~GraphicsFacet();

        /** Populate vertex from system memory.
         * 
         * @param camera provides the ... matrix.
         * @param data sequence of vertex data.
         * @param callback the onDraw handler.
         */
        GraphicsFacet(sxe::scene::Camera::shared_ptr camera, const vertex_vector& data, callable_type callback);

        GraphicsFacet(sxe::scene::Camera::shared_ptr camera,const vertex_vector& data);

        /** Camera providing the view / projection matrices.
         */
        sxe::scene::Camera::shared_ptr getCamera() const;

        /** Camera used to compute view / projection matrices.
         */
        void setCamera(sxe::scene::Camera::shared_ptr camera);

        /** @returns the callback for DrawingTechnique::draw().
         */
        callable_type& onDraw();

        /** Returns the vertices.
         * 
         * This is raw data packed loaded into standard system memory. I.e. for
         * packing into a vertex buffer.
         */
        const vertex_vector& verticesAsVector() const;

      private:
        static const string_type TAG;
        sxe::scene::Camera::shared_ptr mCamera;
        callable_type mOnDraw;
        vertex_vector mVertices;
    };
} }

#endif // SXE_GRAPHICS_GRAPHICSFACET__HPP