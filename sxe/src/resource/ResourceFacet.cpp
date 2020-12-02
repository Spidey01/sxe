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

#include <sxe/resource/ResourceFacet.hpp>

#include <sxe/graphics/MemoryBuffer.hpp>
#include <sxe/logging.hpp>
#include <sxe/resource/ResourceHandle.hpp>
#include <sxe/resource/ResourceManager.hpp>

using std::exception;
using std::to_string;
using sxe::graphics::MemoryBuffer;

namespace sxe { namespace resource {

const ResourceFacet::string_type ResourceFacet::TAG = "ResourceFacet";

ResourceFacet::ResourceFacet(ResourceManager& manager)
    : mResourceManager(manager)
{
    Log::xtrace(TAG, "ResourceFacet()");
}

ResourceFacet::~ResourceFacet()
{
    Log::xtrace(TAG, "~ResourceFacet()");
}

bool ResourceFacet::load(const string_type& resource, MemoryBuffer& buffer)
{
    // return load(resource, &ResourceFacet::filter, buffer);
    return false; // fuck you
}

bool ResourceFacet::load(const string_type& resource, Filter& filter, MemoryBuffer& buffer)
{
    Log::xtrace(TAG, "load(): resource: " + resource + " buffer.id(): " + to_string(buffer.id()));

    try {
        ResourceHandle::unique_ptr res = mResourceManager.get().load(resource);
        if (!res) {
            Log::w(TAG, "ResourceManager::load() returned nullptr for " + resource);
            return false;
        }

        auto input = res->asInputStream();
        if (input == nullptr) {
            Log::w(TAG, "ResourceHandle::asInputStream() returned nullptr for " + resource);
            return false;
        }

        filter(*input, buffer);

    } catch (std::exception& ex) {
        Log::e(TAG, "setupResources() failed", ex);
        return false;
    }

    return true;
}

bool ResourceFacet::filter(std::istream& input, MemoryBuffer& output)
{
    constexpr size_t BlockSize = 4096;

    std::vector<uint8_t> bytes;
    uint8_t block[BlockSize];

    while (input) {
        input.read((char*)block, BlockSize);
        bytes.insert(bytes.end(), block, block + input.gcount());
    }

    output.allocate(bytes.size(), &bytes[0]);
    return true;
}

} }