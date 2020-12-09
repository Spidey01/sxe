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

#include <sxe/sum/Adler32.hpp>

#include <sxe/logging.hpp>

namespace sxe { namespace sum {

Adler32Algorithim::Adler32Algorithim()
    : ChecksumAlgorithim()
    , mSum(adler32_z(0L, nullptr, 0))
{
}

Adler32Algorithim::~Adler32Algorithim()
{
}

void Adler32Algorithim::operator()(const uint8_t* buffer, size_t length)
{
    mSum = ::adler32_z(mSum, (const ::Bytef*)buffer, length);
    Log::d("Adler32Algorithim", "operator(): mSum: " + std::to_string(mSum));
}

std::string Adler32Algorithim::get() const
{
    std::stringstream ss;

    ss << std::hex << mSum;

    Log::d("Adler32Algorithim", "get(): ss.str(): " + ss.str());
    return ss.str();
}

Adler32Checksum::Adler32Checksum()
    : Checksum(Adler32Algorithim())
{
}

Adler32Checksum::~Adler32Checksum()
{
}

Adler32Checksum::Adler32Checksum(std::istream& input)
    : Checksum(input, Adler32Algorithim())
{
}

Adler32Checksum::Adler32Checksum(const string_type& input)
    : Checksum(input, Adler32Algorithim())
{
}

Adler32Checksum::Adler32Checksum(const uint8_t* buffer, size_t length)
    : Checksum(buffer, length, Adler32Algorithim())
{
}

} }