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

#include <sxe/graphics/MemorySegment.hpp>

#include <sxe/logging.hpp>

using std::invalid_argument;
using std::stringstream;
using std::to_string;

namespace sxe { namespace graphics {

const MemorySegment::string_type MemorySegment::TAG = "MemorySegment";

std::atomic<MemorySegment::segment_id> MemorySegment::sLastSegmentId = 0;

MemorySegment::MemorySegment(buffer_ptr buffer, size_type offset, size_type length)
    : id(++sLastSegmentId)
    , buffer(buffer)
    , offset(offset)
    , length(length)
{
    string_type bid;
    if (buffer)
        bid = to_string(buffer->id());
    else
        bid = "nullptr";

    Log::test(TAG, "MemorySegment(): id:" + to_string(id) + " buffer->id" + bid + " length: " + to_string(length));
}

MemorySegment::MemorySegment(buffer_ptr buffer)
    : MemorySegment(buffer, 0, 0)
{
}

MemorySegment::string_type MemorySegment::to_log_string() const
{
    stringstream ss;

    ss << "MemorySegment:{"
       << " id: " << id << ","
       << " length: " << length << ','
       << " offset: " << offset << ','
       << " buffer: " << buffer->to_log_string()
       << " }";

    return ss.str();
}

} }
