#ifndef SXE_SUM_ADLER32__HPP
#define SXE_SUM_ADLER32__HPP
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

#include <sxe/sum/Checksum.hpp>

#include <zlib.h>

namespace sxe { namespace sum {

    /** @brief Wraps zlib's adler32 checksum function.
     */
    class SXE_PUBLIC Adler32Algorithim
        : public ChecksumAlgorithim
    {
      public:
        Adler32Algorithim();
        ~Adler32Algorithim();
        void operator()(const uint8_t* buffer, size_t length) override;
        std::string get() const override;

      private:
        ::uLong mSum;
    };

    /** @brief Alder32 checksum.
     */
    class SXE_PUBLIC Adler32Checksum
        : public Checksum<Adler32Algorithim>
    {
      public:
        Adler32Checksum();
        virtual ~Adler32Checksum();
        Adler32Checksum(std::istream& input);
        Adler32Checksum(const string_type& input);
        Adler32Checksum(const uint8_t* buffer, size_t length);
    };
} }

#endif // SXE_SUM_ADLER32__HPP