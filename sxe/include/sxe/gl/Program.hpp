#ifndef SXE_GL_PROGRAM__HPP
#define SXE_GL_PROGRAM__HPP
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
#include <sxe/graphics/MemoryPool.hpp>
#include <sxe/graphics/Vertex.hpp>
#include <sxe/graphics/stdmathtypes.hpp>

namespace sxe { namespace gl {

    class Shader;

    class SXE_PUBLIC Program
      : virtual public common::Initializable<void>
      , virtual public common::stdtypedefs<Program>
      , virtual public graphics::stdmathtypes
    {
      public:
        using ProgramId = gl20::GLuint;
        using AttributeLocation = gl20::GLint;
        using UniformLocation = gl20::GLint;

        Program();
        virtual ~Program();

        bool initialize(void) override;
        bool uninitialize(void) override;

        /** Get this program's id.
         */
        ProgramId getId() const;

        /** Alias for getId(). */
        ProgramId getProgram() const;

        /** glUseProgram() this program.
         */
        void useProgram();

        /** Calls glGetProgramInfoLog().
         * @param maxLength maximum length of the log to get.
         * @returns string_type built from glGetShaderInfoLog()'s result.
         */
        string_type getInfoLog(int maxLength) const;

        /** Calls glGetProgramInfoLog().
         * @returns getInfoLog() for the GL_INFO_LOG_LENGTH.
         */
        string_type getInfoLog() const;

        /** @returns the GL_INFO_LOG_LENGTH.
         */
        int getInfoLogLength() const;

        /** Validates this program object. */
        bool validate();

        /** @returns the GL_VALIDATE_STATUS.
         */
        bool getValidateStatus() const;

        /** @returns the GL_LINK_STATUS.
         */
        bool getLinkStatus() const;

        /** Attach a Shader to this Program.
         */
        void attach(const Shader& shader);

        /** Detach a Shader to this Program.
         */
        void detach(const Shader& shader);

        /** Links the Program.
         * 
         * After the program is linked, shaders can be detached and delated to
         * reclaim resources.
         * 
         * @returns getLinkStatus().
         */
        bool link();

        /** glDeleteProgram()'s this Program.
         * @returns getDeleteStatus().
         */
        bool deleteProgram();

        /** @returns the GL_DELETE_STATUS.
         */
        bool getDeleteStatus() const;

        /** Get uniform location for GLSL.
         * @param name the name it's called in the shader.
         * @returns the location, which is a separate index space than attributes.
         */
        UniformLocation getUniformLocation(const string_type& name);

        /** Get attribute location for GLSL.
         * @param name the name it's called in the shader.
         * @returns the index, which is a separate index space than uniforms.
         */
        AttributeLocation getAttribLocation(const string_type& name);

        /** Bind  attribute location for GLSL.
         * 
         * @param index of the attribute id to bind.
         * @param name the name it's called in the shader.
         */
        void bindAttribLocation(AttributeLocation index, const string_type& name);

        /** Define an array of generic vertex attribute data.
         * 
         * @param index Specifies the index of the generic vertex attribute to
         * be modified.
         * 
         * @param size Specifies the number of components per generic vertex
         * attribute. The initial value is 4.
         * 
         * @param type Specifies the data type of each component in the array.
         * 
         * @param normalized Specifies whether fixed-point data values should
         * be normalized (GL_TRUE) or converted directly as fixed-point values
         * (GL_FALSE) when they are accessed.
         * 
         * @param stride Specifies the byte offset between consecutive generic
         * vertex attributes. If stride is 0, the generic vertex attributes are
         * understood to be tightly packed in the array. The initial value is
         * 0.
         * 
         * @param pointer Specifies a offset of the first component of the
         * first generic vertex attribute in the array in the data store of the
         * buffer currently bound to the GL_ARRAY_BUFFER target. The initial
         * value is 0.
         */
        void vertexAttribPointer(gl20::GLuint index, gl20::GLint size, gl20::GLenum type, gl20::GLboolean normalized, gl20::GLsizei stride, const void* pointer);

        /** Does glVertexAttribPointer() for Vertex::pos for a vector of Vertex.
         * 
         * @param index the location.
         * @param offset the base offset into the currently bound VertexBufferObject.
         * @param data a vector of Vertex data.
         */
        void vertexPositionPointer(AttributeLocation index, ptrdiff_t offset, const graphics::Vertex::vector& data);

        /** Does glVertexAttribPointer() for Vertex::pos for a vector of Vertex.
         * 
         * @param attrib the location.
         * @param offset the base offset into the currently bound VertexBufferObject.
         * @param data a vector of Vertex data.
         */
        void vertexPositionPointer(const string_type& attrib, ptrdiff_t offset, const graphics::Vertex::vector& data);

        /** Does glVertexAttribPointer() for Vertex::pos for a MemoryPool::Segment of Vertex.
         * 
         * @param index the location.
         * @param segment the buffer details.
         */
        void vertexPositionPointer(AttributeLocation index, const graphics::MemoryPool::Segment& segment);

        /** Does glVertexAttribPointer() for Vertex::pos for a MemoryPool::Segment of Vertex.
         * 
         * @param attrib the location.
         * @param segment the buffer details.
         */
        void vertexPositionPointer(const string_type& attrib, const graphics::MemoryPool::Segment& segment);

        /** Does glVertexAttribPointer() for Vertex::color for a vector of Vertex.
         * 
         * @param index the location.
         * @param offset the base offset into the currently bound VertexBufferObject.
         * @param data a vector of Vertex data.
         */
        void vertexColorPointer(AttributeLocation index, ptrdiff_t offset, const graphics::Vertex::vector& data);

        /** Does glVertexAttribPointer() for Vertex::color for a vector of Vertex.
         * 
         * @param attrib the location.
         * @param offset the base offset into the currently bound VertexBufferObject.
         * @param data a vector of Vertex data.
         */
        void vertexColorPointer(const string_type& attrib, ptrdiff_t offset, const graphics::Vertex::vector& data);

        /** Does glVertexAttribPointer() for Vertex::color for a MemoryPool::Segment of Vertex.
         * 
         * @param index the location.
         * @param segment the buffer details.
         */
        void vertexColorPointer(AttributeLocation index, const graphics::MemoryPool::Segment& segment);

        /** Does glVertexAttribPointer() for Vertex::color for a MemoryPool::Segment of Vertex.
         * 
         * @param attrib the location.
         * @param segment the buffer details.
         */
        void vertexColorPointer(const string_type& attrib, const graphics::MemoryPool::Segment& segment);

        /** Does glUniformMatrix4fv().
         * 
         * @param uniform the location of the uniform to send.
         * @param matrix the value of a GLSL mat4.
         */
        void uniformMatrixPointer(UniformLocation uniform, const mat4& matrix);

        /** @returns a string built from glGetActiveUniform().
         */
        string_type dumpUniform(UniformLocation location);

      private:
        static const string_type TAG;
        ProgramId mId;
    };
} }

#endif // SXE_GL_PROGRAM__HPP