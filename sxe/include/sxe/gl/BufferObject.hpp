#ifndef SXE_GL_BUFFEROBJECT__HPP
#define SXE_GL_BUFFEROBJECT__HPP
/*-
 * Copyright (c) 2020-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <glbinding/gl20/gl.h>
#include <sxe/api.hpp>
#include <sxe/common/Initializable.hpp>
#include <sxe/common/stdtypedefs.hpp>
#include <sxe/logging/Log.hpp>

namespace sxe { namespace gl {

    /** Class encapsulating OpenGL Buffer Objects.
     * 
     * There are two ways to use BufferObject: automatic and manual mode.
     * 
     * In automatic mode the constructor will glGenBuffers() and the destructor
     * will glDeleteBuffers(), making it behave like a normal type.
     * 
     * In manual mode you must explicitly call initialize() and uninitialze()
     * to generate and delete the buffer.
     * 
     * @see VertexBufferObject.
     */
    template <class Derived>
    class SXE_PUBLIC BufferObject
        : virtual public common::stdtypedefs<Derived>
        , virtual public common::Initializable<void>
    {
      public:
        using size_type = gl20::GLsizeiptr;
        using ptrdiff_t = gl20::GLintptr;
        using buffer_id = gl20::GLuint;
        using target_type = gl20::GLenum;
        using usage_type = gl20::GLenum;
        using access_type = gl20::GLenum;

        /** Specifies the type and usage of the buffer.
         * 
         * @param automatic whether ctor/dtor will call initialize/uninitialize.
         * @param target binding target like GL_ARRAY_BUFFER.
         * @param usage use case like GL_STATIC_DRAW.
         * @param tag used for logging.
         * @param level used for logging.
         */
        BufferObject(bool automatic, target_type target, usage_type usage, const string_type& tag, int level)
            : mAutomatic(automatic)
            , mId(0)
            , mTarget(target)
            , mUsage(usage)
            , mTag(tag)
            , mLevel(level)
            , mSize(0)
        {
            if (automatic)
                initialize();
        }

        /** Specifies the type and usage of the buffer.
         * 
         * The default log level of TEST and tag "BufferObject" will be used.
         * 
         * @param automatic whether ctor/dtor will call initialize/uninitialize.
         * @param target binding target like GL_ARRAY_BUFFER.
         * @param usage use case like GL_STATIC_DRAW.
         */
        BufferObject(bool automatic, target_type target, usage_type usage)
            : BufferObject(automatic, target, usage, "BufferObject", logging::Log::TEST)
        {
        }

        virtual ~BufferObject()
        {
            logging::Log::log(mLevel, mTag, "~BufferObject(): mId: " + std::to_string(mId) + " mAutomatic: " + std::to_string(mAutomatic));

            if (mAutomatic && mIsInitialized)
                uninitialize();
        }

        /** Generate the buffer object name (getId()).
         */
        bool initialize() override
        {
            logging::Log::log(mLevel, mTag, "initialize():");

            if (!Initializable::initialize())
                return false;

            gl20::glGenBuffers(1, &mId);

            /* Until bound it isn't "Real" in the glIsBuffer() sense. */
            bind();

            return gl20::glIsBuffer(mId) == gl20::GL_TRUE;
        }

        /** Delete the named buffer object (getId()).
         */
        bool uninitialize() override
        {
            logging::Log::log(mLevel, mTag, "uninitialize(): mId: " + std::to_string(mId));

            if (!Initializable::uninitialize())
                return false;

            gl20::glDeleteBuffers(1, &mId);
            logging::Log::d(mTag, "mId after glDeleteBuffers(): " + std::to_string(mId));

            return true;
        }

        /** Bind the buffer object for use.
         */
        void bind()
        {
            logging::Log::log(mLevel, mTag, "bind(): mId: " + std::to_string(mId));
            gl20::glBindBuffer(mTarget, mId);
        }

        /** Creates and initializes a buffer object's data store.
         * 
         * @param size Specifies the size in bytes of the buffer object's new
         * data store.
         * 
         * @param data Specifies a pointer to data that will be copied into the
         * data store for initialization, or NULL if no data is to be copied.
         */
        void buffer(size_t size, const void* data)
        {
            logging::Log::log(mLevel, mTag, "buffer(): mId: " + std::to_string(mId) + " size: " + std::to_string(size) + " (uintptr_t)data: " + std::to_string((uintptr_t)data));
            mSize = size;
            bind();
            gl20::glBufferData(mTarget, size, data, mUsage);
        }

        /** Updates a subset of a buffer object's data store.
         * 
         * @param offset Specifies the offset into the buffer object's data
         * store where data replacement will begin, measured in bytes.
         * 
         * @param size Specifies the size in bytes of the data store region
         * being replaced.
         * 
         * @param data Specifies a pointer to the new data that will be copied
         * into the data store.
         */
        void buffer(ptrdiff_t offset, size_type size, const void* data)
        {
            logging::Log::log(mLevel, mTag, "buffer(): mId: " + std::to_string(mId) + " offset: " + std::to_string(offset) + " size: " + std::to_string(size) + " (uintptr_t)data: " + std::to_string((uintptr_t)data));
            bind()
            gl20::glBufferSubData(mTarget, offset, size, data);
        }

        /** Map all of a buffer object's data store into the client's address space.
         * 
         * @param access must be GL_READ_ONLY, GL_WRITE_ONLY, or GL_READ_WRITE.
         */
        void* map(access_type access)
        {
            logging::Log::log(mLevel, mTag, "map(): mId: " + std::to_string(mId) + " access: " + std::to_string((int)access));
            bind();
            return gl20::glMapBuffer(mTarget, access);
        }

        /** @returns the buffer id.
         */
        buffer_id getId() const
        {
            return mId;
        }

        /** Size of buffered data.
         * 
         * Equal to sizeof(vector_type::value_type) * v.size() we were
         * initialized with.
         * 
         * @returns size in bytes.
         */
        size_type size() const
        {
            return mSize;
        }

        /** @returns whether the size() is 0.
         */
        bool empty() const
        {
            return mSize == 0;
        }

        /** @returns the target.
         */
        target_type getTarget() const
        {
            return mTarget;
        }

        /** @returns the target.
         */
        usage_type getUsage() const
        {
            return mUsage;
        }

        /** @returns whether automatic mode.
         */
        bool isAutomaticMode() const
        {
            return mAutomatic;
        }

        /** @returns whether manual mode.
         */
        bool isManualMode() const
        {
            return !mAutomatic;
        }

      private:
        bool mAutomatic;
        buffer_id mId;
        target_type mTarget;
        usage_type mUsage;
        int mLevel;
        string_type mTag;
        size_type mSize;
    };
} }

#endif // SXE_GL_BUFFEROBJECT__HPP