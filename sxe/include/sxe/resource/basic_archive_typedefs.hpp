#ifndef SXE_RESOURCE_ARCHIVE_TYPEDEFS__HPP
#define SXE_RESOURCE_ARCHIVE_TYPEDEFS__HPP
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

namespace sxe { namespace resource {

    /** Typedefs common between basic_archive_* implementations.
     */
    template<class CharT, class Traits=std::char_traits<CharT>>
    class basic_archive_typedefs
    {
      public:
        // defined in istream, etc.
        // using char_type = CharT; 
        // ditto.
        // using traits_type = Traits;
        // using int_type = typename Traits::int_type;

        using off_type = typename Traits::off_type;

        using state_type = typename Traits::state_type;
        static_assert(std::is_copy_assignable<state_type>::value,
                      "Traits::state_type must be is_copy_assignable<>");
        static_assert(std::is_default_constructible<state_type>::value,
                      "Traits::state_type must be is_default_constructible<>");

        using pos_type = typename Traits::pos_type;
        static_assert(std::is_same<pos_type, std::fpos<state_type>>::value,
                      "pos_type must be fpos<state_type>");

        /** Convenience typedef for openmode. */
        using openmode_type_ = std::ios_base::openmode;

        /** Convenience typedef for string. */
        using string_type = std::string;

        /** Convenience typedef for path.
         */
        using path_type = sxe::filesystem::path;
    };

} }

#endif // SXE_RESOURCE_ARCHIVE_TYPEDEFS__HPP
