#ifndef SXE_GL_SHADER__HPP
#define SXE_GL_SHADER__HPP
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

#include <glbinding/gl20/types.h>
#include <sxe/api.hpp>
#include <sxe/common/Initializable.hpp>
#include <sxe/common/stdtypedefs.hpp>
#include <sxe/graphics/ShaderType.hpp>

namespace sxe { namespace gl {

    /** Code common to all types of OpenGL shaders.
     */
    class SXE_PUBLIC Shader
        : virtual public common::Initializable<std::istream>
        , virtual public common::stdtypedefs<Shader>
    {
      public:
        using ShaderType = sxe::graphics::ShaderType;
        using istream = std::istream;
        using ShaderId = gl20::GLuint;
  
        /** Create an undefined shader.
         */
        Shader();
        virtual ~Shader();
  
        /** Create an uninitialized shader.
         * 
         * You must call initialize() with the shader source.
         * 
         * @param type what kind of shader.
         */
        Shader(ShaderType type);
  
        /** Create and initialize a shader.
         * 
         * You must call initialize() with the shader source.
         * 
         * @param type what kind of shader.
         * @param[in] source the shader's source.
         * @throws runtime_error if initialize(source) fails.
         */
        Shader(ShaderType type, istream& source);
  
        /** Create the shader.
         * 
         * @param[in] source the GLSL shader code to be compiled.
         * @returns true on success.
         */
        bool initialize(istream& source) override;
  
        /** Delete the shader.
         */
        bool uninitialize() override;
  
        /** @returns the shader type.
         */
        ShaderType getType() const;
  
        /** @returns the shader id.
         */
        ShaderId getId() const;
  
        /** Calls glGetShaderInfoLog().
         * @param maxLength maximum length of the log to get.
         * @returns string_type built from glGetShaderInfoLog()'s result.
         */
        string_type getInfoLog(int maxLength) const;
  
        /** Calls glGetShaderInfoLog().
         * @returns getInfoLog() for the GL_INFO_LOG_LENGTH.
         */
        string_type getInfoLog() const;
  
        /** @returns the GL_INFO_LOG_LENGTH.
         */
        int getInfoLogLength() const;
  
        /** @returns the GL_COMPILE_STATUS.
         */
        bool getCompileStatus() const;
  
        /** @returns the GL_DELETE_STATUS.
         */
        bool getDeleteStatus() const;
  
      protected:
      private:
        static const string_type TAG;
        ShaderType mType;
        ShaderId mId;
    };
} }

  #endif // SXE_GL_SHADER__HPP