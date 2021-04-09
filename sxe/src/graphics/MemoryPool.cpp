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
using std::stringstream;
using std::to_string;

namespace sxe { namespace graphics {

const MemoryPool::string_type MemoryPool::TAG = "MemoryPool";

MemoryPool::MemoryPool(size_type unit)
    : mId((uintptr_t)this)
    , mUnit(unit)
    , mSegments()
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
    if (mSegments.empty())
        return 0;
    if (!mSegments.front().buffer)
        return 0;

    size_type found = 1;
    buffer_id lbid = mSegments.front().buffer->id();

    for (const Segment& seg : mSegments) {
        if (!seg.buffer) {
            Log::w(TAG, "bad buffer in mSegments; Segment::id: " + to_string(seg.id));
            continue;
        }
        buffer_id id = seg.buffer->id();

        if (id != lbid) {
            found += 1;
            lbid = id;
        }
    }

    return found;
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
        /* mSegmentsList places new items on the front not the back. */
        mSegments.emplace_front(ptr);
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
            Log::xtrace(TAG, "deallocate(): erase " + seg->to_log_string());
            seg = mSegments.erase(seg);
        } else {
            seg++;
        }
    }
}

void MemoryPool::deallocate(Segment& segment)
{
    for (auto seg = mSegments.begin(); seg != mSegments.end(); ++seg) {
        if (seg->id != segment.id)
            continue;
        if (seg->buffer != segment.buffer)
            continue;
        if (seg->length != segment.length)
            continue;
        if (seg->offset != segment.offset)
            continue;
        Log::xtrace(TAG, "deallocate(): erase " + seg->to_log_string());
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

    SegmentsList::iterator seg = mSegments.end();

    /* Remember: list is ordered last to first! */

    Log::test(TAG, "buffer(): -------- Start Checking for for available space in current buffers --------");
    logSegmentsList(mSegments, Log::TEST);
    for (seg = mSegments.begin(); seg != mSegments.end(); ++seg) {
        validate(seg->buffer);
        string_type info = seg->to_log_string();
        Log::test(TAG, "buffer(): check " + info);

        /* The first segment of new buffer. */
        if (seg->length == 0) {
            Log::test(TAG, "FIRST");
            break;
        }

        size_type r = remaining(seg->buffer);
        Log::test(TAG, "buffer() check remaining: " + to_string(r));
        if (r >= length) {
            Log::xtrace(TAG, "buffer(): insert before " + info);
            seg = addNewSegment(seg);
            seg->length = length;
            break;
        }
    }
    Log::test(TAG, "buffer(): -------- End Checking for for available space in current buffers --------");

    if (seg == mSegments.end()) {
        Log::xtrace(TAG, "buffer(): allocating new Segment");
        Log::v(TAG, "buffer(): mSegments.size(): " + to_string(mSegments.size()));

        Log::xtrace(TAG, "buffer(): allocating new Segment requires allocating new buffer");
        if (!allocate()) {
            Log::e(TAG, "buffer(): allocate() failed!");
            throw std::bad_alloc();
        }
        seg = mSegments.begin();
    }

    bufferSegment(seg, length, data);
    Log::test(TAG, "buffer(): --- Start mSegments -- ");
    logSegmentsList(mSegments, Log::TEST);
    Log::test(TAG, "buffer(): --- End mSegments -- ");
    return *seg;
}

MemoryPool::size_type MemoryPool::remaining(buffer_ptr buffer)
{
    validate(buffer);

    // FIXME: this is returning based on last allocation in segments, so it
    // fails the holepunch_segments() test case by causing a new buffer
    // allocation.
    for (auto pos = mSegments.begin(); pos != mSegments.end(); ++pos) {
        validate(pos->buffer);
        if (pos->buffer->id() != buffer->id())
            continue;
        return pos->buffer->size() - (pos->length + pos->offset);
    }

    return buffer->size();
}

void MemoryPool::logSegmentsList(SegmentsList& list, int level)
{
    if (!Log::isLoggable(TAG, level))
        return;

    size_t i = 0;
    for (auto seg = list.begin(); seg != list.end(); ++seg) {
        validate(seg->buffer);
        Log::test(TAG, "SegmentsList " + to_string(i) + ": " + seg->to_log_string());
        i++;
    }
}

void MemoryPool::bufferSegment(SegmentsList::iterator seg, size_type length, const void* data)
{
    Log::xtrace(TAG, "bufferSegment(): in seg: " + seg->to_log_string() + " length: " + to_string(length) + " (uintptr_t)data: " + to_string((uintptr_t)data));

    if (!seg->buffer)
        throw std::bad_alloc();
    validate(seg->buffer);

    seg->length = length;

    seg->buffer->bind();
    seg->buffer->buffer(seg->offset, seg->length, data);
    Log::xtrace(TAG, "bufferSegment(): out seg: " + seg->to_log_string());
}

MemoryPool::SegmentsList::iterator MemoryPool::addNewSegment(SegmentsList::iterator pos)
{
    Log::xtrace(TAG, "addNewSegment(): pos: " + pos->to_log_string());

    MemorySegment seg(pos->buffer, pos->offset + pos->length, 0);

    return mSegments.insert(pos, std::move(seg));
}
    } }
