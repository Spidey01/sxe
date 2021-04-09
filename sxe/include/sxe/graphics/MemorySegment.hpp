#ifndef SXE_GRAPHICS_MEMORYSEGMENT__HPP
#define SXE_GRAPHICS_MEMORYSEGMENT__HPP
/*-
 * Copyright (c) 2021-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
#include <sxe/graphics/MemoryBuffer.hpp>

namespace sxe { namespace graphics {

    /** A segment of MemoryBuffer.
     * 
     * Used by MemoryPool for tracking allocations.
     */
    class SXE_PUBLIC MemorySegment
    {
      public:
        using string_type = MemoryBuffer::string_type;
        using size_type = size_t;
        using difference_type = ptrdiff_t;
        using pool_id = MemoryBuffer::pool_id;
        using buffer_ptr = MemoryBuffer::shared_ptr;
        using buffer_id = MemoryBuffer::buffer_id;
        using segment_id = uint64_t;

        /** Defines a segment of the memory pool.
         * 
         * @param buffer the buffer field.
         * @param offset the offset field.
         * @param length the length field.
         */
        MemorySegment(buffer_ptr buffer, size_type offset, size_type length);

        /** Default constructs against buffer.
         * 
         * Equal to 0 offset and length.
         */
        MemorySegment(buffer_ptr buffer);

        /** id of this segment.
         * 
         * Segment identifiers are unique within a process at runtime.
         */
        segment_id id;

        /** Buffer containing this segment.
         */
        buffer_ptr buffer;

        /** Offset into buffer to beginning of this segment.
         */
        size_type offset;

        /** Length of this segment in bytes.
         */
        size_type length;

        operator bool() const
        {
          return buffer && length > 0;
        };

        /** @returns a string suitable for logging this segment's info.
         */
        string_type to_log_string() const;

      private:
        static const string_type TAG;
        static std::atomic<segment_id> sLastSegmentId;
    };
} }

#endif // SXE_GRAPHICS_MEMORYSEGMENT__HPP