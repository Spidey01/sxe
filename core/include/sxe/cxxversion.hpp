#ifndef SXE_CXXVERSION__H
#define SXE_CXXVERSION__H

/*
 * __cplusplus values:
 *
 *  199711L = C++ 98/03
 *  201103L = C++ 11
 *  201402L = C++ 14
 *  201703L = C++ 17
 *  TBD     = C++ 20
 *
 * Compiler flags like -std=[gnu|c]++0x and /std:c++latest might define
 * __cplusplus to be the associated ver but that does not mean all features or
 * headers are supported.
 *
 * E.g. std::any, std::optional, and std::variant require:
 *  - GCC/libstdc++ 7
 *  - Clang/libc++ 4.0,
 *  - Visual C++ 2017 (19.10)
 *
 * The headers don't exist in the previous versions, and using -std=c++17 or
 * /std:c++latest is not enough.
 *
 * To make these issues simplier, I'm just going to define my own macros based on:
 *
 *      https://en.cppreference.com/w/cpp/compiler_support
 *
 * Each macro should be defined such that 'if SXE_CXXnn' always works. Ditto
 * 'if SXE_compiler_VERSION'.
 */


#if defined(_MSC_VER)
/* Microsoft makes this easy unless you want to map product name, product version, macro number without MSDN. */
/* Disclaimer: looks like __cplusplus = 199711L, _MSVC_LANG = what that SHOULD be! */

/*
 * C++ 17 support in Visual Studio 2017 from sub version. By 1915 (version 15.8)
 * it should be as complete as it gets.
 */
#define SXE_CXX17 ( _MSVC_LANG > 201402L && _MSC_VER >= 1915)

/*
 * C++ 14 support in Visual Studio 2015 is pretty complete but a few things may
 * require some version of VS2017, and proper updates to VS2015.
 */
#define SXE_CXX14 (_MSVC_LANG > 201103L && _MSC_VER >= 1900)

/*
 * C++ 11 support in Visual Studio 2015 is pretty good, and was was very incremental from about VS2010 onwards.
 */
#define SXE_CXX11 (_MSVC_LANG > 199711L && _MSC_VER >= 1900)

#define SXE_MSVC_VERSION _MSC_VER
#else
#define SXE_MSVC_VERSION 0
#endif // _MSC_VER

#if defined(__clang__)
/* Clang does similar to GCC. */

#define SXE_CLANG_VERSION ( __clang_major__ * 10000 \
                            + __clang_minor__ * 100 \
                            + __clang_patchlevel__ )

/*
 * C++ 17 support in Clang/libstdc++ is pretty complete by version 4.0.
 */
#define SXE_CXX17 (__cplusplus > 201103L && SXE_CLANG_VERSION >= 40000)

/*
 * C++ 14 support in Clang/libstdc++ is pretty complete by version 3.4
 */
#define SXE_CXX14 ( __cplusplus > 201402L && SXE_CLANG_VERSION > 304000)

/*
 * C++ 11 support in Clang/libstdc++ is pretty complete by version 3.1
 * For all the C++ 11 goodies: you need clang 3.8, at which point you may as we use 4.0!
 */
#define SXE_CXX11 ( __cplusplus > 199711L && SXE_CLANG_VERSION >= 301000)

#else
#define SXE_CLANG_VERSION 0
#endif // __clang__

#if defined(__GNUC__)

/* by now we all wish GCC would just predefine this. */
#define SXE_GCC_VERSION ( __GNUC__ * 10000 \
                          + __GNUC_MINOR__ * 100 \
                          + __GNUC_PATCHLEVEL__ )

/*
 * C++ 17 support in GCC/libstdc++ is pretty complete by version 7.
 * Some headers require version 9.1.
 */
#define SXE_CXX17 (__cplusplus > 201402L && SXE_GCC_VERSION >= 70000)

/*
 * C++ 14 support in GCC/libstdc++ is pretty complete by version 5.0
 */
#define SXE_CXX14 (__cplusplus > 201103L && SXE_GCC_VERSION >= 50000)

/*
 * C++ 11 support in GCC/libstdc++ is pretty complete by version 4.8, and partial from ~4.3.
 * For all the C++ 11 goodies: you need GCC with C++ 14 support.
 */
#define SXE_CXX11 (__cplusplus > 199711L && SXE_GCC_VERSION >= 40800)

#else
#define SXE_GCC_VERSION 0
#endif // __GNUC__

/* If you can't do this, run for the hills and take your toolchain with you! */
#define SXE_CXX98 (__cplusplus >= 199711L)

#if 0 // debug macros.
/* use pragma because MSVC doesn't understand warning? */

#if SXE_CXX98
#pragma message("C++ 98 support.")
#endif
#if SXE_CXX11
#pragma message("C++11 support.")
#endif
#if SXE_CXX14
#pragma message("C++14 support.")
#endif
#if SXE_CXX17
#pragma message("C++17 support.")
#endif

#endif // debug macros.

#endif // SXE_CXXVERSION__H
