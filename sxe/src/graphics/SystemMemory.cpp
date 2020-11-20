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

#include <sxe/graphics/SystemMemory.hpp>

#include <sxe/logging.hpp>

using std::to_string;
using sxe::logging::Log;

namespace sxe { namespace graphics {

SystemMemory::SystemMemory()
    : SystemMemory(static_cast<buffer_id>((uintptr_t)this), Log::TEST, "SystemMemory")
{
}

SystemMemory::SystemMemory(buffer_id theId, int level, const string_type& tag)
    : MemoryBuffer(theId, level, tag)
    , mBuffer(nullptr)
{
}

SystemMemory::~SystemMemory()
{
    if (mBuffer != nullptr) {
        log("~SystemMemory(): (uintptr_t)mBuffer: " + to_string((uintptr_t)mBuffer));
        delete mBuffer;
    }
}

void SystemMemory::bind()
{
    log("bind(): id(): " + std::to_string(id()));
}

void SystemMemory::allocate(size_type size, const void* pointer)
{
    log("allocate(): size: " + std::to_string(size) + " (uintptr_t)pointer: " + std::to_string((uintptr_t)pointer));

    if (mBuffer != nullptr)
        delete[] mBuffer;

    mBuffer = new uint8_t[size];
    memcpy(mBuffer, pointer, size);

    this->size(size);
}

void SystemMemory::buffer(difference_type offset, size_type size, const void* data)
{
    log("buffer(): offset: " + std::to_string(offset) + " size: " + std::to_string(size) + " (uintptr_t)data: " + std::to_string((uintptr_t)data));
    memcpy(mBuffer + offset, data, size);
    this->size(size);
}

void* SystemMemory::map(MapType access)
{
    log("map(): access: " + std::to_string((int)access));
    return mBuffer;
}

bool SystemMemory::unmap()
{
    log("unmap()");
    return true;
}

} }
