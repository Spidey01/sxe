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
    , mBuffers()
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

const MemoryPool::buffer_list& MemoryPool::buffers() const
{
    return mBuffers;
}

MemoryPool::buffer_list& MemoryPool::buffers()
{
    return mBuffers;
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
    for (buffer_ptr ptr : mBuffers) {
        validate(ptr);

        if (ptr->id() == which)
            return ptr;
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
        mBuffers.push_back(ptr);
    }

    return ptr;
}

void MemoryPool::deallocate(buffer_ptr ptr)
{
    Log::xtrace(TAG, "deallocate(): ptr->id(): " + string_type(ptr ? to_string(ptr->id()) : " -- nullptr!"));

    mBuffers.erase(std::remove(mBuffers.begin(), mBuffers.end(), ptr), mBuffers.end());
}

void MemoryPool::validate(buffer_ptr ptr)
{
    if (!ptr)
        throw logic_error("Do not put nullptrs in the MemoryPool");

    if (ptr->pool() != mId)
        throw logic_error("buffer " + to_string(ptr->id()) + " is in pool " + to_string(mId) + " but is owned by pool " + to_string(ptr->pool()));
}
} }
