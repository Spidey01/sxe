#ifndef SXE_CORE_COMMON_UTILS__HPP
#define SXE_CORE_COMMON_UTILS__HPP
/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

namespace sxe { namespace core { namespace common {
    
    /** Various utility functions.
     */
    namespace Utils {

        /* Inverse of std::isspace.
        */
        bool SXE_PUBLIC notSpace(int ch);

        /** Trims leading / trailing whitespace.
         */
        std::string SXE_PUBLIC trim(const std::string& input);

        template <class Sequence, class String>
        void split(Sequence& list, const String& value, char sep)
        {
            size_t start = 0;
            size_t pos = value.find(sep);
            while (pos != string::npos) {
                size_t next = value.find(sep, start + 1);
                size_t count = next - start;

                if (value[start] == sep) {
                    start += 1;
                    count -= 1;
                }

                list.emplace(list.end(), value.substr(start, count));

                start += count + 1;
                pos = next;
            }
        }


        template <class Sequence, class String=std::string>
        String join(Sequence sequence, size_t length, char separator)
        {
            String buf;
            buf.reserve(length * 16);

            for (size_t i=0; i < length; ++i) {
                if (i > 0)
                    buf += (separator);

                buf += sequence[i];
            }

            return buf;
        }

    }

} } }

#endif // SXE_CORE_COMMON_UTILS__HPP
