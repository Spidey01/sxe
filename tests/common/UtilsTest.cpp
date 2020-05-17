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

#include "./UtilsTest.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using std::to_string;
using std::vector;

using namespace sxe::common;

static const string TAG = "UtilsTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(UtilsTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void UtilsTest::notSpace()
{
    Log::xtrace(TAG, "notSpace()");

    CPPUNIT_ASSERT_MESSAGE("The letter a is not a space!",
                           Utils::notSpace('a') == true);

    CPPUNIT_ASSERT_MESSAGE("The space a space!",
                           Utils::notSpace(' ') == false);
}


void UtilsTest::trim()
{
    Log::xtrace(TAG, "trim()");

    string expected = "hello, world";
    string hairy = "   hello, world\t";

    CPPUNIT_ASSERT(Utils::trim(hairy) == expected);
}


void UtilsTest::split()
{
    Log::xtrace(TAG, "split()");

    static const vector<string> expected = {
        "one",
        "two",
        "three"
    };

    string input = "one,two,three";
    vector<string> output;

    CPPUNIT_ASSERT_NO_THROW(Utils::split(std::back_inserter(output), input, ','));

    CPPUNIT_ASSERT(output == expected);
}


void UtilsTest::split_str()
{
    Log::xtrace(TAG, "split_str()");

    static const vector<string> expected = {
        "one",
        "two",
        "three"
    };

    string input = "one,two,three";
    vector<string> output;

    CPPUNIT_ASSERT_NO_THROW(output = Utils::split_str(input, ','));

    CPPUNIT_ASSERT(output == expected);
}


void UtilsTest::join()
{
    Log::xtrace(TAG, "join()");

    static const string expected = "one,two,three";

    static const vector<string> input = {
        "one",
        "two",
        "three"
    };

    string output;

    CPPUNIT_ASSERT_NO_THROW(output = Utils::join(input, 3, ','));
    CPPUNIT_ASSERT(output == expected);
}


void UtilsTest::starts_with()
{
    Log::xtrace(TAG, "starts_with()");

    CPPUNIT_ASSERT(Utils::starts_with("hello, world", "hello"));
    CPPUNIT_ASSERT(!Utils::starts_with("hello, world", "world"));
}


void UtilsTest::ends_with()
{
    Log::xtrace(TAG, "ends_with()");

    CPPUNIT_ASSERT(Utils::ends_with("hello, world", "world"));
    CPPUNIT_ASSERT(!Utils::ends_with("hello, world", "hello"));
}

