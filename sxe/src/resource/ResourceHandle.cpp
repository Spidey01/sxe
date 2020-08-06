/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "sxe/resource/ResourceHandle.hpp"

#include <sxe/logging.hpp>
#include <sxe/resource/ArchiveStream.hpp>

namespace sxe { namespace resource {

const ResourceHandle::string_type ResourceHandle::TAG = "ResourceHandle";

ResourceHandle::LoaderMap ResourceHandle::sLoaderMap = {

    /* path loaders. */
    {"", LoaderType::FileStream},

    /* archive_stream loaders */
    {".zip", LoaderType::ArchiveStream},
    {".tar", LoaderType::ArchiveStream},
    {".tgz", LoaderType::ArchiveStream},
    {".tbz2", LoaderType::ArchiveStream},
    {".txz", LoaderType::ArchiveStream},

};

ResourceHandle::ResourceHandle(const path_type& resource)
    : ResourceHandle("", resource)
{
}

ResourceHandle::ResourceHandle(const path_type& container, const path_type& resource)
    : mContainer(container)
    , mResource(resource)
{
    Log::i(TAG, "ResourceHandle(): mResource: " + mResource.string());
}

ResourceHandle::~ResourceHandle()
{
    Log::i(TAG, "~ResourceHandle(): mResource: " + mResource.string());
}

std::unique_ptr<std::istream> ResourceHandle::asInputStream()
{
    using u_ptr = std::unique_ptr<std::istream>;

    auto it = sLoaderMap.find(mContainer.extension());

    if (it == sLoaderMap.end()) {
        Log::test(TAG, "asInputStream(): returning nullptr because no sLoaderMap entry for mContainer: " + mContainer.string());
        return nullptr;
    }

    switch (it->second) {
        case LoaderType::ArchiveStream:
            Log::test(TAG, "asInputStream(): returning archive_istream for mContainer: " + mContainer.string() + " mResource: " + mResource.string());
            return u_ptr(new sxe::resource::archive_istream(mContainer, mContainer));
        case LoaderType::FileStream:
        default:
            Log::test(TAG, "asInputStream(): returning ifstream for mResource: " + mResource.string());
            return u_ptr(new std::ifstream(mResource.string()));
    }

    Log::test(TAG, "asInputStream(): returning nullptr for mResource: " + mResource.string());
    return nullptr;
}

std::unique_ptr<graphics::VertexVertexMesh> ResourceHandle::asVertexVertexMesh()
{
    using u_ptr = std::unique_ptr<graphics::VertexVertexMesh>;

    auto input = asInputStream();
    if (input == nullptr)
        return nullptr;

    // sometimes parsing from ctor instead of lazy helps.
    auto p = std::make_unique<graphics::VertexVertexMesh>(*input);
    return p;
}

} }
