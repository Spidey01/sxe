#ifndef SXE_COMMON_MUTEXTYPEDEFS__HPP
#define SXE_COMMON_MUTEXTYPEDEFS__HPP
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

namespace sxe { namespace common {

    /** Mix-in for common mutex type definitions.
     */
    template <class MutexType>
    class SXE_PUBLIC mutextypedefs
    {
      public:
        using mutex_type = MutexType;

        using lock_guard = std::lock_guard<mutex_type>;

        using unique_lock = std::unique_lock<mutex_type>;

#if SXE_CXX17
        using scoped_lock = std::scoped_lock<mutex_type>;
#endif
    };

    class SXE_PUBLIC mutex_typedefs : public mutextypedefs<std::mutex>
    {
    };
    class SXE_PUBLIC recursive_mutex_typedefs : public mutextypedefs<std::recursive_mutex>
    {
    };
    class SXE_PUBLIC timed_mutex_typedefs : public mutextypedefs<std::timed_mutex>
    {
    };
    class SXE_PUBLIC recursive_timed_mutex_typedefs : public mutextypedefs<std::recursive_timed_mutex>
    {
    };
} }

#endif // SXE_COMMON_MUTEXTYPEDEFS__HPP
