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
#include <sxe/graphics/stdmathtypes.hpp>
#include <sxe/scene/Camera.hpp>

namespace sxe { namespace graphics {

    /** Facet for rendering entities in a scene.
     */
    class SXE_PUBLIC GraphicsFacet
        : public common::stdtypedefs<GraphicsFacet>
        , public graphics::stdmathtypes
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

        /** Position to translate by.
         */
        vec3& position();

        /** Modifies the orientation matrix.
         * 
         * @param angle in radians.
         * @param axis to rotate.
         */
        void rotate(float angle, const vec3& axis);

        /** Modifies the orientation matrix.
         * 
         * @param angle in degrees.
         * @param axis to rotate.
         */
        void rotateDegrees(float angle, const vec3& axis);

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

        /** Scale modelMatrix() accordingly.
         * 
         * @param v vector to scale by.
         */
        void scaleModelMatrix(vec3 v);

        /** Scale modelMatrix() accordingly.
         * 
         * @param scale as if vec3.xy = scale.
         */
        void scaleModelMatrix(vec2 v);

        /** Scale modelMatrix() accordingly.
         * 
         * @param scale as if vec3.xyz = scale.
         */
        void scaleModelMatrix(float scale);

        /** @returns the model matrix.
         * 
         * Such that coordinates (0, 0, 0) are a vector relative to the models
         * center.
         */
        const mat4& modelMatrix() const;

        /** @returns the view matrix.
         * 
         * Such that the coordinates (0, 0, 0) are a vector relative to the world's center.
         */
        mat4 viewMatrix() const;

        /** @returns the projection matrix.
         * 
         * Such that the coordinates (0, 0, 0) make you wonder what the camera is looking at.
         */
        mat4 projectionMatrix() const;

        /** @returns blah.
         */
        mat4 orientationMatrix() const;

        /** @returns the Model View Projection (MVP) matrix.
         * 
         * Such that multipying transform() by a vector position yields the
         * screen position for rendering.
         */
        mat4 transform() const;

        /** Type for identification of graphics buffers, e.g. VBOs.
         */
        using buffer_id = unsigned int;

        /** Set the vertex buffer id.
         * 
         * Used by DrawingTechniques that utilize Vertex Buffer Objects (VBOs)
         * to upload vertex information to the GPU.
         */
        void setVertexBufferId(buffer_id id);

        /** Get the vertex buffer id.
         * 
         * By default this is 0 until setVertexBufferId() has been called.
         * 
         * @returns the buffer id.
         */
        buffer_id getVertexBufferId() const;

        /** Set the offset into the vertex buffer.
         * 
         * Used by DrawingTechniques that utilize Vertex Buffer Objects (VBOs)
         * to upload vertex information to the GPU. I.e. a VBO that contains
         * vertices for multiple GraphicFacets.
         */
        void setVertexBufferOffset(ptrdiff_t offset);

        /** Get the offset into the vertex buffer.
         * 
         * By default this is 0 until setVertexBufferOffset() has been called.
         * 
         * @returns the offset into vertex buffer.
         */
        ptrdiff_t getVertexBufferOffset() const;

      private:
        static const string_type TAG;
        sxe::scene::Camera::shared_ptr mCamera;
        callable_type mOnDraw;
        vertex_vector mVertices;
        mat4 mModelMatrix;
        vec3 mPosition;
        mat4 mOrientationMatrix;
        buffer_id mVertexBufferId;
        ptrdiff_t mVertexBufferOffset;
    };
} }

#endif // SXE_GRAPHICS_GRAPHICSFACET__HPP