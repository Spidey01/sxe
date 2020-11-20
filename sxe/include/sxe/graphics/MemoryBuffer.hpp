#ifndef SXE_GRAPHICS_MEMORYBUFFER__HPP
#define SXE_GRAPHICS_MEMORYBUFFER__HPP
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

#include <sxe/api.hpp>
#include <sxe/common/Initializable.hpp>
#include <sxe/common/stdtypedefs.hpp>

namespace sxe { namespace graphics {

    /** @brief Graphics memory buffer objects.
     * 
     * Provides a common base for buffering memory for use with
     * DrawingTechniques, and related graphics code.
     * 
     * History:
     * ========
     * 
     * - SxE 1.0 used a VertexBuffer for all intents and purposes.
     * 
     * - SxE 2.0 divides these into Vertex for vertex attributes, and various
     * form of MemoryBuffer.
     * 
     * @see sxe::graphics::Vertex, sxe::graphics::SystemMemory,
     * sxe::gl::BufferObject.
     */
    class SXE_PUBLIC MemoryBuffer
        : virtual public common::stdtypedefs<MemoryBuffer>
    {
      public:
        using bad_alloc = std::bad_alloc;
        using size_type = size_t;
        using difference_type = ptrdiff_t;
        using buffer_id = unsigned int;

        /** Initialize a buffer.
         * 
         * @param defaultId initial value of id().
         * @param level used for logging.
         * @param tag used for logging.
         * @throws bad_alloc if initialze() returns false.
         */
        MemoryBuffer(buffer_id defaultId, int level, const string_type& tag);

        /** MemoryBuffer(0, Log::TEST, tag).
         */
        MemoryBuffer(const string_type& tag);

        /** Destructor.
         * 
         * In automatic mode: calls unitialize() to clean up like a scoped
         * object. In manual mode: it's your job to manually uninitialize().
         */
        virtual ~MemoryBuffer();

        /** @returns the id of this buffer.
         * 
         * By default this is 0. The meaning of id() may vary be
         * implementation, e.g. a OpenGL buffer object.
         */
        buffer_id id() const;

        /** Make the buffer active for rendering.
         * 
         * For backends like OpenGL, do glBindBuffer().
         */
        virtual void bind() = 0;

        /** Size of buffered data.
         * 
         * @returns size in bytes.
         */
        size_type size() const;
        
        /** @returns whether the size() is 0.
         */
        bool empty() const;

        /** Allocate a zeroed buffer.
         * 
         * Unlike STL types like std::vector, to reserve a MemoryBuffer is to
         * effectively delete and allocate a new data buffer.
         * 
         * By default this calls allocate() with a sequence of zeros.
         * 
         * @param size Specifies the size in bytes of the buffer object's new
         * data store.
         */
        virtual void reserve(size_type size);

        /** Allocate buffer object's data store.
         * 
         * While creating the new storage, any pre-existing data store is
         * deleted.
         * 
         * @param size Specifies the size in bytes of the buffer object's new
         * data store.
         * 
         * @param data Specifies a pointer to data that will be copied into the
         * data store for initialization, or nullptr if no data is to be copied.
         */
        virtual void allocate(size_type size, const void* pointer) = 0;

        /** Updates a subset of a buffer object's data store.
         * 
         * This does not create data store. Therefore offset must be within the
         * range already created with allocate().
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
        virtual void buffer(difference_type offset, size_type size, const void* data) = 0;

        enum MapType {
          ReadWriteMapping, /*< Pointer used to read/write. */
          ReadOnlyMapping, /*< Pointer used to read. */
          WriteOnlyMapping, /*< Pointer used to write. */
        };

        /** Map the buffer's data store.
         * 
         * Provides a client pointer to the data store. For graphics memory
         * this can be thought of memory mapping the store to your address
         * space. Performance, alignment, and behavior details can vary, and is
         * therefore undefined.
         * 
         * In most cases it is unlikely you should do more than memcpy() data
         * to or from the pointer.
         * 
         * Accessing the buffer with the backend rendering API while mapped, passing the
         * pointer to its API while mapped is consideed an error. In short be
         * very careful unless you're absolutely sure of what you are doing,
         * and then think about it three more times :P.
         * 
         * Violating the specified MapType is unspecified. Be kind to your GPU.
         * 
         * Implementations should ensure a mapped buffer is umapped at
         * destruction.
         * 
         * @param access the type of mapping to create.
         * @returns nullptr on failure; a valid pointer on success.
         */
        virtual void* map(MapType access) = 0;

        /** Template function to map() and cast to T pointer.
         */
        template <class T>
        T* map_ptr(MapType usage)
        {
            return (T*)map(usage);
        }

        /** Unmap the buffer..
         * 
         * Dereferencing pointers returned by map() after unmap() are undefined. Don't do it.
         * 
         * @returns true on success.
         */
        virtual bool unmap() = 0;

        // maybe map in const/non const ver that use ro/rw ?

        /** Set the log level.
         */
        void level(int level);

        /** Get the log level.
         */
        int level() const;

        /** @returns tag used by log().
         * 
         * Combination of the tag passed to the ctor, and id().
         */
        const string_type& tag() const;

        /** @returns tag passed to the ctor.
         */
        const string_type& baseTag() const;

      protected:

        /** Sets size().
         * @param nbytes the new size value.
         */
        void size(size_type nbytes);

        /** Sets the id of this buffer.
         */
        void id(buffer_id bid);

        /* Log with configured level()/tag().
         */
        void log(const string_type& message);
        void log(const string_type& message, const std::exception& error);

      private:
        buffer_id mId;
        int mLevel;
        string_type mBaseTag;
        string_type mTag;
        size_type mSize;
    };
} }

#endif // SXE_GRAPHICS_MEMORYBUFFER__HPP