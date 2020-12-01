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
#include <sxe/graphics/FrameListener.hpp>
#include <sxe/graphics/MemoryBuffer.hpp>
#include <sxe/graphics/MemoryPool.hpp>
#include <sxe/graphics/SystemMemory.hpp>
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

        /** No data.
         */
        GraphicsFacet();

        ~GraphicsFacet();

        /** Populate vertex data from vertex_vector.
         * 
         * @param vertices[in] sequence of vertex data.
         */
        GraphicsFacet(const vertex_vector& vertices);

        /** Populate vertex data from a mappable MemoryBuffer.
         * 
         * @param data[in] a buffer containing the data; must support map().
         */
        GraphicsFacet(MemoryBuffer& data);

        /** Populate vertex data from a raw pointer.
         * @param data[in] vertices.
         * @param length in bytes.
         */
        GraphicsFacet(uint8_t* data, size_t length);

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

        /** Set the FrameListener for onDraw().
         */
        void setFrameListener(FrameListener listener);

        /** Get the FrameListener for onDraw().
         */
        FrameListener& getFrameListener();

        /** Executes the FrameListener.
         * 
         * If no frame listener is attached this is a harmless no-op.
         */
        void onDraw();

        /** Returns the vertices from system memory.
         * 
         * This is a tightly packed buffer of Vertex structures. Such that
         * map_ptr<Vertex>() can be used to get a pointer to the data.
         * 
         * In most cases: you should only use this to populate a pool of
         * graphics memory, and record the segment with setSegment().
         */
        SystemMemory& vertices();

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

        /** Gets the associated MemoryPool.
         */
        MemoryPool::Segment& getSegment();

        /** Sets the MemoryPool Segment.
         * 
         * Utilized by DrawingTechniques to upload vertex information through a
         * MemoryPool.
         */
        void setSegment(const MemoryPool::Segment& segment);

      private:
        static const string_type TAG;
        SystemMemory mData;
        FrameListener mFrameListener;
        mat4 mModelMatrix;
        vec3 mPosition;
        mat4 mOrientationMatrix;
        sxe::scene::Camera::shared_ptr mCamera;
        FrameListener mOnDraw;
        MemoryPool::Segment mSegment;
    };
} }

#endif // SXE_GRAPHICS_GRAPHICSFACET__HPP