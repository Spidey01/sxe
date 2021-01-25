#ifndef SXE_TESTS_GRAPHICS_MEMORYPOOLFIXTURE__HPP
#define SXE_TESTS_GRAPHICS_MEMORYPOOLFIXTURE__HPP
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

#include <cppunit/extensions/HelperMacros.h>
#include <sxe/graphics/MemoryPool.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

template <class Derived>
class MemoryPoolFixture : public CPPUNIT_NS::TestFixture
{
    CPPUNIT_TEST_SUITE(Derived);
    CPPUNIT_TEST(constructor);
    CPPUNIT_TEST(allocate_and_deallocate);
    CPPUNIT_TEST(one_segment);
    CPPUNIT_TEST(multiple_segments);
    CPPUNIT_TEST(holepunch_segments);
    CPPUNIT_TEST_SUITE_END();

  protected:
    std::string mTag;
    size_t mUnit;
    size_t mCount;
    using MemoryPool = sxe::graphics::MemoryPool;
    MemoryPool::unique_ptr mPool;

    void log_segment(const MemoryPool::Segment& seg, const std::string& name)
    {
        Log::d(mTag, name + ": seg.length: " + to_string(seg.length) + " seg.offset: " + to_string(seg.offset) + " seg.buffer.get(): " + to_string((uintptr_t)seg.buffer.get()));
        Log::v(mTag, name + ": seg.buffer->id(): " + to_string(seg.buffer->id()) + " seg.buffer->pool(): " + to_string(seg.buffer->pool()) + " seg.buffer->size(): " + to_string(seg.buffer->size()));
    }

    void log_buffer(MemoryPool::buffer_ptr buffer, const std::string& name, size_t plen)
    {
        buffer->bind();
        uint8_t *p = buffer->map_ptr<uint8_t>(MemoryBuffer::ReadOnlyMapping);
        std::stringstream ss;
        for (size_t i = 0; i < plen; ++i) {
            ss << (int)p[i];
        }
        Log::test(mTag, name + ": seg.buffer->map_ptr()[0.." + to_string(plen) + "]: " + ss.str());
        buffer->unmap();
    }

    void constructor()
    {
        Log::xtrace(mTag, "constructor()");

        // Assumes mPool doesn't do anything fancy.

        CPPUNIT_ASSERT(mPool->id() >= 0);
        CPPUNIT_ASSERT(mPool->unit() == mUnit);
        CPPUNIT_ASSERT(mPool->count() == mCount);
        CPPUNIT_ASSERT(mPool->size() == (mUnit * mCount));
        CPPUNIT_ASSERT_NO_THROW(mPool->get(0));
    }

    void allocate_and_deallocate()
    {
        Log::xtrace(mTag, "allocate_and_deallocate()");

        MemoryPool::buffer_ptr buffer;
        
        Log::test(mTag, "Allocating a buffer.");
        CPPUNIT_ASSERT_NO_THROW(buffer = mPool->allocate());
        CPPUNIT_ASSERT(buffer != nullptr);
        CPPUNIT_ASSERT(buffer->pool() == mPool->id());
        CPPUNIT_ASSERT(buffer->id() >= 0);
        CPPUNIT_ASSERT(buffer->size() == mPool->unit());
        CPPUNIT_ASSERT(mPool->get(buffer->id()) != nullptr);
        CPPUNIT_ASSERT(mPool->get(buffer->id())->id() == buffer->id());

        Log::test(mTag, "Deallocating a buffer.");
        CPPUNIT_ASSERT_NO_THROW(mPool->deallocate(buffer));
        CPPUNIT_ASSERT(mPool->size() == 0);
    }

    void one_segment()
    {
        Log::xtrace(mTag, "one_segment()");

        MemoryPool::buffer_ptr buffer = mPool->allocate();
        Log::d(TAG, "mPool->count(): " + to_string(mPool->count()));
        // XXX: field is wrong. Fix not committed.
        CPPUNIT_ASSERT(mPool->count() > mCount);

        std::vector<uint8_t> data(mUnit / 4);
        std::fill(data.begin(), data.end(), 'X');

        MemoryPool::Segment segment = mPool->buffer(data.size(), &data[0]);
        CPPUNIT_ASSERT(segment.buffer == buffer);
        CPPUNIT_ASSERT(segment.offset == 0);
        CPPUNIT_ASSERT(segment.length == data.size());

        CPPUNIT_ASSERT_NO_THROW(mPool->deallocate(segment));

        CPPUNIT_ASSERT(mPool->size() == 0);
        CPPUNIT_ASSERT(mPool->count() == mCount);
    }

    void multiple_segments()
    {
        Log::xtrace(TAG, "multiple_segments()");

        constexpr size_t MaxBuffers = 2;
        const size_t MaxBytes = mUnit * MaxBuffers;
        const size_t ChunkSize = mUnit / 4;

        std::vector<uint8_t> data(ChunkSize);
        int value = 0;
        MemoryPool::size_type lastOffset = 0;
        constexpr auto MaxId = std::numeric_limits<MemoryPool::buffer_id>::max();
        MemoryPool::buffer_id lastId = MaxId;

        for (size_t bytes = 0; bytes < MaxBytes; value++, bytes += ChunkSize) {
            memset(&data[0], value, ChunkSize);
            MemoryPool::Segment seg = mPool->buffer(ChunkSize, &data[0]);
            Log::test(TAG, "bytes: " + to_string(bytes) + "/" + to_string(MaxBytes) + " value: " + to_string(value)
                      + " seg.length: " + to_string(seg.length) + " seg.offset: " + to_string(seg.offset) + " seg.buffer->id(): " + to_string(seg.buffer->id())
                      + " lastOffset: " + to_string(lastOffset) + " lastId: " + to_string(lastId));

            CPPUNIT_ASSERT(seg.length == ChunkSize);

            CPPUNIT_ASSERT(seg.buffer != nullptr);

            /* Segment offsets are 0 every mUnit size, not global to the pool. */
            if (seg.buffer->id() != lastId) {
                // XXX getting last element when reaching end of second segment.
                CPPUNIT_ASSERT(seg.offset == 0);
            } else {
                CPPUNIT_ASSERT(seg.offset == lastOffset + seg.length);
            }

            if (seg.offset > 0)
                CPPUNIT_ASSERT(seg.buffer->id() == lastId);

            lastOffset = seg.offset;
            lastId = seg.buffer->id();
        }
    }

    void holepunch_segments()
    {
        Log::xtrace(mTag, "holepunch_segments()");

        size_t length = mUnit / 4;

        std::vector<uint8_t> data(length);

        Log::d(mTag, "Setup seg1");
        memset(&data[0], 1, data.size());
        MemoryPool::Segment seg1 = mPool->buffer(length, &data[0]);
        MemoryPool::size_type offset1 = 0;
        log_segment(seg1, "seg1");

        Log::d(mTag, "Setup seg2");
        memset(&data[0], 2, data.size());
        MemoryPool::Segment seg2 = mPool->buffer(length, &data[0]);
        MemoryPool::size_type offset2 = length;
        log_segment(seg2, "seg2");

        Log::d(mTag, "Setup seg3");
        memset(&data[0], 3, data.size());
        MemoryPool::Segment seg3 = mPool->buffer(length, &data[0]);
        MemoryPool::size_type offset3 = length * 2;
        log_segment(seg3, "seg3");

        Log::d(mTag, "Setup seg4");
        memset(&data[0], 4, data.size());
        MemoryPool::Segment seg4 = mPool->buffer(length, &data[0]);
        MemoryPool::size_type offset4 = mUnit - length;
        log_segment(seg4, "seg4");

        CPPUNIT_ASSERT(seg1.offset == offset1);
        CPPUNIT_ASSERT(seg2.offset == offset2);
        CPPUNIT_ASSERT(seg3.offset == offset3);
        CPPUNIT_ASSERT(seg4.offset == offset4);

        CPPUNIT_ASSERT(seg1.length == length);
        CPPUNIT_ASSERT(seg2.length == length);
        CPPUNIT_ASSERT(seg3.length == length);
        CPPUNIT_ASSERT(seg4.length == length);

        // If 4 segments at 25% of mUnit they should be the same buffer.
        CPPUNIT_ASSERT(seg1.buffer->id() == seg2.buffer->id());
        CPPUNIT_ASSERT(seg2.buffer->id() == seg3.buffer->id());
        CPPUNIT_ASSERT(seg3.buffer->id() == seg4.buffer->id());

        Log::d(mTag, "Turn seg3 into a hole.");
        mPool->deallocate(seg3);

        memset(&data[0], 5, data.size());
        MemoryPool::Segment fill = mPool->buffer(length / 2, &data[0]);
        log_segment(fill, "fill");
        log_buffer(fill.buffer, "fill.buffer", fill.buffer->size());
        CPPUNIT_ASSERT(fill.offset == offset3);
        CPPUNIT_ASSERT(fill.length == length / 2);
        CPPUNIT_ASSERT(fill.buffer->id() == seg1.buffer->id());

        memset(&data[0], 6, data.size());
        MemoryPool::Segment fillY = mPool->buffer(length / 2, &data[0]);
        log_segment(fillY, "fillY");
        log_buffer(fillY.buffer, "fillY.buffer", fillY.buffer->size());
        CPPUNIT_ASSERT(fillY.offset == offset3);
        CPPUNIT_ASSERT(fillY.length == length / 2);
        CPPUNIT_ASSERT(fillY.buffer->id() == seg1.buffer->id());
    }
};

#endif // SXE_TESTS_GRAPHICS_MEMORYPOOLFIXTURE__HPP