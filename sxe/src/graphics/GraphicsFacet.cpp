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

#include <sxe/logging.hpp>

using std::runtime_error;
using std::to_string;
using string_type = sxe::graphics::GraphicsFacet::string_type;
using sxe::scene::Camera;
using vertex_vector = sxe::graphics::GraphicsFacet::vertex_vector;

namespace sxe { namespace graphics {

static const MemoryPool::Segment EmptySegment = {nullptr, 0, 0};

const string_type GraphicsFacet::TAG = "GraphicsFacet";

GraphicsFacet::GraphicsFacet()
    : mData()
    , mModelMatrix(1.0)
    , mPosition(0, 0, 0)
    , mOrientationMatrix(1.0f)
    , mCamera(nullptr)
    , mOnDraw()
    , mSegment(EmptySegment)
{
}

GraphicsFacet::GraphicsFacet(const vertex_vector& vertices)
    : GraphicsFacet()
{
    Log::xtrace(TAG, "GraphicsFacet(): vertices.size(): " + to_string(vertices.size()));

    size_t length = sizeof(vertex_vector::value_type) * vertices.size();
    mData.allocate(length, &vertices[0]);
}

GraphicsFacet::GraphicsFacet(MemoryBuffer& data)
    : GraphicsFacet()
{
    Log::xtrace(TAG, "GraphicsFacet(): data.size(): " + to_string(data.size()));
    mData.allocate(data.size(), data.map(MemoryBuffer::ReadOnlyMapping));
    data.unmap();
}

GraphicsFacet::GraphicsFacet(uint8_t* data, size_t length)
    : GraphicsFacet()
{
    Log::xtrace(TAG, "GraphicsFacet(): (uintptr_t)data: " + to_string((uintptr_t)data) + " length: " + to_string(length));
    mData.allocate(length, data);
}

GraphicsFacet::~GraphicsFacet()
{
    Log::xtrace(TAG, "~GraphicsFacet()");
}

GraphicsFacet::vec3& GraphicsFacet::position()
{
    return mPosition;
}

void GraphicsFacet::rotate(float angle, const vec3& axis)
{
    mOrientationMatrix = glm::rotate(mOrientationMatrix, glm::radians(angle), axis);
}

void GraphicsFacet::rotateDegrees(float angle, const vec3& axis)
{
    rotate(glm::degrees(angle), axis);
}

Camera::shared_ptr GraphicsFacet::getCamera() const
{
    return mCamera;
}

void GraphicsFacet::setCamera(Camera::shared_ptr camera)
{
    Log::d(TAG, "setCamera(): camera: " + to_string((uintptr_t)camera.get()) + " mCamera: " + to_string((uintptr_t)mCamera.get()));
    mCamera = camera;
}

void GraphicsFacet::setFrameListener(FrameListener listener)
{
    mFrameListener = listener;
}

FrameListener& GraphicsFacet::getFrameListener()
{
    return mFrameListener;
}

void GraphicsFacet::onDraw()
{
    if (mFrameListener)
        mFrameListener();
}

SystemMemory& GraphicsFacet::vertices()
{
    return mData;
}

void GraphicsFacet::scaleModelMatrix(vec3 v)
{
    mModelMatrix = glm::scale(modelMatrix(), v);
}

void GraphicsFacet::scaleModelMatrix(vec2 v)
{
    vec3 scale(v.x, v.y, 1);
    scaleModelMatrix(scale);
}

void GraphicsFacet::scaleModelMatrix(float scale)
{
    scaleModelMatrix(vec3(scale, scale, scale));
}

const GraphicsFacet::mat4& GraphicsFacet::modelMatrix() const
{
    return mModelMatrix;
}

GraphicsFacet::mat4 GraphicsFacet::viewMatrix() const
{
    mat4 view = mCamera ? mCamera->view() : mat4(1);

    return glm::translate(view, mPosition);
}

GraphicsFacet::mat4 GraphicsFacet::projectionMatrix() const
{
    if (mCamera)
        return mCamera->projection();

    return mat4(1);
}

GraphicsFacet::mat4 GraphicsFacet::orientationMatrix() const
{
    return mOrientationMatrix;
}

GraphicsFacet::mat4 GraphicsFacet::transform() const
{
    return projectionMatrix() * viewMatrix() * modelMatrix() * orientationMatrix();
}

MemoryPool::Segment& GraphicsFacet::getSegment()
{
    return mSegment;
}

void GraphicsFacet::setSegment(const MemoryPool::Segment& segment)
{
    mSegment = segment;
}

void GraphicsFacet::clearSegment()
{
    mSegment = EmptySegment;
}

} }
