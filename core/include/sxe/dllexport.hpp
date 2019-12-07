#ifndef SXE_DLLEXPORT__HPP
#define SXE_DLLEXPORT__HPP
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

 #if SXE_MSC_VERSION
    /* Compilers using __declspec() syntax. */
    #define SXE_DLL_EXPORT __declspec(dllexport)
    #define SXE_DLL_HIDDEN
#else
    #if SXE_GCC_VERSION || SXE_CLANG_VERSION
        /* Compilers using __attribute__ syntax. */
        #define SXE_DLL_EXPORT __attribute__ ((visibility ("default")))
        #define SXE_DLL_HIDDEN  __attribute__ ((visibility ("hidden")))
    #else
        /* Compilers who don't care. */
        #define SXE_DLL_EXPORT
        #define SXE_DLL_HIDDEN
    #endif
#endif

#endif // SXE_DLLEXPORT__HPP
