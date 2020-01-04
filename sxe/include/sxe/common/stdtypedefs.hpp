#ifndef SXE_COMMON_STDTYPEDEFS__HPP
#define SXE_COMMON_STDTYPEDEFS__HPP
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
#include <sxe/stdheaders.hpp>
#include <sxe/filesystem.hpp>

namespace sxe { namespace common {

    /** Mix-in for common type definitions.
     *
     *  - normal types for strings and paths.
     */
    template <class Derived>
    class SXE_PUBLIC stdtypedefs
    {
      public:

        /** Type used for string parameters.
         */
        using string_type = std::string;

        /** Type used for filesystem path parameters.
         */
        using path_type = sxe::filesystem::path;

        /** unique_ptr's of this type.
         */
        using unique_ptr = std::unique_ptr<Derived>;

        /** shared_ptr's of this type.
         */
        using shared_ptr = std::shared_ptr<Derived>;

        /** weak_ptr's of this type.
         */
        using weak_ptr = std::weak_ptr<Derived>;

    };


} }

#endif // SXE_COMMON_STDTYPEDEFS__HPP
