/*-
 * Copyright (c) 2019-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "./InputCodeTest.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using std::to_string;

using namespace sxe::core::input;

static const string TAG = "InputCodeTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(InputCodeTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void InputCodeTest::ctor()
{
    Log::xtrace(TAG, "ctor()");

    InputCode unknown;
    Log::d(TAG, unknown.dump());

    CPPUNIT_ASSERT(unknown.code() == InputCode::IC_UNKNOWN);
    CPPUNIT_ASSERT(unknown.symbol() == unknown.upper());
    CPPUNIT_ASSERT(unknown.upper() == '\0');
    CPPUNIT_ASSERT(unknown.lower() == '\0');

    InputCode letterA(InputCode::IC_A);
    Log::d(TAG, letterA.dump());

    CPPUNIT_ASSERT(letterA.code() == InputCode::IC_A);
    CPPUNIT_ASSERT(letterA.symbol() == letterA.upper());
    CPPUNIT_ASSERT(letterA.upper() == 'A');
    CPPUNIT_ASSERT(letterA.lower() == 'a');
}


void InputCodeTest::fromChar()
{
    Log::xtrace(TAG, "fromChar()");

    InputCode x = InputCode::fromChar('X');
    Log::d(TAG, x.dump());

    CPPUNIT_ASSERT(x.code() == InputCode::IC_X);
    CPPUNIT_ASSERT(x.symbol() == x.upper());
    CPPUNIT_ASSERT(x.upper() == 'X');
    CPPUNIT_ASSERT(x.lower() == 'x');
}


void InputCodeTest::fromSequence()
{
    Log::xtrace(TAG, "fromSequence()");

    std::vector<InputCode> codes;
    std::string letters = "aBc 123";

    InputCode::fromSequence(letters.begin(), letters.end(), std::back_inserter(codes));

    Log::xtrace(TAG, "Dumping.");
    for (const InputCode& cd : codes)
        Log::d(TAG, cd.dump());

    Log::xtrace(TAG, "Asserting.");
    CPPUNIT_ASSERT(codes.at(0).code() == InputCode::IC_A);
    CPPUNIT_ASSERT(codes.at(1).code() == InputCode::IC_B);
    CPPUNIT_ASSERT(codes.at(2).code() == InputCode::IC_C);
    CPPUNIT_ASSERT(codes.at(3).code() == InputCode::IC_SPACE);
    CPPUNIT_ASSERT(codes.at(4).code() == InputCode::IC_1);
    CPPUNIT_ASSERT(codes.at(5).code() == InputCode::IC_2);
    CPPUNIT_ASSERT(codes.at(6).code() == InputCode::IC_3);
}


