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

#include "./SettingsFixture.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::config::Settings;

static const string TAG = "SettingsFixture";

SettingsFixture::settings_ptr SettingsFixture::make_settings() const
{
    throw std::runtime_error("Override make_settings() and build a factory.");
    // return nullptr;
}


void SettingsFixture::strings()
{
    Log::xtrace(TAG, "strings()");

    settings_ptr s = make_settings();

    static const string key = "hello";
    static const string value = "world";

    CPPUNIT_ASSERT(s->contains(key) == false);
    CPPUNIT_ASSERT(s->getString(key) == "");
    CPPUNIT_ASSERT_NO_THROW(s->setString(key, value));
    CPPUNIT_ASSERT(s->contains(key) == true);
    CPPUNIT_ASSERT(s->getString(key) == value);
}


void SettingsFixture::booleans()
{
    Log::xtrace(TAG, "booleans()");

    settings_ptr s = make_settings();

    static const string key = " George Boole";

    CPPUNIT_ASSERT(s->contains(key) == false);
    CPPUNIT_ASSERT(s->getBool(key) == false);
    CPPUNIT_ASSERT_NO_THROW(s->setBool(key, true));
    CPPUNIT_ASSERT(s->contains(key) == true);
    CPPUNIT_ASSERT(s->getBool(key) == true);
}


void SettingsFixture::integers()
{
    Log::xtrace(TAG, "integers()");

    settings_ptr s = make_settings();

    static const string int_key = "signed int";
    static const int int_value = 12345;

    Log::test(TAG, "integers(): int");
    CPPUNIT_ASSERT(s->contains(int_key) == false);
    CPPUNIT_ASSERT(s->getInt(int_key) == 0);
    CPPUNIT_ASSERT_NO_THROW(s->setInt(int_key, int_value));
    CPPUNIT_ASSERT(s->contains(int_key) == true);
    CPPUNIT_ASSERT(s->getInt(int_key) == int_value);

    static const string long_key = "signed long";
    static const long long_value = 12345;

    Log::test(TAG, "integers(): long");
    CPPUNIT_ASSERT(s->contains(long_key) == false);
    CPPUNIT_ASSERT(s->getLong(long_key) == 0);
    CPPUNIT_ASSERT_NO_THROW(s->setLong(long_key, long_value));
    CPPUNIT_ASSERT(s->contains(long_key) == true);
    CPPUNIT_ASSERT(s->getLong(long_key) == long_value);
}


void SettingsFixture::floatingpoints()
{
    Log::xtrace(TAG, "floatingpoints()");

    settings_ptr s = make_settings();

    static const string float_key = "floatingpoint";
    static const float float_value = 2.45f;

    Log::test(TAG, "floatingpoints(): float");
    CPPUNIT_ASSERT(s->contains(float_key) == false);
    CPPUNIT_ASSERT(s->getFloat(float_key) == 0);
    CPPUNIT_ASSERT_NO_THROW(s->setFloat(float_key, float_value));
    CPPUNIT_ASSERT(s->contains(float_key) == true);
    float back = s->getFloat(float_key);
    CPPUNIT_ASSERT(back >= 2.3f && back <= 2.5f);

    // the old interface didn't do doubles.
}


void SettingsFixture::keys()
{
    Log::xtrace(TAG, "keys()");

    settings_ptr s = make_settings();

    Settings::KeyList keys = s->keys();

    CPPUNIT_ASSERT_MESSAGE("There are no default keys for SettingsFixture.",
                   keys.empty());

    static const string k1 = "Hello";
    s->setString(k1, "World");
    static const string k2 = "numbers";
    s->setInt(k2, 12345);

    keys = s->keys();

    CPPUNIT_ASSERT_MESSAGE("There should be two keys->",
                   keys.size() == 2);
    CPPUNIT_ASSERT_MESSAGE("There should be a key: " + k1,
                   std::find(keys.cbegin(), keys.cend(), k1) != keys.cend());
    CPPUNIT_ASSERT_MESSAGE("There should be a key: " + k2,
                   std::find(keys.cbegin(), keys.cend(), k2) != keys.cend());
}


void SettingsFixture::merge()
{
    Log::xtrace(TAG, "merge()");

    settings_ptr parent = make_settings();
    settings_ptr child = make_settings();

    parent->setString("k1", "one");
    child->setString("k2", "two");

    parent->merge(*child);

    CPPUNIT_ASSERT(parent->getString("k1") == "one");
    CPPUNIT_ASSERT(parent->getString("k2") == "two");
}


void SettingsFixture::anykeynotifications()
{
    Log::xtrace(TAG, "anykeynotifications()");

    settings_ptr s = make_settings();

    bool notified = false;

    Settings::OnChangedListener listener = [&notified](const string& key) -> void
    {
        Log::d(TAG, "anykeynotifications() listener(" + key + ") called");
        notified = true;
    };


    auto bid = s->addChangeListener(listener);
    Log::v(TAG, "notifications(): bid: " + std::to_string(bid));

    s->setString("any", "value");
    CPPUNIT_ASSERT_MESSAGE("Notifications for any key.",
                           notified == true);
    notified = false;

    s->removeChangeListener(bid);

    s->setString("any", "no one listening");
    CPPUNIT_ASSERT_MESSAGE("No broadcast notifications after unsubscribe",
                           notified == false);
}


void SettingsFixture::specifickeynotifications()
{
    Log::xtrace(TAG, "specifickeynotifications()");

    settings_ptr s = make_settings();
    bool notified = false;

    Settings::OnChangedListener listener = [&notified](const string& key) -> void
    {
        Log::d(TAG, "specifickeynotifications() listener(" + key + ") called");
        notified = true;
    };

    static const string unique_key = "my magical key";

    auto sid = s->addChangeListener(listener, unique_key);
    Log::v(TAG, "notifications(): sid: " + std::to_string(sid));

    s->setString("Hello", "notifications");
    CPPUNIT_ASSERT_MESSAGE("No notifications for unsubscribed keys->",
                           notified == false);

    s->setString(unique_key, "foo");

    CPPUNIT_ASSERT_MESSAGE("Notifications for specific keys->",
                           notified == true);
    notified = false;

    s->removeChangeListener(unique_key, sid);

    s->setString(unique_key, "no one listening");
    CPPUNIT_ASSERT_MESSAGE("No specific notifications after unsubscribe",
                           notified == false);
}



