/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "SettingsExpanderTest.hpp"

#include <sxe/config/SettingsMap.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::config::Settings;
using sxe::config::SettingsExpander;
using sxe::config::SettingsMap;

static const string TAG = "SettingsExpanderTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(SettingsExpanderTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);


void SettingsExpanderTest::noop()
{
    Log::xtrace(TAG, "noop()");

    SettingsMap test;
    SettingsExpander e(test);

    test.setString("foo", "bar");
    CPPUNIT_ASSERT(e.expand("foo") == "bar");

    test.setString("bar", "ham");
    CPPUNIT_ASSERT(e.expand("bar") == "ham");
}


void SettingsExpanderTest::simple()
{
    Log::xtrace(TAG, "simple()");

    SettingsMap test;
    SettingsExpander e(test);

    test.setString("x", "foo");
    test.setString("y", "bar");
    test.setString("xy", "${x}+${y}");

    CPPUNIT_ASSERT(e.expand("xy") == "foo+bar");
}


void SettingsExpanderTest::nested()
{
    Log::xtrace(TAG, "nested()");

    SettingsMap test;
    SettingsExpander e(test);

    test.setString("bar", "ham");
    test.setString("first", "${bar}");
    test.setString("last", "eggs");
    test.setString("quux", "${first}, spam, and ${last}");

    CPPUNIT_ASSERT(e.expand("quux") == "ham, spam, and eggs");
}


void SettingsExpanderTest::escaped()
{
    Log::xtrace(TAG, "escaped()");

    SettingsMap test;
    SettingsExpander e(test);

    string success = "Resistance is futile.";
    test.setString("escaped", success);

    string value;

    test.setString("foo", "\\\\${escaped}");
    value = e.expand("foo");
    success = "\\\\Resistance is futile.";
    Log::d(TAG, "expected: \"" + success + "\" actual: \"" + value + "\"");
    CPPUNIT_ASSERT_MESSAGE("Escaping the escape should work.",
                           value == success);

    test.setString("foo", "\\${escaped}");
    success = "\\Resistance is futile.";
    value = e.expand("foo");
    Log::d(TAG, "expected: \"" + success + "\" actual: \"" + value + "\"");
    CPPUNIT_ASSERT_MESSAGE("Escaping prefix should be enough",
                           value == success);

    success = "$\\{escaped}";
    test.setString("foo", success);
    CPPUNIT_ASSERT_MESSAGE("Escaping openMarker should be enough",
                           e.expand("foo") == success);

    success = "${escaped\\}";
    test.setString("foo", success);
    CPPUNIT_ASSERT_MESSAGE("Escaping closeMarker should be enough",
                           e.expand("foo") == success);
}

