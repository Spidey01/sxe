#ifndef SXE_RESOURCE_RESOURCEHANDLE__HPP
#define SXE_RESOURCE_RESOURCEHANDLE__HPP
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

#include <sxe/api.hpp>
#include <sxe/common/stdtypedefs.hpp>
#include <sxe/graphics/VertexVertexMesh.hpp>

namespace sxe { namespace resource {

    /** Resource handle.
     */
    class SXE_PUBLIC ResourceHandle
        : public virtual common::stdtypedefs<ResourceHandle>
    {
      public:
        /** Wrapper for make_unique. */
        template <class... Args>
        static unique_ptr make_unique(Args&&... args);

        /** Wrapper for make_shared. */
        template <class... Args>
        static shared_ptr make_shared(Args&&... args);

        /** Create a handle to a resource.
         * 
         * @param resource the resource associated.
         */
        ResourceHandle(const path_type& resource);

        /** Create a handle to a resource in a container.
         * 
         * Use this for tasks like openning a resource from an archive file.
         * 
         * @param container /path/to/container.
         * @param resource /path/inside/container.
         */
        ResourceHandle(const path_type& container, const path_type& resource);

        virtual ~ResourceHandle();

        /** Return an istream for the resource.
         */
        std::unique_ptr<std::istream> asInputStream();

        /** Return a VV Mesh for the resource.
         */
        std::unique_ptr<graphics::VertexVertexMesh> asVertexVertexMesh();

      protected:
      private:
        static const string_type TAG;

        enum class LoaderType {
            FileStream,   /**< std::ifstream. */
            ArchiveStream /**< sxe::resource::archive_stream. */
        };

        using LoaderMap = std::map<path_type, LoaderType>;

        /** Map of path_type::extension() to a common loader.
         * 
         * E.g. To map ".zip" and ".tar" to the same ArchiveStream loader.
         */
        static LoaderMap sLoaderMap;

        /** The filesystem.
         */
        path_type mContainer;

        /** The resource to handle.
         */
        path_type mResource;
    };

    template <class... Args>
    ResourceHandle::unique_ptr ResourceHandle::make_unique(Args&&... args)
    {
        return std::make_unique<ResourceHandle>(std::forward<Args>(args)...);
    }

    template <class... Args>
    ResourceHandle::shared_ptr ResourceHandle::make_shared(Args&&... args)
    {
        return std::make_shared<ResourceHandle>(std::forward<Args>(args)...);
    }

} }

#endif // SXE_RESOURCE_RESOURCEHANDLE__HPP
