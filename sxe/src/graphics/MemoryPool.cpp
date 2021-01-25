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

#include <sxe/graphics/MemoryPool.hpp>

#include <sxe/logging.hpp>

using std::invalid_argument;
using std::logic_error;
using std::to_string;

namespace sxe { namespace graphics {

const MemoryPool::string_type MemoryPool::TAG = "MemoryPool";

MemoryPool::MemoryPool(size_type unit)
    : mId((uintptr_t)this)
    , mUnit(unit)
{

}

MemoryPool::MemoryPool(size_type unit, size_type count)
    : MemoryPool(unit)
{

}

MemoryPool::~MemoryPool()
{

}

MemoryPool::pool_id MemoryPool::id() const
{
    return mId;
}

MemoryPool::size_type MemoryPool::unit() const
{
    return mUnit;
}

MemoryPool::size_type MemoryPool::count() const
{
    return mBuffers.size();
}

MemoryPool::size_type MemoryPool::size() const
{
    return count() * unit();
}

MemoryPool::buffer_ptr MemoryPool::get(buffer_id which)
{
    for (Segment& seg : mSegments) {
        validate(seg.buffer);

        if (seg.buffer->id() == which)
            return seg.buffer;
    }

    Log::v(TAG, "get(): buffer " + to_string(which) + " not found");
    return nullptr;
}

MemoryPool::buffer_ptr MemoryPool::allocate()
{
    Log::xtrace(TAG, "allocate(): unit(): " + to_string(unit()) + " bytes; old count(): " + to_string(count()));

    buffer_ptr ptr = create();

    if (ptr) {
        ptr->pool(mId);
        ptr->reserve(mUnit);
        validate(ptr);

        Segment segment;
        segment.buffer = ptr;
        segment.length = 0;
        segment.offset = 0;
        mSegments.push_back(segment);
    }

    return ptr;
}

void MemoryPool::deallocate(buffer_ptr ptr)
{
    Log::xtrace(TAG, "deallocate(): ptr->id(): " + string_type(ptr ? to_string(ptr->id()) : " -- nullptr!"));

    auto seg = mSegments.begin();
    while (seg != mSegments.end())
    {
        if (seg->buffer == ptr) {
            Log::xtrace(TAG, "deallocate(): seg->buffer: id: " + to_string(seg->buffer->id()) + " size: " + to_string(seg->buffer->size()));
            Log::xtrace(TAG, "deallocate(): seg->offset: " + to_string(seg->offset));
            Log::xtrace(TAG, "deallocate(): seg->length: " + to_string(seg->length));
            seg = mSegments.erase(seg);
        } else {
            seg++;
        }
    }
}

void MemoryPool::deallocate(Segment& segment)
{
    for (auto seg = mSegments.begin(); seg != mSegments.end(); ++seg) {
        if (seg->buffer != segment.buffer)
            continue;
        if (seg->length != segment.length)
            continue;
        if (seg->offset != segment.offset)
            continue;
        Log::xtrace(TAG, "deallocate(): seg->buffer: id: " + to_string(seg->buffer->id()) + " size: " + to_string(seg->buffer->size()));
        Log::xtrace(TAG, "deallocate(): seg->offset: " + to_string(seg->offset));
        Log::xtrace(TAG, "deallocate(): seg->length: " + to_string(seg->length));
        mSegments.erase(seg);
        return;
    }
}

void MemoryPool::validate(buffer_ptr ptr)
{
    if (!ptr)
        throw logic_error("Do not put nullptrs in the MemoryPool");

    if (ptr->pool() != mId)
        throw logic_error("buffer " + to_string(ptr->id()) + " is in pool " + to_string(mId) + " but is owned by pool " + to_string(ptr->pool()));
}

MemoryPool::Segment MemoryPool::buffer(size_type length, const void* data)
{
    Log::xtrace(TAG, "buffer(): length: " + to_string(length) + " (uintptr_t)data: " + to_string((intptr_t)data));
    Segment segment;

    segment.buffer = nullptr;
    segment.offset = 0;
    segment.length = 0;

    buffer_id skip = 0;

    // XXX: needs to cover holes, e.g. segment right would be overwritten.
    Log::test(TAG, "--------");
    for (auto seg = mSegments.rbegin(); seg != mSegments.rend(); ++seg) {
        validate(seg->buffer);
        Log::xtrace(TAG, "buffer(): seg->buffer: id: " + to_string(seg->buffer->id()) + " size: " + to_string(seg->buffer->size()));
        Log::xtrace(TAG, "buffer(): seg->offset: " + to_string(seg->offset));
        Log::xtrace(TAG, "buffer(): seg->length: " + to_string(seg->length));

        if (seg->buffer->id() == skip) {
            Log::v(TAG, "seg: skipping, buffer " + to_string(seg->buffer->id()) + " already full");
            continue;
        }

        // size_type nextOffset = segment.length + seg->offset;
        size_type nextOffset = segment.length + seg->length + seg->offset;
        size_type remaining = seg->buffer->size() - nextOffset;
        Log::v(TAG, "next offset: " + to_string(nextOffset));
        Log::v(TAG, "remaining " + to_string(remaining));

        if (remaining >= length) {
            Log::v(TAG, "Using this segment.");
            segment.buffer = seg->buffer;
            segment.offset = nextOffset;
            segment.length = length;

            /* First segment into new/empty buffer */
            if (seg->length == 0) {
                Log::v(TAG, "Update first segment of buffer");
                *seg = segment;
                Log::xtrace(TAG, "seg->buffer: id: " + to_string(seg->buffer->id()) + " size: " + to_string(seg->buffer->size()));
                Log::xtrace(TAG, "seg->offset: " + to_string(seg->offset));
                Log::xtrace(TAG, "seg->length: " + to_string(seg->length));

                break;
            }

            mSegments.push_back(segment);
        } else {
            /* To next buffer's first eg. */
            skip = seg->buffer->id();
        }
    }
    Log::test(TAG, "--------");

    if (!segment.buffer) {
        Log::v(TAG, "New buffer + segment required.");
        segment.buffer = allocate();
        segment.length = length;
        segment.offset = 0;
        assert(mSegments.back().buffer == segment.buffer);
        mSegments.back() = segment;
    }

    if (!segment.buffer) 
        throw std::bad_alloc();

    segment.buffer->buffer(segment.offset, segment.length, data);
    size_type next = segment.length + segment.offset;
    size_type remaining = segment.buffer->size() - next;
    Log::d(TAG, "buffered " + to_string(length) + " bytes; next offset " + to_string(next) + " remaining bytes: " + to_string(remaining));

    return segment;
}

} }
