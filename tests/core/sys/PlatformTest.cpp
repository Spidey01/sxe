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

#include "./PlatformTest.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using sxe::core::sys::Platform;
using std::string;

static const string TAG = "PlatformTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(PlatformTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

static string expectedGuess()
{
 #if defined(__ANDROID__)
    return Platform::ANDROID;
 #elif defined(__linux__)
    return Platform::LINUX;
 #elif defined(__APPLE__)
    return Platform::MAC_OS;
 #elif defined(_WIN32) || defined(WINDOWS)
    return Platform::WINDOWS;
 #else
    Log::w(TAG, "guess(): your OS is not supported. All bets are off.");
    return "";
 #endif
}


void PlatformTest::staticFields()
{
    Log::xtrace(TAG, "staticFields()");

    CPPUNIT_ASSERT_MESSAGE("Platform::ANDROID cannot be empty.", !Platform::ANDROID.empty());
    CPPUNIT_ASSERT_MESSAGE("Platform::LINUX cannot be empty.", !Platform::LINUX.empty());
    CPPUNIT_ASSERT_MESSAGE("Platform::MAC_OS cannot be empty.", !Platform::MAC_OS.empty());
    CPPUNIT_ASSERT_MESSAGE("Platform::WINDOWS cannot be empty.", !Platform::WINDOWS.empty());
}


void PlatformTest::guess()
{
    Log::xtrace(TAG, "guess()");

    const string expected = expectedGuess();
    string name;
    CPPUNIT_ASSERT_NO_THROW_MESSAGE("On a supported OS: guess() never throws.",
                                    name = Platform::guess());

    CPPUNIT_ASSERT_MESSAGE("Platform::guess() is should match the field for " + expected,
                           name == expected);
}


void PlatformTest::ctor()
{
    Log::xtrace(TAG, "ctor()");

    Platform platform;

    CPPUNIT_ASSERT_MESSAGE("Default ctor should give a name equal to guess.",
                           platform.name() == Platform::guess());

    CPPUNIT_ASSERT_MESSAGE("Platform::name() cannot be empty.", !platform.name().empty());
    CPPUNIT_ASSERT_MESSAGE("Platform::arch() cannot be empty.", !platform.arch().empty());
    CPPUNIT_ASSERT_MESSAGE("Platform::version() cannot be empty.", !platform.version().empty());
    CPPUNIT_ASSERT_MESSAGE("Platform::toString()() cannot be empty.", !platform.toString().empty());

    CPPUNIT_ASSERT_MESSAGE("On a supported OS: at least one of these should be true.",
                           (platform.isAndroid() || platform.isMacOS() || platform.isUnix() || platform.isWindows()));
}


