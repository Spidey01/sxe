#ifndef SXE_TESTS_GRAPHICS_MEMORYBUFFERFIXTURE__HPP
#define SXE_TESTS_GRAPHICS_MEMORYBUFFERFIXTURE__HPP
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
#include <sxe/graphics/MemoryBuffer.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

template <class Derived>
class MemoryBufferFixture : public CPPUNIT_NS::TestFixture
{
    CPPUNIT_TEST_SUITE(Derived);
    CPPUNIT_TEST(pool);
    CPPUNIT_TEST(reserve);
    CPPUNIT_TEST(allocate);
    CPPUNIT_TEST(buffer);
    CPPUNIT_TEST(map);
    CPPUNIT_TEST_SUITE_END();

  protected:
    std::string mTag;
    size_t mSize;
    sxe::graphics::MemoryBuffer::unique_ptr mBuffer;

    void pool()
    {
        Log::xtrace(mTag, "pool()");

        constexpr MemoryBuffer::pool_id owner = 5;

        CPPUNIT_ASSERT(mBuffer->pool() == 0);
        CPPUNIT_ASSERT_NO_THROW(mBuffer->pool(owner));
        CPPUNIT_ASSERT(mBuffer->pool() == owner);
    }

    void reserve()
    {
        Log::xtrace(mTag, "reserve()");

        Log::test(mTag, "Reserving " + std::to_string(mSize) + " bytes.");
        CPPUNIT_ASSERT_NO_THROW(mBuffer->bind());
        CPPUNIT_ASSERT_NO_THROW(mBuffer->reserve(mSize));
        CPPUNIT_ASSERT(mBuffer->size() == mSize);
    }

    void allocate()
    {
        Log::xtrace(mTag, "allocate()");

        std::vector<uint8_t> data(mSize);
        std::fill(data.begin(), data.end(), 0);

        Log::test(mTag, "Allocating " + std::to_string(mSize) + " bytes.");
        CPPUNIT_ASSERT_NO_THROW(mBuffer->bind());
        CPPUNIT_ASSERT_NO_THROW(mBuffer->allocate(data.size(), &data[0]));
        CPPUNIT_ASSERT(mBuffer->size() == mSize);
    }

    void buffer()
    {
        Log::xtrace(mTag, "buffer()");

        std::vector<uint8_t> data(mSize);
        size_t length = mSize / 2;
        auto half = std::next(data.begin(), length);
        std::fill(data.begin(), half - 1, 'F');
        std::fill(half, data.end(), 'B');

        Log::test(mTag, "Reserving " + std::to_string(mSize) + " bytes.");
        CPPUNIT_ASSERT_NO_THROW(mBuffer->bind());
        CPPUNIT_ASSERT_NO_THROW(mBuffer->reserve(mSize));
        CPPUNIT_ASSERT(mBuffer->size() == mSize);

        Log::test(mTag, "Buffering 0.." + to_string(std::distance(data.begin(), half - 1)));
        CPPUNIT_ASSERT_NO_THROW(mBuffer->buffer(0, length-1, &data[0]));

        Log::test(mTag, "Buffering " + to_string(std::distance(half, data.end())) + ".." + to_string(data.size()));
        CPPUNIT_ASSERT_NO_THROW(mBuffer->buffer(length, length, &data[length]));

        uint8_t* ptr = mBuffer->map_ptr<uint8_t>(MemoryBuffer::ReadOnlyMapping);
        if (ptr == nullptr) {
            Log::test(mTag, "buffer(): implementation does not support map().");
            Log::w(mTag, "buffer() can't verify buffer because memory mapping is not supported.");
            return;
        }
        CPPUNIT_ASSERT(mBuffer->map_length<uint8_t>() == mSize);

        for (size_t i = 0; i < data.size(); ++i) {
            Log::test(mTag, "index " + to_string(i) + " data[i]: " + to_string((int)data[i]) + " ptr[i]: " + to_string((int)ptr[i]));
            CPPUNIT_ASSERT_EQUAL_MESSAGE("difference at index " + to_string(i),
                                         data[i], ptr[i]);
        }

        CPPUNIT_ASSERT_NO_THROW(mBuffer->unmap());
    }

    void map()
    {
        Log::xtrace(mTag, "map()");

        Log::test(mTag, "map(): reserving " + to_string(mSize) + " bytes.");
        CPPUNIT_ASSERT_NO_THROW(mBuffer->bind());
        mBuffer->reserve(mSize);

        int32_t* ptr = nullptr;
        constexpr int32_t FourBytes = 0xCAFEBABE;

        Log::test(mTag, "map(): writing data.");

        ptr = mBuffer->map_ptr<int32_t>(MemoryBuffer::WriteOnlyMapping);
        CPPUNIT_ASSERT_MESSAGE("map(): map() failed, or implementation does not support WriteOnlyMapping.",
                               ptr != nullptr);

        size_t len = mBuffer->map_length<int32_t>();
        CPPUNIT_ASSERT(len == mSize / sizeof(int32_t));

        for (size_t i = 0; i < len; ++i) {
            ptr[i] = FourBytes;
        }

        CPPUNIT_ASSERT(mBuffer->unmap());

        ptr = nullptr;

        Log::test(mTag, "map(): reading data.");
        ptr = mBuffer->map_ptr<int32_t>(MemoryBuffer::ReadOnlyMapping);
        CPPUNIT_ASSERT_MESSAGE("map(): map() failed, or implementation does not support ReadOnlyMapping.",
                               ptr != nullptr);

        len = mBuffer->map_length<int32_t>();
        CPPUNIT_ASSERT(len == mSize / sizeof(int32_t));

        for (size_t i = 0; i < len; ++i) {
            Log::test(mTag, "index " + to_string(i) + " ptr[i]: " + to_string(ptr[i]) + " FourBytes: " + to_string(FourBytes));
            CPPUNIT_ASSERT_EQUAL(FourBytes, ptr[i]);
        }

        CPPUNIT_ASSERT(mBuffer->unmap());
    }
};

#endif // SXE_TESTS_GRAPHICS_MEMORYBUFFERFIXTURE__HPP