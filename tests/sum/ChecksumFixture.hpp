#ifndef SXE_TESTS_SUM_CHECKSUMFIXTURE__HPP
#define SXE_TESTS_SUM_CHECKSUMFIXTURE__HPP
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
#include <sxe/sum/Checksum.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

template <class Implementation, class Derived>
class ChecksumFixture : public CPPUNIT_NS::TestFixture
{
    CPPUNIT_TEST_SUITE(Derived);
    CPPUNIT_TEST(updateStream);
    CPPUNIT_TEST(updateString);
    CPPUNIT_TEST(updateBuffer);
    CPPUNIT_TEST_SUITE_END();

  protected:
    std::string mTag;

    /** The implementation from setUp() / tearDown().
     */
    std::unique_ptr<typename Implementation> mChecksum;

    /** Some data to checksum.
     */
    std::string mInput;

    /** The result.
     */
    std::string mExpected;

  protected:
    void updateStream()
    {
        Log::xtrace(mTag, "updateStream()");

        std::stringstream ss(mInput);

        CPPUNIT_ASSERT_NO_THROW(mChecksum->update(ss));
        Log::test(TAG, "mChecksum->value(): " + mChecksum->value());
        CPPUNIT_ASSERT(mChecksum->value() == mExpected);
    }

    void updateString()
    {
        Log::xtrace(mTag, "updateString()");

        CPPUNIT_ASSERT_NO_THROW(mChecksum->update(mInput));
        Log::test(TAG, "mChecksum->value(): " + mChecksum->value());
        CPPUNIT_ASSERT(mChecksum->value() == mExpected);
    }

    void updateBuffer()
    {
        Log::xtrace(mTag, "updateBuffer()");

        auto buf = (uint8_t*)mInput.c_str();
        size_t len = mInput.size();
        CPPUNIT_ASSERT_NO_THROW(mChecksum->update(buf, len));
        Log::test(TAG, "mChecksum->value(): " + mChecksum->value());
        CPPUNIT_ASSERT(mChecksum->value() == mExpected);
    }
};

#endif // SXE_TESTS_SUM_CHECKSUMFIXTURE__HPP