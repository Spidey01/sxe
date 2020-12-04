/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <sxe/gl/VertexBufferTechnique.hpp>

#include <glbinding/gl20/gl.h>
#include <sxe/logging.hpp>
#include <sxe/resource/ResourceManager.hpp>

using std::invalid_argument;
using std::to_string;
using std::runtime_error;
using sxe::graphics::GraphicsFacet;
using sxe::graphics::MemoryBuffer;
using sxe::graphics::MemoryPool;
using sxe::graphics::ShaderType;
using sxe::graphics::SystemMemory;
using sxe::graphics::Vertex;
using sxe::graphics::shaderTypeToString;
using sxe::resource::ResourceHandle;
using sxe::resource::ResourceManager;

namespace sxe { namespace gl {

const VertexBufferTechnique::string_type VertexBufferTechnique::TAG = "VertexBufferTechnique";

VertexBufferTechnique::VertexBufferTechnique(ResourceManager& resources)
    : DrawingTechnique(TAG, "OpenGL[ES] 2.0 vertex buffer objects.")
    , mProgram()
    , mMemoryPool(64 * (1024 * 1024)) // 64MiB;
	, mPositionName("sxe_vertex_position")
    , mPositionIndex(0)
    , mColorName("sxe_vertex_color")
    , mColorIndex(1)
	, mTransformName("sxe_transform")
    , mTransformIndex(-1)
{
    mProgram.initialize();

    path_type shaderPath = "glsl";
    path_type vertexShaderPath = shaderPath / (TAG + ".vert");
    path_type fragmentShaderPath = shaderPath / (TAG + ".frag");

    Shader::unique_ptr vert = loadShader(resources, vertexShaderPath, ShaderType::Vertex);
    Shader::unique_ptr frag = loadShader(resources, fragmentShaderPath, ShaderType::Fragment);

    if (!vert)
        throw runtime_error(TAG + "(): loadShader() returned nullptr for " + vertexShaderPath.string());
    if (!frag)
        throw runtime_error(TAG + "(): loadShader() returned nullptr for " + fragmentShaderPath.string());

    mProgram.attach(*vert);
    mProgram.attach(*frag);

    if (!mProgram.link())
        throw runtime_error(TAG + "(): mProgram.link() failed: " + mProgram.getInfoLog());

	mProgram.detach(*vert);
	mProgram.detach(*frag);
    // ~Shader auto deinits.

    if (!mProgram.validate())
        throw runtime_error(TAG + "(): mProgram.validate() failed: " + mProgram.getInfoLog());

    mProgram.bindAttribLocation(mPositionIndex, mPositionName);
    auto posIdx = mProgram.getAttribLocation(mPositionName);
    if (posIdx != mPositionIndex) {
        throw runtime_error(TAG + ": " + mPositionName + " is at location " + to_string(posIdx) + " but must be at location " + to_string(mPositionIndex));
    }

    mProgram.bindAttribLocation(mColorIndex, mColorName);
    auto clrIdx = mProgram.getAttribLocation(mColorName);
    if (clrIdx != mColorIndex) {
        throw runtime_error(TAG + ": " + mColorName + " is at location " + to_string(clrIdx) + " but must be at location " + to_string(mColorIndex));
    }
}

VertexBufferTechnique::~VertexBufferTechnique()
{
    mProgram.uninitialize();
}

void VertexBufferTechnique::buffer(GraphicsFacet& facet)
{
    DrawingTechnique::buffer(facet);

    SystemMemory& ram = facet.vertices();
    Vertex* vertices = ram.map_ptr<Vertex>(SystemMemory::ReadOnlyMapping);
    graphics::MemoryPool::Segment seg = mMemoryPool.buffer(ram.size(), &vertices[0]);
    ram.unmap();
    facet.setSegment(seg);
}

void VertexBufferTechnique::unbuffer(GraphicsFacet& facet)
{
    DrawingTechnique::unbuffer(facet);

    MemoryPool::Segment& seg = facet.getSegment();
    MemoryBuffer::shared_ptr buffer = seg.buffer;

    if (buffer) {
        if (buffer->pool() != mMemoryPool.id())
            throw invalid_argument(TAG + "::unbuffer(): facet.getSegment().buffer->pool() is not ours.");
        mMemoryPool.deallocate(seg);
    } else {
        Log::w(TAG, "unbuffer(): facet.getSegment().buffer == nullptr");
    }

    facet.clearSegment();
}

void VertexBufferTechnique::frameStarted()
{
    DrawingTechnique::frameStarted();
    auto cc = clearColor();
    gl20::glClearColor(cc.r, cc.g, cc.b, cc.a);
    gl20::ClearBufferMask mask = gl20::ClearBufferMask::GL_COLOR_BUFFER_BIT | gl20::ClearBufferMask::GL_DEPTH_BUFFER_BIT | gl20::ClearBufferMask::GL_STENCIL_BUFFER_BIT;
    gl20::glClear(mask);

    gl20::glLoadIdentity();

    mProgram.useProgram();
    gl20::glEnableVertexAttribArray(mPositionIndex);
    gl20::glEnableVertexAttribArray(mColorIndex);
}

void VertexBufferTechnique::draw(GraphicsFacet& facet)
{
    DrawingTechnique::draw(facet);

    graphics::SystemMemory& data = facet.vertices();
    Vertex* vertices = data.map_ptr<Vertex>(graphics::SystemMemory::ReadOnlyMapping);

    if (facet.getSegment().buffer == nullptr) {
        buffer(facet);
	}

    graphics::MemoryPool::Segment& segment = facet.getSegment();
    if (!segment.buffer)
        throw std::logic_error(TAG + "::draw(): facet.getSegment().buffer == nullptr.");
    segment.buffer->bind();

    ptrdiff_t offset = segment.offset;
    mProgram.vertexPositionPointer(mPositionIndex, segment);
    mProgram.vertexColorPointer(mColorIndex, segment);

    if (mTransformIndex == -1) {
        mTransformIndex = mProgram.getUniformLocation(mTransformName);
        Log::v(TAG, "location of " + mTransformName + " is " + to_string(mTransformIndex));
    }
    mProgram.uniformMatrixPointer(mTransformIndex, facet.transform());
    
    gl20::glDrawArrays(gl20::GL_TRIANGLES, 0, (gl20::GLsizei)segment.length / sizeof(Vertex));
    data.unmap();
}

void VertexBufferTechnique::frameEnded()
{
    DrawingTechnique::frameEnded();
    gl20::glDisableVertexAttribArray(mPositionIndex);
    gl20::glDisableVertexAttribArray(mColorIndex);
}

Shader::unique_ptr VertexBufferTechnique::loadShader(sxe::resource::ResourceManager& resources, const path_type& path, ShaderType type)
{
    Log::xtrace(TAG, "loadShader(): path: " + path.string() + " type: " + shaderTypeToString(type));

    string_type what = TAG + "::loadShader(): " + path.string() + ": ";

    ResourceHandle::unique_ptr handle = resources.load(path);
    if (!handle)
        throw runtime_error(what + "ResourceManager::load() returned nullptr.");

    std::unique_ptr<std::istream> buffer = handle->asInputStream();
    if (!buffer)
        throw runtime_error(what + "ResourceHandle::asInputStream() returned nullptr.");

    Shader::unique_ptr shader = std::make_unique<Shader>(type);

    if (!shader->initialize(*buffer))
        throw runtime_error(what + "Shader::initialize failed!");

    if (!shader->getCompileStatus())
        throw runtime_error(what + "Shader failed to compile: " + shader->getInfoLog());

    return shader;
}
} }
