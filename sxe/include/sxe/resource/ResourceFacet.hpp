#ifndef SXE_RESOURCE_RESOURCEFACET__HPP
#define SXE_RESOURCE_RESOURCEFACET__HPP
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

#include <sxe/api.hpp>
#include <sxe/common/stdtypedefs.hpp>

namespace sxe {
    namespace graphics {
        class MemoryBuffer;
    }
}

namespace sxe { namespace resource {
    class ResourceManager;

    /** @brief Facet for resource loading.
     */
    class SXE_PUBLIC ResourceFacet
        : public common::stdtypedefs<ResourceFacet>
    {
      public:
        ResourceFacet(ResourceManager& manager);
        ~ResourceFacet();

        /** Function to filter data into the buffer.
         * 
         * Called with the input stream of bytes, and the output buffer to
         * fill. Return false if you hit an error.
         */
        using Filter = std::function<bool(std::istream&, sxe::graphics::MemoryBuffer&)>;

        /** Load the resource into a buffer.
         * 
         * Calls load() with our default filter() function.
         * 
         * @param resource what to load.
         * @param[out] buffer stores the data.
         * @returns true on success.
         */
        bool load(const string_type& resource, sxe::graphics::MemoryBuffer& buffer);

        /** Load the resource into a buffer.
         * 
         * Effectively loads resource as an input stream, and uses filter to buffer the data.
         * 
         * @param resource what to load.
         * @param[in] filter how to consume the stream.
         * @param[out] buffer stores the data.
         * @returns true on success.
         */
        bool load(const string_type& resource, Filter& filter, sxe::graphics::MemoryBuffer& buffer);

        /** Default resource filter.
         * 
         * Bytes in, bytes out.
         * @param[in] input the byte stream to buffer.
         * @param[out] buffer stores the bytes.
         * @returns true on success.
         */
        static bool filter(std::istream& input, sxe::graphics::MemoryBuffer& buffer);

      private:
        const static string_type TAG;
        std::reference_wrapper<ResourceManager> mResourceManager;
    };
} }

#endif // SXE_RESOURCE_RESOURCEFACET__HPP