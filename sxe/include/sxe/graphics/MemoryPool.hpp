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
#include <sxe/graphics/MemorySegment.hpp>

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

        // For compat until other code updated. E.g. GraphicsFacet.
        using Segment = MemorySegment;

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

        /** Dellocates a segment of the pool.
         * 
         * This removes the allocated MemorySegments. When the last
         * MemorySegment referencing a buffer is removed, the MemoryBuffer is
         * no longer part of the pool.
         * 
         * @param segment the segment to dellocate.
         */
        void deallocate(MemorySegment& segment);

        /** Buffer data to the pool.
         * 
         * Finds an available buffer with at least length bytes remaining. If
         * no buffer exists: allocate() will be called to create one.
         * 
         * @returns a MemorySegment describing the buffer.
         * 
         * @throws bad_alloc if allocate() fails.
         */
        MemorySegment buffer(size_type length, const void* data);

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

        /** Our idea of remaining space.
         * 
         * @returns number of bytes remaining in buffer, according to mSegments.
         */
        size_type remaining(buffer_ptr buffer);

      private:
        static const string_type TAG;
        pool_id mId;
        size_type mUnit;
        using SegmentsList = std::list<MemorySegment>;
        SegmentsList mSegments;

        void logSegmentsList(SegmentsList& segments, int level);

        /** Find first MemorySegment from buffer.
         * I.e. the buffer's mSegments entry with offset 0.
         * 
         * @returns findSegment(buffer, 0).
         */
        SegmentsList::iterator findSegment(buffer_ptr buffer);

        /** Find MemorySegment from buffer offset.
         * 
         * @param buffer the buffer to find.
         * @param offset the position in buffer to find.
         * 
         * @returns Position of specified segment or mSegments.end().
         */
        SegmentsList::iterator findSegment(buffer_ptr buffer, size_type offset);

        /** Find start of free space.
         * 
         * @returns position after which a segment of length can be inserted, or mSegments.end().
         */
        SegmentsList::iterator findFreeSpace(size_type length);

        /** Buffers the data into the specified segment.
         * 
         * @param seg the segment to use.
         * @param length data length in bytes.
         * @param data upload to segment's buffer.
         * @throws bad_alloc if there is no buffer.
         */
        void bufferSegment(SegmentsList::iterator seg, size_type length, const void* data);

        /** Adds a new segment with length zero.
         * 
         * Commits a new segment at position using the same buffer as pos.
         * 
         * @param pos buffer for the new segment, and inserted before pos.
         * @returns iterator to the new segment.
         */
        SegmentsList::iterator addNewSegment(SegmentsList::iterator pos);

        /** Adds a new segment with specified length.
         * 
         * Commits a new segment at position using the same buffer as pos.
         * 
         * @param pos buffer for the new segment, and inserted before pos.
         * @param length the length of the new MemorySegment.
         * @returns iterator to the new segment.
         */
        SegmentsList::iterator addNewSegment(SegmentsList::iterator pos, size_type length);
    };
} }

#endif // SXE_GRAPHICS_MEMORYPOOL__HPP