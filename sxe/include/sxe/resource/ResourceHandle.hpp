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

        virtual ~ResourceHandle();

      protected:
      private:
        static const string_type TAG;

        /** The resource to handle.
         */
        path_type mResource;
    };

    template <class... Args>
    static ResourceHandle::unique_ptr ResourceHandle::make_unique(Args&&... args)
    {
        return std::make_unique<ResourceHandle>(std::forward<Args>(args)...);
    }

    template <class... Args>
    static ResourceHandle::shared_ptr ResourceHandle::make_shared(Args&&... args)
    {
        return std::make_shared<ResourceHandle>(std::forward<Args>(args)...);
    }

} }

#endif // SXE_RESOURCE_RESOURCEHANDLE__HPP