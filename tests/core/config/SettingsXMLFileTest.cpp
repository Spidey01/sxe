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

#include "./SettingsXMLFileTest.hpp"

#include <sxe/core/config/SettingsXMLFile.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::core::config::SettingsXMLFile;

static const string TAG = "SettingsXMLFileTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(SettingsXMLFileTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void SettingsXMLFileTest::strings()
{
    Log::xtrace(TAG, "strings()");

    SettingsXMLFile map;

    static const string key = "hello";
    static const string value = "world";

    CPPUNIT_ASSERT(map.contains(key) == false);
    CPPUNIT_ASSERT(map.getString(key) == "");
    CPPUNIT_ASSERT_NO_THROW(map.setString(key, value));
    CPPUNIT_ASSERT(map.contains(key) == true);
    CPPUNIT_ASSERT(map.getString(key) == value);
}


void SettingsXMLFileTest::booleans()
{
    Log::xtrace(TAG, "booleans()");

    SettingsXMLFile map;

    static const string key = " George Boole";

    CPPUNIT_ASSERT(map.contains(key) == false);
    CPPUNIT_ASSERT(map.getBool(key) == false);
    CPPUNIT_ASSERT_NO_THROW(map.setBool(key, true));
    CPPUNIT_ASSERT(map.contains(key) == true);
    CPPUNIT_ASSERT(map.getBool(key) == true);
}


void SettingsXMLFileTest::integers()
{
    Log::xtrace(TAG, "integers()");

    SettingsXMLFile map;

    static const string int_key = "signed int";
    static const int int_value = 12345;

    Log::test(TAG, "integers(): int");
    CPPUNIT_ASSERT(map.contains(int_key) == false);
    CPPUNIT_ASSERT(map.getInt(int_key) == 0);
    CPPUNIT_ASSERT_NO_THROW(map.setInt(int_key, int_value));
    CPPUNIT_ASSERT(map.contains(int_key) == true);
    CPPUNIT_ASSERT(map.getInt(int_key) == int_value);

    static const string long_key = "signed long";
    static const long long_value = 12345;

    Log::test(TAG, "integers(): long");
    CPPUNIT_ASSERT(map.contains(long_key) == false);
    CPPUNIT_ASSERT(map.getLong(long_key) == 0);
    CPPUNIT_ASSERT_NO_THROW(map.setLong(long_key, long_value));
    CPPUNIT_ASSERT(map.contains(long_key) == true);
    CPPUNIT_ASSERT(map.getLong(long_key) == long_value);
}


void SettingsXMLFileTest::floatingpoints()
{
    Log::xtrace(TAG, "floatingpoints()");

    SettingsXMLFile map;

    static const string float_key = "floatingpoint";
    static const float float_value = 2.45f;

    Log::test(TAG, "floatingpoints(): float");
    CPPUNIT_ASSERT(map.contains(float_key) == false);
    CPPUNIT_ASSERT(map.getFloat(float_key) == 0);
    CPPUNIT_ASSERT_NO_THROW(map.setFloat(float_key, float_value));
    CPPUNIT_ASSERT(map.contains(float_key) == true);
    float back = map.getFloat(float_key);
    CPPUNIT_ASSERT(back >= 2.3f && back <= 2.5f);

    // the old interface didn't do doubles.
}


void SettingsXMLFileTest::keys()
{
    Log::xtrace(TAG, "keys()");

    SettingsXMLFile map;

    SettingsXMLFile::KeyList keys = map.keys();

    CPPUNIT_ASSERT_MESSAGE("There are no default keys for SettingsXMLFile.",
                   keys.empty());

    static const string k1 = "Hello";
    map.setString(k1, "World");
    static const string k2 = "numbers";
    map.setInt(k2, 12345);

    keys = map.keys();

    CPPUNIT_ASSERT_MESSAGE("There should be two keys.",
                   keys.size() == 2);
    CPPUNIT_ASSERT_MESSAGE("There should be a key: " + k1,
                   std::find(keys.cbegin(), keys.cend(), k1) != keys.cend());
    CPPUNIT_ASSERT_MESSAGE("There should be a key: " + k2,
                   std::find(keys.cbegin(), keys.cend(), k2) != keys.cend());
}


void SettingsXMLFileTest::merge()
{
    Log::xtrace(TAG, "merge()");

    SettingsXMLFile parent;
    SettingsXMLFile child;

    parent.setString("k1", "one");
    child.setString("k2", "two");

    parent.merge(child);

    CPPUNIT_ASSERT(parent.getString("k1") == "one");
    CPPUNIT_ASSERT(parent.getString("k2") == "two");
}


void SettingsXMLFileTest::anykeynotifications()
{
    Log::xtrace(TAG, "anykeynotifications()");

    SettingsXMLFile map;

    bool notified = false;

    SettingsXMLFile::OnChangedListener listener = [&notified](const string& key) -> void
    {
        Log::d(TAG, "anykeynotifications() listener(" + key + ") called");
        notified = true;
    };


    auto bid = map.addChangeListener(listener);
    Log::v(TAG, "notifications(): bid: " + std::to_string(bid));

    map.setString("any", "value");
    CPPUNIT_ASSERT_MESSAGE("Notifications for any key.",
                           notified == true);
    notified = false;

    map.removeChangeListener(bid);

    map.setString("any", "no one listening");
    CPPUNIT_ASSERT_MESSAGE("No broadcast notifications after unsubscribe",
                           notified == false);
}


void SettingsXMLFileTest::specifickeynotifications()
{
    Log::xtrace(TAG, "specifickeynotifications()");

    SettingsXMLFile map;
    bool notified = false;

    SettingsXMLFile::OnChangedListener listener = [&notified](const string& key) -> void
    {
        Log::d(TAG, "specifickeynotifications() listener(" + key + ") called");
        notified = true;
    };

    static const string unique_key = "my magical key";

    auto sid = map.addChangeListener(listener, unique_key);
    Log::v(TAG, "notifications(): sid: " + std::to_string(sid));

    map.setString("Hello", "notifications");
    CPPUNIT_ASSERT_MESSAGE("No notifications for unsubscribed keys.",
                           notified == false);

    map.setString(unique_key, "foo");

    CPPUNIT_ASSERT_MESSAGE("Notifications for specific keys.",
                           notified == true);
    notified = false;

    map.removeChangeListener(unique_key, sid);

    map.setString(unique_key, "no one listening");
    CPPUNIT_ASSERT_MESSAGE("No specific notifications after unsubscribe",
                           notified == false);
}


