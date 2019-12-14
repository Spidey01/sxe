#ifndef SXE_STDHEADERS__H
#define SXE_STDHEADERS__H

/** Standard C++ headers.
 *
 * These are included in groups as noted by https://en.cppreference.com/w/cpp/header and guarded by SXE_CXXnn.
 */
#include <sxe/cxxversion.hpp>

/* Utilities library */
#include <cstdlib>
#include <csignal>
#include <csetjmp>
#include <cstdarg>
#include <typeinfo>
#if SXE_CXX11
#include <typeindex>
#include <type_traits>
#endif
#include <bitset>
#include <functional>
#include <utility>
#include <ctime>
#if SXE_CXX11
#include <chrono>
#endif
#include <cstddef>
#if SXE_CXX11
#include <initializer_list>
#include <tuple>
#endif
#if SXE_CXX17
#include <any>
#include <optional>
#include <variant>
#endif

/* Dynamic memory maangement. */
#include <new>
#include <memory>
#if SXE_CXX11
#include <scoped_allocator>
#endif
#if SXE_CXX17 && (!SXE_GCC_VERSION || SXE_GCC_VERSION > 90100)
#include <memory_resource>
#endif

/* Numeric limits. */
#include <climits>
#include <cfloat>
#if SXE_CXX11
#include <cstdint>
#include <cinttypes>
#endif
#include <limits>

/* Error handling. */
#include <exception>
#include <stdexcept>
#include <cassert>
#if SXE_CXX11
#include <system_error>
#endif
#include <cerrno>

/* Strings library. */
#include <cctype>
#include <cwctype>
#include <cstring>
#include <cwchar>
#if SXE_CXX11
#include <cuchar>
#endif
#include <string>
#if SXE_CXX17
#include <string_view>
#include <charconv>
#endif

/* Containers library. */
#if SXE_CXX11
#include <array>
#endif
#include <vector>
#include <deque>
#include <list>
#if SXE_CXX11
#include <forward_list>
#endif
#include <set>
#include <map>
#if SXE_CXX11
#include <unordered_set>
#include <unordered_map>
#endif
#include <stack>
#include <queue>

/* Iterators library. */
#include <iterator>

/* Algorithms library. */
#include <algorithm>
#if SXE_CXX17 && (!SXE_GCC_VERSION || SXE_GCC_VERSION > 90100)
#include <execution>
#endif

/* Numerics library. */
#include <cmath>
#include <complex>
#include <valarray>
#if SXE_CXX11
#include <random>
#endif
#include <numeric>
#if SXE_CXX11
#include <ratio>
#include <cfenv>
#endif

/* Input/output library. */
#include <iosfwd>
#include <ios>
#include <istream>
#include <ostream>
#include <iostream>
#include <fstream>
#include <sstream>
#include <iomanip>
#include <streambuf>
#include <cstdio>

/* Localization library. */
#include <locale>
#include <clocale>

/* Regular Expressions library. */
#if SXE_CXX11
#include <regex>
#endif

/** Atomic Operations library. */
#if SXE_CXX11
#include <atomic>
#endif

/* Thread support library. */
#if SXE_CXX11
#include <thread>
#include <mutex>
#include <shared_mutex>
#include <future>
#include <condition_variable>
#endif

/* Filesystem library. */
#if SXE_CXX17
#include <filesystem>
#endif

#endif // SXE_STDHEADERS__H
