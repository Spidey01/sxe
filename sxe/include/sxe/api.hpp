#ifndef SXE_SXEAPI__HPP
#define SXE_SXEAPI__HPP
/*-
 * Copyright (c) 2019-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <sxe/cxxversion.hpp>
#include <sxe/stdheaders.hpp>

#include <sxe/dllexport.hpp>
#include <sxe/dllimport.hpp>

#if defined(SXE_DLL)
    /* SxE shared library. */
    #ifdef SXE_DLL_EXPORTS
        /* Building the SxE shared library. */
        #define SXE_PUBLIC SXE_DLL_EXPORT
    #else
        /* Linking to SxE shared library. */
        #define SXE_PUBLIC SXE_DLL_IMPORT
    #endif // SXE_DLL_EXPORTS
    #define SXE_PRIVATE SXE_DLL_HIDDEN
#else
    /* SxE static library. */
    #define SXE_PUBLIC
    #define SXE_PRIVATE
#endif // SXE_DLL

#include <sxe/filesystem.hpp>
#include <sxe/common/stdtypedefs.hpp>

#endif // SXE_SXEAPI__HPP
