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

#include <sxe/sum/CRC32.hpp>

#include <sxe/logging.hpp>

namespace sxe { namespace sum {

CRC32Algorithim::CRC32Algorithim()
    : ChecksumAlgorithim()
    , mSum(::crc32_z(0L, nullptr, 0))
{
}

CRC32Algorithim::~CRC32Algorithim()
{
}

void CRC32Algorithim::operator()(const uint8_t* buffer, size_t length)
{
    mSum = ::crc32_z(mSum, (const ::Bytef*)buffer, length);
    Log::d("CRC32Algorithim", "operator(): mSum: " + std::to_string(mSum));
}

std::string CRC32Algorithim::get() const
{
    std::stringstream ss;

    ss << std::hex << mSum;

    Log::d("CRC32Algorithim", "get(): ss.str(): " + ss.str());
    return ss.str();
}

CRC32Checksum::CRC32Checksum()
    : Checksum(CRC32Algorithim())
{
}

CRC32Checksum::~CRC32Checksum()
{
}

CRC32Checksum::CRC32Checksum(std::istream& input)
    : Checksum(input, CRC32Algorithim())
{
}

CRC32Checksum::CRC32Checksum(const string_type& input)
    : Checksum(input, CRC32Algorithim())
{
}

CRC32Checksum::CRC32Checksum(const uint8_t* buffer, size_t length)
    : Checksum(buffer, length, CRC32Algorithim())
{
}

} }
