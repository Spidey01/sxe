#ifndef SXE_TESTS_SUM_CRC32TEST__HPP
#define SXE_TESTS_SUM_CRC32TEST__HPP
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

#include <cppunit/extensions/HelperMacros.h>
#include <sxe/sum/CRC32.hpp>

#include "ChecksumFixture.hpp"

class CRC32Test : public ChecksumFixture<sxe::sum::CRC32Checksum, CRC32Test>
{
  public:
    void setUp() override;
    void tearDown() override;
};

#endif // SXE_TESTS_SUM_CRC32TEST__HPP