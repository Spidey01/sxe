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

#include <sxe/graphics/MemoryBuffer.hpp>

#include <sxe/logging.hpp>

using std::to_string;
using sxe::logging::Log;

namespace sxe { namespace graphics {

MemoryBuffer::MemoryBuffer(buffer_id defaultId, int level, const string_type& tag)
    : mId(defaultId)
    , mLevel(level)
    , mBaseTag(tag)
    , mTag(mBaseTag + ":" + to_string(mId))
    , mSize(0)
    , mPoolId(0)
{
    log("MemoryBuffer()");
}

MemoryBuffer::MemoryBuffer(const string_type& tag)
    : MemoryBuffer(0, Log::TEST, tag)
{
}

MemoryBuffer::~MemoryBuffer()
{
    log("~MemoryBuffer(): mId: " + std::to_string(mId) + " mSize: " + to_string(mSize));
}

MemoryBuffer::pool_id MemoryBuffer::pool() const
{
    return mPoolId;    
}

void MemoryBuffer::pool(pool_id owner)
{
    log("pool(): owner: " + to_string(owner) + " (old) mPoolId: " + to_string(mPoolId));
    mPoolId = owner;
}

MemoryBuffer::buffer_id MemoryBuffer::id() const
{
    return mId;
}

MemoryBuffer::size_type MemoryBuffer::size() const
{
    return mSize;
}

bool MemoryBuffer::empty() const
{
    return mSize == 0;
}

void MemoryBuffer::reserve(size_type size)
{
    log("reserve(): size: " + std::to_string(size));

    std::vector<uint8_t> zeros(size, 0);
    allocate(zeros.size(), &zeros[0]);
}

void MemoryBuffer::id(buffer_id bid)
{
    log("id(): new id: " + to_string(bid) + " old id: " + to_string(mId));
    mId = bid;
    mTag = mBaseTag + ":" + to_string(mId);
}

void MemoryBuffer::size(size_type nbytes)
{
    log("size(): nbytes: " + to_string(nbytes) + " old mSize: " + to_string(mSize));
    mSize = nbytes;
}

void MemoryBuffer::level(int level)
{
    mLevel = level;
}

int MemoryBuffer::level() const
{
    return mLevel;
}

const MemoryBuffer::string_type& MemoryBuffer::tag() const
{
    return mTag;
}

const MemoryBuffer::string_type& MemoryBuffer::baseTag() const
{
    return mBaseTag;
}

void MemoryBuffer::log(const string_type& message)
{
    sxe::logging::Log::log(mLevel, mTag, message);
}

void MemoryBuffer::log(const string_type& message, const std::exception& error)
{
    sxe::logging::Log::log(mLevel, mTag, message, error);
}

} }
