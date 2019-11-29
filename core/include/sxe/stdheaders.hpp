#ifndef SXE_STDHEADERS__H
#define SXE_STDHEADERS__H

/** Standard C++ headers.
 *
 * These are included in groups as noted by https://en.cppreference.com/w/cpp/header.
 */

/*
 * __cplusplus values:
 *
 *  199711L = C++ 98/03
 *  201103L = C++ 11
 *  201402L = C++ 14
 *  201703L = C++ 17
 *  TBD     = C++ 20
 */

/* Utilities library */
#include <cstdlib>
#include <csignal>
#include <csetjmp>
#include <cstdarg>
#include <typeinfo>
#if __cplusplus > 199711L
#include <typeindex>
#include <type_traits>
#endif
#include <bitset>
#include <functional>
#include <utility>
#include <ctime>
#if __cplusplus > 199711L
#include <chrono>
#endif
#include <cstddef>
#if __cplusplus > 199711L
#include <initializer_list>
#include <tuple>
#endif
#if __cplusplus > 201402L
#include <any>
#include <optional>
#include <variant>
#endif

/* Dynamic memory maangement. */
#include <new>
#include <memory>
#if __cplusplus > 199711L
#include <scoped_allocator>
#endif
#if __cplusplus > 201402L
#include <memory_resource>
#endif

/* Numeric limits. */
#include <climits>
#include <cfloat>
#if __cplusplus > 199711L
#include <cstdint>
#include <cinttypes>
#endif
#include <limits>

/* Error handling. */
#include <exception>
#include <stdexcept>
#include <cassert>
#if __cplusplus > 199711L
#include <system_error>
#endif
#include <cerrno>

/* Strings library. */
#include <cctype>
#include <cwctype>
#include <cstring>
#include <cwchar>
#if __cplusplus > 199711L
#include <cuchar>
#endif
#include <string>
#if __cplusplus > 201402L
#include <string_view>
#include <charconv>
#endif

/* Containers library. */
#if __cplusplus > 199711L
#include <array>
#endif
#include <vector>
#include <deque>
#include <list>
#if __cplusplus > 199711L
#include <forward_list>
#endif
#include <set>
#include <map>
#if __cplusplus > 199711L
#include <unordered_set>
#include <unordered_map>
#endif
#include <stack>
#include <queue>

/* Iterators library. */
#include <iterator>

/* Algorithms library. */
#include <algorithm>
#if __cplusplus > 201402L
#include <execution>
#endif

/* Numerics library. */
#include <cmath>
#include <complex>
#include <valarray>
#if __cplusplus > 199711L
#include <random>
#endif
#include <numeric>
#if __cplusplus > 199711L
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
#if __cplusplus > 199711L
#include <regex>
#endif

/** Atomic Operations library. */
#if __cplusplus > 199711L
#include <atomic>
#endif

/* Thread support library. */
#if __cplusplus > 199711L
#include <thread>
#include <mutex>
#include <shared_mutex>
#include <future>
#include <condition_variable>
#endif

/* Filesystem library. */
#if __cplusplus > 201402L
#include <filesystem>
#endif

#endif
