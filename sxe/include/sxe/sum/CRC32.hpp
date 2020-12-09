#ifndef SXE_SUM_CRC32__HPP
#define SXE_SUM_CRC32__HPP
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

    /** @brief Wraps zlib's crc32 checksum function.
     */
    class SXE_PUBLIC CRC32Algorithim
        : public ChecksumAlgorithim
    {
      public:
        CRC32Algorithim();
        ~CRC32Algorithim();
        void operator()(const uint8_t* buffer, size_t length) override;
        std::string get() const override;

      private:
        ::uLong mSum;
    };

    /** @brief 32-bit cyclic redundancy check.
     */
    class SXE_PUBLIC CRC32Checksum
        : public Checksum<CRC32Algorithim>
    {
      public:
        CRC32Checksum();
        virtual ~CRC32Checksum();
        CRC32Checksum(std::istream& input);
        CRC32Checksum(const string_type& input);
        CRC32Checksum(const uint8_t* buffer, size_t length);
    };

} }

#endif // SXE_SUM_CRC32__HPP