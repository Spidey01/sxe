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

#include <sxe/gl/Shader.hpp>

#include <glbinding/gl20/gl.h>
#include <sxe/logging.hpp>

using std::runtime_error;
using std::to_string;

namespace sxe { namespace gl {

const Shader::string_type Shader::TAG = "Shader";

Shader::Shader()
    : mType(ShaderType::Undefined)
    , mId(0)
{
}

Shader::~Shader()
{
    if (isInitialized())
        uninitialize();
}

Shader::Shader(ShaderType type)
    : mType(type)
    , mId(0)
{
}

Shader::Shader(ShaderType type, istream& source)
    : Shader(type)
{
    if (!initialize(source))
        throw runtime_error("Shader(ShaderType, istream&): initialize failed!");
}

bool Shader::initialize(istream& source)
{
    Log::xtrace(TAG, "initialize()");

    gl20::GLenum type;
    switch (mType) {
        case ShaderType::Vertex:
            type = gl20::GL_VERTEX_SHADER;
            break;

        case ShaderType::Fragment:
            type = gl20::GL_FRAGMENT_SHADER;
            break;

        case ShaderType::Undefined:
        default:
            Log::w(TAG, "Unsupported shader type: " + to_string((int)mType));
            return false;
    }

    string_type typeName = shaderTypeToString(mType);

    Log::v(TAG, "initialize(): creating " + typeName + " shader");
    mId = gl20::glCreateShader(type);
    if (mId == 0) {
        Log::e(TAG, "glCreateShader() failed: " + getInfoLog());
        return false;
    }

    string_type name = typeName + " shader id " + to_string(mId);

    Log::v(TAG, "initialize(): sourcing " + name);
    string_type line;
    std::vector<gl20::GLint> lengths;
    std::vector<gl20::GLchar*> lines;
    while (source) {
        std::getline(source, line);

        auto length = static_cast<gl20::GLint>(line.size() + 1);
        gl20::GLchar* buffer = new char[line.size() + 1];
        memset(buffer, '\0', length);

        lengths.push_back(length);
        lines.push_back(buffer);
    }
    if (lengths.size() != lines.size())
        throw std::logic_error("Shader::initialize(): didn't get the same number of lines and line lengths");
    gl20::glShaderSource(mId, (gl20::GLsizei)lines.size(), &lines[0], &lengths[0]);
    for (size_t i=0; i < lines.size(); ++i) {
        gl20::GLchar *buffer = lines[i];
        delete[] buffer;
    }


    Log::v(TAG, "initialize(): compiling " + name);
    gl20::glCompileShader(mId);
    if (!getCompileStatus()) {
        Log::e(TAG, "initialize(): failed compiling " + name + ": " + getInfoLog());
        gl20::glDeleteShader(mId);
        return false;
    }

    return Initializable::initialize(source);
}

bool Shader::uninitialize()
{
    Log::xtrace(TAG, "uninitialize()");

    gl20::glDeleteShader(mId);

    return Initializable::uninitialize();
}

Shader::ShaderType Shader::getType() const
{
    return mType;
}

Shader::ShaderId Shader::getId() const
{
    return mId;
}

Shader::string_type Shader::getInfoLog(int maxLength) const
{
    gl20::GLsizei maxIncludingNullTerminator = maxLength;
    gl20::GLsizei lengthMinusNullTerminator;
    std::vector<gl20::GLchar> buffer(maxIncludingNullTerminator);
    gl20::glGetShaderInfoLog(getId(), maxIncludingNullTerminator, &lengthMinusNullTerminator, &buffer[0]);
    return string_type(buffer.begin(), buffer.end());
}

Shader::string_type Shader::getInfoLog() const
{
    return getInfoLog(getInfoLogLength());
}

int Shader::getInfoLogLength() const
{
    gl20::GLint length = 0;
    gl20::glGetShaderiv(getId(), gl20::GL_INFO_LOG_LENGTH, &length);
    return length;
}

bool Shader::getCompileStatus() const
{
    gl20::GLint success = 0;
    gl20::glGetShaderiv(mId, gl20::GL_COMPILE_STATUS, &success);
    return success;
}

bool Shader::getDeleteStatus() const
{
    gl20::GLint success = 0;
    gl20::glGetShaderiv(mId, gl20::GL_DELETE_STATUS, &success);

    return success;
}


} }
