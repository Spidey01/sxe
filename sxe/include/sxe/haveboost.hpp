#ifndef SXE_HAVEBOOST__HPP
#define SXE_HAVEBOOST__HPP
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

#ifndef SXE_HAVE_BOOST
#ifndef HAVE_BOOST
#define SXE_HAVE_BOOST 0
#else // HAVE_BOOST
#define SXE_HAVE_BOOST 1
#endif // HAVE_BOOST
#define SXE_HAVE_BOOST 0
#endif // SXE_HAVE_BOOST


#if SXE_HAVE_BOOST

#include <boost/filesystem.hpp>
#include <boost/optional.hpp>
#include <boost/property_tree/ptree.hpp>

#else // these will trigger a failure if you toolchain can't provide comparable stuff.

#include <filesystem>
#include <optional>

#endif

#endif // SXE_HAVEBOOST__HPP
