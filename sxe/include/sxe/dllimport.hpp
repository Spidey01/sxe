#ifndef SXE_DLLIMPORT__HPP
#define SXE_DLLIMPORT__HPP
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

#if SXE_MSVC_VERSION
    /* Compilers using __declspec() syntax. */
    #define SXE_DLL_IMPORT __declspec(dllimport)
#else
    #if SXE_GCC_VERSION || SXE_CLANG_VERSION
        /* Compilers using __attribute__ syntax. */
        #define SXE_DLL_IMPORT __attribute__ ((visibility ("default")))
    #else
        /* Compilers who don't care. */
        #define SXE_DLL_IMPORT
    #endif
#endif

#endif // SXE_DLLIMPORT__HPP
