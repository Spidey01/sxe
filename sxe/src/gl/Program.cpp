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

#include <sxe/gl/Program.hpp>

#include <glbinding/gl20/gl.h>
#include <sxe/gl/Shader.hpp>
#include <sxe/logging.hpp>

using std::to_string;
using sxe::graphics::Vertex;

namespace sxe { namespace gl {

const Program::string_type Program::TAG = "Program";

Program::Program()
    : mId(0)
{
    Log::test(TAG, "Program()");
}

Program::~Program()
{
    Log::test(TAG, "~Program(): mId: " + to_string(mId));
}

bool Program::initialize(void)
{
    mId = gl20::glCreateProgram();

    if (!gl20::glIsProgram(mId)) {
        Log::e(TAG, "initialize(): failed to create program!");
        return false;
    }

    return Initializable::initialize();
}

bool Program::uninitialize(void)
{
    if (!Initializable::uninitialize())
        return false;

    if (!deleteProgram()) {
        Log::e(TAG, "uninitialize(): deleteProgram failed!");
        return false;
    }

    return true;
}

Program::ProgramId Program::getId() const
{
    return mId;
}

Program::ProgramId Program::getProgram() const
{
    return mId;
}

void Program::useProgram()
{
    Log::test(TAG, "useProgram(): mId: " + to_string(mId));
    gl20::glUseProgram(mId);
}

Program::string_type Program::getInfoLog(int maxLength) const
{
    gl20::GLsizei maxIncludingNullTerminator = maxLength;
    gl20::GLsizei lengthMinusNullTerminator;
    std::vector<gl20::GLchar> buffer(maxIncludingNullTerminator);
    gl20::glGetProgramInfoLog(getId(), maxIncludingNullTerminator, &lengthMinusNullTerminator, &buffer[0]);
    return string_type(buffer.begin(), buffer.end());
}

Program::string_type Program::getInfoLog() const
{
    return getInfoLog(getInfoLogLength());
}

int Program::getInfoLogLength() const
{
    gl20::GLint length = 0;
    gl20::glGetProgramiv(getId(), gl20::GL_INFO_LOG_LENGTH, &length);
    return length;
}

bool Program::validate()
{
    gl20::glValidateProgram(mId);

    return getValidateStatus();
}

bool Program::getValidateStatus() const
{
    gl20::GLint valid = 0;
    gl20::glGetProgramiv(mId, gl20::GL_VALIDATE_STATUS, &valid);
    return valid;
}

bool Program::getLinkStatus() const
{
    gl20::GLint linked = 0;
    gl20::glGetProgramiv(mId, gl20::GL_LINK_STATUS, &linked);
    return linked;
}

void Program::attach(const Shader& shader)
{
    Log::xtrace(TAG, "attach(): shader.getId(): " + to_string(shader.getId()) + " mId: " + to_string(mId));
    gl20::glAttachShader(mId, shader.getId());
}

void Program::detach(const Shader& shader)
{
    Log::xtrace(TAG, "dettach(): shader.getId(): " + to_string(shader.getId()) + " mId: " + to_string(mId));
    gl20::glDetachShader(mId, shader.getId());
}

bool Program::link()
{
    Log::xtrace(TAG, "link() mId: " + to_string(mId));

    gl20::glLinkProgram(mId);

    return getLinkStatus();
}

bool Program::deleteProgram()
{
    Log::xtrace(TAG, "deleteProgram(): mId: " + to_string(mId));

    gl20::glDeleteProgram(mId);

    return getDeleteStatus();
}

bool Program::getDeleteStatus() const
{
    gl20::GLint success = 0;
    gl20::glGetProgramiv(mId, gl20::GL_DELETE_STATUS, &success);

    return success;
}

Program::UniformLocation Program::getUniformLocation(const string_type& name)
{
    return gl20::glGetUniformLocation(mId, name.c_str());
}

Program::AttributeLocation Program::getAttribLocation(const string_type& name)
{
    return gl20::glGetAttribLocation(mId, name.c_str());
}

void Program::bindAttribLocation(AttributeLocation index, const string_type& name)
{
    return gl20::glBindAttribLocation(mId, index, name.c_str());
}

void Program::vertexAttribPointer(gl20::GLuint index, gl20::GLint size, gl20::GLenum type, gl20::GLboolean normalized, gl20::GLsizei stride, const void* pointer)
{
    Log::test(TAG, "vertexAttribPointer(): mId: " + to_string(mId) + " index: " + to_string(index) + " size: " + to_string(size) + " type: " + to_string((int)type) + " normalized: " + to_string((bool)normalized) + " stride: " + to_string(stride) + " pointer: " + to_string((ptrdiff_t)pointer));

    useProgram();

    gl20::glVertexAttribPointer(index, size, type, normalized, stride, pointer);
}


void Program::vertexPositionPointer(AttributeLocation index, ptrdiff_t offset, const graphics::Vertex::vector& data)
{
    Log::test(TAG, "vertexPositionPointer(): mId: " + to_string(mId) + " index: " + to_string(index) + " offset: " + to_string(offset) + " data.size(): " + to_string(data.size()));

    gl20::GLint size = Vertex::position_type::length();
    gl20::GLsizei stride = sizeof(Vertex);

    size_t posOffset = offsetof(Vertex, pos) + offsetof(Vertex::position_type, x);
    ptrdiff_t pointer = offset + posOffset;

    vertexAttribPointer(index, size, gl20::GL_FLOAT, false, stride, (const void*)pointer);
}

void Program::vertexPositionPointer(const string_type& attrib, ptrdiff_t offset, const graphics::Vertex::vector& data)
{
    // TODO: make a map of these.
    vertexPositionPointer(getAttribLocation(attrib), offset, data);
}

void Program::vertexPositionPointer(AttributeLocation index, const graphics::MemoryPool::Segment& segment)
{
    Log::test(TAG, "vertexPositionPointer(): mId: " + to_string(mId) + " index: " + to_string(index) + " segment.buffer.id(): " + to_string(segment.buffer->id()));

    gl20::GLint size = Vertex::position_type::length();
    gl20::GLsizei stride = sizeof(Vertex);

    size_t posOffset = offsetof(Vertex, pos) + offsetof(Vertex::position_type, x);
    ptrdiff_t pointer = segment.offset + posOffset;

    vertexAttribPointer(index, size, gl20::GL_FLOAT, false, stride, (const void*)pointer);
}

void Program::vertexPositionPointer(const string_type& attrib, const graphics::MemoryPool::Segment& segment)
{
    return vertexPositionPointer(getAttribLocation(attrib), segment);
}

void Program::vertexColorPointer(AttributeLocation index, ptrdiff_t offset, const graphics::Vertex::vector& data)
{
    Log::test(TAG, "vertexColorPointer(): mId: " + to_string(mId) + " index: " + to_string(index) + " offset: " + to_string(offset) + " data.size(): " + to_string(data.size()));

    gl20::GLint size = Vertex::color_type::length();
    gl20::GLsizei stride = sizeof(Vertex);

    size_t colorOffset = offsetof(Vertex, color) + offsetof(Vertex::color_type, r);
    ptrdiff_t pointer = offset + colorOffset;

    vertexAttribPointer(index, size, gl20::GL_FLOAT, false, stride, (const void*)pointer);
}

void Program::vertexColorPointer(const string_type& attrib, ptrdiff_t offset, const graphics::Vertex::vector& data)
{
    // TODO: make a map of these.
    vertexColorPointer(getAttribLocation(attrib), offset, data);
}

void Program::vertexColorPointer(AttributeLocation index, const graphics::MemoryPool::Segment& segment)
{
    Log::test(TAG, "vertexColorPointer(): mId: " + to_string(mId) + " index: " + to_string(index) + " segment.buffer.id(): " + to_string(segment.buffer->id()));

    gl20::GLint size = Vertex::position_type::length();
    gl20::GLsizei stride = sizeof(Vertex);

    size_t colorOffset = offsetof(Vertex, color) + offsetof(Vertex::color_type, r);
    ptrdiff_t pointer = segment.offset + colorOffset;

    vertexAttribPointer(index, size, gl20::GL_FLOAT, false, stride, (const void*)pointer);
}

void Program::vertexColorPointer(const string_type& attrib, const graphics::MemoryPool::Segment& segment)
{
    return vertexColorPointer(getAttribLocation(attrib), segment);
}

void Program::uniformMatrixPointer(UniformLocation uniform, const glm::mat4& matrix)
{
    useProgram();
    gl20::glUniformMatrix4fv(uniform, 1, false, glm::value_ptr(matrix));
}

Program::string_type Program::dumpUniform(UniformLocation location)
{
    // Is there a spec or getter for max GLSL identifier?
    std::vector<gl20::GLchar> buf(256);

    gl20::GLsizei length = 0;
    gl20::GLint size = 0;
    gl20::GLenum type = gl20::GL_INVALID_ENUM;

    gl20::glGetActiveUniform(
        mId,
        location,
        buf.size(),
        &length,
        &size,
        &type,
        &buf[0]);

    return string_type(&buf[0], length);
}

} }
