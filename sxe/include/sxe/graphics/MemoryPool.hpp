#ifndef SXE_GRAPHICS_MEMORYPOOL__HPP
#define SXE_GRAPHICS_MEMORYPOOL__HPP
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
#include <sxe/common/stdtypedefs.hpp>
#include <sxe/graphics/MemoryBuffer.hpp>

namespace sxe { namespace graphics {

    /** A pool of MemoryBuffers for graphics rendering.
     */
    class SXE_PUBLIC MemoryPool
        : public virtual common::stdtypedefs<MemoryPool>
    {
      public:
        using size_type = size_t;
        using difference_type = ptrdiff_t;
        using pool_id = MemoryBuffer::pool_id;
        using buffer_ptr = MemoryBuffer::shared_ptr;
        using buffer_id = MemoryBuffer::buffer_id;
        using buffer_list = std::list<MemoryBuffer::shared_ptr>;

        /** Defines a segment of the memory pool.
         * 
         */
        struct Segment
        {
            /** Buffer containing this segment.
             */
            buffer_ptr buffer;

            /** Offset into buffer to beginning of this segment.
             */
            size_type offset;

            /** Length of this segmeant.
             */
            size_type length;
        };


        /** Create a new memory pool with no buffers.
         * 
         * @param unit the allocation unit, how memory each MemoryBuffer should
         * reserve.
         */
        MemoryPool(size_type unit);

        /** Create a new memory pool with count buffers.
         * 
         * @param unit the allocation unit, how memory each MemoryBuffer should
         * reserve.
         * 
         * @param count how many buffers to pre-create.
         */
        MemoryPool(size_type unit, size_type count);


        virtual ~MemoryPool();

        /** @returns the id of this pool
         */
        pool_id id() const;

        /** @returns allocation unit.
         */
        size_type unit() const;

        /** @returns number of buffers in this pool.
         */
        size_type count() const;

        /** @returns estimated size of this pool.
         */
        size_type size() const;

        /** @returns the specified buffer, or nullptr.
         */
        buffer_ptr get(buffer_id which);

        /** Allocates a new buffer in the pool.
         * 
         * @returns the new buffer.
         */
        buffer_ptr allocate();

        /** Deallocates a new buffer in the pool.
         * 
         * This effectively severs the connection between the buffer and this pool.
         * 
         * @param ptr the buffer to remove.
         */
        void deallocate(buffer_ptr ptr);

        /** Buffer data to the pool.
         * 
         * Finds an available buffer with at least length bytes remaining. If
         * no buffer exists: allocate() will be called to create one.
         * 
         * @returns a Segment describing the buffer.
         * 
         * @throws bad_alloc if allocate() fails.
         */
        Segment buffer(size_type length, const void* data);

      protected:

        /** Validate buffer.
         * 
         * @param ptr buffer to validate as a member of this pool.
         * 
         * @throws logic_error if you've gone insane.
         */
        void validate(buffer_ptr ptr);

        /** Create a new buffer to be added to the pool
         * 
         * Implementations just need to provide a buffer instance. Taking care
         * of caling reserve(), adding to the pool, etc is taken care of by the
         * base class.
         */
        virtual buffer_ptr create() = 0;

      private:
        static const string_type TAG;
        pool_id mId;
        size_type mUnit;
        buffer_list mBuffers;
        std::deque<Segment> mSegments;
    };
} }

#endif // SXE_GRAPHICS_MEMORYPOOL__HPP