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

using std::to_string;
using std::runtime_error;
using sxe::graphics::GraphicsFacet;
using sxe::graphics::Vertex;
using sxe::resource::ResourceHandle;
using sxe::resource::ResourceManager;
using sxe::graphics::ShaderType;
using sxe::graphics::shaderTypeToString;

namespace sxe { namespace gl {

const VertexBufferTechnique::string_type VertexBufferTechnique::TAG = "VertexBufferTechnique";

VertexBufferTechnique::VertexBufferTechnique(ResourceManager& resources)
    : DrawingTechnique(TAG, "OpenGL[ES] 2.0 vertex buffer objects.")
    , mProgram()
    , mVBO()
    , mNextOffset(0)
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

    /*
     * Originally in 1.x we brutelly used 1 VBO per entity in the RenderData structure.
     * In 2.x we want a buffer pool, but are bootstrapping this code first.
     * For now: just allocate 1 VBO big enough for the demos until work can proceed.
     */
    // Note to self:
    // - On Centauri: I can allocate up to 2023 MiB, but it takes 3 seconds to allocate, zero, and buffer. 256 MiB takes ~1 second.
    // - On Stark: I can allocate up to 511 MiB, which takes ~1 second to allocate, zero, and buffer.
    size_t size = 64 * (1024 * 1024); // 64MiB;
	Log::d(TAG, "Allocating " + to_string(size) + " bytes of VBO.");
    mVBO.reserve(size);
	Log::d(TAG, "Allocated " + to_string(mVBO.size()) + " bytes of VBO.");
}

VertexBufferTechnique::~VertexBufferTechnique()
{
    mProgram.uninitialize();
}

void VertexBufferTechnique::buffer(GraphicsFacet& facet)
{
    Log::xtrace(TAG, "buffer()");

    facet.setVertexBufferId(mVBO.id());
    facet.setVertexBufferOffset(mNextOffset);

    const GraphicsFacet::vertex_vector& vertices = facet.verticesAsVector();
    size_t length = sizeof(Vertex) * vertices.size();
    size_t remaining = mVBO.size() - mNextOffset;
    size_t lastOffset = mNextOffset;
    size_t nextOffset = length + lastOffset;

    if (nextOffset > remaining) {
        Log::e(TAG, "draw(): VertexBufferObject will overflow by " + to_string((nextOffset - lastOffset) - remaining) + " bytes");
        throw std::bad_alloc();
    }

    mVBO.buffer(facet.getVertexBufferOffset(), length, &vertices[0]);
    mNextOffset += length;
    remaining -= length;
    Log::v(TAG, "buffered " + to_string(length) + " bytes; next offset " + to_string(mNextOffset) + " remaining bytes: " + to_string(remaining));
}

void VertexBufferTechnique::frameStarted()
{
    DrawingTechnique::frameStarted();
    auto cc = clearColor();
    gl20::glClearColor(cc.r, cc.g, cc.b, cc.a);
    gl20::ClearBufferMask mask = gl20::ClearBufferMask::GL_COLOR_BUFFER_BIT | gl20::ClearBufferMask::GL_DEPTH_BUFFER_BIT | gl20::ClearBufferMask::GL_STENCIL_BUFFER_BIT;
    gl20::glClear(mask);

    gl20::glLoadIdentity();

    mVBO.bind();
    mProgram.useProgram();
    gl20::glEnableVertexAttribArray(mPositionIndex);
    gl20::glEnableVertexAttribArray(mColorIndex);
}

void VertexBufferTechnique::draw(GraphicsFacet& facet)
{
    DrawingTechnique::draw(facet);

    const GraphicsFacet::vertex_vector& vertices = facet.verticesAsVector();

    // Starting with the old design we don't get multi vbo
    // old design ugly was one VBO per mesh.
    // working towards new pool of array buffer.
	// for starts: 1 VBO, multi mesh; then work on pool API.
    if (facet.getVertexBufferId() == 0) {
        buffer(facet);
	}


    mVBO.bind();
    ptrdiff_t offset = facet.getVertexBufferOffset();
	mProgram.vertexPositionPointer(mPositionIndex, offset, vertices);
    mProgram.vertexColorPointer(mColorIndex, offset, vertices);

    if (mTransformIndex == -1) {
        mTransformIndex = mProgram.getUniformLocation(mTransformName);
        Log::v(TAG, "location of " + mTransformName + " is " + to_string(mTransformIndex));
    }
    mProgram.uniformMatrixPointer(mTransformIndex, facet.transform());
    
    gl20::glDrawArrays(gl20::GL_TRIANGLES, 0, (gl20::GLsizei)vertices.size());
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
