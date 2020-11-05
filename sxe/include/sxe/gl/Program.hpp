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

namespace sxe { namespace gl {

    class Shader;

    class SXE_PUBLIC Program
      : virtual public common::Initializable<void>
      , virtual public common::stdtypedefs<Program>
    {
      public:
        using ProgramId = gl20::GLuint;

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

      private:
        static const string_type TAG;
        ProgramId mId;
    };
} }

#endif // SXE_GL_PROGRAM__HPP