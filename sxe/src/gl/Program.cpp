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
    gl20::glAttachShader(mId, shader.getId());
}

void Program::detach(const Shader& shader)
{
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

Program::AttributeIndex Program::getAttribLocation(const string_type& name)
{
    return gl20::glGetAttribLocation(mId, name.c_str());
}

void Program::bindAttribLocation(AttributeIndex index, const string_type& name)
{
    return gl20::glBindAttribLocation(mId, index, name.c_str());
}

} }
