#ifndef SXE_COMMON_UTILS__HPP
#define SXE_COMMON_UTILS__HPP
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

namespace sxe {  namespace common {
    
    /** Various utility functions.
     */
    namespace Utils
    {

        /* Inverse of std::isspace.
        */
        bool SXE_PUBLIC notSpace(int ch);

        /** Trims leading / trailing whitespace.
         */
        std::string SXE_PUBLIC trim(const std::string& input);

        /** Simple split on character.
         * 
         * @param[out] first the first position to start inserting results.
         * @param[in] value the input to split.
         * @param sep the character to split on.
         */
        template <class InputIt, class String>
        void split(InputIt first, const String& value, char sep)
        {
            size_t start = 0;
            size_t pos = value.find(sep);

            if (pos == String::npos && !value.empty()) {
                *first++ = value;
                return;
            }

            while (pos != String::npos) {
                size_t next = value.find(sep, start + 1);
                size_t count = next - start;

                if (value[start] == sep) {
                    start += 1;
                    count -= 1;
                }

                *first++ = value.substr(start, count);

                start += count + 1;
                pos = next;
            }
        }

        /** Convenience form of split for STL containers.
         *
         * This mostly exists for the case where you don't care about the
         * details and want to write code like the following:
         *
         *     for (const string& str : split(value, ',')) {
         *         ...
         *     }
         *  
         */
        template <class String = std::string, class Sequence = std::vector<String>>
        Sequence split_str(const String& value, char sep)
        {
            Sequence r;

            split(std::back_inserter(r), value, sep);

            return r;
        }

        /** Splits with a regular expression instead of a fixed seperator.
         * 
         * @param[out] first the first position to start inserting results.
         * @param[in] value the input to split.
         * @param sep the pattern to split on.
         */
        template <class InputIt, class String, class RegExp>
        void split_regex(InputIt first, const String& value, RegExp sep)
        {
            using std::sregex_token_iterator;

            /* C++'s regex library lets us iterate tokens, and submatch -1 means the inbetweens. */
            auto search_begin = sregex_token_iterator(value.begin(), value.end(), sep, -1);
            auto search_end = sregex_token_iterator();

            for (sregex_token_iterator it = search_begin; it != search_end; ++it) {
                *first++ = it->str();
            }
        }

        /** Simple join on character.
         * 
         * @param sequence a sequence supporting array indexing.
         * @param length max number of elements to join.
         * @param separator insert this between elements.
         * @returns string with the result.
         */
        template <class Sequence, class String = std::string>
        String join(Sequence sequence, size_t length, char separator)
        {
            String buf;
            buf.reserve(length * 16);

            for (size_t i = 0; i < length; ++i) {
                if (i > 0)
                    buf += (separator);

                buf += sequence[i];
            }

            return buf;
        }

        bool SXE_PUBLIC starts_with(const std::string& str, const std::string& prefix);

        bool SXE_PUBLIC ends_with(const std::string& str, const std::string& suffix);

    } // namespace Utils
} }

#endif // SXE_COMMON_UTILS__HPP
